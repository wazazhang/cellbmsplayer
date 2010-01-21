package com.g2d.studio.scene;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;

import com.cell.CIO;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.util.IDFactoryInteger;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.entity.SceneGroup;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class SceneManager extends JPanel implements IDynamicIDFactory<SceneNode>, ActionListener
{
	private static final long serialVersionUID = 1L;

	private static SceneManager instance;
	public static SceneManager getInstance() {
		return instance;
	}
//	------------------------------------------------------------------------------------------------------------------------------
	IDFactoryInteger<SceneNode>		id_factory			= new IDFactoryInteger<SceneNode>();
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	final private ReentrantLock		scene_lock			= new ReentrantLock();
	
	final public File				scene_dir;
	final public File				scene_list;
	final public G2DTree			g2d_tree;
	
	final private G2DWindowToolBar	tool_bar			= new G2DWindowToolBar(this);
	final private JButton			tool_scene_graph 	= new JButton(Tools.createIcon(Res.icon_scene_graph));
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	public SceneManager(Studio studio, ProgressForm progress)
	{
		super(new BorderLayout());
		progress.startReadBlock("初始化场景...");
		instance = this;
		this.scene_dir	= new File(studio.project_save_path, "scenes");
		this.scene_list	= new File(scene_dir, "scene.list");
		{
			SceneGroup tree_root = new SceneGroup("场景管理器");
			this.g2d_tree	= new G2DTree(tree_root);
			if (scene_dir.exists() && scene_list.exists()) {
				String[] all_scene = CIO.readAllLine(scene_list.getPath(), "UTF-8");
				progress.setMaximum("", all_scene.length);
				int i=0;
				for (String node_path : all_scene) {
					tree_root.loadPath(node_path);
					progress.setValue("", i++);
				}
			}
			this.g2d_tree.reload();
		}
		this.g2d_tree.addMouseListener(new TreeMouseAdapter());
		JScrollPane scroll = new JScrollPane(g2d_tree);
		this.add(scroll, BorderLayout.CENTER);
		
		tool_scene_graph.setToolTipText("打开场景图");
		tool_scene_graph.addActionListener(this);
		tool_bar.add(tool_scene_graph);
		
		this.tool_bar.setFloatable(false);
		this.add(tool_bar, BorderLayout.NORTH);
		
		g2d_tree.setDragEnabled(true);
	}

//	------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tool_bar.save) {
			saveAll();
		}
		else if (e.getSource() == tool_scene_graph) {
			SceneGraphViewer sg = new SceneGraphViewer(this);
			sg.setVisible(true);
		}
	}

	public SceneGraph createSceneGraph()
	{
		Vector<SceneNode> 	nodes 	= getAllScenes();
		ArrayList<Scene> 	scenes 	= new ArrayList<Scene>(nodes.size());
		for (SceneNode node : nodes) {
			scenes.add(node.getData());
		}
		return new SceneGraph(scenes);
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	public Vector<SceneNode> getAllScenes() {
		SceneGroup tree_root = (SceneGroup)g2d_tree.getTreeModel().getRoot();
		return G2DTree.getNodesSubClass(tree_root, SceneNode.class);
	}
	
	@Override
	public Integer createID() {
		return id_factory.createID();
	}

	public boolean containsSceneID(String id) {
		return id_factory.get(Integer.parseInt(id)) != null;
	}
	
	public SceneNode getSceneNode(String id) {
		return id_factory.get(Integer.parseInt(id));
	}
	public SceneNode getSceneNode(int id) {
		return id_factory.get(id);
	}
	
	public void addScene(SceneNode node, SceneGroup root)
	{
		if (node!=null) {
			if (id_factory.storeID(node.getIntID(), node)) {
				root.add(node);
				if (g2d_tree!=null) {
					g2d_tree.reload(root);
				}
			}
		}
	}
	
	public void addScene(File scene_file, SceneGroup root)
	{
		addScene(loadScene(scene_file), root);
	}
	
	public void removeScene(SceneNode node)
	{
		SceneNode removed = id_factory.killID(node.getIntID());
		if (removed!= null) {
			SceneGroup parent = (SceneGroup)node.getParent();
			parent.remove(node);
			if (g2d_tree!=null) {
				g2d_tree.reload(parent);
			}
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

//	String getPathString(SceneNode node)
//	{
//		String path = node.getID()+".xml";
//		TreeNode p = node.getParent();
//		while (p != null) {
//			if (p.getParent()!=null) {
//				path = p.toString() + "/" + path;
//			}
//			p = p.getParent();
//		}
//		return path;
//	}
//	
//	String[] getPath(String path)
//	{
//		String[] ret = CUtil.splitString(path, "/");
//		return ret;
//	}
	
	public void saveSceneList() 
	{
		synchronized (scene_lock) {
			StringBuffer all_scene = new StringBuffer();
			for (SceneNode node : getAllScenes()) {
				all_scene.append(SceneGroup.toPathString(node, "/") + node.getID() + ".xml" + "\n");
			}
			com.cell.io.CFile.writeText(scene_list, all_scene.toString(), "UTF-8");
		}
		System.out.println("save scene list");
	}

	public void saveAll() 
	{
		synchronized (scene_lock) {
			saveSceneList();
			for (SceneNode node : getAllScenes()) {
				saveScene(scene_dir, node);
			}
		}
	}
	
	public void saveScene(SceneNode node)
	{
		saveSceneList();
		saveScene(scene_dir, node);
	}
	
//	public void loadScene(String node_path, SceneGroup group)
//	{
//		group.buildPath(node_path, group);
//	}
	
//	-------------------------------------------------------------------------------------------------------------------------

	class TreeMouseAdapter extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e) {
			TreePath path = g2d_tree.getPathForLocation(e.getX(), e.getY());			
			if (path!=null) {
				Object click_node = path.getLastPathComponent();
				if (click_node == g2d_tree.getSelectedNode()) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (click_node instanceof SceneGroup) {
							new RootMenu((SceneGroup)click_node).show(g2d_tree, e.getX(), e.getY());
						}
						else if (click_node instanceof SceneNode) {
							new SceneMenu((SceneNode)click_node).show(g2d_tree, e.getX(), e.getY());
						}
					}
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount()==2) {
						if (click_node instanceof SceneNode) {
							((SceneNode)click_node).getSceneEditor().setVisible(true);
						}
					}
				}
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	scene root node
	
	class RootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;

		JMenuItem add_scene = new JMenuItem("添加场景");
		
		public RootMenu(SceneGroup root) {
			super(root, SceneManager.this, SceneManager.this.g2d_tree);
			add_scene.addActionListener(this);
			add(add_scene);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			super.actionPerformed(e);
			if (e.getSource() == add_scene) {
				AddSceneDialog dialog = new AddSceneDialog();
				CPJWorld world = dialog.showDialog();
				if (world!=null) {
					SceneNode node = new SceneNode(
							SceneManager.this,
							dialog.getSceneName(),
							Studio.getInstance().getCPJResourceManager().getNodeIndex(world));
					addScene(node, (SceneGroup)root);
				}
			}
		}
	}
	
	class AddSceneDialog extends CPJResourceSelectDialog<CPJWorld>
	{
		private static final long serialVersionUID = 1L;
		
		TextField text = new TextField();
		
		public AddSceneDialog() {
			super(Studio.getInstance(), CPJResourceType.WORLD);
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JLabel(" 输入场景名字 "), BorderLayout.WEST);
			panel.add(text, BorderLayout.CENTER);
			super.add(panel, BorderLayout.NORTH);
		}
		
		public String getSceneName() {
			return text.getText();
		}
		
		@Override
		protected boolean checkOK() {
			if (text.getText().length()==0) {
				JOptionPane.showMessageDialog(this, "场景名字不能为空！");
				return false;
			}
			if (getSelectedObject()==null) {
				JOptionPane.showMessageDialog(this, "还未选择场景对应的资源！");
				return false;
			}
			return true;
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	scene menu
	
	class SceneMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		SceneNode node;

		JMenuItem info			= new JMenuItem();
		JMenuItem open_scene	= new JMenuItem("打开场景");
		JMenuItem rename_scene	= new JMenuItem("重命名");
		JMenuItem delete_scene	= new JMenuItem("删除");
		
		public SceneMenu(SceneNode node)
		{
			this.node = node;
			info.setText("场景 : " + node.getName() + " (" + node.getID() + ")");
			info.setEnabled(false);
			add(info);
			add(open_scene);
			add(rename_scene);
			add(delete_scene);
			open_scene.addActionListener(this);
			rename_scene.addActionListener(this);
			delete_scene.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == open_scene) {
				node.getSceneEditor().setVisible(true);
			} 
			else if (e.getSource() == rename_scene) {
				String new_name = JOptionPane.showInputDialog(SceneManager.this, "输入场景名字", node.getData().name);
				if (new_name!=null && new_name.length()>0) {
					node.setName(new_name);
					saveScene(node);
					g2d_tree.repaint();
				}
			}
			else if (e.getSource() == delete_scene) {
				if (JOptionPane.showConfirmDialog(SceneManager.this, "确实要删除场景\"" + node.getName() + "\"！") == JOptionPane.YES_OPTION) {
					removeScene(node);
				}
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------
	

	
//	-------------------------------------------------------------------------------------------------------------------------
//	
	SceneNode loadScene(File file)
	{
		if (file.exists()) {
			Scene data = RPGObjectMap.readNode(file.getPath(), Scene.class);
			if (data!=null) {
				SceneNode node = new SceneNode(data);
				return node;
			}
		}
		return null;
	}
	
	static void saveScene(File root, SceneNode node)
	{
		Scene data = node.getData();
		File file = new File(root, node.getID()+".xml");
		RPGObjectMap.writeNode(data, file);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
}

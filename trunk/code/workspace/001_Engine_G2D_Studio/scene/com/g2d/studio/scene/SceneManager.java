package com.g2d.studio.scene;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.cell.rpg.scene.Scene;
import com.g2d.display.event.MouseListener;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DTree;
import com.thoughtworks.xstream.XStream;

public class SceneManager extends JPanel implements IDynamicIDFactory<SceneNode>
{
	private static final long serialVersionUID = 1L;
	
	final ReentrantLock				scene_lock = new ReentrantLock();
	final File						scene_dir;
	final DefaultMutableTreeNode	tree_root;
	final G2DTree					g2d_tree;
	
	public SceneManager(Studio studio, ProgressForm progress)
	{
		super(new BorderLayout());
		this.scene_dir	= new File(studio.project_save_path, "scenes");
		this.tree_root	= new DefaultMutableTreeNode("场景管理器");
		if (scene_dir.exists()) {
			for (File file : scene_dir.listFiles()) {
				if (file.getName().toLowerCase().endsWith(".xml")) {
					addScene(loadScene(file));
				}
			}
		}
		this.g2d_tree	= new G2DTree(tree_root);
		this.g2d_tree.addMouseListener(new TreeMouseAdapter());
		JScrollPane scroll = new JScrollPane(g2d_tree);
		add(scroll);
	}

	@Override
	public int createID() {
		synchronized(scene_lock) {
			int id = 0;
			Vector<SceneNode> nodes = G2DTree.getNodesSubClass(tree_root, SceneNode.class);
			for (SceneNode node : nodes) {
				if (node.getIntID() > id) {
					id = node.getIntID();
				}
			}
			return id+1;
		}
	}

	public boolean containsSceneID(String id)
	{
		synchronized(scene_lock) {
			Vector<SceneNode> nodes = G2DTree.getNodesSubClass(tree_root, SceneNode.class);
			for (SceneNode node : nodes) {
				if (node.getID().equals(id)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public void addScene(SceneNode node)
	{
		synchronized(scene_lock) {
			if (node!=null) {
				if (!containsSceneID(node.getID())) {
					tree_root.add(node);
				}
			}
		}
		if (g2d_tree!=null) {
			g2d_tree.reload();
		}
	}
	
	public void removeScene(SceneNode node)
	{
		synchronized(scene_lock) {
			if (containsSceneID(node.getID())) {
				tree_root.remove(node);
			}
		}
		if (g2d_tree!=null) {
			g2d_tree.reload();
		}
	}
	
	public void saveAll() 
	{
		synchronized (scene_lock) {
			Vector<SceneNode> nodes = G2DTree.getNodesSubClass(tree_root,
					SceneNode.class);
			for (SceneNode node : nodes) {
				saveScene(scene_dir, node);
			}
		}
	}
	
	public void saveScene(SceneNode node)
	{
		saveScene(scene_dir, node);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------

	class TreeMouseAdapter extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e) {
			TreePath path = g2d_tree.getPathForLocation(e.getX(), e.getY());			
			if (path!=null) {
				Object click_node = path.getLastPathComponent();
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (tree_root == click_node) {
						new RootMenu().show(g2d_tree, e.getX(), e.getY());
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

//	-------------------------------------------------------------------------------------------------------------------------
//	scene root node
	
	class RootMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JMenuItem add_scene = new JMenuItem("添加场景");
		
		public RootMenu() {
			add_scene.addActionListener(this);
			add(add_scene);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_scene) {
				AddSceneDialog dialog = new AddSceneDialog();
				CPJWorld world = dialog.showDialog();
				if (world!=null) {
					SceneNode node = new SceneNode(
							SceneManager.this,
							dialog.getSceneName(),
							Studio.getInstance().getCPJResourceManager().getNodeIndex(world));
					tree_root.add(node);
					g2d_tree.reload();
				}
			}
		}
	}
	
	class AddSceneDialog extends CPJResourceSelectDialog<CPJWorld>
	{
		private static final long serialVersionUID = 1L;
		
		TextField text = new TextField();
		
		public AddSceneDialog() {
			super(CPJResourceType.WORLD);
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
			info.setText(node.getID() + " : " + node.getName());
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
				String new_name = JOptionPane.showInputDialog(SceneManager.this, "输入场景名字", node.getName());
				if (new_name!=null && new_name.length()>0) {
					node.setName(new_name);
					saveScene(node);
					g2d_tree.repaint();
				}
			}
			else if (e.getSource() == delete_scene) {
				removeScene(node);
			}
		}
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
//	
	static SceneNode loadScene(File file)
	{
		if (file.exists())
		{
			try{
				StringReader reader = new StringReader(com.cell.io.File.readText(file, "UTF-8"));
				ObjectInputStream ois = new XStream().createObjectInputStream(reader);
				try{
					Scene data = (Scene)ois.readObject();
					SceneNode node = new SceneNode(data);
					return node;
				}finally{
					ois.close();
				}
			}catch(Throwable ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	static void saveScene(File root, SceneNode node)
	{
		if (!root.exists()) {
			root.mkdirs();
		}
		try{
			File file = new File(root, node.getID()+".xml");
			Scene data = node.getData();
			StringWriter writer = new StringWriter(10240);
			ObjectOutputStream oos = new XStream().createObjectOutputStream(writer);
			try{
				oos.writeObject(data);
			}finally{
				oos.close();
			}
			com.cell.io.File.writeText(file, writer.toString(), "UTF-8");
		}catch(Throwable ex) {
			ex.printStackTrace();
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
}

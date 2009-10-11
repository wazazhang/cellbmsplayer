package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import jxl.Sheet;
import jxl.Workbook;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.gfx.IImages;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSRow;
import com.cell.util.concurrent.ThreadPool;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.actor.FormActorViewer;
import com.g2d.studio.actor.FormActorViewerGroup;
import com.g2d.studio.scene.FormSceneViewer;
import com.g2d.studio.scene.FormSceneViewerGroup;
import com.g2d.studio.ui.FormUIComponentView;
import com.g2d.util.AbstractFrame;
import com.g2d.util.Util;

public class Studio extends AbstractFrame
{
	private static final long 		serialVersionUID = Version.VersionGS;
	
	private static Studio instance;
	
	public static Studio getInstance() {
		return instance;
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------------------------
	
	ThreadPool						thread_pool = new ThreadPool("studio project");
	
	final public File 				project_path;
	final public File 				project_file;
	
	public FormActorViewerGroup		group_actor;
	public FormSceneViewerGroup		group_scene;
//	public FormUIComponentViewGroup	group_ui;
	
	// 资源视图
	public JTree 					tree;
	public DefaultMutableTreeNode	tree_root 		= new DefaultMutableTreeNode();
	
	//
	final JToolBar 					state_bar	 	= new JToolBar();
	
	
	
	
	// 当前选择的 AObjectViewer
	protected	AFormDisplayObjectViewer<?>	selected_node;
	
	
	
	
	public Studio(String g2d_file) throws Throwable
	{				
		JComponent.setDefaultLocale(Locale.CHINA);
		ProgressForm progress_form = new ProgressForm();
		progress_form.setVisible(true);
		JProgressBar progress = progress_form.progress;
		
		try
		{
			project_file = new File(g2d_file);
			project_path = new File(project_file.getParent());
			
			// sysetm init
			progress.setString("init g2d system...");
			{
				instance = this;
				
				CObject.initSystem(
						new CStorage("g2d_studio"), 
						new CAppBridge(
						this.getClass().getClassLoader(), 
						this.getClass()));
				
				Config.load(Config.class, g2d_file);
				
				new SetResourceManager();
				
//				UILayoutManager.getInstance().save_path = project + "/" + Config.ROOT_UILAYOUT_PATH;
				
				UIComponent.editor_mode = true;
			}
			
			//
			this.setTitle("Cell Studio : " + project_path.getPath());
			this.setSize(300, AbstractFrame.getScreenHeight()-80);
			this.setLocation(0, 20);
			this.setLayout(new BorderLayout());
			
			// init jtree
			progress.setString("init objects...");
			{
				
				
				// resource view
				{
					// spr view
					group_actor = new FormActorViewerGroup(this);
					tree_root.add(group_actor);
					
					group_scene = new FormSceneViewerGroup(this);
					tree_root.add(group_scene);
				}
				
				
				// ui view
				{
					//group_ui = new FormUIComponentViewGroup(this);
					//tree_root.add(group_ui);
				}
				
				// stage view
				{
	//				Hashtable<String, Hashtable<String,?>> stage_root	= new Hashtable<String, Hashtable<String,?>>();
	//				tree_root.put("stage", stage_root);
				}
				
				tree = new JTree(tree_root);
				tree.setCellRenderer(new TreeRender());
				tree.setRootVisible(false);
				tree.addMouseListener(new TreeMouseListener());
				tree.addTreeSelectionListener(new TreeSelect());
				//tree_resources.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
				//this.add(tree);
				
				JScrollPane scroll = new JScrollPane(tree);
				scroll.setVisible(true);
				this.add(scroll, BorderLayout.CENTER);
			}
			
			
			
			// init state
			{
				final JPanel mem_state = new JPanel(){
					public void paint(Graphics g) {
						super.paint(g);
						Util.drawHeapStatus(g, Color.BLACK, 1, 1, getWidth()-2, getHeight()-2);
					}
				};
//				state_bar.setMinimumSize(new Dimension(200, 50));
//				state_bar.setSize(new Dimension(200, 50));
				
				thread_pool.scheduleAtFixedRate(new Runnable(){
					public void run() {
						mem_state.repaint();
					}
				}, 2000, 2000);

				JButton gc = new JButton("gc");
				gc.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						System.gc();
					}
				});
				state_bar.add(mem_state);
				state_bar.add(gc);
				
				this.add(state_bar, BorderLayout.SOUTH);
			}
			
			
			
			this.addWindowListener(new StudioWindowListener());
			
			loadAll(progress);
			
		}
		catch (Throwable e) {
			throw e;
		}
		finally{
			progress_form.setVisible(false);
			progress_form.dispose();
		}
		
		
	}

	
	/**
	 * 得到工作空间跟目录下的文件
	 * @param path
	 * @return
	 */
	public File createFile(String path)
	{
		return new File(project_path.getPath() + "/" + path);
	}
	
	
	public void setSelectedNode(AFormDisplayObjectViewer<?> node)
	{
		selected_node = node;
	}
	
	public AFormDisplayObjectViewer<?> getSelectedNode()
	{
		return selected_node;
	}
	
	public FormActorViewer getSelectedNodeAsActor()
	{
		if (selected_node instanceof FormActorViewer) {
			return (FormActorViewer)selected_node;
		}
		return null;
	}
	
	public FormSceneViewer getSelectedNodeAsScene()
	{
		if (selected_node instanceof FormSceneViewer) {
			return (FormSceneViewer)selected_node;
		}
		return null;
	}

	public FormUIComponentView getSelectedNodeAsUI()
	{
		if (selected_node instanceof FormUIComponentView) {
			return (FormUIComponentView)selected_node;
		}
		return null;
	}
	
	public FormActorViewer getActor(String parent, String actor) {
		try {
			return group_actor.getNode(parent, actor);
		} catch (Exception e) {
			return null;
		}
	}
	public Collection<FormActorViewer> getActors() {
		ArrayList<FormActorViewer> ret = new ArrayList<FormActorViewer>();
		for (ATreeNodeSet<FormActorViewer> set : group_actor.childs) {
			for (ATreeNodeLeaf<FormActorViewer> actor : set.childs){
				ret.add(actor.user_data);
			}
		}
		return ret;
	}
	
//-----------------------------------------------------------------------------------------------------------
// scene operator
	
	public FormSceneViewer getScene(String scene_name) {
		try {
			String[] ps = CUtil.splitString(scene_name, "/");
			return group_scene.getNode(ps[0], ps[1]);
		} catch (Exception e) {
			return null;
		}
	}
	
	public FormSceneViewer getScene(String parent, String scene) {
		try {
			return group_scene.getNode(parent, scene);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Collection<FormSceneViewer> getScenes() {
		ArrayList<FormSceneViewer> ret = new ArrayList<FormSceneViewer>();
		for (ATreeNodeSet<FormSceneViewer> set : group_scene.childs) {
			for (ATreeNodeLeaf<FormSceneViewer> scene : set.childs){
				ret.add(scene.user_data);
			}
		}
		return ret;
	}
	
	
	public Collection<XLSFile> getXLSFiles()
	{
		ArrayList<XLSFile> ret = new ArrayList<XLSFile>();
		
		File root_xls = createFile(Config.ROOT_XLS);
		
		for (File xls : root_xls.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.lastIndexOf(".xls")>0;
			}
		})) {
			ret.add(new XLSFile(xls));
		}
		
		return ret;
	}

	/**
	 * 返回以 xls 的 row[c0][c1] 的集合
	 * @param xls_file
	 * @return
	 */
	public Collection<XLSRow> getXLSPrimaryRows(XLSFile xls_file)
	{
		ArrayList<XLSRow> ret = new ArrayList<XLSRow>();
		
		InputStream is = CIO.loadStream(createFile(Config.ROOT_XLS + "/" + xls_file.xls_file).getPath());
		
		try
		{
			Workbook	rwb				= Workbook.getWorkbook(is);
		    Sheet		rs				= rwb.getSheet(0);
		    int			row_count		= rs.getRows();
		    
			for (int r = 2; r < row_count; r++)
			{
				try
				{
					String primary_key = rs.getCell(1, r).getContents().trim();
					if (primary_key.length()<=0) {
						 System.out.println("\ttable eof at row : " + r);
						break;
					}
					
					String c0 = rs.getCell(0, r).getContents().trim();
					String c1 = rs.getCell(1, r).getContents().trim();
					ret.add(new XLSRow(xls_file, c1, c0));
				}
				catch (Exception e) {
					System.err.println("read error at row " + r);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	
	public FormUIComponentView getUI(String parent, String ui) {
		try {
//			return group_ui.getNode(parent, ui);
		} catch (Exception e) {}
		return null;
	}


	public void saveAll() 
	{
		group_actor.saveAll();
		group_scene.saveAll();
//		group_ui.saveAll();
	}
	
	public void loadAll(JProgressBar progress)
	{
		progress.setString("load actor objects...");
		group_actor.loadAll();

		progress.setString("load scene objects...");
		group_scene.loadAll();

//		progress.setString("load ui objects...");
//		group_ui.loadAll();
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	ui action
	
	class StudioWindowListener implements WindowListener
	{
		public void windowActivated(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			
			int result = JOptionPane.showConfirmDialog(Studio.this, "保存并退出?");
			if (result == JOptionPane.OK_OPTION)
			{
				saveAll();
				
				System.out.println("save and exit");
				System.exit(1);
			}
			else if (result == JOptionPane.NO_OPTION)
			{
				System.out.println("exit");
				System.exit(1);
			}
			else
			{
				new Thread(){
					public void run() {
						Studio.this.setVisible(true);
					}
				}.start();
			}


			
		}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}
	
	class TreeRender extends DefaultTreeCellRenderer
	{
		ImageIcon icon = Tools.createIcon(Tools.readImage("/com/g2d/studio/icon.bmp"));
		
		@Override
		public Component getTreeCellRendererComponent(
				JTree tree, 
				Object value,
				boolean sel, 
				boolean expanded, 
				boolean leaf,
				int row,
				boolean hasFocus) 
		{
			Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,row, hasFocus);
			
			if (value instanceof ATreeNodeLeaf){
				ATreeNodeLeaf aleaf = ((ATreeNodeLeaf)value);
				if (aleaf.user_data instanceof FormActorViewer) {
					FormActorViewer form = (FormActorViewer)(aleaf.user_data);
					ImageIcon aicon = form.getSnapshot(false);
					if (aicon == null) {
						setIcon(icon);
					}else{
						setIcon(aicon);
					}
				}
			}
			//System.out.println("getTreeCellRendererComponent");
			return comp;
		}
		
		
		
	}
	
	
	class TreeSelect implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent e) {
			
			MutableTreeNode node = (MutableTreeNode)tree.getLastSelectedPathComponent();

			if (node instanceof ATreeNodeLeaf){
				ATreeNodeLeaf aleaf = ((ATreeNodeLeaf)node);
				if (aleaf.user_data instanceof FormActorViewer) {
					FormActorViewer form = (FormActorViewer)(aleaf.user_data);
					form.getSnapshot(true);
				}
			}
			
//			TreePath selPath = e.getPath();
//			TreeNode node = getTreeNode(selPath.getPath());
//			System.out.println(node);
			
		}
	}
	
	class TreeMouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			selected_node = null;
			
			int selRow = tree.getRowForLocation(e.getX(), e.getY());
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			
			if (selRow != -1) 
			{
				Object obj = selPath.getLastPathComponent();
//				System.out.println(node);
				if (obj instanceof TreeNode) 
				{
					TreeNode node = (TreeNode)obj;
					
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (e.getClickCount() == 1) {
							node.onSelected(tree, e);
						}
						else if (e.getClickCount() == 2) {
							node.onDoubleClicked(tree, e);
						}
					}
					
					if (e.getButton() == MouseEvent.BUTTON3){
						node.onRightClicked(tree, e);
					}
					
					if (e.getButton() == MouseEvent.BUTTON1){
						if (node instanceof ATreeNodeLeaf){
							selected_node = ((ATreeNodeLeaf) node).getViewer();;
							 if (e.getClickCount() == 2) {
								if (selected_node != null) {
									selected_node.setVisible(true);
								}
							}
						}
					}
					
				}
				
				
			}
		}
	}
	
	
	
	class ProgressForm extends AbstractFrame
	{
		final public JProgressBar progress = new JProgressBar();
		
		public ProgressForm()
		{
			progress.setStringPainted(true);
			progress.setIndeterminate(true);

			this.setTitle("loading...");
			this.setSize(200, 50);
			this.add(progress);
			this.setCenter();
		}
		
		
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	resource manager
	
	static public class SetResource extends CellSetResource
	{
		public SetResource(String setPath, String name)  throws Exception{
			super(setPath, name, false);
		}
		
		protected IImages getLocalImage(IMG img) throws IOException {
			StreamTiles tiles = new StreamTiles(img);
			return tiles;
		}
		
		public void initAllStreamImages()
		{
			Enumeration<IMG> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				IMG ts = imgs.nextElement();
				IImages images = getImages(ts);
				if (images instanceof StreamTiles) {
					((StreamTiles) images).run();
				}
			}
		}
		
		public void destoryAllStreamImages(){
			if (ResourceManager!=null) {
				for (Object obj : ResourceManager.values()) {
					if (obj instanceof StreamTiles){
						StreamTiles stiles = (StreamTiles)obj;
						stiles.unloadAllImages();
					}
				}
			}
		}
	}
	
	public class SetResourceManager extends CellSetResourceManager
	{
		public SetResourceManager() {
			instance = this;
		}
		
		@Override
		public SetResource createSet(String setPath) throws Exception {
			return new SetResource(project_path.getPath() + "/" + setPath, setPath);
		}
		
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	main entry

	
	static public void main(String[] args)
	{
		try
		{
			Studio studio = null;
			
			if (args==null || args.length==0 || !new File(args[0]).exists() || !new File(args[0]).isFile()) 
			{
				//
				java.awt.FileDialog fd = new FileDialog(new JFrame(), "Open workspace", FileDialog.LOAD);
				fd.show();
				String file = fd.getDirectory() + fd.getFile();
				
				System.out.println("Chose to open this file: " + file);
				
				studio = new Studio(file);
			}
			else
			{
				System.out.println("Open: " + args[0]);
				studio = new Studio(args[0]);
			}
			
			studio.setVisible(true);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			
			String message = "Open workspace error ! \n" + e.getClass().getName() + " : " + e.getMessage() + "\n";
			for (StackTraceElement stack : e.getStackTrace()) {
				message += "\t"+stack.toString()+"\n";
			}
			JOptionPane.showMessageDialog(null, message);
			
			System.exit(1);
		}
	}
	
	
}


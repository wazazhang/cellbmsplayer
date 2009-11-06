package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.cell.CObject;
import com.cell.gfx.IImages;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSRow;
import com.cell.util.concurrent.ThreadPool;

import com.g2d.Tools;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.studio.cpj.CPJResourceManager;
import com.g2d.studio.gameedit.ObjectManager;
import com.g2d.studio.res.Res;
import com.g2d.studio.sound.SoundManager;
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
	final public File				project_save_path;
	final public File				project_save_path_scene;
	final public File				project_save_path_unit;
	final public File				project_save_path_avatar;
	final public File				project_save_path_effect;

	CPJResourceManager				frame_cpj_resource_manager;
	ObjectManager					frame_object_editor;
	SoundManager					frame_sound_manager;


	private Studio(String g2d_file) throws Throwable
	{							
		CObject.initSystem(
			new CStorage("g2d_studio"), 
			new CAppBridge(
			this.getClass().getClassLoader(), 
			this.getClass()));
		
		Config.load(Config.class, g2d_file);
		
		JComponent.setDefaultLocale(Locale.CHINA);
		
		UIManager.setLookAndFeel(new javax.swing.plaf.synth.SynthLookAndFeel());
		
		instance 			= this;
		project_file 		= new File(g2d_file);
		project_path 		= new File(project_file.getParent());
		
		project_save_path			= new File(project_file.getPath()+".save");
		project_save_path_scene		= new File(project_save_path.getPath()+File.separatorChar+"scene");
		project_save_path_unit		= new File(project_save_path.getPath()+File.separatorChar+"unit");
		project_save_path_avatar	= new File(project_save_path.getPath()+File.separatorChar+"avatar");
		project_save_path_effect	= new File(project_save_path.getPath()+File.separatorChar+"effect");
		project_save_path.mkdirs();
		project_save_path_scene.mkdirs();
		project_save_path_unit.mkdirs();
		project_save_path_avatar.mkdirs();
		project_save_path_effect.mkdirs();
		
		// sysetm init
		ProgressForm progress_form = new ProgressForm();
		progress_form.setVisible(true);
		JProgressBar progress = progress_form.progress;
		progress_form.setIconImage(Res.icon_edit);
		progress.setString("init g2d system...");


		
		try
		{
			//
			this.setTitle(Config.TITLE + " : " + project_path.getPath());
			this.setIconImage(Res.icon_edit);
			this.setSize(300, AbstractFrame.getScreenHeight()-60);
			this.setLocation(0, 0);
			this.setLayout(new BorderLayout());

			new SetResourceManager();

			// init jtree
			progress.setString("init objects...");
		
			initToolBar();
			
			initStateBar();
			
			this.addWindowListener(new StudioWindowListener());
			
		}
		catch (Throwable e) {
			throw e;
		} finally{
			progress_form.setVisible(false);
			progress_form.dispose();
		}
	}

	// init tool bar
	private void initToolBar()
	{
		JToolBar tool_bar = new JToolBar();
		
		// res manager
		{
			frame_cpj_resource_manager = new CPJResourceManager();
			JButton btn = new JButton();
			btn.setToolTipText(frame_cpj_resource_manager.getTitle());
			btn.setIcon(Tools.createIcon(Res.icons_bar[7]));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_cpj_resource_manager.setVisible(true);
				}
			});
			tool_bar.add(btn);
		}
		// unit manager
		{
			frame_object_editor = new ObjectManager();
			JButton btn = new JButton();
			btn.setToolTipText(frame_object_editor.getTitle());
			btn.setIcon(Tools.createIcon(Res.icons_bar[4]));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_object_editor.setVisible(true);
				}
			});
			tool_bar.add(btn);
		}
		// sound manager
		{
			frame_sound_manager = new SoundManager();
			JButton btn = new JButton();
			btn.setToolTipText(frame_sound_manager.getTitle());
			btn.setIcon(Tools.createIcon(Res.icons_bar[3]));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_sound_manager.setVisible(true);
				}
			});
			tool_bar.add(btn);
		}
		
		this.add(tool_bar, BorderLayout.NORTH);
	}
	
	// init state bar
	private void initStateBar()
	{
		JToolBar state_bar = new JToolBar();
		
		// heap state
		{
			final JPanel mem_state = new JPanel() {
				public void paint(Graphics g) {
					super.paint(g);
					Util.drawHeapStatus(g, Color.BLACK, 1, 1, getWidth() - 2,
							getHeight() - 2);
				}
			};
			thread_pool.scheduleAtFixedRate(new Runnable() {
				public void run() {
					mem_state.repaint();
				}
			}, 2000, 2000);
			state_bar.add(mem_state);
		}

		// gc
		{
			JButton gc = new JButton("gc");
			gc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.gc();
				}
			});
			state_bar.add(gc);
		}

		this.add(state_bar, BorderLayout.SOUTH);

	}
	
	/**
	 * 得到工作空间跟目录下的文件
	 * @param path
	 * @return
	 */
	public File getFile(String path)
	{
		return new File(project_path.getPath() + "/" + path);
	}

//-----------------------------------------------------------------------------------------------------------

	/**
	 * 返回以 xls 的 row[c0][c1] 的集合
	 * @param xls_file
	 * @return
	 */
	public<T extends XLSRow> Collection<T> getXLSPrimaryRows(XLSFile xls_file, Class<T> cls)
	{
		File path = getFile(Config.ROOT_XLS + "/" + xls_file.xls_file);
		return XLSRow.getXLSRows(path, new AtomicReference<XLSFile>(xls_file), cls);
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	public void saveAll() 
	{

	}
	
	public void loadAll(JProgressBar progress)
	{
	
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	ui action
	
	class StudioWindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e) {
			int result = JOptionPane.showConfirmDialog(Studio.this, "保存并退出?");
			if (result == JOptionPane.OK_OPTION) {
				saveAll();
				System.out.println("save and exit");
				System.exit(1);
			} else if (result == JOptionPane.NO_OPTION) {
				System.out.println("exit");
				System.exit(1);
			} else {
				new Thread() {
					public void run() {
						Studio.this.setVisible(true);
					}
				}.start();
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
	
	static public class SetResource extends Resource
	{
		boolean is_load_resource = false;
		
		public SetResource(String setPath, String name)  throws Exception{
			super(setPath, name, false);
		}
		
		public SetResource(File file, String name) throws Exception {
			super(file, name, false);
		}
		
		@Override
		protected IImages getLocalImage(ImagesSet img) throws IOException {
			StreamTypeTiles tiles = new StreamTypeTiles(img);
			return tiles;
		}
		
		synchronized public boolean isLoadImages()
		{
			return is_load_resource;
		}
		
		synchronized public void initAllStreamImages()
		{
			is_load_resource = true;
			Enumeration<ImagesSet> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				ImagesSet ts = imgs.nextElement();
				IImages images = getImages(ts);
				if (images instanceof StreamTiles) {
					((StreamTiles) images).run();
				}
			}
		}
		
		synchronized public void destoryAllStreamImages(){
			is_load_resource = false;
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


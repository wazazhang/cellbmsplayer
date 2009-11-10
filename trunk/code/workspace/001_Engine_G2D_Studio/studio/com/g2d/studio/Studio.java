package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import com.cell.sound.mute_impl.NullSound;
import com.cell.sound.mute_impl.NullSoundManager;
import com.cell.sound.openal_impl.JALSoundManager;
import com.cell.util.concurrent.ThreadPool;

import com.g2d.Tools;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.CPJResourceManager;
import com.g2d.studio.gameedit.ObjectManager;
import com.g2d.studio.icon.IconManager;
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
	
	final public ThreadPool			thread_pool = new ThreadPool("studio project");
	
	com.cell.sound.SoundManager		sound_system;
	
	final public File 				project_path;
	final public File 				project_file;
	final public File				project_save_path;

	final public File 				root_xls_path;
	final public File 				root_sound_path;
	
	private CPJResourceManager		frame_cpj_resource_manager;
	private ObjectManager			frame_object_manager;
	private SoundManager			frame_sound_manager;
	private IconManager				frame_icon_manager;

	private Studio(String g2d_file) throws Throwable
	{							
		CObject.initSystem(
			new CStorage("g2d_studio"), 
			new CAppBridge(
			this.getClass().getClassLoader(), 
			this.getClass()));
		
		Config.load(Config.class, g2d_file);
		
		try{
			JComponent.setDefaultLocale(Locale.CHINA);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception err){
			err.printStackTrace();
		}
		try{
			sound_system = JALSoundManager.getInstance();
		}catch(Throwable tr) {			
			tr.printStackTrace();
			sound_system = new NullSoundManager();
		}			
		com.cell.sound.SoundManager.setSoundManager(sound_system);
		
		instance 			= this;
		project_file 		= new File(g2d_file);
		project_path 		= new File(project_file.getParent());
		
		project_save_path	= new File(project_file.getPath()+".save");
		project_save_path.mkdirs();
		
		
		root_xls_path 		= Studio.getInstance().getFile(Config.XLS_ROOT);
		root_sound_path		= Studio.getInstance().getFile(Config.SOUND_ROOT);
		
		// sysetm init
		ProgressForm progress_form = new ProgressForm();
		progress_form.setVisible(true);
		progress_form.setIconImage(Res.icon_edit);

		try
		{
			//
			this.setTitle(Config.TITLE + " : " + project_path.getPath());
			this.setIconImage(Res.icon_edit);
			this.setSize(300, AbstractFrame.getScreenHeight()-60);
			this.setLocation(0, 0);
			this.setLayout(new BorderLayout());

			new SetResourceManager();

			initToolBar(progress_form);
			
			initStateBar(progress_form);
			
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
	private void initToolBar(ProgressForm progress)
	{
		JToolBar tool_bar = new JToolBar();
	
		// icon manager
		{
			progress.startReadBlock("初始化图标...");
			frame_icon_manager = new IconManager(progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_icon_manager.getTitle());
			btn.setIcon(Tools.createIcon(Res.icon_hd));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_icon_manager.setVisible(true);
				}
			});
			tool_bar.add(btn);
		}
		// sound manager
		{
			progress.startReadBlock("初始化声音...");
			frame_sound_manager = new SoundManager(progress);
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
		// res manager
		{
			progress.startReadBlock("初始化资源...");
			frame_cpj_resource_manager = new CPJResourceManager(progress);
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
			progress.startReadBlock("初始化物体...");
			frame_object_manager = new ObjectManager(progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_object_manager.getTitle());
			btn.setIcon(Tools.createIcon(Res.icons_bar[4]));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_object_manager.setVisible(true);
				}
			});
			tool_bar.add(btn);
		}

		this.add(tool_bar, BorderLayout.NORTH);
	}
	
	// init state bar
	private void initStateBar(ProgressForm progress)
	{
		JToolBar state_bar = new JToolBar();
		
		progress.startReadBlock("初始化工具...");
		
		// heap state
		{
			final JLabel mem_state = new JLabel("  "+Util.getHeapString()+"  ") {
				private static final long serialVersionUID = 1L;
				public void paint(Graphics g) {
					super.paint(g);
					super.setText("  "+Util.getHeapString()+"  ");
					Util.drawHeapStatus(g, Color.BLACK, 1, 1, getWidth() - 2, getHeight() - 2, false);
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
	public com.cell.sound.SoundManager getSoundSystem() {
		return sound_system;
	}		
	

	public CPJResourceManager getCPJResourceManager() {
		return frame_cpj_resource_manager;
	}

	public ObjectManager getObjectManager() {
		return frame_object_manager;
	}

	public SoundManager getSoundManager() {
		return frame_sound_manager;
	}
	
	public IconManager getIconManager() {
		return frame_icon_manager;
	}
	
//	-----------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------
	
	public void saveAll() 
	{
		try{
			frame_object_manager.saveAll();
		}catch(Throwable ex) {
			ex.printStackTrace();
		}
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
	
	public class ProgressForm extends AbstractFrame
	{
		private static final long serialVersionUID = 1L;
		private JProgressBar progress = new JProgressBar();
		
		private int total = 10000;
		
		public ProgressForm()
		{
			this.setTitle("初始化中...");
			this.setSize(200, 50);
			this.add(progress);
			this.setCenter();
			progress.setStringPainted(true);
			progress.setMaximum(total);
		}
		
		public void startReadBlock(String title) {
			progress.setString(title);
			progress.setValue(progress.getValue()+2500);
		}
		
		public void increment() {
			progress.setValue(progress.getValue()+1);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	resource manager
	
	public class SetResourceManager extends CellSetResourceManager
	{
		public SetResourceManager() {
			instance = this;
		}
		@Override
		public StudioResource createSet(String setPath) throws Exception {
			return new StudioResource(project_path.getPath() + "/" + setPath, setPath);
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


package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.cell.CUtil;
import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.TItem;
import com.g2d.studio.gameedit.template.TNpc;
import com.g2d.studio.gameedit.template.TSkill;
import com.g2d.studio.gameedit.template.TemplateNode;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thoughtworks.xstream.XStream;

public class ObjectManager extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	File zip_file;
	
	G2DWindowToolBar toolbar = new G2DWindowToolBar(this);
	
	final ObjectTreeView<TNpc> 				tree_units_view;
	final ObjectTreeView<TItem> 			tree_items_view;
	final ObjectTreeView<TSkill>			tree_skills_view;
	final DynamicObjectTreeView<DAvatar>	tree_avatars_view;
	
	public ObjectManager(ProgressForm progress) 
	{
		super.setSize(800, Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setTitle("物体编辑器");
		super.setIconImage(Res.icon_edit);

		this.add(toolbar, BorderLayout.NORTH);
		
		zip_file = new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"objects.zip");
		
		JTabbedPane table = new JTabbedPane();
		// TNPC
		{
			ArrayList<TNpc> npcs = TemplateNode.listXLSRows(TNpc.class);
			tree_units_view = new ObjectTreeView<TNpc>("NPC模板", TNpc.class, npcs);
			table.addTab("NPC", Tools.createIcon(Res.icon_res_2), tree_units_view);
		}
		// TItem
		{
			ArrayList<TItem> items = TemplateNode.listXLSRows(TItem.class);
			tree_items_view = new ObjectTreeView<TItem>("道具模板", TItem.class, items);
			table.addTab("物品", Tools.createIcon(Res.icon_res_4), tree_items_view);
		}
		// TSkill
		{
			ArrayList<TSkill> skills = TemplateNode.listXLSRows(TSkill.class);
			tree_skills_view = new ObjectTreeView<TSkill>("技能模板", TSkill.class, skills);
			table.addTab("技能", Tools.createIcon(Res.icon_res_3), tree_skills_view);
		}

		// DAvatar
		{
			tree_avatars_view = new DynamicObjectTreeView<DAvatar>("AVATAR", DAvatar.class);
			table.addTab("AVATAR", Tools.createIcon(Res.icon_res_4), tree_avatars_view);
			tree_avatars_view.g2d_tree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (tree_avatars_view.tree_root == tree_avatars_view.g2d_tree.getSelectedNode()) {
							MenuAvatarRoot menu = new MenuAvatarRoot();
							menu.show(tree_avatars_view.g2d_tree, e.getX(), e.getY());
						}
					}
				}
			});
		}

		try {
			loadAll();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
		this.add(table, BorderLayout.CENTER);
	}
	
	public <T extends ObjectNode> T getObject(Class<T> type, String id)
	{
		MutableTreeNode root = null;
		if (type.equals(TNpc.class)) {
			root = tree_units_view.tree_root;
		} else if (type.equals(TItem.class)) {
			root = tree_items_view.tree_root;
		} else if (type.equals(TSkill.class)) {
			root = tree_skills_view.tree_root;
		} else {
			return null;
		}
		Vector<T> list = G2DTree.getNodesSubClass(root, type);
		for (T t : list) {
			if (t.getID().equals(id)) {
				return t;
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public void loadAll() throws Throwable
	{
		if (zip_file.exists()) 
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(com.cell.io.File.readData(zip_file));
			ZipInputStream zip_in = new ZipInputStream(bais);

			try{
				ZipEntry entry =  null;
				while ((entry = zip_in.getNextEntry()) != null) {
					try{
						String type_name	= ObjectNode.getTypeName(entry);
						String xls_id		= ObjectNode.getID(entry);
						if (type_name.equals("npc")) {
							getObject(TNpc.class, xls_id).load(zip_in, entry);
						}
						else if (type_name.equals("item")) {
							getObject(TItem.class, xls_id).load(zip_in, entry);
						}
						else if (type_name.equals("skill")) {
							getObject(TSkill.class, xls_id).load(zip_in, entry);
						}
						else if (type_name.equals("avatar")) {
							tree_avatars_view.addNode(new DAvatar(zip_in, entry));
						}
					}catch(Exception err) {
						err.printStackTrace();
					}
				}
			}finally{
				zip_in.close();
			}
		}
		
		System.out.println(getClass().getSimpleName() + " : load all");
	}
	
	@SuppressWarnings("unchecked")
	public void saveAll() throws Throwable
	{
		if (!zip_file.exists()) {
			zip_file.getParentFile().mkdirs();
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try
		{
			if (tree_units_view.tree_root.getChildCount() > 0) {
				Enumeration<TNpc> npcs = tree_units_view.tree_root.children();
				while (npcs.hasMoreElements()) {
					npcs.nextElement().save(zip_out, "npc");
				}
			}
			if (tree_items_view.tree_root.getChildCount() > 0) {
				Enumeration<TItem> items = tree_items_view.tree_root.children();
				while (items.hasMoreElements()) {
					items.nextElement().save(zip_out, "item");
				}
			}
			if (tree_skills_view.tree_root.getChildCount() > 0) {
				Enumeration<TSkill> skills = tree_skills_view.tree_root.children();
				while (skills.hasMoreElements()) {
					skills.nextElement().save(zip_out, "skill");
				}
			}
			if (tree_avatars_view.tree_root.getChildCount() > 0) {
				Enumeration<DAvatar> avatars = tree_avatars_view.tree_root.children();
				while (avatars.hasMoreElements()) {
					avatars.nextElement().save(zip_out, "avatar");
				}
			}
		}finally{
			zip_out.close();
		}
		com.cell.io.File.wirteData(zip_file, baos.toByteArray());

		System.out.println(getClass().getSimpleName() + " : save all");
	}
	


	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toolbar.save) {
			try {
				saveAll();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	class MenuAvatarRoot extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JMenuItem add_avatar = new JMenuItem("添加AVATAR");
		
		public MenuAvatarRoot() {
			add_avatar.addActionListener(this);
			add(add_avatar);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_avatar) {
				String ret = JOptionPane.showInputDialog(ObjectManager.this, "输入AVATAR名字");
				DAvatar avatar = new DAvatar(tree_avatars_view, ret);
				tree_avatars_view.addNode(avatar);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

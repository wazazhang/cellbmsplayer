package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextField;
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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.cell.CUtil;
import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.CPJResourceList;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.TItem;
import com.g2d.studio.gameedit.template.TUnit;
import com.g2d.studio.gameedit.template.TSkill;
import com.g2d.studio.gameedit.template.TemplateNode;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DListSelectDialog;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thoughtworks.xstream.XStream;

public class ObjectManager extends ManagerForm implements ActionListener
{
	private static final long serialVersionUID = 1L;

	File zip_file;
	
	G2DWindowToolBar toolbar = new G2DWindowToolBar(this);
	
	final ObjectTreeView<TUnit> 			tree_units_view;
	final ObjectTreeView<TItem> 			tree_items_view;
	final ObjectTreeView<TSkill>			tree_skills_view;
	final DynamicObjectTreeView<DAvatar>	tree_avatars_view;
	
	public ObjectManager(ProgressForm progress) 
	{
		super(progress, "物体编辑器");
		
		this.add(toolbar, BorderLayout.NORTH);
		
		zip_file = new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"objects.zip");
		
		JTabbedPane table = new JTabbedPane();
		// TNPC
		{
			
			tree_units_view = new ObjectTreeView<TUnit>("单位模板", TUnit.class, null);
			table.addTab("单位", Tools.createIcon(Res.icon_res_2), tree_units_view);
			table.addChangeListener(tree_units_view);
		}
		// TItem
		{
			tree_items_view = new ObjectTreeView<TItem>("道具模板", TItem.class, null);
			table.addTab("物品", Tools.createIcon(Res.icon_res_4), tree_items_view);
			table.addChangeListener(tree_items_view);
		}
		// TSkill
		{
			tree_skills_view = new ObjectTreeView<TSkill>("技能模板", TSkill.class, null);
			table.addTab("技能", Tools.createIcon(Res.icon_res_3), tree_skills_view);
			table.addChangeListener(tree_skills_view);
		}

		// DAvatar
		{
			tree_avatars_view = new DynamicObjectTreeView<DAvatar>("AVATAR", DAvatar.class);
			table.addTab("AVATAR", Tools.createIcon(Res.icon_res_4), tree_avatars_view);
			tree_avatars_view.g2d_tree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (tree_avatars_view.tree_root == tree_avatars_view.g2d_tree.getSelectedNode()) {
							AvatarRootMenu menu = new AvatarRootMenu();
							menu.show(tree_avatars_view.g2d_tree, e.getX(), e.getY());
						}
					}
				}
			});
			table.addChangeListener(tree_avatars_view);
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
		if (type.equals(TUnit.class)) {
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
//			ByteArrayInputStream bais = new ByteArrayInputStream(com.cell.io.File.readData(zip_file));
//			ZipInputStream zip_in = new ZipInputStream(bais);
//			
//			try{
//				ZipEntry entry =  null;
//				while ((entry = zip_in.getNextEntry()) != null) {
//					try{
//						String type_name	= ObjectNode.getTypeName(entry);
//						String xls_id		= ObjectNode.getID(entry);
//						if (type_name.equals("npc")) {
//							getObject(TNpc.class, xls_id).load(zip_in, entry);
//						}
//						else if (type_name.equals("item")) {
//							getObject(TItem.class, xls_id).load(zip_in, entry);
//						}
//						else if (type_name.equals("skill")) {
//							getObject(TSkill.class, xls_id).load(zip_in, entry);
//						}
//						else if (type_name.equals("avatar")) {
//							tree_avatars_view.addNode(new DAvatar(zip_in, entry));
//						}
//					}catch(Exception err) {
//						err.printStackTrace();
//					}
//				}
//			}finally{
//				zip_in.close();
//			}
			
			ObjectStreamFilter.loadAll(zip_file, ObjectNode.class);
			
		}
		
		System.out.println(getClass().getSimpleName() + " : load all");
	}
	
	@SuppressWarnings("unchecked")
	public void saveAll() throws Throwable
	{
		if (!zip_file.exists()) {
			zip_file.getParentFile().mkdirs();
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024*1024);
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		zip_out.setLevel(ZipOutputStream.STORED);
		try
		{
			if (tree_units_view.tree_root.getChildCount() > 0) {
				Enumeration<TUnit> npcs = tree_units_view.tree_root.children();
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
	
	class AvatarRootMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JMenuItem add_avatar = new JMenuItem("添加AVATAR");
		
		public AvatarRootMenu() {
			add_avatar.addActionListener(this);
			add(add_avatar);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_avatar) {
				AvatarAddDialog dialog = new AvatarAddDialog();
				CPJSprite spr = dialog.showDialog();
				if (spr!=null) {
					DAvatar avatar = new DAvatar(
							tree_avatars_view, 
							dialog.getAvatarName(),
							Studio.getInstance().getCPJResourceManager().getNodeIndex(spr));
					tree_avatars_view.addNode(avatar);
				}
			}
		}
	}
	
	class AvatarAddDialog extends CPJResourceSelectDialog<CPJSprite>
	{
		private static final long serialVersionUID = 1L;
		
		TextField text = new TextField();
		
		public AvatarAddDialog() {
			super(CPJResourceType.ACTOR);
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JLabel(" 输入AVATAR名字 "), BorderLayout.WEST);
			panel.add(text, BorderLayout.CENTER);
			super.add(panel, BorderLayout.NORTH);
		}
		
		public String getAvatarName() {
			return text.getText();
		}
		
		@Override
		protected boolean checkOK() {
			if (text.getText().length()==0) {
				JOptionPane.showMessageDialog(this, "AVATAR名字不能为空！");
				return false;
			}
			if (getSelectedObject()==null) {
				JOptionPane.showMessageDialog(this, "还未选择AVATAR主角身体！");
				return false;
			}
			return true;
		}
	}


//	-------------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

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
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.cell.CUtil;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.quest.QuestItem;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSColumns;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.IDFactoryInteger;
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
import com.g2d.studio.gameedit.dynamic.DEffect;
//import com.g2d.studio.gameedit.dynamic.DQuestItem;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
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

	G2DWindowToolBar 	toolbar	= new G2DWindowToolBar(this);
	JTabbedPane 		table	= new JTabbedPane();

	final public File objects_dir;
	
	
	final ObjectTreeViewTemplate<XLSUnit, TUnit> 		tree_units_view;
	final ObjectTreeViewTemplate<XLSItem, TItem> 		tree_items_view;
	final ObjectTreeViewTemplate<XLSSkill, TSkill>		tree_skills_view;
	final ObjectTreeViewDynamic<DAvatar, TAvatar>		tree_avatars_view;
	final ObjectTreeViewDynamic<DEffect, TEffect>		tree_effects_view;
//	final ObjectTreeViewDynamic<DQuestItem, QuestItem>	tree_quest_items_view;

	
	public ObjectManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "物体编辑器");
		
		this.add(toolbar, BorderLayout.NORTH);
		
		
		objects_dir = new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"objects");
		
		// ------------ xls template ------------ //
		{	
			// XLSUnit
			tree_units_view = new ObjectTreeViewTemplate<XLSUnit, TUnit>("单位模板", XLSUnit.class, TUnit.class, 
					new File(objects_dir, "tunit.obj/tunit.list"), studio.xls_tunit, progress);
			table.addTab("单位", Tools.createIcon(Res.icon_res_2), tree_units_view);
			table.addChangeListener(tree_units_view);
		}{	
			// XLSItem
			tree_items_view = new ObjectTreeViewTemplate<XLSItem, TItem>("道具模板", XLSItem.class, TItem.class, 
					new File(objects_dir, "titem.obj/titem.list"), studio.xls_titem, progress);
			table.addTab("物品", Tools.createIcon(Res.icon_res_4), tree_items_view);
			table.addChangeListener(tree_items_view);
		}{	
			// XLSSkill
			tree_skills_view = new ObjectTreeViewTemplate<XLSSkill, TSkill>("技能模板", XLSSkill.class, TSkill.class, 
					new File(objects_dir, "tskill.obj/tskill.list"), studio.xls_tskill, progress);
			table.addTab("技能", Tools.createIcon(Res.icon_res_3), tree_skills_view);
			table.addChangeListener(tree_skills_view);
		}{
		// ------------ dynamic ------------ //
		}{	
			// DAvatar
			tree_avatars_view = new AvatarTreeView("AVATAR", new File(objects_dir, "tavatar.obj/tavatar.list"));
			table.addTab("AVATAR", Tools.createIcon(Res.icon_res_4), tree_avatars_view);
			table.addChangeListener(tree_avatars_view);
		}{	
			// DAvatar
			tree_effects_view = new EffectTreeView("特效", new File(objects_dir, "teffect.obj/teffect.list"));
			table.addTab("魔法效果/特效", Tools.createIcon(Res.icon_res_3), tree_effects_view);
			table.addChangeListener(tree_effects_view);
		}{
//			// QuestItem
//			tree_quest_items_view = new QuestItemTreeView("任务标志", new File(objects_dir, "questitem.obj/questitem.list"));
//			table.addTab("任务标志", Tools.createIcon(Res.icon_quest), tree_quest_items_view);
//			table.addChangeListener(tree_quest_items_view);
		}
			
		this.add(table, BorderLayout.CENTER);
	}

	public XLSColumns getUnitXLSColumns() {
		return tree_units_view.xls_columns;
	}
	
	public ObjectTreeView<?,?>[] getPages() {
		ObjectTreeView<?,?>[] pages = new ObjectTreeView<?, ?>[table.getTabCount()];
		for (int i=0; i<table.getTabCount(); i++) {
			ObjectTreeView<?,?> page = (ObjectTreeView<?,?>)table.getComponentAt(i);
			pages[i] = page;
		}
		return pages;
	}

	@SuppressWarnings("unchecked")
	public <T extends ObjectNode<?>> Vector<T> getObjects(Class<T> type)
	{
		for (ObjectTreeView<?,?> page : getPages()) {
			if (page.node_type.equals(type)) {
				return (Vector<T>)page.getAllObject();
			}
		}
		return null;
	}
	
	public <T extends ObjectNode<?>> T getObject(Class<T> type, int id)
	{
		for (ObjectTreeView<?,?> page : getPages()) {
			if (page.node_type.equals(type)) {
				return type.cast(page.getNode(id));
			}
		}
		return null;
	}

	public <T extends ObjectNode<?>> T getObject(Class<T> type, String id)
	{
		return getObject(type, Integer.parseInt(id));
	}
	
	public void saveAll() throws Throwable
	{
		for (ObjectTreeView<?,?> page : getPages()) {
			page.saveAll();
		}
		System.out.println(getClass().getSimpleName() + " : save all");
	}
	

	
	
	
	

	
//	-------------------------------------------------------------------------------------------------------------------------------
	


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

	
	
	
	
	
	
	
	
	
	
}

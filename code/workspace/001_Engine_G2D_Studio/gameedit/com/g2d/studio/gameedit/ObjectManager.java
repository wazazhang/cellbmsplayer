package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JTabbedPane;

import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.xls.XLSColumns;
import com.g2d.Tools;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

public class ObjectManager extends ManagerForm implements ActionListener
{
	private static final long serialVersionUID = 1L;

	G2DWindowToolBar 	toolbar	= new G2DWindowToolBar(this);
	JTabbedPane 		table	= new JTabbedPane();

	final public File objects_dir;
	
	XLSColumns player_xls_columns;
	
	final ObjectTreeViewTemplate<XLSUnit, TUnit> 		tree_units_view;
	final ObjectTreeViewTemplate<XLSItem, TItem> 		tree_items_view;
	final ObjectTreeViewTemplate<XLSSkill, TSkill>		tree_skills_view;
	final ObjectTreeViewDynamic<DAvatar, TAvatar>		tree_avatars_view;
	final ObjectTreeViewDynamic<DEffect, TEffect>		tree_effects_view;
//	final ObjectTreeViewDynamic<DQuestItem, QuestItem>	tree_quest_items_view;

	
	public ObjectManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "物体编辑器", Res.icons_bar[4]);
		
		this.add(toolbar, BorderLayout.NORTH);
		
		
		objects_dir = new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"objects");
		{
			player_xls_columns = XLSColumns.getXLSColumns(studio.xls_tplayer);
		}
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

	public XLSColumns getPlayerXLSColumns() {
		return player_xls_columns;
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

package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TItemList;
import com.cell.rpg.template.TShopItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSColumns;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.Tools;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.gameedit.dynamic.DItemList;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSShopItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

public class ObjectManager
{
	final public 	File		objects_dir;
	final public 	XLSColumns	player_xls_columns;
	private			XLSColumns	unit_xls_columns;

	LinkedHashMap<Class<?>, ObjectManagerTree<?, ?>> managers = new LinkedHashMap<Class<?>, ObjectManagerTree<?,?>>();
	
	public ObjectManager(Studio studio) 
	{
		this.objects_dir 		= new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"objects");
		this.player_xls_columns	= XLSColumns.getXLSColumns(studio.xls_tplayer);
	}
	
	public void loadAll(Studio studio, ProgressForm progress) 
	{
		// ------------ xls template ------------ //
		{
			// XLSUnit
			ObjectTreeViewTemplate<XLSUnit, TUnit> view = new ObjectTreeViewTemplate<XLSUnit, TUnit>(
					"单位模板", XLSUnit.class, TUnit.class, 
					new File(objects_dir, "tunit.obj/tunit.list"), studio.xls_tunit, progress);
			ObjectManagerTree<XLSUnit, TUnit> form = new ObjectManagerTree<XLSUnit, TUnit>(
					studio, progress, Res.icon_hd, view);
			managers.put(view.node_type, form);
			this.unit_xls_columns = view.xls_columns;
		}{
			// XLSItem
			ObjectTreeViewTemplate<XLSItem, TItem> view = new ObjectTreeViewTemplate<XLSItem, TItem>(
					"道具模板", XLSItem.class, TItem.class, 
					new File(objects_dir, "titem.obj/titem.list"), studio.xls_titem, progress);
			ObjectManagerTree<XLSItem, TItem> form = new ObjectManagerTree<XLSItem, TItem>(
					studio, progress, Res.icon_res_4, view);
			managers.put(view.node_type, form);
		}{	
			// XLSSkill
			ObjectTreeViewTemplate<XLSSkill, TSkill> tree_skills_view = new SkillTreeView(
					"技能模板", new File(objects_dir, "tskill.obj/tskill.list"), studio.xls_tskill, progress);
			ObjectManagerTree<XLSSkill, TSkill>	form_skills_view = new ObjectManagerTree<XLSSkill, TSkill>(
					studio, progress, Res.icon_res_3, tree_skills_view);
			managers.put(tree_skills_view.node_type, form_skills_view);
		}{	
			// XLSShopItem
			ShopItemTreeView tree_shopitems_view = new ShopItemTreeView(
					"商品模板", new File(objects_dir, "tshopitem.obj/tshopitem.list"), studio.xls_tshopitem, progress);
			ObjectManagerTree<XLSShopItem, TShopItem> form_shopitems_view = new ObjectManagerTree<XLSShopItem, TShopItem>(
					studio, progress, Res.icon_res_7, tree_shopitems_view);
			managers.put(tree_shopitems_view.node_type, form_shopitems_view);
		}{
		// ------------ dynamic ------------ //
		}{
			// DAvatar
			ObjectTreeViewDynamic<DAvatar, TAvatar> tree_avatars_view = new AvatarTreeView(
					"AVATAR", new File(objects_dir, "tavatar.obj/tavatar.list"));
			ObjectManagerTree<DAvatar, TAvatar> form_avatars_view = new ObjectManagerTree<DAvatar, TAvatar>(
					studio, progress, Res.icon_res_2, tree_avatars_view);
			managers.put(tree_avatars_view.node_type, form_avatars_view);
		}{	
			// DAvatar
			ObjectTreeViewDynamic<DEffect, TEffect>	tree_effects_view = new EffectTreeView(
					"魔法效果/特效", new File(objects_dir, "teffect.obj/teffect.list"));
			ObjectManagerTree<DEffect, TEffect>	form_effects_view = new ObjectManagerTree<DEffect, TEffect>(
					studio, progress, Res.icon_res_8, tree_effects_view);
			managers.put(tree_effects_view.node_type, form_effects_view);
		}{
			// DItemList
			ObjectTreeViewDynamic<DItemList, TItemList> tree_item_list_view = new ItemListTreeView(
					"掉落道具列表", new File(objects_dir, "titemlist.obj/titemlist.list"));
			ObjectManagerTree<DItemList, TItemList>	form_item_list_view = new ObjectManagerTree<DItemList, TItemList>(
					studio, progress, Res.icon_res_9, tree_item_list_view);
			managers.put(tree_item_list_view.node_type, form_item_list_view);
		}
	}

	public Collection<ObjectManagerTree<?, ?>> getPages() {
		return managers.values();
	}
	
	public <T extends ObjectNode<?>> void refresh(Class<T> type) {
		for (ObjectManagerTree<?, ?> page : managers.values()) {
			if (page.tree_view.node_type.equals(type)) {
				page.tree_view.reload();
			}
		}
	}
	
	public XLSColumns getPlayerXLSColumns() {
		return player_xls_columns;
	}
	
	public XLSColumns getUnitXLSColumns() {
		return unit_xls_columns;
	}
	
	public ObjectTreeView<?,?> getPage(Class<?> type) {
		if (managers.containsKey(type)) {
			return managers.get(type).tree_view;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ObjectNode<?>> Vector<T> getObjects(Class<T> type) {
		ObjectTreeView<?,?> page = getPage(type);
		if (page.node_type.equals(type)) {
			return (Vector<T>)page.getAllObject();
		}
		return null;
	}
	
	public <T extends ObjectNode<?>> T getObject(Class<T> type, int id) {
		ObjectTreeView<?,?> page = getPage(type);
		if (page.node_type.equals(type)) {
			return type.cast(page.getNode(id));
		}
		return null;
	}

	public <T extends ObjectNode<?>> T getObject(Class<T> type, String id)
	{
		return getObject(type, Integer.parseInt(id));
	}
	
	public void saveAll() throws Throwable
	{
		for (ObjectManagerTree<?,?> page : managers.values()) {
			page.saveAll();
		}
		System.out.println(getClass().getSimpleName() + " : save all");
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public static <T extends XLSTemplateNode<?>> T createXLSObjectFromRow(
			Class<T>					node_type,
			Map<String, XLSFullRow>		xls_row_map, 
			String 						row_key,
			TemplateNode 				data) 
	{
		T node = null;
		try{
			XLSFullRow row = xls_row_map.get(row_key);
			if (row!=null) {
				if (node_type.equals(XLSUnit.class)) {
					node = (node_type.cast(new XLSUnit(row.xls_file, row, data)));
				} else if (node_type.equals(XLSItem.class)) {
					node = (node_type.cast(new XLSItem(row.xls_file, row, data)));
				} else if (node_type.equals(XLSShopItem.class)) {
					node = (node_type.cast(new XLSShopItem(row.xls_file, row, data)));
				} else if (node_type.equals(XLSSkill.class)) {
					node = (node_type.cast(new XLSSkill(row.xls_file, row, data)));
				}
			} else {
				System.err.println(node_type + " : XML 行不存在 : " + row_key);
			}
		} catch (Exception err){
			err.printStackTrace();
		}
		return node;
	}
	
	
	
	
	
	
	
	
}

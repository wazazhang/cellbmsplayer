package com.cell.rpg.template;

import java.util.ArrayList;

import com.cell.rpg.item.ItemPropertyTemplate;
import com.g2d.annotation.Property;


public class TSkill extends TemplateNode
{
	@Property("图标编号")
	public String	icon_index;
	
/**	 每级的属性类型*/
	private ArrayList<Class<? extends ItemPropertyTemplate>> 
			columns = new ArrayList<Class<? extends ItemPropertyTemplate>>();
	
/**	 所有等级的属性
	[level][type] */
	private ArrayList<ArrayList<ItemPropertyTemplate>> 
			level_properties = new ArrayList<ArrayList<ItemPropertyTemplate>>();
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	public TSkill(int id, String name) {
		super(id, name);
		level_properties.add(createLevelProperties());
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (columns == null) {
			columns= new ArrayList<Class<? extends ItemPropertyTemplate>>();
		}
		if (level_properties == null) {
			level_properties = new ArrayList<ArrayList<ItemPropertyTemplate>>();
			level_properties.add(createLevelProperties());
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 获得该技能每个等级的属性数量
	 * @return
	 */
	public int getMaxColumn() {
		return columns.size();
	}
	
	/**
	 * 获得该列对应的属性类型
	 * @param column
	 * @return
	 */
	public Class<? extends ItemPropertyTemplate> getColumnType(int column) {
		if (column>=0 && column<getMaxColumn()) {
			return columns.get(column);
		}
		return null;
	}

	/**
	 * 获得指定列，指定行的属性
	 * @param column
	 * @param level
	 * @return
	 */
	public ItemPropertyTemplate getColumnProperty(int column, int level) {
		if (column>=0 && column<getMaxColumn()) {
			if (level>=0 && level<getMaxLevel()) {
				return level_properties.get(level).get(column);
			}
		}
		return null;
	}

	/**
	 * 添加一个属性，
	 * 每一等级都将自动添加该属性
	 * @param colum_type
	 * @return
	 */
	synchronized
	public int addColumn(Class<? extends ItemPropertyTemplate> colum_type) {
		columns.add(colum_type);
		int column = columns.size()-1;
		for (ArrayList<ItemPropertyTemplate> level : level_properties) {
			ItemPropertyTemplate tt = null;
			try{
				tt = colum_type.newInstance();
			} catch (Exception err) {
				err.printStackTrace();
			}
			level.add(tt);
		}
		return column;
	}
	
	/**
	 * 移除一个属性，
	 * 每一等级都将自动移除该属性
	 * @param column
	 */
	synchronized
	public void removeColumn(int column) {
		if (column>=0 && column<getMaxColumn()) {
			columns.remove(column);
			for (ArrayList<ItemPropertyTemplate> level : level_properties) {
				level.remove(column);
			}
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------

	/**
	 * 获得该技能的最大等级
	 * @return
	 */
	public int getMaxLevel() {
		return level_properties.size();
	}
	
	/**
	 * 获得该等级的所有属性能力
	 * @param level
	 * @return
	 */
	public ArrayList<ItemPropertyTemplate> getLevelProperties(int level) {
		if (level>=0 && level<getMaxLevel()) {
			return new ArrayList<ItemPropertyTemplate>(level_properties.get(level));
		}
		return null;
	}
	
	/**
	 * 得到该列所有等级的属性能力
	 * @param column
	 * @return
	 */
	public ArrayList<ItemPropertyTemplate> getColumnProperties(int column) {
		ArrayList<ItemPropertyTemplate> ret = new ArrayList<ItemPropertyTemplate>(getMaxLevel());
		if (column>=0 && column<getMaxColumn()) {
			for (ArrayList<ItemPropertyTemplate> level : level_properties) {
				ret.add(level.get(column));
			}
		}
		return ret;
	}
	
	/**
	 * 设置最大等级
	 * @param max_level
	 */
	synchronized
	public void setMaxLevel(int max_level) {
		if (getMaxLevel() > max_level) {
			int dcount = getMaxLevel() - max_level;
			for (int i=0; i<dcount; i++) {
				level_properties.remove(level_properties.size()-1);
			}
		} else if (getMaxLevel() < max_level) {
			int acount = max_level - getMaxLevel();
			for (int i=0; i<acount; i++) {
				level_properties.add(createLevelProperties());
			}
		}
	}
	
	private ArrayList<ItemPropertyTemplate> createLevelProperties() {
		ArrayList<ItemPropertyTemplate> line = new ArrayList<ItemPropertyTemplate>(columns.size());
		for (Class<? extends ItemPropertyTemplate> cls : columns) {
			ItemPropertyTemplate tt = null;
			try {
				tt = cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			line.add(tt);
		}
		return line;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------

	
//	----------------------------------------------------------------------------------------------------------------------------

	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}

}

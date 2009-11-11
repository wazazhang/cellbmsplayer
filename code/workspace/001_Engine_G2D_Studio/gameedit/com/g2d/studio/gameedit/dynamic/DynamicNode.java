package com.g2d.studio.gameedit.dynamic;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.cell.rpg.RPGObject;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.gameedit.entity.ObjectNode;

public abstract class DynamicNode<T extends RPGObject> extends ObjectNode<T>
{
	final protected T	bind_data;
	final int			id;
	private String		name;
	
	/**
	 * 创建新节点时
	 * @param factory
	 * @param name
	 */
	public DynamicNode(IDynamicIDFactory<?> factory, String name) {
		this.id 		= factory.createID();
		this.name 		= name;
		this.bind_data	= newData(factory, name);
	}
	
	/**
	 * 读取新节点时
	 * @param data
	 * @param id
	 * @param name
	 */
	protected DynamicNode(T data, int id, String name) {
		this.id			= id;
		this.name		= name;
		this.bind_data	= data;
	}
	
	
	abstract protected T newData(IDynamicIDFactory<?> factory, String name) ;

	public T getData() {
		return bind_data;
	}
	
	final public int getIntID() {
		return id;
	}
	
	@Override
	final public String getID() {
		return id+"";
	}
	
	@Override
	final public String getName() {
		return name;
	}
	
	final public void setName(String name) {
		if (name!=null && name.length()>0) {
			this.name = name;
		}
	}
	
//	-------------------------------------------------------------------------------------------------------
	
	/**
	 * 根据数据在此创建已有的节点
	 * @param <T>
	 * @param <D>
	 * @param type
	 * @param data
	 * @return
	 */
	public static <T extends DynamicNode<?>, D extends RPGObject> T createNode(Class<T> type, D data) 
	{
		if (type.equals(DAvatar.class)) {
			return type.cast(new DAvatar((TAvatar)data));
		}
		return null;
	}
	
}

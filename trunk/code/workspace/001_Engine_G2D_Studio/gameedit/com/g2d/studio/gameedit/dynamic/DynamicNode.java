package com.g2d.studio.gameedit.dynamic;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.cell.rpg.RPGObject;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.gameedit.entity.ObjectNode;

public abstract class DynamicNode<T extends RPGObject> extends ObjectNode<T>
{
	final T			bind_data;
	final int		id;
	public String	name;
	
	public DynamicNode(IDynamicIDFactory<?> factory, String name, T data) {
		this.id = factory.createID();
		this.name = name;
		this.bind_data	= (data == null) ? newData(factory, name) : data;
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
	
//	-------------------------------------------------------------------------------------------------------
	
	public static <T extends DynamicNode<?>, D extends RPGObject> T createNode(Class<T> type, D data) 
	{
		return null;
	}
	
}

package com.g2d.studio.gameedit.dynamic;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.cell.util.MarkedHashtable;
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.gameedit.entity.ObjectNode;

public abstract class DynamicNode<C extends DynamicNode<?>> extends ObjectNode<C>
{
	final int		id;
	
	public String	name;
	
	public DynamicNode(ZipInputStream zip_in, ZipEntry entry) {
		load(zip_in, entry);
		this.id = Integer.parseInt(getID(entry));
	}
	
	public DynamicNode(IDynamicIDFactory<?> factory, String name) {
		this.id = factory.createID();
		this.name = name;
	}

	final public int getIntID() {
		return id;
	}
	
	@Override
	protected void onRead(MarkedHashtable data) {
		super.onRead(data);
		name = data.getString("name", "no name");
	}
	
	@Override
	protected void onWrite(MarkedHashtable data) {
		super.onWrite(data);
		data.putString("name", name);
	}
	
	@Override
	public String getID() {
		return id+"";
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}

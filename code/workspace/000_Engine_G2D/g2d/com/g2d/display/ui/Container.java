package com.g2d.display.ui;

import java.awt.Graphics2D;
import java.util.Hashtable;
import java.util.Vector;

import com.g2d.Version;

public abstract class Container extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	Vector<UIComponent> comonents;
	
	@Override
	protected void init_field()
	{
		super.init_field();
		comonents = new Vector<UIComponent>();
	}
	
	
	synchronized public boolean addChild(UIComponent child) {
		comonents.add(child);
		return super.addChild(child);
	}
	
	synchronized public boolean removeChild(UIComponent child) {
		comonents.remove(child);
		return super.removeChild(child);
	}

	synchronized public boolean addComponent(UIComponent child) {
		return this.addChild(child);
	}
	
	synchronized public boolean addComponent(UIComponent child, int x, int y) {
		child.x = x;
		child.y = y;
		return this.addChild(child);
	}
	
	synchronized public boolean removeComponent(UIComponent child) {
		return this.removeChild(child);
	}
	
	@SuppressWarnings("unchecked")
	synchronized public Vector<UIComponent> getComonents() {
		return (Vector<UIComponent>)comonents.clone();
	}
	
	public void render(Graphics2D g)
	{
		g.clip(local_bounds);
		super.render(g);
	}
	
	
	
	
//	-----------------------------------------------------------------------------------------------------

	/** return user define attributes */
	public Hashtable<String, UIComponent> getXMLComponents()
	{
		Hashtable<String, UIComponent> ret = new Hashtable<String, UIComponent>();
		
		for (String key : data_map.keySet()) {
			if (key.startsWith("item_")) {
				ret.put(key.substring("item_".length()), (UIComponent)data_map.get(key));
			}
		}
		
		return ret;
	}

	
	public UIComponent getXMLComponent(String key) 
	{
		if (data_map==null) return null;
		return (UIComponent)data_map.get("item_"+key);
	}
	
	public Object putXMLComponent(String key, UIComponent value) 
	{
		if (value==null) {
			return data_map.remove("item_"+key);
		}
		return data_map.put("item_"+key, value);
	}
	
}

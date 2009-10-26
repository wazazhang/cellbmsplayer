package com.g2d.display.ui;

import java.util.Vector;

public abstract class Container extends UIComponent
{
	public Container() {}
	
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
	
	synchronized public Vector<UIComponent> getComonents() {
		return super.getChildsSubClass(UIComponent.class);
	}
	
	synchronized public<T extends UIComponent> Vector<T> getComonentsSubClass(Class<T> cls) {
		return super.getChildsSubClass(cls);
	}
	
//	-----------------------------------------------------------------------------------------------------

//	/** return user define attributes */
//	public Hashtable<String, UIComponent> getXMLComponents()
//	{
//		Hashtable<String, UIComponent> ret = new Hashtable<String, UIComponent>();
//		
//		for (String key : data_map.keySet()) {
//			if (key.startsWith("item_")) {
//				ret.put(key.substring("item_".length()), (UIComponent)data_map.get(key));
//			}
//		}
//		
//		return ret;
//	}
//
//	
//	public UIComponent getXMLComponent(String key) 
//	{
//		if (data_map==null) return null;
//		return (UIComponent)data_map.get("item_"+key);
//	}
//	
//	public Object putXMLComponent(String key, UIComponent value) 
//	{
//		if (value==null) {
//			return data_map.remove("item_"+key);
//		}
//		return data_map.put("item_"+key, value);
//	}
	
}

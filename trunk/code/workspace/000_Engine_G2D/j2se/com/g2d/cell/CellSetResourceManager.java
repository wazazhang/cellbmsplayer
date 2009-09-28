package com.g2d.cell;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CellSetResourceManager
{
	protected static CellSetResourceManager instance;
	
	public static CellSetResourceManager getManager() {
		return instance;
	}
	

//	--------------------------------------------------------------------------------------------------------------------
//	
	protected ConcurrentHashMap<String, CellSetResource> set_resources = new ConcurrentHashMap<String, CellSetResource>();
	
	@SuppressWarnings("unchecked")
	public<T extends CellSetResource> T getSet(String name) throws Exception {
		synchronized (set_resources) {
			if (set_resources.containsKey(name)) {
				return (T)set_resources.get(name);
			} else {
				T set = (T)createSet(name);
				set_resources.put(name, set);
				return set;
			}
		}
	}
	
	protected abstract CellSetResource createSet(String name) throws Exception;
	
	public void clear() {
		synchronized (set_resources) {
			set_resources.clear();
		}
	}
}

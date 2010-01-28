package com.g2d.cell;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CellSetResourceManager
{
//	--------------------------------------------------------------------------------------------------------------------
//	
	protected ConcurrentHashMap<String, CellSetResource> set_resources = new ConcurrentHashMap<String, CellSetResource>();
	
	@SuppressWarnings("unchecked")
	public <T extends CellSetResource> T getSet(String path) throws Exception {
		synchronized (set_resources) {
			if (set_resources.containsKey(path)) {
				return (T)set_resources.get(path);
			} else {
				T set = (T)createSet(path);
				set_resources.put(path, set);
				return set;
			}
		}
	}
	
	protected abstract CellSetResource createSet(String path) throws Exception;
	
	public void clear() {
		synchronized (set_resources) {
			for (CellSetResource res : set_resources.values()) {
				res.dispose();
				res.destoryAllResource();
			}
			set_resources.clear();
		}
	}
}

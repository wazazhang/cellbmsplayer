package com.cell.rpg.instance.zones;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.cell.CUtil;
import com.cell.util.Pair;

/**
 * 副本进度，进度指的是，当玩家在此副本中游戏时，杀死了BOSS，触发了事件，副本中的数据将会改变。
 * @author WAZA
 */
public class Data implements Serializable, Iterable<Entry<String, Object>>, Comparator<String>
{
	private static final long serialVersionUID = 1L;
	
	private TreeMap<String, Object> vars = new TreeMap<String, Object>(this);
	
	public Object put(String key, Double value) {
		return vars.put(key, value);
	}
	
	public Object put(String key, String value) {
		return vars.put(key, value);
	}
	
	public Object get(String key) {
		return vars.get(key);
	}

	public Object remove(String key) {
		return vars.remove(key);
	}
	
	public Iterator<Entry<String, Object>> iterator() {
		return vars.entrySet().iterator();
	}
	
	public Set<Entry<String, Object>> entrySet() {
		return vars.entrySet();
	}
	
	@Override
	public int compare(String o1, String o2) {
		return CUtil.getStringCompare().compare(o1, o2);
	}
}

package com.cell.util;

import java.io.Serializable;
import java.util.Map;

public class Pair<K, V> implements Serializable, Map.Entry<K, V>
{
	K key;
	V value;
	
	public Pair(K k, V v) {
		key = k;
		value = v;
	}
	
	public Pair() {}

	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}
	
	
}

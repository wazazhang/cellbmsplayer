package com.cell.util;

import java.io.Serializable;

public class Pair<K, V> implements Serializable
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
	public void setValue(V value) {
		this.value = value;
	}
	
	
}

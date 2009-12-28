/**
 * 
 */
package com.cell.sql;

import java.util.Collection;

public interface SQLColumnMap<K, V>
{
	public Collection<V>	values();

	public V 				put(K key, V value);

	public V 				get(Object key);

	public V 				remove(Object key);

	public int	 			size();
}
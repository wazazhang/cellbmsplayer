package com.cell.sql;


public interface SQLTableRow<K> extends SQLFieldGroup
{
	public K getPrimaryKey();
	
}

package com.cell.sql;

public class SQMTypeManager 
{
	static SQLTypeComparer type_comparer = new SQLTypeComparerMySQL();
	
	/**
	 * @param typeComparer the type_comparer to set
	 */
	static public void setTypeComparer(SQLTypeComparer typeComparer) {
		type_comparer = typeComparer;
	}
	
	/**
	 * @return
	 */
	static public SQLTypeComparer getTypeComparer() {
		return type_comparer;
	}
}

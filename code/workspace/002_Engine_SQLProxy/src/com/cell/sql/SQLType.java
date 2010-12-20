package com.cell.sql;

import java.sql.Types;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Data Type數據類型
 * @author WAZA
 */
public enum SQLType 
{
	BOOLEAN			(Types.BOOLEAN),
	BYTE			(Types.TINYINT),
	SHORT			(Types.SMALLINT),
	INTEGER			(Types.INTEGER),
	LONG			(Types.BIGINT),
	FLOAT			(Types.REAL),
	DOUBLE			(Types.DOUBLE),
	STRING			(Types.VARCHAR),
	STRUCT			(Types.BLOB),
	TEXT_STRUCT		(Types.CLOB),
	XML_STRUCT		(Types.CLOB),
	TIME			(Types.TIME),
	TIMESTAMP		(Types.TIMESTAMP),
	
	INTEGER_ARRAY	(Types.ARRAY),
	;
	
	final public int		jdbc_type;
	final public String		type_name;
	
//	final int length;
	
	private SQLType(int jdbcType) {
		jdbc_type = jdbcType;
		type_name = SQMTypeManager.getTypeComparer().getDirverTypeString(jdbc_type).toLowerCase();
	}
	
	@Override
	public String toString() {
		return type_name;
	}
	
	public boolean equals(String typeName) {
		throw new NotImplementedException();
		//return type_name.equals(typeName.toLowerCase());
	}

	public boolean typeEquals(int jdbcType) {
		return SQMTypeManager.getTypeComparer().typeEquals(this, jdbcType);
	}
	
	
//	------------------------------------------------------------------------------------------------


	
	
	
	
}

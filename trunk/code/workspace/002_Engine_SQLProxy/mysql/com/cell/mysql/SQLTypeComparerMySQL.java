package com.cell.mysql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.sql.Types;

import com.cell.sql.SQLStructCLOB;
import com.cell.sql.SQLType;
import com.cell.sql.SQLTypeComparer;
import com.cell.sql.util.SQLUtil;
import com.cell.xstream.XStreamAdapter;

public class SQLTypeComparerMySQL implements SQLTypeComparer 
{
	public boolean typeEquals(SQLType type, int mysql_jdbc_type) 
	{
		switch(type)
		{
		case STRUCT:
			switch(mysql_jdbc_type){
			case Types.BINARY: 			return true;
			case Types.VARBINARY: 		return true;
			case Types.LONGVARBINARY: 	return true;
			}
			break;
		case TEXT_STRUCT:
		case XML_STRUCT:
			switch(mysql_jdbc_type){
			case Types.VARCHAR: 		return true;
			case Types.LONGVARCHAR: 	return true;
			}
			break;
		case BOOLEAN:
			switch (mysql_jdbc_type) {
			case Types.TINYINT: 		return true;
			case Types.BIT:				return true;
			}
			break;
		}
		return type.jdbc_type == mysql_jdbc_type;
	}

	public Object toJavaObject(SQLType type, Class<?> type_clazz, Object sqlObject) throws Exception 
	{
		switch(type)
		{
		case STRUCT:
			return SQLUtil.binToBlob((byte[])sqlObject);
		case TEXT_STRUCT:
			return SQLUtil.stringToClob((String)sqlObject, type_clazz);
		case XML_STRUCT:
			return SQLUtil.stringToXML((String)sqlObject);
		case BYTE:
			return ((Number)sqlObject).byteValue();
		case SHORT:
			return ((Number)sqlObject).shortValue();
		default:
			return sqlObject;
		}
	}
	
	public Object toSQLObject(SQLType type, Class<?> type_clazz, Object javaObject) throws Exception 
	{
		switch(type)
		{
		case STRUCT:
			return SQLUtil.blobToBin((Serializable)javaObject);
		case TEXT_STRUCT:
			return SQLUtil.clobToString((SQLStructCLOB)javaObject);
		case XML_STRUCT:
			return SQLUtil.xmlToString((Serializable)javaObject);
		default:
			return javaObject;
		}
	}
	
	@Override
	public ObjectInputStream getXMLInputStream(Reader reader) throws IOException {
		ObjectInputStream ois = XStreamAdapter.getInstance().createReadStream(reader);
		return ois;
	}
	
	@Override
	public ObjectOutputStream getXMLOutputStream(Writer writer) throws IOException {
		ObjectOutputStream oos = XStreamAdapter.getInstance().createWriteStream(writer);
		return oos;
	}
	
	public String getDirverTypeString(int jdbc_type) 
	{
		switch (jdbc_type) {
		case Types.BOOLEAN:		return "boolean";
		case Types.TINYINT: 	return "tinyint";
		case Types.SMALLINT:	return "smallint";
		case Types.INTEGER: 	return "integer";
		case Types.BIGINT: 		return "bigint";
		case Types.REAL: 		return "float";
		case Types.DOUBLE: 		return "double";
		case Types.VARCHAR: 	return "varchar";
		case Types.BLOB: 		return "blob";
		case Types.CLOB: 		return "text";
		case Types.TIME: 		return "time";
		case Types.TIMESTAMP:	return "timestamp";
		case Types.ARRAY:		return "blob";
		}
		return null;
	}
}


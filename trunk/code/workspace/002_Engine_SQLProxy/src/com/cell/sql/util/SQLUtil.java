package com.cell.sql.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.reflect.Parser;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLFieldGroup;
import com.cell.sql.SQLStructBLOB;
import com.cell.sql.SQLStructCLOB;
import com.cell.sql.SQLStructXML;
import com.cell.sql.SQLTableManager;
import com.cell.sql.SQMTypeManager;

public class SQLUtil
{
	final static private byte[] ZERO_DATA = new byte[0];
	final static private String ZERO_TEXT = "";
	
	/**
	 * 将一个struct编码成Blob
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static byte[] blobToBin(SQLStructBLOB struct) throws Exception
	{
		if (struct == null) {
			return ZERO_DATA;
		}
		ByteArrayOutputStream	baos	= new ByteArrayOutputStream(1024);
		ObjectOutputStream		oos		= new ObjectOutputStream(baos);
		try{
			oos.writeObject(struct);
			return baos.toByteArray();
		}finally{
			oos.close();
		}
	}
	
	/**
	 * 将一个Blob解码成struct
	 * @param <T>
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static SQLStructBLOB binToBlob(byte[] data) throws Exception
	{
		ByteArrayInputStream	bais	= new ByteArrayInputStream(data);
		ObjectInputStream		ois		= new ObjectInputStream(bais);
		try{
			SQLStructBLOB ret = (SQLStructBLOB)ois.readObject();
			return ret;
		}finally{
			ois.close();
		}
	}
	

//	---------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * 将一个struct编码成Blob
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String clobToString(SQLStructCLOB struct) throws Exception
	{
		if (struct == null) {
			return ZERO_TEXT;
		}
		StringWriter writer = new StringWriter(512);
		try{
			struct.encode(writer);
			writer.flush();
			String ret = writer.toString();
			return ret;
		}finally{
			writer.close();
		}
	}
	
	/**
	 * 将一个Blob解码成struct
	 * @param <T>
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static SQLStructCLOB stringToClob(String data, Class<?> clazz) throws Exception
	{
		if (data == null || data.length() == 0) {
			return null;
		}
		StringReader	reader	= new StringReader(data);
		try{
			SQLStructCLOB	ret		= (SQLStructCLOB)clazz.newInstance();
			ret.decode(reader);
			return ret;
		}finally{
			reader.close();
		}
	}

	
//	---------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 将一个struct编码成String
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String xmlToString(SQLStructXML struct) throws Exception
	{
		if (struct == null) {
			return ZERO_TEXT;
		}
		StringWriter 		sw	= new StringWriter(1024);
		ObjectOutputStream	oos	= SQMTypeManager.getTypeComparer().getXMLOutputStream(sw);
		try{
			oos.writeObject(struct);
			oos.flush();
			return sw.toString();
		}finally{
			oos.close();
		}
	}
	
	/**
	 * 将一个String解码成struct
	 * @param <T>
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static SQLStructXML stringToXML(String data) throws Exception
	{
		if (data == null || data.length() == 0) {
			return null;
		}
		StringReader		sr	= new StringReader(data);
		ObjectInputStream	ois	= SQMTypeManager.getTypeComparer().getXMLInputStream(sr);
		try{
			SQLStructXML ret = (SQLStructXML)ois.readObject();
			return ret;
		}finally{
			ois.close();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 将src所有SQLField字段的值赋给dst
	 * @param <T>
	 * @param src
	 * @param dst
	 * @throws Exception
	 */
	public static <T extends SQLFieldGroup> void setSQLFields(T src, T dst) throws Exception
	{
		setSQLFields(SQLTableManager.getSQLColumns(src.getClass()), src, dst);
	}
	
	/**
	 * 将src所有SQLField字段的值赋给dst
	 * @param <T>
	 * @param sql_columns
	 * @param src
	 * @param dst
	 * @throws Exception
	 */
	public static <T extends SQLFieldGroup> void setSQLFields(
			SQLColumn[] sql_columns,
			T src,
			T dst) throws Exception
	{
		for (SQLColumn c : sql_columns) {
			c.setObject(dst, c.getObject(src));
		}
	}
	
	/**
	 * 得到该类所有签名的字段
	 * @param clazz
	 * @return
	 */
	public static SQLColumn[] getSQLColumns(Class<? extends SQLFieldGroup> clazz)
	{
		return SQLTableManager.getSQLColumns(clazz);
	}

	public static HashMap<String, SQLColumn> getSQLColumnsMap(Class<? extends SQLFieldGroup> clazz)
	{
		SQLColumn[] columns = SQLTableManager.getSQLColumns(clazz);
		
		HashMap<String, SQLColumn> ret = new HashMap<String, SQLColumn>(columns.length);
		
		for (SQLColumn c : columns) {
			ret.put(c.name, c);
		}
		
		return ret;
	}

	
	
}
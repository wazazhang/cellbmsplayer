package com.cell.sql.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import com.cell.CIO;
import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.reflect.Parser;
import com.cell.sql.SQLFieldGroup;
import com.cell.sql.SQLStructBLOB;
import com.cell.sql.SQLStructCLOB;
import com.cell.sql.SQLTableManager;
import com.cell.sql.SQLTableManager.SQLColumn;

public class SQLUtil
{
	/**
	 * 将一个struct编码成Blob
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static byte[] blobToBin(SQLStructBLOB struct) throws Exception
	{
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
	

	
	/**
	 * 将一个struct编码成Blob
	 * @param struct
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String clobToString(SQLStructCLOB struct) throws Exception
	{
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
		StringReader	reader	= new StringReader(data);
		try{
			SQLStructCLOB	ret		= (SQLStructCLOB)clazz.newInstance();
			ret.decode(reader);
			return ret;
		}finally{
			reader.close();
		}
	}
	
	
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
	
	public static SQLColumn[] getSQLColumns(Class<? extends SQLFieldGroup> clazz)
	{
		return SQLTableManager.getSQLColumns(clazz);
	}
	
}

package com.cell.sql;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.CUtil.ICompare;
import com.cell.CUtil.StringCompare;
import com.cell.reflect.Fields;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.cell.sql.util.SQLUtil;


/**
 * 管理数据库表的抽象结构
 * SQLColumnMap为获取当前SQL表和内存的缓冲区
 * @author WAZA
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLColumnManager<K, R extends SQLTableRow<K>> extends SQLColumnAdapter<K, R>
{
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	final SQLColumnMap<K, R> 		data_map;
	final ReentrantReadWriteLock	data_lock		= new ReentrantReadWriteLock();
	
	protected Lock 					data_readLock 	= data_lock.readLock();
	protected Lock 					data_writeLock 	= data_lock.writeLock();

//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	public SQLColumnManager(Class<R> cls, SQLColumnMap<K, R> data_map)
	{
		super(cls);
		this.data_map		= data_map;
	}
	
	public R newRow() throws Exception {
		return table_class.newInstance();
	}

	/**
	 * 获得SQL表和内存的缓冲区
	 * @return
	 */
	protected SQLColumnMap<K, R> getDataMap() {
		return data_map;
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	public void loadAll(Connection conn) throws Exception
	{
		// 读入表数据
		try
		{
			if (data_map.size() > 0) {
				throw new Exception("already loaded [" + table_name + "] data  !");
			}
			
			long starttime = System.currentTimeMillis();
			log.info("loading [" + table_name + "] ...");
			
			ValidateResult vresult = validateAndAutoFix(conn);
			
			if (vresult == ValidateResult.OK)
			{
				data_writeLock.lock();
				try {
					ArrayList<R> rows = selectAll(conn);
					for (R row : rows) {
						data_map.put(row.getPrimaryKey(), row);
					}
				} finally {
					data_writeLock.unlock();
				}
				log.info("loaded  [" + table_name + "] " + size() + " rows use " + (System.currentTimeMillis() - starttime) + "ms");
			}
			else
			{
				throw new SQLException("validate error !");
			}
		} 
		catch (Exception e) {
			log.error("load table [" + table_name + "] faild!\n" + e.getMessage(), e);
			throw e;
		}
	}
	
	public void close(Connection conn)
	{
		data_writeLock.lock();
		try {
			for (R row : data_map.values()) {
				try {
					updateWithDB(row, conn, table_columns);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		} finally {
			data_writeLock.unlock();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
//	write operator
	
	/** 
	 * 向数据库插入一行数据，如果该行已经存在于数据库则将会引起SQLExcption
	 * @param conn
	 * @param row
	 * @return 
	 * @throws Exception
	 */
	public boolean insert(Connection conn, R row) throws Exception
	{
		data_writeLock.lock();
		try {
			insertWithDB(row, conn);
			data_map.put(row.getPrimaryKey(), row);
			return true;
		} finally {
			data_writeLock.unlock();
		}
	}

	/**
	 * 从数据库删除一行数据
	 * @param conn
	 * @param primary_key
	 * @return 被删除的数据
	 * @throws Exception
	 */
	public R remove(Connection conn, K primary_key) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(primary_key);
			if (row != null) {
				deleteWithDB(primary_key, conn);
				data_map.remove(primary_key);
			}
			return row;
		} finally {
			data_writeLock.unlock();
		}
	}
	
	/**
	 * 向数据库存储指定key值的指定的字段
	 * @param conn
	 * @param primary_key
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public R updateFields(Connection conn, K primary_key, SQLColumn ... fields) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(primary_key);
			if (row != null) {
				updateWithDB(row, conn, fields);
			}
			return row;
		} finally {
			data_writeLock.unlock();
		}
	}

	/**
	 * 向数据库存储对象指定的字段
	 * @param conn
	 * @param obj
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public R updateFields(Connection conn, R obj, SQLColumn ... fields) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(obj.getPrimaryKey());
			if (row != null) {
				updateWithDB(obj, conn, fields);
				data_map.put(obj.getPrimaryKey(), obj);
				return row;
			}
			return null;
		} finally {
			data_writeLock.unlock();
		}
	}

	/**
	 * 向数据库存储对象根据key值
	 * @param conn
	 * @param primary_key
	 * @return
	 * @throws Exception
	 */
	public R update(Connection conn, K primary_key) throws Exception
	{
		return updateFields(conn, primary_key, table_columns);
	}

	/**
	 * 向数据库存储对象
	 * @param conn
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public R update(Connection conn, R obj) throws Exception
	{
		return updateFields(conn, obj, table_columns);
	}

	/**
	 * 执行插入或者更新操作，如果该行已经存在于数据库，则执行更新语句，否则执行插入语句。
	 * @param conn
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public R put(Connection conn, R obj) throws Exception
	{
		data_writeLock.lock();
		try {
			R row = data_map.get(obj.getPrimaryKey());
			if (row != null) {
				updateWithDB(obj, conn, table_columns);
				data_map.put(obj.getPrimaryKey(), obj);
				return row;
			} else {
				insertWithDB(obj, conn);
				data_map.put(obj.getPrimaryKey(), obj);
				return null;
			}
		} finally {
			data_writeLock.unlock();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
//	read only

	/**
	 * 得到所有对象的迭代子
	 * @return
	 */
	public Iterator<R> iterator()
	{
		data_readLock.lock();
		try {
			return data_map.values().iterator();
		} finally {
			data_readLock.unlock();
		}
	}
	
	/**
	 * 根据key值获得对象
	 * @param primary_key
	 * @return
	 */
	public R get(K primary_key)
	{
		data_readLock.lock();
		try {
			R row = data_map.get(primary_key);
			return row;
		} finally {
			data_readLock.unlock();
		}
	}
	
	/**
	 * 根据key值获得对象，如果目标不存在，则将新值插入
	 * @param primary_key
	 * @return
	 */
	public R putIfAbsent(K primary_key, R new_value)
	{
		data_readLock.lock();
		try {
			R row = data_map.get(primary_key);
			if (row != null) {
				return row;
			} else {
				if (primary_key.equals(new_value.getPrimaryKey())) {
					data_map.put(primary_key, new_value);
				}
				return null;
			}
		} finally {
			data_readLock.unlock();
		}
	}
	
	/**
	 * 查找指定的对象
	 * @param finder 重载finder的equals(Object obj)方法获得对象
	 * @return
	 * @see Object.equals(Object obj);
	 */
	public R find(Object finder)
	{
		data_readLock.lock();
		try {
			for (R row : data_map.values()) {
				if (finder.equals(row)){
					return row;
				}
			}
			return null;
		} finally {
			data_readLock.unlock();
		}
	}
	
	/**
	 * 返回数据库行的数量
	 * @return
	 */
	public int size(){
		data_readLock.lock();
		try {
			return data_map.size();
		} finally {
			data_readLock.unlock();
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
}

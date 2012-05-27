package com.fc.castle.service.impl.mysql;

import java.sql.Connection;

import com.cell.sql.SQLColumn;
import com.cell.sql.SQLTableManager;
import com.cell.sql.SQLTableRow;


public class MySQLTable<K, R extends SQLTableRow<K>> extends SQLTableManager<K, R>
{
	public MySQLTable(Class<R> type, String tableName) throws Exception 
	{
		super(type, tableName);
	}
	
	@Override
	public R newRow() throws Exception
	{
		return table_class.newInstance();
	}
	
	public R read(MySQLPersistanceManager db, K key)
	{
		Connection conn = db.getConnection();
		try {
			R row = select(key, conn);
			return row;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	public boolean write(MySQLPersistanceManager db, R row)
	{
		Connection conn = db.getConnection();
		try {
			updateWithDB(row, conn);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return false;
	}
	
	public boolean writeFields(MySQLPersistanceManager db, R row, String[] fieldsName)
	{
		Connection conn = db.getConnection();
		try {		
			SQLColumn[] columns = getColumns(fieldsName);
			updateWithDB(row, conn, columns);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return false;
	}
	
	public boolean insert(MySQLPersistanceManager db, R row)
	{
		Connection conn = db.getConnection();
		try {
			insertWithDB(row, conn);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return false;
	}
	
	public R find(MySQLPersistanceManager db, String field_name, String field_value) 
	{
		Connection conn = db.getConnection();
		try {
			R row = select(conn, field_name, field_value);
			return row;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	
}

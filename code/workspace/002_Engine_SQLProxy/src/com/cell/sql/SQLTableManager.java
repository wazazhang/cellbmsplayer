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
 * loadAll 读入所有数据，在运行时，只写入数据，所有Table的PrimaryKey由该类管理。
 * @author WAZA
 *
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLTableManager<K, R extends SQLTableRow<K>>
{
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	final public static Logger 		log 		= LoggerFactory.getLogger(SQLTableManager.class);

//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	final public	Class<R> 		table_class;
	final public	SQLTable		table_type;
	final public	String			table_name;
	final public	SQLColumn[]		table_columns;
	
	ConcurrentHashMap<K, R> 		data_map 		= new ConcurrentHashMap<K, R>();
	ReentrantReadWriteLock			data_lock		= new ReentrantReadWriteLock();
	
	protected Lock 					data_readLock 	= data_lock.readLock();
	protected Lock 					data_writeLock 	= data_lock.writeLock();
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	public SQLTableManager(Class<R> cls)
	{
		table_class		= cls;
		table_type		= cls.getAnnotation(SQLTable.class);
		table_name		= table_type.name();
		table_columns	= getSQLColumns(cls);
	}
	
	public R newRow() throws Exception {
		return table_class.newInstance();
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public SQLColumn[] getColumns(String ... columns_name)
	{
		SQLColumn[] columns = new SQLColumn[columns_name.length];
		for (int i=columns.length-1; i>=0; --i) {
			columns[i] = getColumn(columns_name[i]);
		}
		return columns;
	}
	
	public SQLColumn getColumn(String column_name)
	{
		for (SQLColumn c : table_columns) {
			if (c.name.equals(column_name)) {
				return c;
			}
		}
		return null;
	}
	
	public SQLColumn getColumn(Field field)
	{
		for (SQLColumn c : table_columns) {
			if (c.leaf_field.equals(field)) {
				return c;
			}
		}
		return null;
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	public void loadAll(Connection conn) throws Exception
	{
		// 读入表数据
		try
		{
			long starttime = System.currentTimeMillis();
			log.info("loading [" + table_name + "] ...");
			
			ValidateResult vresult = validateAndAutoFix(conn);
			
			if (vresult == ValidateResult.OK)
			{
//				for (SQLColumn c : table_columns)
//				{
//					System.out.println("\t" + c.name + " " + c.anno.type() + " index = " + c.index);
//				}
//				
				data_writeLock.lock();
				try {
					HashMap<K, R> map = new HashMap<K, R>();
					ArrayList<R> rows = selectAll(conn);
					for (R row : rows) {
						map.put(row.getPrimaryKey(), row);
					}
					data_map.clear();
					data_map.putAll(map);
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
				deleteWithDB(row, conn);
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

	static String getSplitChar(int i, int len) {
		return ((i != len - 1) ? ",\n" : "\n");
	}
	
	/**
	 * 从结果集中读入数据
	 * @param result
	 * @throws SQLException
	 */
	final void fromResult(R row, ResultSet result) throws Exception
	{
		for (int i=0; i<table_columns.length; i++){
			table_columns[i].setObject(row, result.getObject(table_columns[i].index));
		}
	}

	/**
	 * 执行插入指令，将一个新行插入到DB。
	 * @param conn
	 * @throws Exception
	 */
	final void insertWithDB(R row, Connection conn) throws Exception
	{
		StringBuffer sb = new StringBuffer("INSERT INTO ");
		sb.append(table_name);
		sb.append("(\n");
		
		for (int i=0; i<table_columns.length; i++){
			SQLColumn c = table_columns[i];
			sb.append("\t"); 
			sb.append(c.name);
			sb.append(getSplitChar(i, table_columns.length));
		}
		sb.append(") VALUES (\n");
		for (int i=0; i<table_columns.length; i++){
			sb.append("\t?"); 
			sb.append(getSplitChar(i, table_columns.length));
		}
		sb.append(");");

		PreparedStatement statement = conn.prepareStatement(sb.toString());
		try{
			for (int i=0; i<table_columns.length; i++){
				SQLColumn c = table_columns[i];
				statement.setObject(i+1, c.getObject(row), c.anno.type().jdbc_type);
			}
			statement.execute();
		}finally{
			statement.close();
		}
		sb = null;
	}

//	/**
//	 * 执行更新指令，将更新指定的行。
//	 * @param conn
//	 * @throws Exception
//	 */
//	final void updateWithDB(R row, Connection conn) throws Exception
//	{
//		updateWithDB(row, conn, table_columns);
//	}
	
	/**
	 * 执行更新指令，将更新指定的行。
	 * @param conn
	 * @throws Exception
	 */
	final void updateWithDB(R row, Connection conn, SQLColumn[] columns) throws Exception
	{
		StringBuffer sb = new StringBuffer("UPDATE ");
		sb.append(table_name);
		sb.append(" SET\n");
		
		for (int i=0; i<columns.length; i++){
			SQLColumn c = columns[i];
			sb.append("\t"); 
			sb.append(c.name); 
			sb.append("=?"); 
			sb.append(getSplitChar(i, columns.length));
		}
		
		sb.append("WHERE "); 
		sb.append(table_type.primary_key_name()); 
		sb.append("='");
		sb.append(row.getPrimaryKey()); 
		sb.append("';");
		
		PreparedStatement statement = conn.prepareStatement(sb.toString());
		try{
			for (int i=0; i<columns.length; i++){
				SQLColumn c = columns[i];
				statement.setObject(i+1, 
						c.getObject(row), 
						c.anno.type().jdbc_type);
			}
			statement.execute();
		}finally{
			statement.close();
		}
		sb = null;
	}
	
	/**
	 * 删除oid对应的列。
	 * @param conn
	 * @throws Exception
	 */
	final void deleteWithDB(R row, Connection conn) throws Exception
	{
		StringBuffer sb = new StringBuffer("DELETE FROM ");
		sb.append(table_name);
		sb.append(" WHERE ");
		sb.append(table_type.primary_key_name()); 
		sb.append("='"); 
		sb.append(row.getPrimaryKey()); 
		sb.append("';");
		
		Statement statement = conn.createStatement();
		try{
			statement.execute(sb.toString());
		}finally{
			statement.close();
		}
		sb = null;
	}

	final R select(K primary_key, Connection conn) throws SQLException
	{
		Statement statement = conn.createStatement();
		
		try {
			R row = newRow();
			
			StringBuffer sb = new StringBuffer("SELECT * FROM ");
			sb.append(table_name);
			sb.append(" WHERE ");
			sb.append(table_type.primary_key_name());
			sb.append("='");
			sb.append(primary_key);
			sb.append("';");
			
			ResultSet result = statement.executeQuery(sb.toString());
			try {
				result.next();
				fromResult(row, result);
				return row;
			} finally {
				result.close();
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			statement.close();
		}

		return null;
	}
	
	/**
	 * 从表中读出所有对象
	 * @param <T>
	 * @param tableClass
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	final ArrayList<R> selectAll(Connection conn) throws SQLException
	{
		ArrayList<R> ret = new ArrayList<R>();

		String 		sql 		= "SELECT * FROM " + table_name + " ;";
		Statement	statement 	= conn.createStatement();
		ResultSet 	result 		= statement.executeQuery(sql);
		
		try {
			while (result.next()) {
				try {
					R row = newRow();
					fromResult(row, result);
					ret.add(row);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			throw e;
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			result.close();
			statement.close();
		}
		return ret;
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static enum ValidateResult
	{
		OK,
		ERROR,
		ERROR_NOT_EXIST,
		ERROR_LENGTH,
		ERROR_NAME,
		ERROR_TYPE,
	}
	
	public boolean validateTable(Connection conn, boolean auto_create_struct) throws SQLException
	{
		// 验证表结构
		log.info("validating table struct [" + table_name + "] ...");
		
		ValidateResult result = validateTable(conn);
		
		if (result != ValidateResult.OK)
		{
			log.warn("validate error [" + table_name + "]");
			
			if (result == ValidateResult.ERROR_NOT_EXIST && auto_create_struct)
			{
				log.info("creating table struct [" + table_name + "] ...");
				
				Statement statement = conn.createStatement();
				
				String create = getCreateTableSQL(this, false);
				System.out.println(create);
				statement.execute(create);
				statement.close();
				
				result = validateTable(conn);
			}
			else
			{
				throw new SQLException("table struct [" + table_name + "] not validate !");
			}
		}

		return result == ValidateResult.OK;
	}
	
	/**
	 * 检查指定数据库里的表结构是否符合java的结构
	 * @param tableClass
	 * @param statement
	 * @return
	 */
	public ValidateResult validateTable(Connection conn) throws SQLException
	{
		ValidateResult vresult = validateAndAutoFix(conn);
		
		if (vresult == ValidateResult.OK)
		{
			String sql = "SELECT * FROM " + table_name + " LIMIT 0;";
			
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ResultSetMetaData metadata = result.getMetaData();
			result.close();
			statement.close();
			
			// last validate
			for (SQLColumn c : table_columns)
			{
				int rc = c.index;
				
				String rname = metadata.getColumnName(rc);
				String tname = c.name;
				
				// 检测名字是否正确
				if (!rname.equalsIgnoreCase(tname)) 
				{
					System.err.println(
							"Table '" + table_name + "' " +
							"field name ["+rname+","+tname+"] not equal! " +
							"at column " + rc 
							);
					
					return ValidateResult.ERROR_NAME;
				}
				
				// 检查类型是否正确
				if (!c.anno.type().typeEquals(metadata.getColumnType(rc))) 
				{
					String	rtypename	= metadata.getColumnTypeName(rc);
					int		rtype		= metadata.getColumnType(rc);
					String	ttypename	= c.anno.type().toString();
					int		ttype		= c.anno.type().jdbc_type;
					
					System.err.println(
							"Table '" + table_name + "' " +
							"field type ["+rtypename+"("+rtype+")"+","+ttypename+"("+ttype+")"+"] not equal! " +
							"at column " + rc 
							);
					
					return ValidateResult.ERROR_TYPE;
				}
			
				
				
			}
			
			return ValidateResult.OK;
		}
		
		return vresult;
	}
	
	private ValidateResult validateAndAutoFix(Connection conn) throws SQLException
	{
		String sql = "SELECT * FROM " + table_name + " LIMIT 0;";

		Statement statement = conn.createStatement();
		
		while(true)
		{
			ResultSet result = null;
			
			try{
				result = statement.executeQuery(sql);
			}catch (Exception e) {
				return ValidateResult.ERROR_NOT_EXIST;
			}
			
			ResultSetMetaData metadata = result.getMetaData();
			result.close();
			
			ArrayList<SQLColumn> not_exists = new ArrayList<SQLColumn>();
			
			for (SQLColumn c : table_columns)
			{
				int rc = indexOfSQLColumn(metadata, c);
				
				if (rc > 0) {
					c.setIndex(rc);
				}
				else if (rc == -1) {
					not_exists.add(c);
				} 
				else if (rc == -2)
				{
					String	rtypename	= metadata.getColumnTypeName(c.index);
					int		rtype		= metadata.getColumnType(c.index);
					String	ttypename	= c.anno.type().toString();
					int		ttype		= c.anno.type().jdbc_type;
					
					String reson = (
							"Table '" + table_name + "' " +
							"field : " + c.name + " : type ["+rtypename+"("+rtype+")"+","+ttypename+"("+ttype+")"+"] not equal! " +
							"at column " + c.index 
							);

					throw new SQLException(reson);
				}
			}
			
			if (!not_exists.isEmpty())
			{
				for (SQLColumn c : not_exists)
				{
					String add_sql = "ALTER TABLE " + table_name + " ADD COLUMN " + 
					c.name + " " + 
					c.anno.type() + " " + 
					c.anno.constraint() + ";";
					System.out.println(add_sql);
					statement.executeUpdate(add_sql);
				}
			}
			else
			{
				break;
			}
		}
		statement.close();
		
		CUtil.sort(table_columns, table_columns[0]);
		
		return ValidateResult.OK;
		
	}
	
	/**
	 * @param metadata
	 * @param c
	 * @return 
	 * >=1 : ok<br>
	 * -1 : not exist<br>
	 * -2 : type not equal<br>
	 * @throws SQLException
	 */
	private static int indexOfSQLColumn(ResultSetMetaData metadata, SQLColumn c) throws SQLException
	{
		String	tname	= c.name;
		SQLType	ttype	= c.anno.type();
		
		for (int rc = 1; rc <= metadata.getColumnCount(); rc++) {
			// 检测名字是否正确
			if (tname.equalsIgnoreCase(metadata.getColumnName(rc))) {
				c.setIndex(rc);
				// 检查字段类型是否正确
				if (ttype.typeEquals(metadata.getColumnType(rc))) {
					return rc;
				}else{
					return -2;
				}
			}
		}
		return -1;
	}
	
	
//	---------------------------------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------------------------------

	static public SQLColumn[] getSQLColumns(Class<? extends SQLFieldGroup> tableClass)
	{
		SQLColumn[] columns = null;
		
		try 
		{
			ArrayList<SQLColumn>	full_columns_list 	= new ArrayList<SQLColumn>();
			Stack<Field> 			fields_stack		= new Stack<Field>();

			getSQLColumns(
					tableClass, 
					full_columns_list, 
					fields_stack);
			
			columns = full_columns_list.toArray(new SQLColumn[full_columns_list.size()]);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return columns;
	}

	/**
	 * 递归该类（包括子字段），找到所有SQLField字段
	 * @param gclass
	 * @param full_columns_list
	 * @param fields_stack
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	static private void getSQLColumns(
			Class<?>				gclass,
			ArrayList<SQLColumn> 	full_columns_list, 
			Stack<Field> 			fields_stack
			) throws IllegalArgumentException, IllegalAccessException
	{
		ArrayList<Field> fields = Fields.getDeclaredAndSuperFields(gclass);
		
		for (Field field : fields)
		{
			fields_stack.push(field);
			
			// 得到SQLFieldGroup子复合类型
			try
			{
				field.getType().asSubclass(SQLFieldGroup.class);
				// 递归
				getSQLColumns(
						field.getType(),
						full_columns_list, 
						fields_stack
						);
			}
			catch (Exception e) 
			{
				// 得到SQLField字段
				SQLField anno = field.getAnnotation(SQLField.class);
				if (anno != null)
				{
					full_columns_list.add(new SQLColumn(anno, fields_stack));
				}
			}
			
			fields_stack.pop();
		}
	}
	

//	---------------------------------------------------------------------------------------------------------------------------------
	

	/**
	 * 创建表在数据库中，对列
	 * @param tableClass
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	public static String getCreateTableSQL(SQLTableManager<?, ?> table)
	{
		return getCreateTableSQL(table, true);
	}
	
	public static String getCreateTableSQL(final SQLTableManager<?, ?> table, final boolean sort)
	{
		SQLColumn[] columnss = new SQLColumn[table.table_columns.length];
		System.arraycopy(table.table_columns, 0, columnss, 0, columnss.length);
		CUtil.sort(columnss, new ICompare<SQLColumn, SQLColumn>() {
			StringCompare sc = new StringCompare();
			public int compare(SQLColumn a, SQLColumn b) {
				if (a.name.equals(table.table_type.primary_key_name())) return 1;
				if (b.name.equals(table.table_type.primary_key_name())) return -1;
				if (sort) {
					return sc.compare(a.name, b.name);
				}
				return 0;
			}
		});
		
		String sql = "CREATE TABLE " + table.table_name + "(\n";

		for (int i=0; i<columnss.length; i++){
			SQLColumn column = columnss[i];
			sql += "\t"+
				column.name + " " + 
				column.anno.type() + " " + 
				column.anno.constraint();
			
				String default_value = column.anno.defaultValue();
				if (default_value!=null && default_value.length()>0) {
					sql += " DEFAULT '" + column.anno.defaultValue() + "',\n";
				}else{
					sql += ",\n";
				}
		}
		
		sql += "\tPRIMARY KEY (" + table.table_type.primary_key_name() + ")";
		if (!table.table_type.constraint().trim().isEmpty()) {
			sql += ", " + table.table_type.constraint() + "\n";
		}else{
			sql += "\n";
		}
		
		sql += ");";

		return sql;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static class SQLColumn implements ICompare<SQLColumn, SQLColumn>
	{
		/** 在数据库中，该列的描述 */
		final public SQLField 				anno;
		
		/** 该字段在数据库中的名字 */
		final public String					name;
		
		/** 该字段在数据库中的位置 */
		private int index;
		
		final public ArrayList<Field> 		fields;
		final public Field					leaf_field;
		
		
		public SQLColumn(SQLField anno, Stack<Field> fields_stack)
		{
			this.anno		= anno;
			this.fields 	= new ArrayList<Field>(fields_stack);
			this.leaf_field	= this.fields.get(fields.size()-1);
			
			String name = fields.get(0).getName();
			for (int i=1; i<fields.size(); i++) {
				name += "__" + fields.get(i).getName();
			}
			this.name		= name;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + " : " + name;
		}
		
		public int compare(SQLColumn a, SQLColumn b) {
			return b.index - a.index;
		}
		
		private void setIndex(int i){
			index = i;
		}
		
		private SQLFieldGroup getLeafTable(SQLFieldGroup table) throws Exception
		{
			for (int i=1; i<fields.size(); i++) {
				table = (SQLFieldGroup)(table.getField(fields.get(i-1)));
			}
			return table;
		}
		
		public Object getObject(SQLFieldGroup table) throws Exception
		{
			table = getLeafTable(table);
			Object java_object = table.getField(leaf_field);
			return SQMTypeManager.getTypeComparer().toSQLObject(
					anno.type(), 
					leaf_field.getType(),
					java_object);
		}
		
		public void setObject(SQLFieldGroup table, Object data) throws Exception
		{
			if (data != null) {
				table = getLeafTable(table);
				table.setField(leaf_field, 
						SQMTypeManager.getTypeComparer().toJavaObject(
								anno.type(),
								leaf_field.getType(),
								data));
			}
		}
	}

}

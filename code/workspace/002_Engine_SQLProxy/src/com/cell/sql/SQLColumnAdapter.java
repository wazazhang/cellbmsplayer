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
 * 数据库表和实体的关系
 * @author WAZA
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLColumnAdapter<K, R extends SQLTableRow<K>>
{
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	final public	Class<R> 		table_class;
	final public	SQLTable		table_type;
	final public	String			table_name;
	final public	SQLColumn[]		table_columns;
	final protected Logger			log;
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	public SQLColumnAdapter(Class<R> cls)
	{
		this.table_class	= cls;
		this.table_type		= cls.getAnnotation(SQLTable.class);
		this.table_name		= table_type.name();
		this.table_columns	= getSQLColumns(cls);
		this.log 			= LoggerFactory.getLogger(getClass().getSimpleName() + "[" + cls.getName() + "]");
	}

	/**
	 * 创建行实体
	 * @return
	 * @throws Exception
	 */
	abstract public R newRow() throws Exception ;
	
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	final public SQLColumn[] getColumns(String ... columns_name)
	{
		SQLColumn[] columns = new SQLColumn[columns_name.length];
		for (int i=columns.length-1; i>=0; --i) {
			columns[i] = getColumn(columns_name[i]);
		}
		return columns;
	}
	
	final public SQLColumn getColumn(String column_name)
	{
		for (SQLColumn c : table_columns) {
			if (c.name.equals(column_name)) {
				return c;
			}
		}
		return null;
	}
	
	final public SQLColumn getColumn(Field field)
	{
		for (SQLColumn c : table_columns) {
			if (c.leaf_field.equals(field)) {
				return c;
			}
		}
		return null;
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 从结果集中读入数据
	 * @param result
	 * @throws SQLException
	 */
	final protected R fromResult(R row, ResultSet result) throws Exception
	{
		for (int i=0; i<table_columns.length; i++){
			table_columns[i].setObject(row, result.getObject(table_columns[i].index));
		}
		return row;
	}
	
	/**
	 * 执行插入指令，将一个新行插入到DB。
	 * @param conn
	 * @throws Exception
	 */
	final protected void insertWithDB(R row, Connection conn) throws Exception
	{
		insertWithDB(row, conn, table_columns);
	}
	
	/**
	 * 执行插入指令，将一个新行插入到DB。
	 * @param conn
	 * @throws Exception
	 */
	final protected void insertWithDB(R row, Connection conn, SQLColumn[] columns) throws Exception
	{
		StringBuffer sb = new StringBuffer("INSERT INTO ");
		sb.append(table_name);
		sb.append("(\n");
		
		for (int i=0; i<columns.length; i++){
			SQLColumn c = columns[i];
			sb.append("\t"); 
			sb.append(c.name);
			sb.append(getSplitChar(i, columns.length));
		}
		sb.append(") VALUES (\n");
		for (int i=0; i<columns.length; i++){
			sb.append("\t?"); 
			sb.append(getSplitChar(i, columns.length));
		}
		sb.append(");");

		PreparedStatement statement = conn.prepareStatement(sb.toString());
		try{
			for (int i=0; i<columns.length; i++){
				SQLColumn c = columns[i];
				statement.setObject(i+1, c.getObject(row), c.anno.type().jdbc_type);
			}
			statement.execute();
		}finally{
			statement.close();
		}
		sb = null;
	}

	/**
	 * 执行更新指令，将更新指定的行。
	 * @param conn
	 * @throws Exception
	 */
	final protected void updateWithDB(R row, Connection conn) throws Exception
	{
		updateWithDB(row, conn, table_columns);
	}
	
	/**
	 * 执行更新指令，将更新指定的行。
	 * @param conn
	 * @throws Exception
	 */
	final protected void updateWithDB(R row, Connection conn, SQLColumn[] columns) throws Exception
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
	final protected void deleteWithDB(K primary_key, Connection conn) throws Exception
	{
		StringBuffer sb = new StringBuffer("DELETE FROM ");
		sb.append(table_name);
		sb.append(" WHERE ");
		sb.append(table_type.primary_key_name()); 
		sb.append("='"); 
		sb.append(primary_key);
		sb.append("';");
		
		Statement statement = conn.createStatement();
		try{
			statement.execute(sb.toString());
		}finally{
			statement.close();
		}
		sb = null;
	}

	final protected R select(K primary_key, Connection conn) throws SQLException
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
				if (result.next()) {
					return fromResult(row, result);
				}
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
	final protected ArrayList<R> selectAll(Connection conn) throws SQLException
	{
		return query(conn, "SELECT * FROM " + table_name + " ;");
	}
	
	final protected ArrayList<R> query(Connection conn, String sql) throws SQLException
	{
		ArrayList<R> 	ret 		= new ArrayList<R>();
		Statement		statement 	= conn.createStatement();
		ResultSet 		result 		= statement.executeQuery(sql);
		try {
			while (result.next()) {
				try {
					ret.add(fromResult(newRow(), result));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			throw e;
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				result.close();
			} catch (Exception err) {}
			try {
				statement.close();
			} catch (Exception err) {}
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
	
	final public boolean validateTable(Connection conn,
			boolean auto_create_struct,
			boolean sort_field,
			boolean create_comment) throws SQLException
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
				
				String create = getCreateTableSQL(this, sort_field, create_comment);
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
	final public ValidateResult validateTable(Connection conn) throws SQLException
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
	
	final ValidateResult validateAndAutoFix(Connection conn) throws SQLException
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
					c.anno.constraint() + 
					(c.anno.defaultValue().isEmpty() ? "" : " DEFAULT '" + c.anno.defaultValue() + "'") +
					";";
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

//	---------------------------------------------------------------------------------------------------------------------------------

	final private static String getSplitChar(int i, int len) {
		return ((i != len - 1) ? ",\n" : "\n");
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
	static int indexOfSQLColumn(ResultSetMetaData metadata, SQLColumn c) throws SQLException
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
	static void getSQLColumns(
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
	 * 获得该类型对应SQL的创建语句
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static String getCreateTableSQL(SQLColumnAdapter<?, ?> table)
	{
		return getCreateTableSQL(table, true, true);
	}
	
	/**
	 * 获得该类型对应SQL的创建语句
	 * @param table
	 * @param sort_fields		是否对列进行排序
	 * @param create_comment	是否产生注释信息
	 * @return 
	 * @throws SQLException
	 */
	public static String getCreateTableSQL(final SQLColumnAdapter<?, ?> table, final boolean sort_fields, boolean create_comment)
	{
		SQLColumn[] columnss = new SQLColumn[table.table_columns.length];
		System.arraycopy(table.table_columns, 0, columnss, 0, columnss.length);
		CUtil.sort(columnss, new ICompare<SQLColumn, SQLColumn>() {
			StringCompare sc = new StringCompare();
			public int compare(SQLColumn a, SQLColumn b) {
				if (a.name.equals(table.table_type.primary_key_name())) return 1;
				if (b.name.equals(table.table_type.primary_key_name())) return -1;
				if (sort_fields) {
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
				column.anno.type();
			
				String constraint = column.anno.constraint();
				if (constraint != null && constraint.length() > 0) {
					sql += " " + constraint;
				}
				
				String default_value = column.anno.defaultValue();
				if (default_value != null && default_value.length() > 0) {
					sql += " DEFAULT '" + default_value + "'";
				}
				if (create_comment) {
					String comment = column.getAllComment();
					if (comment != null && comment.length() > 0) {
						sql += " COMMENT '" + comment + "'";
					}
				}
				sql += ",\n";
		}
		
		sql += "\tPRIMARY KEY (" + table.table_type.primary_key_name() + ")";
		if (!table.table_type.constraint().trim().isEmpty()) {
			sql += ", " + table.table_type.constraint() + "\n";
		} else {
			sql += "\n";
		}
		sql += ")";
		
		if (!table.table_type.properties().trim().isEmpty()) {
			sql += " " + table.table_type.properties();
		}
		if (create_comment && !table.table_type.comment().trim().isEmpty()) {
			sql += " COMMENT='" + table.table_type.comment() + "'";
		}
		sql += ";";
		return sql;
	}

}
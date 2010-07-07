/**
 * 
 */
package com.cell.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil.ICompare;
import com.cell.sql.anno.SQLField;

public class SQLColumn implements ICompare<SQLColumn, SQLColumn>
{
	final public static Logger 		log 		= LoggerFactory.getLogger(SQLColumn.class);
	
	/** 在数据库中，该列的描述 */
	final public SQLField 				anno;
	
	/** 该字段在数据库中的名字 */
	final public String					name;

	/**得到层次关系Field*/
	final public ArrayList<Field> 		fields;
	
	/**得到最终数据Field*/
	final public Field					leaf_field;
	
	/** 该字段在数据库中的位置 */
	int index;
	
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
	
	void setIndex(int i){
		index = i;
	}
	
	SQLFieldGroup getLeafTable(SQLFieldGroup table) throws Exception
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
		try{
			return SQMTypeManager.getTypeComparer().toSQLObject(
					anno.type(), 
					leaf_field.getType(),
					java_object);
		} catch (Exception err) {
			log.error("getObject Column field \"" + leaf_field.getName() + "\" error : " + err.getMessage());
			throw err;
		}
	}
	
	public void setObject(SQLFieldGroup table, Object data) throws Exception
	{
		if (data != null) {
			table = getLeafTable(table);
			try{
				table.setField(leaf_field, 
						SQMTypeManager.getTypeComparer().toJavaObject(
								anno.type(),
								leaf_field.getType(),
								data));
			} catch (Exception err) {
				log.error("setObject Column field \"" + leaf_field.getName() + "\" error : " + err.getMessage());
				throw err;
			}
		}
	}
}
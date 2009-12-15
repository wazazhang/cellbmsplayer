package com.cell.rpg.formula;

import java.util.Map;

import com.cell.sql.SQLColumn;
import com.cell.sql.SQLFieldGroup;
import com.cell.sql.SQLTableManager;
import com.cell.sql.util.SQLUtil;
import com.g2d.annotation.Property;

@Property("单位属性")
public class ObjectProperty extends AbstractValue
{
	@Property("单位字段")
	public String						filed_name;

//	-------------------------------------------------------------------------
	
	public ObjectProperty() {}
	
	public ObjectProperty(String field_name) {
		this.filed_name = field_name;
	}
	
	@Override
	public double getValue() {
		try{
			if (column != null) {
				Object obj = column.getObject(object);
				if (obj instanceof Number) {
					return ((Number)obj).doubleValue();
				}
			}
		}catch(Exception err){}	
		return 0;
	}
	
	@Override
	public String toString() {
		if (column != null) {
			return filed_name + "(" + getValue() + ")";
		} else {
			return filed_name;
		}
	}

//	-------------------------------------------------------------------------

	transient SQLColumn		column;
	transient SQLFieldGroup	object;

	/**
	 * 在计算前务必设置被计算的单位
	 * @param object
	 */
	public void setObject(SQLFieldGroup o, Map<String, SQLColumn> columns) {
		if (o != null) {
			if (this.column == null) {
				this.column = columns.get(filed_name);
			}
			this.object = o;
		}
	}

	public SQLColumn getSQLColumn() {
		return column;
	}
	
	public SQLFieldGroup getSQLObject() {
		return object;
	}
	
}
	


package com.cell.rpg.formula;

import java.util.Map;

import com.cell.rpg.formula.helper.IFormulaAdapter;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLFieldGroup;
import com.cell.sql.SQLTableManager;
import com.cell.sql.util.SQLUtil;
import com.g2d.annotation.Property;

@Property("单位-属性")
public abstract class ObjectProperty extends AbstractValue
{
	@Property("单位字段")
	public String						filed_name;

	public ObjectProperty() {}
	
	public ObjectProperty(String field_name) {
		this.filed_name = field_name;
	}
	
	@Override
	public String toString() {
		return filed_name;
	}
}
	


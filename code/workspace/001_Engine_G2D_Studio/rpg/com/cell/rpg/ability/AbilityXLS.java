package com.cell.rpg.ability;


import com.cell.CUtil;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSRow;
import com.g2d.annotation.Property;

@Property("XLS数据绑定")
public abstract class AbilityXLS extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;
	
	@Property("XLS文件名")
	public XLSFile xls_file_name;
	
	@Property("XLS主键值")
	public XLSRow xls_primary_key;

	@Override
	public String toString() {
		return super.toString() + " : " + xls_file_name + " : " + xls_primary_key;
	}
}

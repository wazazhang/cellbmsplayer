package com.fc.castle.data.message;

import com.cell.net.io.flash.ASClass;
import com.cell.net.io.flash.ASFunction;
import com.cell.sql.SQLTableRow;

@ASClass(dynamic=true)
public abstract class AbstractTemplate extends AbstractData implements SQLTableRow<Integer>
{
	private static final long serialVersionUID = 1L;
	

	@ASFunction({
	"public function getName() : String {",
	"	return null;",
	"}"})
	abstract public String getName();
	
	@Override
	@ASFunction({
	"public function getType() : int {",
	"	return 0;",
	"}"})
	abstract public Integer getPrimaryKey();
	
	
	@Override
	public String toString() {
		return getName() + "(" + getPrimaryKey() + ")";
	}
}

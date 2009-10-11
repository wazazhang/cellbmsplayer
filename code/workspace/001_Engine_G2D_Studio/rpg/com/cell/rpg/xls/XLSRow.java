package com.cell.rpg.xls;

import java.io.Serializable;

public class XLSRow implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	final public XLSFile 	xls_file;
	
	final public String 	id;
	final public String 	desc;
	
	public XLSRow(XLSFile file, String id, String desc) {
		this.xls_file = file;
		this.id = id;
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc + "(" + id + ")";
	}
}

package com.cell.rpg.xls;

import java.io.File;
import java.io.Serializable;

public class XLSFile implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	final public String xls_file;
	
	public XLSFile(File file) {
		this.xls_file = file.getName();
	}
	
	@Override
	public String toString() {
		return xls_file;
	}
}

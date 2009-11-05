package com.g2d.studio.gameedit.template;

import java.io.File;
import java.util.ArrayList;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSRow;
import com.g2d.studio.swing.G2DTreeNode;

public abstract class TemplateTreeNode<C extends TemplateTreeNode<?>> extends G2DTreeNode<C>
{
	final XLSFile	xls_file;
	final XLSRow	xls_row;
	
	TemplateTreeNode(XLSFile xls_file, XLSRow xls_row) {
		this.xls_file = xls_file;
		this.xls_row = xls_row;
	}
	
	
	public XLSFile getXLSFile() {
		return xls_file;
	}
	
	public XLSRow getXLSRow() {
		return xls_row;
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------
	
	public static <T extends TemplateTreeNode<?>> ArrayList<T> listXLSRows(File file, Class<T> cls)
	{
		ArrayList<T> ret = new ArrayList<T>();
		
		
		return ret;
	}
	
}

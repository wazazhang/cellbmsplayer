package com.g2d.studio.gameedit.template;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.rpg.xls.XLSRow;
import com.g2d.studio.swing.G2DTreeNode;

public abstract class TemplateTreeNode extends G2DTreeNode<G2DTreeNode<?>>
{
	final XLSFile		xls_file;
	final XLSFullRow	xls_fullrow;
	
	TemplateTreeNode(XLSFile xls_file, XLSFullRow xls_row) {
		this.xls_file = xls_file;
		this.xls_fullrow = xls_row;
	}
	
	
	public XLSFile getXLSFile() {
		return xls_file;
	}
	
	public XLSFullRow getXLSRow() {
		return xls_fullrow;
	}
	
	@Override
	public String getName() {
		return getXLSRow().desc;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return false;
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	
	public static <T extends TemplateTreeNode> ArrayList<T> listXLSRows(File file, Class<T> cls)
	{
		ArrayList<T> ret = new ArrayList<T>();
		for (XLSFullRow row : XLSFullRow.getXLSRows(file, XLSFullRow.class)) {
			if (cls.equals(TNpc.class)) {
				
			}
		}
		return ret;
	}
	
}

package com.g2d.studio.gameedit.dynamic;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;

final public class DEffect extends DynamicNode
{

	public DEffect(ZipInputStream zip_in, ZipEntry entry) {
		super(zip_in, entry);
	}
	
	@Override
	final public String getEntryName() {
		return "effect/"+getID()+".xml";
	}
	@Override
	protected ImageIcon createIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

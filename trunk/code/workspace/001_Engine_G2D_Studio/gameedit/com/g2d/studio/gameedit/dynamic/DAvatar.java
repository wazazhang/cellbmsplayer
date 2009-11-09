package com.g2d.studio.gameedit.dynamic;

import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;

public class DAvatar extends DynamicNode
{
	ArrayList<String> avatar_resources = new ArrayList<String>();
	
	public DAvatar(IDynamicIDFactory<DAvatar> factory, String name) {
		super(factory, name);
	}
	
	public DAvatar(ZipInputStream zip_in, ZipEntry entry) {
		super(zip_in, entry);
	}
	
	@Override
	protected ImageIcon createIcon() {
		return null;
	}
	
	@Override
	protected void onRead(MarkedHashtable data) {
		super.onRead(data);
		avatar_resources = data.getObject("avatar_resources", new ArrayList<String>());
	}
	
	@Override
	protected void onWrite(MarkedHashtable data) {
		super.onWrite(data);
		data.put("avatar_resources", avatar_resources);
	}
	
	
	
}

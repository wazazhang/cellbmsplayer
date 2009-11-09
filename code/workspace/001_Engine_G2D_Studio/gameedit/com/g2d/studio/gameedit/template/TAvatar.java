package com.g2d.studio.gameedit.template;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;

public class TAvatar extends TemplateNode
{
	ArrayList<String> avatar_resources = new ArrayList<String>();
	
	
	public TAvatar(XLSFile xls_file, XLSFullRow xls_row) {
		super(xls_file, xls_row);
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

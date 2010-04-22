package com.g2d.studio.gameedit.template;

import javax.swing.ImageIcon;

import com.cell.rpg.template.TShopItem;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.XLSObjectViewer;

final public class XLSShopItem extends XLSTemplateNode<TShopItem>
{
	private XLSItem item_template = null;
	
	public XLSShopItem(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);
	}
	
	@Override
	protected TShopItem newData(XLSFile xlsFile, XLSFullRow xlsRow) {
		return new TShopItem(getIntID(), xlsRow.desc);
	}
	
	public ObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new XLSObjectViewer<XLSShopItem>(this);
		}
		return edit_component;
	}
	
	@Override
	public String getName() {
		return super.getName();
	}
	
	@Override
	protected ImageIcon createIcon() {
		return super.createIcon();
	}
}

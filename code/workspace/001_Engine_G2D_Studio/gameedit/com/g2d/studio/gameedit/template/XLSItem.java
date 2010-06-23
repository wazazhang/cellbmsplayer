package com.g2d.studio.gameedit.template;

import javax.swing.ImageIcon;

import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.XLSObjectViewer;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.res.Res;

final public class XLSItem  extends XLSTemplateNode<TItem>
{
	public XLSItem(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);		
		if (template_data.icon_index!=null) {
			icon_file = Studio.getInstance().getIconManager().getIcon(template_data.icon_index);
		}
	}
	
	@Override
	protected TItem newData(XLSFile xlsFile, XLSFullRow xlsRow) {
		return new TItem(getIntID(), xlsRow.desc);
	}
	
	public ObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new XLSObjectViewer<XLSItem>(this, 
					new ObjectAdapters.ItemPropertiesSelectAdapter());
		}
		return edit_component;
	}

	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_file==null || !icon_file.icon_file_name.equals(getData().icon_index)) {
			resetIcon();
		} 
		return super.getIcon(update);
	}
	
	@Override
	protected ImageIcon createIcon() {
		icon_file = Studio.getInstance().getIconManager().getIcon(getData().icon_index);
		if (icon_file!=null) {
			return new ImageIcon(Tools.combianImage(20, 20, icon_file.getImage()));
		}
		return Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
	}
	
	
	
}
package com.g2d.studio.gameedit.template;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.res.Res;

public class TItem  extends TemplateTreeNode
{
	IconFile icon_file;
	
	public TItem(XLSFile xls_file, XLSFullRow xls_row) {
		super(xls_file, xls_row);
	}

	@Override
	protected void onRead(MarkedHashtable data) {
		super.onRead(data);
		icon_file = data.getObject("icon_file", null);
	}
	
	@Override
	protected void onWrite(MarkedHashtable data) {
		super.onWrite(data);
		data.put("icon_file", icon_file);
	}
	
	public IconFile getIconFile() {
		return icon_file;	
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_snapshot == null) {
			if (icon_file!=null) {
				icon_snapshot = Tools.createIcon(Tools.combianImage(20, 20, icon_file.image));
			} else {
				icon_snapshot = Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
			}
		}
		return super.getIcon(update);
	}

}

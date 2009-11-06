package com.g2d.studio.gameedit.template;

import javax.swing.ImageIcon;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.studio.cpj.entity.CPJSprite;

public class TNpc  extends TemplateTreeNode
{
	CPJSprite cpj_sprite;
	
	public TNpc(XLSFile xls_file, XLSFullRow xls_row) {
		super(xls_file, xls_row);
	}

	public CPJSprite getCPJSprite() {
		return cpj_sprite;	
	}
	
	public void setCPJSprite(CPJSprite sprite) {
		
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_snapshot != null) {
			
		}
		return super.getIcon(update);
	}
}

package com.g2d.studio.icon;

import java.awt.Window;

import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DListSelectDialog;

public class IconSelectDialog extends G2DListSelectDialog<IconFile>
{
	private static final long serialVersionUID = 1L;
	
	public IconSelectDialog()
	{
		super(Studio.getInstance().getIconManager(), new IconList());
		super.setTitle("选择一个图标");
	}
	
	public IconSelectDialog(Window owner) {
		super(owner, new IconList());
		super.setTitle("选择一个图标");
	}
}

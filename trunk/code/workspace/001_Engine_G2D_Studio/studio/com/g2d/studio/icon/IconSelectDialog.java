package com.g2d.studio.icon;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceList;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.swing.G2DListSelectDialog;
import com.g2d.util.AbstractDialog;

public class IconSelectDialog extends G2DListSelectDialog<IconFile>
{
	private static final long serialVersionUID = 1L;
	
	public IconSelectDialog()
	{
		super(Studio.getInstance().getIconManager(),new IconList());
		super.setTitle("选择一个图标");
	}
	
}

package com.g2d.studio.swing;

import javax.swing.ImageIcon;

public abstract interface G2DListItem 
{
	abstract public ImageIcon	getIcon(boolean update); 

	abstract public String		getName();
}

package com.g2d.util;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;


public class AbstractDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	public AbstractDialog()
	{
		super((Window)null, ModalityType.APPLICATION_MODAL);
		super.setAlwaysOnTop(true);
		super.setSize(600, 400);
		setCenter();
	}
	
	public void setCenter()
	{
		setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2);
	}
	
	public static void setCenter(Component dialog)
	{
		dialog.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - dialog.getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - dialog.getHeight()/2);
	}
	
}

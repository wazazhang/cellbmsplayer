package com.g2d.studio.cpj;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.g2d.studio.Studio;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.swing.G2DListSelectDialog;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractFrame;

public class CPJResourceSelectDialog<T extends CPJObject<?>> extends G2DListSelectDialog<T>
{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	public CPJResourceSelectDialog(Window window, CPJResourceType type) {
		super(window, (CPJResourceList<T>)Studio.getInstance().getCPJResourceManager().createObjectsPanel(type));
		super.setTitle("选择一个资源");
	}
}

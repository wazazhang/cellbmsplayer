package com.g2d.studio.cpj;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceManager.CPJResourceList;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractFrame;

public class SelectResourceDialog<T extends CPJObject<?>> extends AbstractDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	CPJResourceList<T> list;
	
	JButton ok = new JButton("确定");
	
	T object;
	
	@SuppressWarnings("unchecked")
	public SelectResourceDialog(CPJResourceType type)
	{
		super.setLayout(new BorderLayout());
		super.setSize(400, 400);
		super.setCenter();
		list = (CPJResourceList<T>)Studio.getInstance().getCPJResourceManager().createObjectsPanel(type);
		super.add(list, BorderLayout.CENTER);
		super.add(ok, BorderLayout.SOUTH);
		ok.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		object = list.getSelectedObject();
		this.setVisible(false);
	}
	
	public T showDialog() {
		super.setVisible(true);
		return object;
	}
	
}

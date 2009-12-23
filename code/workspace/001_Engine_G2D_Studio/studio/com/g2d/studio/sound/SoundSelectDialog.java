package com.g2d.studio.sound;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceList;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.swing.G2DListSelectDialog;
import com.g2d.util.AbstractDialog;

public class SoundSelectDialog extends G2DListSelectDialog<SoundFile> implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;
	
	JLabel label = new JLabel();
	
	public SoundSelectDialog(Component owner, String def)
	{
		super(owner, new SoundList());
		super.setTitle("选择一个声音");
		SoundList list = (SoundList)getList();
		super.getList().setSelectedValue(list.getSoundFile(def), true);
	}
	
	@Override
	public Component getComponent(ObjectPropertyPanel panel) {
		if (getSelectedObject()!=null) {
			label.setIcon(getSelectedObject().getListIcon(false));
			label.setText(getSelectedObject().getListName());
		} else {
			label.setText("");
		}
		return label;
	}

	@Override
	public String getValue() {
		return label.getText();
	}
}

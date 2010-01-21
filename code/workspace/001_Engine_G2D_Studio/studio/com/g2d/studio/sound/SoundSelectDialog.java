package com.g2d.studio.sound;

import java.awt.Component;

import javax.swing.JLabel;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.swing.G2DListSelectDialog;

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

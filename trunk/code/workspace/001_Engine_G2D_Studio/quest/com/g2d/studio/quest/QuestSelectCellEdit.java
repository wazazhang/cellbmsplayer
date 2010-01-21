package com.g2d.studio.quest;

import java.awt.Component;

import javax.swing.JLabel;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.swing.G2DListSelectDialog;

public class QuestSelectCellEdit extends G2DListSelectDialog<QuestNode> implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	public QuestSelectCellEdit(Component owner) {
		super(owner, new QuestList());
	}
	
	@Override
	public Component getComponent(ObjectPropertyPanel panel) {
		QuestNode node = getSelectedObject();
		cell_edit_component.setText(node+"");
		return cell_edit_component;
	}
	
	@Override
	public Integer getValue() {
		QuestNode node = getSelectedObject();
		if (node != null) {
			return node.getIntID();
		}
		return -1;
	}
}

package com.g2d.studio.item.property;

import java.awt.Component;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.cell.rpg.item.ItemPropertyTemplate;
import com.cell.rpg.item.ItemPropertyTypes;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DListSelectDialog;

@SuppressWarnings("serial")
public class ItemPropertySelectDialog extends G2DListSelectDialog<ItemPropertyNode> implements PropertyCellEdit<ItemPropertyTemplate>
{
	JLabel cell_edit_component = new JLabel();
	
	public ItemPropertySelectDialog(Component owner) {
		super(owner, new ItemPropertyList());
	}
	
	@Override
	public Component getComponent(ObjectPropertyPanel panel) {
		ItemPropertyNode node = getSelectedObject();
		if (node != null) {
			cell_edit_component.setText(node.getListName());
		} else {
			cell_edit_component.setText("");
		}
		return cell_edit_component;
	}
	
	@Override
	public ItemPropertyTemplate getValue() {
		ItemPropertyNode node = getSelectedObject();
		if (node != null) {
			return node.getItemPropertyTemplate();
		}
		return null;
	}

}

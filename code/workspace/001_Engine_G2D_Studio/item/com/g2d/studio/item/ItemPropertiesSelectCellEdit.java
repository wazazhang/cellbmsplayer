package com.g2d.studio.item;

import java.awt.Component;

import javax.swing.JLabel;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListSelectDialog;

public class ItemPropertiesSelectCellEdit extends G2DListSelectDialog<ItemPropertiesNode> implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	public ItemPropertiesSelectCellEdit(Component owner) {
		super(owner, new ItemPropertiesList());
	}
	
	@Override
	public Component getComponent(ObjectPropertyPanel panel) {
		ItemPropertiesNode node = getSelectedObject();
		cell_edit_component.setText(node+"");
		return cell_edit_component;
	}
	
	public ItemPropertiesNode setValue(int id) {
		ItemPropertiesNode node = Studio.getInstance().getItemManager().getNode(id);
		if (node != null) {
			getList().setSelectedValue(node, true);
		}
		return node;
	}
	
	@Override
	public Integer getValue() {
		ItemPropertiesNode node = getSelectedObject();
		if (node != null) {
			return node.getIntID();
		}
		return -1;
	}
	
	static class ItemPropertiesList extends G2DList<ItemPropertiesNode>
	{
		public ItemPropertiesList() {
			super(Studio.getInstance().getItemManager().getAllNodes());
		}
	}
}

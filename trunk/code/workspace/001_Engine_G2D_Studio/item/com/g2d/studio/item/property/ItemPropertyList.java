package com.g2d.studio.item.property;

import java.util.Vector;

import com.cell.rpg.item.ItemPropertyTypes;
import com.g2d.studio.swing.G2DList;

@SuppressWarnings("serial")
public class ItemPropertyList extends G2DList<ItemPropertyNode>
{
	public ItemPropertyList() {
		Vector<ItemPropertyNode> list_data = new Vector<ItemPropertyNode>();
		for (Class<?> type : ItemPropertyTypes.getItemPropertyTypesList()) {
			list_data.add(new ItemPropertyNode(type));
		}
		super.setListData(list_data);
		super.setLayoutOrientation(G2DList.VERTICAL);
	}
}
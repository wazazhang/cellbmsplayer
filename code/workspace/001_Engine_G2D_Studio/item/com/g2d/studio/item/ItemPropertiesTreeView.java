package com.g2d.studio.item;


import com.cell.rpg.item.ItemProperties;
import com.g2d.studio.gameedit.ObjectTreeViewDynamic;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.io.File;

public class ItemPropertiesTreeView extends ObjectTreeViewDynamic<ItemPropertiesNode, ItemProperties>
{
	private static final long serialVersionUID = 1L;

	public ItemPropertiesTreeView(File item_properties_list_file) {
		super("道具属性管理器", ItemPropertiesNode.class, ItemProperties.class, item_properties_list_file);
	}
	
	@Override
	protected ObjectGroup<ItemPropertiesNode, ItemProperties> createTreeRoot(String title) {
		return new ItemPropertiesGroup(title, this);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------------


//	-------------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
}

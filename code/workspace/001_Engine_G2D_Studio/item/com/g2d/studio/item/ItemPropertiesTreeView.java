package com.g2d.studio.item;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;

import com.cell.rpg.item.ItemProperties;
import com.cell.rpg.template.TAvatar;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.AvatarTreeView;
import com.g2d.studio.gameedit.ObjectTreeViewDynamic;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;
import com.g2d.util.AbstractDialog;

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

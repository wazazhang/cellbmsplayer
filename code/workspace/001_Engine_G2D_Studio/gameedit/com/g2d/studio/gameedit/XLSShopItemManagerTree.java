package com.g2d.studio.gameedit;

import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import com.cell.rpg.template.TShopItem;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DShopItemList;
import com.g2d.studio.gameedit.template.XLSShopItem;

@SuppressWarnings("serial")
public class XLSShopItemManagerTree extends ObjectManagerTree<XLSShopItem, TShopItem>
{
	private JButton 				open_item_list;
	private ObjectManagerTree<?, ?>	itemlist_form;
	
	public XLSShopItemManagerTree(Studio studio, ProgressForm progress, Image icon, 
			ObjectTreeView<XLSShopItem, TShopItem> tree_view) {
		super(studio, progress, icon, tree_view);
	}
	
	
	
	@Override
	void initToolbars(ObjectManager manager) 
	{
		super.initToolbars(manager);
		
		open_item_list	= new JButton();
		itemlist_form	= manager.getPageForm(DShopItemList.class);
		toolbar.addSeparator();
		{
			open_item_list.setText(
					itemlist_form.getTitle());
			open_item_list.setToolTipText(
					"打开\""+itemlist_form.getTitle()+"\"");
			open_item_list.setIcon(
					Tools.createIcon(itemlist_form.getIconImage()));
			open_item_list.addActionListener(this);
			
			int dw = 200, dh = 50;
			itemlist_form.setLocation(
					itemlist_form.getX() + dw, 
					itemlist_form.getY() + dh);
			itemlist_form.setSize(
					itemlist_form.getWidth()- dw, 
					itemlist_form.getHeight()-dh);
		}
		toolbar.add(open_item_list);
	}	
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open_item_list) {
			itemlist_form.setVisible(true);
		} else {
			super.actionPerformed(e);
		}
	}
	
}

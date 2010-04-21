package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import com.cell.rpg.RPGObject;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TItemList;
import com.cell.rpg.template.TShopItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSColumns;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.Tools;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.gameedit.dynamic.DItemList;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSShopItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

@SuppressWarnings("serial")
public class ObjectManagerTree<T extends ObjectNode<D>, D extends RPGObject> extends ManagerForm implements ActionListener
{
	final G2DWindowToolBar		toolbar	= new G2DWindowToolBar(this);
	
	final ObjectTreeView<T, D>	tree_view;
	
	public ObjectManagerTree(Studio studio, ProgressForm progress, Image icon, ObjectTreeView<T, D> tree_view) 
	{
		super(studio, progress, tree_view.getTitle(), icon);
		this.tree_view = tree_view;
		this.add(toolbar, BorderLayout.NORTH);
		this.add(tree_view, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toolbar.save) {
			try {
				saveAll();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void saveAll() throws Throwable {
		tree_view.saveAll();
		System.out.println(tree_view.data_type.getSimpleName() + " : save all");
	}
	
	

}

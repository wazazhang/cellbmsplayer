package com.g2d.studio.actor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.studio.ATreeNodeGroup;
import com.g2d.studio.ATreeNodeLeaf;
import com.g2d.studio.ATreeNodeSet;
import com.g2d.studio.Config;
import com.g2d.studio.ResourceManager;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.SetResource;


public class FormActorViewerGroup extends ATreeNodeGroup<FormActorViewer>
{
	public FormActorViewerGroup(Studio studio)  throws Exception
	{
		super(studio, Config.ROOT_CHARACTER, Config.RES_ACTOR_);
	}
	

	@Override
	public ATreeNodeLeaf<FormActorViewer>[] createViewers(ATreeNodeSet<FormActorViewer> parent)  throws Exception
	{
		SetResource res = CellSetResourceManager.getManager().getSet(parent.path + "/" + Config.RES_ACTOR_OUTPUT);
		res.initAllStreamImages();
		
		FormActorViewer[] viewers = new FormActorViewer[res.SprTable.size()];
		ATreeNodeLeaf<FormActorViewer>[] leafs = new ATreeNodeLeaf[res.SprTable.size()];
		
		int i=0;
		for (CellSetResource.SpriteSet spr : res.SprTable.values()) {
			try{
				leafs[i] = new ATreeNodeLeaf(spr.Name, parent);
				viewers[i] = new FormActorViewer(leafs[i], res, spr.Name);
				leafs[i].setUserObject(viewers[i]);
			}catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		
		return leafs;
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("overview");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FormActorsPan.showFormActorsPan(FormActorViewerGroup.this);
			}
		});
		menu.add(item);
		menu.show(studio.tree, e.getX(), e.getY());
		
	}
	
}

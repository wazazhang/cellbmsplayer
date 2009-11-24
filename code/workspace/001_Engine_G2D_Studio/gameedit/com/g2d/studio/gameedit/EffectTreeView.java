package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.AvatarTreeView.AvatarRootMenu;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.util.AbstractDialog;

public class EffectTreeView extends ObjectTreeViewDynamic<DEffect, TEffect>
{
	private static final long serialVersionUID = 1L;

	public EffectTreeView(String title, File list_file) 
	{
		super(title, DEffect.class, TEffect.class, list_file);		
	}

	@Override
	protected EffectGroup createTreeRoot(String title) {
		return new EffectGroup(title);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public class EffectGroup extends ObjectGroup<DEffect, TEffect>
	{
		private static final long serialVersionUID = 1L;

		public EffectGroup(String name) {
			super(name, 
					EffectTreeView.this.list_file,
					EffectTreeView.this.node_type, 
					EffectTreeView.this.data_type);
		}
		
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
			return new EffectGroup(name);
		}
		
		@Override
		protected boolean createObjectNode(String key, TEffect data) {
			try{
				addNode(this, new DEffect(data));
				return true;
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new EffectRootMenu(this).show(
						getTree(),
						e.getX(),
						e.getY());
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	
//	-------------------------------------------------------------------------------------------------------------------------------
	class EffectRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		EffectGroup root;
		JMenuItem add_effect = new JMenuItem("添加EFFECT");
		
		public EffectRootMenu(EffectGroup root) {
			super(root, getTree(), getTree());
			this.root = root;
			add_effect.addActionListener(this);
			add(add_effect);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------
	

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	

}

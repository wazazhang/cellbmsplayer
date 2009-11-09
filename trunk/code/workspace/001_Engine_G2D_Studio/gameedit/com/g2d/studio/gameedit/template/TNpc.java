package com.g2d.studio.gameedit.template;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;
import com.g2d.Tools;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.res.Res;

public class TNpc extends TemplateTreeNode
{
	CPJSprite cpj_sprite;
	
	public TNpc(XLSFile xls_file, XLSFullRow xls_row) {
		super(xls_file, xls_row);
	}

	@Override
	protected void onRead(MarkedHashtable data) {
		super.onRead(data);
		CPJIndex<CPJSprite> res_index = data.getObject("res_index", null);
		if (res_index != null) {
			cpj_sprite = Studio.getInstance().getCPJResourceManager().getNode(res_index);
		}
	}
	
	@Override
	protected void onWrite(MarkedHashtable data) {
		super.onWrite(data);
		if (cpj_sprite!=null) {
			CPJIndex<CPJSprite> res_index = cpj_sprite.getCPJIndex(CPJResourceType.ACTOR);
			data.put("res_index", res_index);
		}
	}
	
	public CPJSprite getCPJSprite() {
		return cpj_sprite;	
	}
	
	public void setCPJSprite(CPJSprite sprite) {
		this.cpj_sprite = sprite;
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_snapshot == null) {
			if (cpj_sprite!=null) {
				icon_snapshot = Tools.createIcon(Tools.combianImage(20, 20, cpj_sprite.getIcon(update).getImage()));
			} else {
				icon_snapshot = Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
			}
		}
		return super.getIcon(update);
	}

	public ObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new NPCObjectViewer();
		}
		return edit_component;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	class NPCObjectViewer extends ObjectViewer<TNpc>
	{
		private static final long serialVersionUID = 1L;

		public NPCObjectViewer() 
		{
			super(TNpc.this);
			
			JPanel panel = new JPanel();
			{
				JButton set_binding = new JButton("绑定资源");
				set_binding.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CPJSprite spr = new CPJResourceSelectDialog<CPJSprite>(CPJResourceType.ACTOR).showDialog();
						cpj_sprite = spr;
						icon_snapshot = null;
						Studio.getInstance().getObjectManager().repaint();
					}
				});
				panel.add(set_binding, BorderLayout.SOUTH);
			}
			table.addTab("属性", panel);
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

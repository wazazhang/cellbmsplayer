package com.g2d.studio.gameedit.template;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
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
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.XLSObjectViewer;
import com.g2d.studio.res.Res;
import com.g2d.studio.rpg.AbilityForm;

final public class XLSUnit extends XLSTemplateNode<TUnit>
{
	CPJSprite cpj_sprite;
	
	public XLSUnit(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);
		if (template_data.getDisplayNode()!=null) {
			CPJIndex<CPJSprite> spr_index = Studio.getInstance().getCPJResourceManager().getNode(
					CPJResourceType.ACTOR, 
					template_data.getDisplayNode().cpj_project_name,
					template_data.getDisplayNode().cpj_object_id);
			if (spr_index != null) {
				cpj_sprite = spr_index.getObject();
			}
		}
	}
	
	@Override
	protected TUnit newData(XLSFile xls_file, XLSFullRow xls_row) {
		return new TUnit(getIntID(), xls_row.desc);
	}
	
	public CPJSprite getCPJSprite() {
		return cpj_sprite;	
	}
	
	public void setCPJSprite(CPJSprite sprite) {
		template_data.setDisplayNode(sprite.parent.getName(), sprite.getName());
		this.cpj_sprite = sprite;
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		getData().icon_index = "0";
		if (icon_file==null) {
			icon_file = Studio.getInstance().getIconManager().getIcon(getData().icon_index);
		}
		return super.getIcon(update);
	}
	@Override
	public ImageIcon createIcon() {
		if (cpj_sprite!=null) {
			return Tools.createIcon(Tools.combianImage(20, 20, cpj_sprite.getIcon(true).getImage()));
		} else {
			return super.createIcon();
		}
	}

	public ObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new NPCObjectViewer();
		}
		return edit_component;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	class NPCObjectViewer extends XLSObjectViewer<XLSUnit> implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JPanel	page_properties;
		JButton	set_binding		= new JButton("绑定资源");

		public NPCObjectViewer() 
		{
			super(XLSUnit.this,
					new ObjectAdapters.UnitBattleTeamNodeAdapter(),
					new ObjectAdapters.UnitDropItemNodeAdapter());
			if (cpj_sprite!=null) {
				set_binding.setIcon(cpj_sprite.getIcon(false));
			}
			page_properties = new JPanel();
//			page_properties.setLayout(new GridLayout(2, 1));
			set_binding.addActionListener(this);
			page_properties.add(set_binding);			
			table.insertTab("属性", null, page_properties, null, 0);
			table.setSelectedIndex(0);
		}
		

		public void actionPerformed(ActionEvent e)
		{
			if (set_binding==e.getSource()) {
				CPJSprite spr = new CPJResourceSelectDialog<CPJSprite>(
						Studio.getInstance().getObjectManager(),
						CPJResourceType.ACTOR).showDialog();
				if (spr != null) {
					setCPJSprite(spr);
					XLSUnit.this.getIcon(true);
					set_binding.setIcon(cpj_sprite.getIcon(false));
					Studio.getInstance().getObjectManager().repaint();
				}
			}
		}
		
	}
	
		
	
	
	
	
	
	
	
	
	
	
}

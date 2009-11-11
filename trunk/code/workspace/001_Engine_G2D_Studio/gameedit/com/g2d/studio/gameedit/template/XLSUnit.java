package com.g2d.studio.gameedit.template;

import java.awt.BorderLayout;
import java.awt.Component;
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
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.res.Res;

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
		return new TUnit(xls_row.id, xls_row.desc);
	}
	
	public CPJSprite getCPJSprite() {
		return cpj_sprite;	
	}
	
	public void setCPJSprite(CPJSprite sprite) {
		template_data.setDisplayNode(sprite.parent.getName(), sprite.getName());
		this.cpj_sprite = sprite;
	}
	
	@Override
	public ImageIcon createIcon() {
		if (cpj_sprite!=null) {
			return Tools.createIcon(Tools.combianImage(20, 20, cpj_sprite.getIcon(true).getImage()));
		} else {
			return Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
		}
	}

	public TemplateObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new NPCObjectViewer();
		}
		return edit_component;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	class NPCObjectViewer extends TemplateObjectViewer<XLSUnit>
	{
		private static final long serialVersionUID = 1L;
		
		JButton set_binding = new JButton("绑定资源");
		
		public NPCObjectViewer() 
		{
			super(XLSUnit.this);
			if (cpj_sprite!=null) {
				set_binding.setIcon(cpj_sprite.getIcon(false));
			}
			set_binding.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CPJSprite spr = new CPJResourceSelectDialog<CPJSprite>(CPJResourceType.ACTOR).showDialog();
					if (spr != null) {
						setCPJSprite(spr);
						XLSUnit.this.getIcon(true);
						set_binding.setIcon(cpj_sprite.getIcon(false));
						Studio.getInstance().getObjectManager().repaint();
					}
				}
			});
			page_properties.add(set_binding, BorderLayout.SOUTH);
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

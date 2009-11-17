package com.g2d.studio.gameedit.template;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.gameedit.template.XLSUnit.NPCObjectViewer;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.icon.IconSelectDialog;
import com.g2d.studio.res.Res;

final public class XLSItem  extends XLSTemplateNode<TItem>
{
	transient IconFile icon_file;
	
	public XLSItem(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);
		if (template_data.icon_index!=null) {
			icon_file = Studio.getInstance().getIconManager().getIcon(template_data.icon_index);
		}
	}
	
	@Override
	protected TItem newData(XLSFile xlsFile, XLSFullRow xlsRow) {
		return new TItem(getIntID(), xlsRow.desc);
	}
	
	public IconFile getIconFile() {
		return icon_file;	
	}

	public void setIcon(IconFile icon) {
		template_data.icon_index = icon.icon_file_name;
		this.icon_file = icon;
	}
	
	@Override
	public ImageIcon createIcon() {
		if (icon_file!=null) {
			return Tools.createIcon(Tools.combianImage(20, 20, icon_file.image));
		} else {
			return Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
		}
	}

	

	public TemplateObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new ItemObjectViewer();
		}
		return edit_component;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	class ItemObjectViewer extends TemplateObjectViewer<XLSItem>
	{
		private static final long serialVersionUID = 1L;
		
		JButton set_binding = new JButton("设置图标");
		
		public ItemObjectViewer() 
		{
			super(XLSItem.this);
			if (icon_file!=null) {
				set_binding.setIcon(icon_file.getIcon(false));
			}
			set_binding.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					IconFile icon = new IconSelectDialog().showDialog();
					if (icon != null) {
						XLSItem.this.setIcon(icon);	
						XLSItem.this.getIcon(true);	
						set_binding.setIcon(icon_file.getIcon(false));
						Studio.getInstance().getObjectManager().repaint();
					}
				}
			});
			page_properties.add(set_binding, BorderLayout.SOUTH);
		}
		
	}
	
	
	
}

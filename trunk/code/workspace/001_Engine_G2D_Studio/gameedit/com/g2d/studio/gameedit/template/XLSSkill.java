package com.g2d.studio.gameedit.template;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.MarkedHashtable;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.XLSObjectViewer;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.icon.IconSelectDialog;
import com.g2d.studio.res.Res;

final public class XLSSkill extends XLSTemplateNode<TSkill>
{
	public XLSSkill(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);
	}
	
	@Override
	protected TSkill newData(XLSFile xlsFile, XLSFullRow xlsRow) {
		return new TSkill(getIntID(), xlsRow.desc);
	}

	public ObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
//			edit_component = new SkillObjectViewer();
			edit_component = new XLSObjectViewer<XLSSkill>(this);
		}
		return edit_component;
	}
	
	
	
//	-----------------------------------------------------------------------------------------------------------------
	
//	class SkillObjectViewer extends XLSObjectViewer<XLSSkill>
//	{
//		private static final long serialVersionUID = 1L;
//		
//		JButton set_binding = new JButton("设置图标");
//		
//		public SkillObjectViewer() 
//		{
//			super(XLSSkill.this);
//
//			if (getIconFile()!=null) {
//				set_binding.setIcon(getIconFile().getListIcon(false));
//			}
//			set_binding.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					IconFile icon = new IconSelectDialog().showDialog();
//					if (icon != null) {
//						XLSSkill.this.setIcon(icon);
//						XLSSkill.this.getIcon(true);
//						set_binding.setIcon(getIconFile().getListIcon(false));
//						Studio.getInstance().getObjectManager().repaint();
//					}
//				}
//			});
//			page_properties.add(set_binding, BorderLayout.SOUTH);
//		}
//		
//		
//		
//	}
	
	
	
}

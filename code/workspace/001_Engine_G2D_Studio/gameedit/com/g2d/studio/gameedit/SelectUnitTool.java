package com.g2d.studio.gameedit;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import com.cell.rpg.io.RPGObjectMap;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.util.AbstractFrame;

public class SelectUnitTool extends AbstractFrame
{
	private static final long serialVersionUID = 1L;
	
	private static SelectUnitTool instance;
	
	public static SelectUnitTool getUnitTool()
	{
		if (instance == null) {
			instance = new SelectUnitTool();
		}
		return instance;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------
	
	private Hashtable<JToggleButton , XLSUnit> unit_map = new Hashtable<JToggleButton, XLSUnit>();
	
	JScrollPane scroll_pan = new JScrollPane();
	
	
	
	private SelectUnitTool() 
	{
		this.setIconImage(Res.icon_edit);
		this.setSize(200, 400);
		this.setAlwaysOnTop(true);
		this.setTitle("单位面板");
		
		refresh();
		
		this.add(scroll_pan);
	}
	
	public void refresh()
	{
		JPanel 		panel 			= new JPanel(null);
		ButtonGroup button_group	= new ButtonGroup();
		
		Vector<XLSUnit> tunits 		= Studio.getInstance().getObjectManager().tree_units_view.getAllObject();
		
		int w = 32;
		int h = 32;
		int wc = 5;
		
		int i=0;
		for (XLSUnit tunit : tunits)
		{
			JToggleButton btn = new JToggleButton();
			btn.setToolTipText(tunit.getName());
			btn.setLocation(i%wc * w, i/wc * h);
			btn.setSize(w, h);
			if (tunit.getCPJSprite()!=null) {
				btn.setIcon(Tools.createIcon(Tools.combianImage(w-4, h-4, tunit.getCPJSprite().getSnapShoot())));
				unit_map.put(btn, tunit);
			}
			button_group.add(btn);
			panel.add(btn);
			i++;
		}
		
		scroll_pan.setViewportView(panel);
		
		this.repaint();
		
		System.out.println("refresh units");
	}
	
	@Override
	public void setVisible(boolean b) {
		refresh();
		super.setVisible(b);
	}
	
}

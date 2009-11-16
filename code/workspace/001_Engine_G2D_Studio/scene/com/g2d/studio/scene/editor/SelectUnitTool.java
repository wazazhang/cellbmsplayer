package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.cell.rpg.io.RPGObjectMap;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.util.AbstractFrame;

public class SelectUnitTool extends AbstractFrame implements ActionListener
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
	private Hashtable<JToggleButton , CPJSprite> res_map = new Hashtable<JToggleButton, CPJSprite>();
	
	JTabbedPane table = new JTabbedPane();

	JScrollPane	unit_scroll_pan 	= new JScrollPane();
	JLabel 		unit_label 			= new JLabel("单位:");
	JButton 	unit_refresh 		= new JButton(" 刷新 ");
	XLSUnit		selected_xls;
	
	JScrollPane	res_scroll_pan		= new JScrollPane();
	JLabel 		res_label 			= new JLabel("资源:");
	JButton 	res_refresh 		= new JButton(" 刷新 ");
	CPJSprite	selected_spr;
	
	private SelectUnitTool() 
	{
		this.setIconImage(Res.icon_edit);
		this.setSize(200, 400);
		this.setAlwaysOnTop(true);
		this.setTitle("单位面板");
		this.setLocation(
				Studio.getInstance().getX() + Studio.getInstance().getWidth(), 
				Studio.getInstance().getY()
				);
		
		{
			JPanel unit_panel = new JPanel(new BorderLayout());
			JToolBar tool_bar = new JToolBar();
			tool_bar.add(unit_label);
			tool_bar.addSeparator();
			tool_bar.add(unit_refresh);
			unit_refresh.addActionListener(this);
			
			unit_panel.add(tool_bar, BorderLayout.NORTH);
			unit_panel.add(unit_scroll_pan, BorderLayout.CENTER);
			
			table.addTab("单位", unit_panel);
		}
		{
			JPanel res_panel = new JPanel(new BorderLayout());
			JToolBar tool_bar = new JToolBar();
			tool_bar.add(res_label);
			tool_bar.addSeparator();
			tool_bar.add(res_refresh);
			res_refresh.addActionListener(this);
			
			res_panel.add(tool_bar, BorderLayout.NORTH);
			res_panel.add(res_scroll_pan, BorderLayout.CENTER);
			table.addTab("不可破坏", res_panel);
		}
		
		this.add(table, BorderLayout.CENTER);
		
		refreshXLSUnit();
		
		refreshCPJ();
	}
	
	void refreshXLSUnit()
	{
		selected_xls = null;
		unit_map.clear();
		JPanel 		panel 			= new JPanel(null);
		ButtonGroup button_group	= new ButtonGroup();
		
		Vector<XLSUnit> tunits 		= Studio.getInstance().getObjectManager().getObjects(XLSUnit.class);
		
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
				btn.addActionListener(this);
				unit_map.put(btn, tunit);
			}
			button_group.add(btn);
			panel.add(btn);
			i++;
		}
		
		unit_scroll_pan.setViewportView(panel);
		
		System.out.println("refresh units");
	}
	
	void refreshCPJ()
	{
		selected_spr = null;
		res_map.clear();
		JPanel 		panel 			= new JPanel(null);
		ButtonGroup button_group	= new ButtonGroup();
		
		Vector<CPJSprite> actors 	= Studio.getInstance().getCPJResourceManager().getNodes(CPJResourceType.ACTOR, CPJSprite.class);
		
		int w = 32;
		int h = 32;
		int wc = 5;
		
		int i=0;
		for (CPJSprite actor : actors)
		{
			JToggleButton btn = new JToggleButton();
			btn.setToolTipText(actor.getName());
			btn.setLocation(i%wc * w, i/wc * h);
			btn.setSize(w, h);
			btn.setIcon(Tools.createIcon(Tools.combianImage(w-4, h-4, actor.getSnapShoot())));
			btn.addActionListener(this);
			res_map.put(btn, actor);
			button_group.add(btn);
			panel.add(btn);
			i++;
		}
		
		res_scroll_pan.setViewportView(panel);
		
		System.out.println("refresh resource");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == unit_refresh) {
			this.refreshXLSUnit();
		} 
		else if (e.getSource() == res_refresh) {
			this.refreshCPJ();
		}
		else if (unit_map.containsKey(e.getSource())) {
			XLSUnit unit = unit_map.get(e.getSource());
			if (unit != null) {
				unit_label.setText("单位:" + unit.getName());
				selected_xls = unit;
				selected_xls.getCPJSprite();
				selected_xls.getCPJSprite().getDisplayObject();
				this.repaint(100);
			}
		}
		else if (res_map.containsKey(e.getSource())) {
			CPJSprite spr = res_map.get(e.getSource());
			if (spr != null) {
				res_label.setText("单位:" + spr.getName());
				selected_spr = spr;
				selected_spr.getDisplayObject();
				this.repaint(100);
			}
		}
	}
	
	public XLSUnit getSelectedUnit() {
		if (table.getSelectedIndex() == 0) {
			return selected_xls;
		}
		return null;
	}
	
	public CPJSprite getSelectedSpr() {
		if (table.getSelectedIndex() == 1) {
			return selected_spr;
		}
		return null;
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);		
		refreshXLSUnit();
		refreshCPJ();
	}
	
}

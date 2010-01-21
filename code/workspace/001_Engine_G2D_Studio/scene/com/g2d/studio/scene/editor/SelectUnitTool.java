package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
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
	final int icon_size			= 32;
	final int icon_column_count	= 5;
	
	private Hashtable<JToggleButton , XLSUnit> unit_map = new Hashtable<JToggleButton, XLSUnit>();
	private Hashtable<JToggleButton , CPJSprite> res_map = new Hashtable<JToggleButton, CPJSprite>();
	
	JTabbedPane table = new JTabbedPane();

	JScrollPane		unit_scroll_pan 	= new JScrollPane();
	JLabel 			unit_label 			= new JLabel("单位:");
	JButton 		unit_refresh 		= new JButton(" 刷新 ");	
	JProgressBar	unit_progress		= new JProgressBar();
	XLSUnit			selected_xls;
	
	JScrollPane		res_scroll_pan		= new JScrollPane();
	JLabel 			res_label 			= new JLabel("资源:");
	JButton 		res_refresh 		= new JButton(" 刷新 ");
	JProgressBar	res_progress		= new JProgressBar();
	CPJSprite		selected_spr;
	
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
			unit_progress.setStringPainted(true);
			unit_scroll_pan.getVerticalScrollBar().setUnitIncrement(icon_size);
			
			unit_panel.add(tool_bar, BorderLayout.NORTH);
			unit_panel.add(unit_scroll_pan, BorderLayout.CENTER);
			unit_panel.add(unit_progress, BorderLayout.SOUTH);
			table.addTab("单位", unit_panel);
		}
		{
			JPanel res_panel = new JPanel(new BorderLayout());
			JToolBar tool_bar = new JToolBar();
			tool_bar.add(res_label);
			tool_bar.addSeparator();
			tool_bar.add(res_refresh);
			res_refresh.addActionListener(this);
			res_progress.setStringPainted(true);
			res_scroll_pan.getVerticalScrollBar().setUnitIncrement(icon_size);
			
			res_panel.add(tool_bar, BorderLayout.NORTH);
			res_panel.add(res_scroll_pan, BorderLayout.CENTER);
			res_panel.add(res_progress, BorderLayout.SOUTH);
			table.addTab("不可破坏", res_panel);
		}
		
		this.add(table, BorderLayout.CENTER);
		
		refreshXLSUnit();
		
		refreshCPJ();
	}
	
	void refreshXLSUnit()
	{
		unit_refresh.setEnabled(false);
		
		new Thread()
		{
			@Override
			public void run() 
			{
				this.setPriority(MIN_PRIORITY);
				try{
					HashMap<JToggleButton , XLSUnit> map = new HashMap<JToggleButton, XLSUnit>();
					JPanel 		panel 			= new JPanel(null);
					ButtonGroup button_group	= new ButtonGroup();
		
					Vector<XLSUnit> tunits 		= Studio.getInstance().getObjectManager().getObjects(
							XLSUnit.class);
					
					unit_progress.setMaximum(tunits.size());
					
					int mw = 0;
					int mh = 0;
					
					int w = icon_size;
					int h = icon_size;
					int wc = icon_column_count;
					
					int i=0;
					for (XLSUnit tunit : tunits)
					{
						JToggleButton btn = new JToggleButton();
						btn.setToolTipText(tunit.getName());
						btn.setLocation(i%wc * w, i/wc * h);
						btn.setSize(w, h);
						if (tunit.getCPJSprite()!=null) {
							btn.setIcon(Tools.createIcon(Tools.combianImage(w-4, h-4, tunit.getCPJSprite().getSnapShoot())));
							btn.addActionListener(SelectUnitTool.this);
							map.put(btn, tunit);
						}
						button_group.add(btn);
						panel.add(btn);
						mw = Math.max(mw, btn.getX() + btn.getWidth());
						mh = Math.max(mh, btn.getY() + btn.getHeight());
						i++;
						unit_progress.setValue(i);
						unit_progress.setString(tunit.getName() + 
								"    " + i +"/" + tunits.size());
					}
	
					panel.setSize(mw, mh);
					panel.setPreferredSize(new Dimension(mw, mh));
					panel.setMinimumSize(new Dimension(mw, mh));
					
	
					selected_xls = null;
					unit_map.clear();
					unit_map.putAll(map);
					unit_scroll_pan.setViewportView(panel);
					unit_progress.setString(i +"/" + tunits.size());
					
					System.out.println("refresh units");
				} 
				finally {
					unit_refresh.setEnabled(true);
				}
			}
		}.start();
		
		
		
	}
	
	void refreshCPJ()
	{

		res_refresh.setEnabled(false);
		
		new Thread()
		{
			@Override
			public void run() 
			{				
				this.setPriority(MIN_PRIORITY);
				try{
					Hashtable<JToggleButton , CPJSprite> map = new Hashtable<JToggleButton, CPJSprite>();
					JPanel 		panel 			= new JPanel(null);
					ButtonGroup button_group	= new ButtonGroup();
					
					Vector<CPJSprite> actors 	= Studio.getInstance().getCPJResourceManager().getNodes(
							CPJResourceType.ACTOR, 
							CPJSprite.class);
	
					res_progress.setMaximum(actors.size());
					
					int mw = 0;
					int mh = 0;
					
					int w = icon_size;
					int h = icon_size;
					int wc = icon_column_count;
					
					int i=0;
					for (CPJSprite actor : actors)
					{
						JToggleButton btn = new JToggleButton();
						btn.setToolTipText(actor.getName());
						btn.setLocation(i%wc * w, i/wc * h);
						btn.setSize(w, h);
						btn.setIcon(Tools.createIcon(Tools.combianImage(w-4, h-4, actor.getSnapShoot())));
						btn.addActionListener(SelectUnitTool.this);
						map.put(btn, actor);
						button_group.add(btn);
						panel.add(btn);
						mw = Math.max(mw, btn.getX() + btn.getWidth());
						mh = Math.max(mh, btn.getY() + btn.getHeight());
						i++;
						res_progress.setValue(i);
						res_progress.setString(actor.getName() + 
								"    " + i +"/" + actors.size());
					}
	
					panel.setSize(mw, mh);
					panel.setPreferredSize(new Dimension(mw, mh));
					panel.setMinimumSize(new Dimension(mw, mh));
	
					selected_spr = null;
					res_map.clear();
					res_map.putAll(map);
					res_scroll_pan.setViewportView(panel);
					res_progress.setString(i +"/" + actors.size());

					System.out.println("refresh resource");
				}
				finally{
					res_refresh.setEnabled(true);
				}
			}
		}.start();
		
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

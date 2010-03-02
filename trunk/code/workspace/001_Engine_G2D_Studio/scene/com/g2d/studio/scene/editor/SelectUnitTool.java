package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.util.AbstractFrame;

public class SelectUnitTool extends AbstractFrame// implements ActionListener
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

	JTabbedPane table = new JTabbedPane();

	XLSUnitPanel	page_xls_unit_panel;
	CPJSpritePanel	page_cpj_sprite_panel;
	DEffectPanel	page_deffect_panel;
	
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

		this.page_xls_unit_panel		= new XLSUnitPanel();
		this.table.addTab("单位", page_xls_unit_panel);
		this.page_xls_unit_panel.onRefresh();
		
		this.page_cpj_sprite_panel	= new CPJSpritePanel();
		this.table.addTab("不可破坏", page_cpj_sprite_panel);
		this.page_cpj_sprite_panel.onRefresh();
		
		this.page_deffect_panel	= new DEffectPanel();
		this.table.addTab("特效", page_deffect_panel);
		this.page_deffect_panel.onRefresh();
		
		this.add(table, BorderLayout.CENTER);
	}
	
	public XLSUnit getSelectedUnit() {
		if (table.getSelectedIndex() == 0) {
			return this.page_xls_unit_panel.getSelected();
		}
		return null;
	}
	
	public CPJSprite getSelectedSpr() {
		if (table.getSelectedIndex() == 1) {
			return this.page_cpj_sprite_panel.getSelected();
		}
		return null;
	}

	public DEffect getSelectedEffect() {
		if (table.getSelectedIndex() == 2) {
			return this.page_deffect_panel.getSelected();
		}
		return null;
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);		
		this.page_xls_unit_panel.onRefresh();
		this.page_cpj_sprite_panel.onRefresh();
		this.page_deffect_panel.onRefresh();
	}
	
//	----------------------------------------------------------------------------------------------------------------

	class XLSUnitPanel extends ObjectPanel<XLSUnit>
	{
		public XLSUnitPanel() {
			super(XLSUnit.class, "单位");
		}
		
		@Override
		protected void onRefresh() 
		{
			refresh.setEnabled(false);
			
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
						
						progress.setMaximum(tunits.size());
						
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
								btn.addActionListener(XLSUnitPanel.this);
								map.put(btn, tunit);
							}
							button_group.add(btn);
							panel.add(btn);
							mw = Math.max(mw, btn.getX() + btn.getWidth());
							mh = Math.max(mh, btn.getY() + btn.getHeight());
							i++;
							progress.setValue(i);
							progress.setString(tunit.getName() + 
									"    " + i +"/" + tunits.size());
						}
		
						panel.setSize(mw, mh);
						panel.setPreferredSize(new Dimension(mw, mh));
						panel.setMinimumSize(new Dimension(mw, mh));
						
						selected_object = null;
						res_map.clear();
						res_map.putAll(map);
						scroll_pan.setViewportView(panel);
						progress.setString(i +"/" + tunits.size());
						
						System.out.println("refresh units");
					} 
					finally {
						refresh.setEnabled(true);
					}
				}
			}.start();
		}
		
		@Override
		protected void onSelected(XLSUnit spr) {
			spr.getCPJSprite();
			spr.getCPJSprite().getDisplayObject();
		}
	}
	
	class CPJSpritePanel extends ObjectPanel<CPJSprite>
	{
		public CPJSpritePanel() {
			super(CPJSprite.class, "精灵");
		}
		
		
		@Override
		protected void onRefresh() 
		{
			refresh.setEnabled(false);
			
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
		
						progress.setMaximum(actors.size());
						
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
							try{
								btn.setIcon(Tools.createIcon(Tools.combianImage(w-4, h-4, actor.getSnapShoot())));
							} catch (Exception er){
								er.printStackTrace();
							}
							btn.addActionListener(CPJSpritePanel.this);
							map.put(btn, actor);
							button_group.add(btn);
							panel.add(btn);
							mw = Math.max(mw, btn.getX() + btn.getWidth());
							mh = Math.max(mh, btn.getY() + btn.getHeight());
							i++;
							progress.setValue(i);
							progress.setString(actor.getName() + 
									"    " + i +"/" + actors.size());
						}
		
						panel.setSize(mw, mh);
						panel.setPreferredSize(new Dimension(mw, mh));
						panel.setMinimumSize(new Dimension(mw, mh));
		
						selected_object = null;
						res_map.clear();
						res_map.putAll(map);
						scroll_pan.setViewportView(panel);
						progress.setString(i +"/" + actors.size());

						System.out.println("refresh resource");
					}
					finally{
						refresh.setEnabled(true);
					}
				}
			}.start();
			
		
		}
		
		@Override
		protected void onSelected(CPJSprite spr) {
			spr.getDisplayObject();
		}
	}
	
	class DEffectPanel extends ObjectPanel<DEffect>
	{
		public DEffectPanel() {
			super(DEffect.class, "特效");
		}
		
		@Override
		protected void onRefresh() {

			refresh.setEnabled(false);
			
			new Thread()
			{
				@Override
				public void run() 
				{
					this.setPriority(MIN_PRIORITY);
					try{
						HashMap<JToggleButton , DEffect> map = new HashMap<JToggleButton, DEffect>();
						JPanel 		panel 			= new JPanel(new GridLayout(0, 1));
						ButtonGroup button_group	= new ButtonGroup();
						Vector<DEffect> effects 	= Studio.getInstance().getObjectManager().getObjects(DEffect.class);
						
						progress.setMaximum(effects.size());
						
						int i=0;
						for (DEffect effect : effects)
						{
							JToggleButton btn = new JToggleButton(effect.getName());
							btn.setToolTipText(effect.getName());
							btn.setIcon(effect.getIcon(false));
							btn.addActionListener(DEffectPanel.this);
							map.put(btn, effect);
							
							button_group.add(btn);
							panel.add(btn);
							
							i++;
							progress.setValue(i);
							progress.setString(effect.getName() + 
									"    " + i +"/" + effects.size());
						}

						selected_object = null;
						res_map.clear();
						res_map.putAll(map);
						scroll_pan.setViewportView(panel);
						progress.setString(i +"/" + effects.size());
						
						System.out.println("refresh effects");
					} 
					finally {
						refresh.setEnabled(true);
					}
				}
			}.start();
		
		}
		
		@Override
		protected void onSelected(DEffect spr) {
			
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------------
	
	abstract static class ObjectPanel<T extends G2DTreeNode<?>> extends JPanel implements ActionListener
	{	
		final static int icon_size			= 32;
		final static int icon_column_count	= 5;
	
		String			title			;
		
		JScrollPane		scroll_pan 		= new JScrollPane();
		JLabel 			label 			= new JLabel("单位:");
		JButton 		refresh 		= new JButton(" 刷新 ");	
		JProgressBar	progress		= new JProgressBar();
		
		Hashtable<JToggleButton , T> res_map = new Hashtable<JToggleButton, T>();
		T selected_object;
		
		ObjectPanel(Class<T> type, String title)
		{
			super(new BorderLayout());
			
			this.title = title;
			
			label.setText(title+":");
			
			JToolBar tool_bar = new JToolBar();
			tool_bar.add(label);
			tool_bar.addSeparator();
			tool_bar.add(refresh);
			refresh.addActionListener(this);
			progress.setStringPainted(true);
			scroll_pan.getVerticalScrollBar().setUnitIncrement(icon_size);
			
			this.add(tool_bar, BorderLayout.NORTH);
			this.add(scroll_pan, BorderLayout.CENTER);
			this.add(progress, BorderLayout.SOUTH);		
		}
		
		public T getSelected() {
			return selected_object;
		}
		
		@Override
		final public void actionPerformed(ActionEvent e) {
			if (e.getSource() == refresh) {
				onRefresh();
			} 
			else if (res_map.containsKey(e.getSource())) {
				selected_object = res_map.get(e.getSource());
				if (selected_object != null) {
					label.setText(title + ":" + selected_object.getName());
					onSelected(selected_object);
					this.repaint(100);
				}
			}
		}
		
		abstract protected void onRefresh() ;
		
		abstract protected void onSelected(T spr);
	}
}

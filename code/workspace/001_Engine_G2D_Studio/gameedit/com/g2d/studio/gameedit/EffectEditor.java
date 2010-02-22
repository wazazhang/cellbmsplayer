package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.rpg.particle.Layer;
import com.cell.rpg.template.TEffect;


public class EffectEditor extends JSplitPane
{
	final TEffect effect;
	
	Layers 		layers;
	LayerEdit 	layer_edit;
	
	public EffectEditor(TEffect e) 
	{
		this.effect		= e;
		this.layers		= new Layers();
		this.layer_edit	= new LayerEdit();
		
		setLeftComponent(layers);
		setRightComponent(layer_edit);
		
		try{
			layers.setData(effect);
		}catch(Exception err){
			err.printStackTrace();
		}
	}

	
//	--------------------------------------------------------------------------------------------------------------------
	
	class Layers extends JPanel implements ActionListener, ListSelectionListener
	{
		JList layers = new JList();
		
		JButton layer_add	= new JButton("添加层");
		JButton layer_del	= new JButton("删除层");
		
		public Layers() 
		{
			super(new BorderLayout());
			
			JToolBar bar = new JToolBar();
			bar.add(layer_add);
			bar.add(layer_del);
			layer_add.addActionListener(this);
			layer_del.addActionListener(this);
			
			layers.addListSelectionListener(this);
			
			super.add(bar, BorderLayout.NORTH);
			super.add(new JScrollPane(layers), BorderLayout.CENTER);
		}
		
		void setData(TEffect e) {
			Vector<Layer> list = new Vector<Layer>();
			for (Layer l : e.particles) {
				list.add(l);
			}
			layers.setListData(list);
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Object obj = layers.getSelectedValue();
			if (obj instanceof Layer) {
				layer_edit.setData((Layer)obj);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == layer_add) {
				effect.particles.addLayer(new Layer());
			} else if (e.getSource() == layer_del) {
				Object obj = layers.getSelectedValue();
				if (obj instanceof Layer) {
					effect.particles.removeLayer((Layer)obj);
				}
			}
			setData(effect);
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	class LayerEdit extends JTabbedPane
	{
		JPanel page_appearance	= new PageAppearance();
		JPanel page_origin		= new PageOrigin();
		JPanel page_emission	= new PageEmission();
		JPanel page_flow		= new PageFlow();
		JPanel page_scene		= new PageScene();
		JPanel page_influences	= new PageInfluences();
		
		public LayerEdit() {
			addTab("外观", page_appearance);
			addTab("原点", page_origin);
			addTab("发射", page_emission);
			addTab("循环", page_flow);
			addTab("场景", page_scene);
			addTab("影响", page_influences);
		}
		
		void setData(Layer layer) {
			
		}
		
		
		class PageAppearance extends JPanel
		{
			
		}
		
		class PageOrigin extends JPanel
		{
			
		}
		
		class PageEmission extends JPanel
		{
			
		}
		
		class PageFlow extends JPanel
		{
			
		}
		
		class PageScene extends JPanel
		{
			
		}
		
		class PageInfluences extends JPanel
		{
			
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

}


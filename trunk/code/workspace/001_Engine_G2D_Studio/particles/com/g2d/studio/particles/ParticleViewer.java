package com.g2d.studio.particles;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.cell.util.EnumManager.ValueEnum;
import com.g2d.CompositeRule;
import com.g2d.display.particle.ParticleData;
import com.g2d.display.particle.ParticleDisplay;
import com.g2d.display.ui.ComboBox;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.EffectEditor;
import com.g2d.studio.res.Res;

public class ParticleViewer extends JFrame implements ActionListener
{
	final private EffectEditor cur_edit;

	DisplayObjectPanel	display_object_panel	= new DisplayObjectPanel();
	JToolBar 			tools 					= new JToolBar();
	JComboBox			composite_list			= new JComboBox(CompositeRule.getEnumNames());
	
	JCheckBox			toggle_show_debug		= new JCheckBox("显示坐标系");
	JCheckBox			toggle_show_origin		= new JCheckBox("显示产生范围");
	
	ParticleStage		stage;
	
	public ParticleViewer(EffectEditor data) 
	{
		super.setTitle("粒子查看器 : " + data.getData().getName());
		super.setSize(800, 600);
		super.setAlwaysOnTop(true);
		
		this.cur_edit = data;
		{
//			tools.add(new JLabel("混合模式"));
//			tools.add(composite_list);
//			composite_list.addActionListener(this);
//			composite_list.setSelectedItem(CompositeRule.SRC_OVER.name());
			
			tools.add(toggle_show_debug);
			toggle_show_debug.addActionListener(this);
			
			tools.add(toggle_show_origin);
			toggle_show_origin.addActionListener(this);
		}
		add(display_object_panel, BorderLayout.CENTER);
		add(tools, BorderLayout.SOUTH);
		
		stage = new ParticleStage();
		display_object_panel.getCanvas().setFPS(Config.DEFAULT_FPS);
		display_object_panel.getCanvas().changeStage(stage, data);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == composite_list) {
			try{
				String name = composite_list.getSelectedItem().toString();
				CompositeRule rule = CompositeRule.forName(name);
//				ParticleDisplay.composite_rule = rule.rule;
				System.out.println("Change composite rule : " + rule);
			}catch(Exception err){
				err.printStackTrace();
			}
		} 
		else if (e.getSource() == toggle_show_debug) {
			stage.is_show_cross = toggle_show_debug.isSelected();
		}
		else if (e.getSource() == toggle_show_origin) {
			stage.is_show_spawn_region = toggle_show_origin.isSelected();
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) {
			display_object_panel.start();
		} else {
			display_object_panel.stop();
		}
		super.setVisible(b);
	}
	
}

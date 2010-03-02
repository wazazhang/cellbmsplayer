package com.g2d.studio.particles;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	DisplayObjectPanel	display_object_panel	= new DisplayObjectPanel();
	
	JComboBox			composite_list			= new JComboBox(CompositeRule.getEnumNames());
	
	private EffectEditor cur_edit;
	
	public ParticleViewer() {
		super.setTitle("粒子查看器");
//		super.setIconImage(Res.icon_scene_graph);
//		super.setLocation(Studio.getInstance().getIconManager().getLocation());
//		super.setSize(Studio.getInstance().getIconManager().getSize());
		super.setSize(800, 600);
		super.setAlwaysOnTop(true);

		super.add(display_object_panel, BorderLayout.CENTER);
		
		JToolBar tools = new JToolBar();
		tools.add(new JLabel("混合模式"));
		tools.add(composite_list);
		composite_list.addActionListener(this);
//		super.add(tools, BorderLayout.SOUTH);
		
		display_object_panel.getCanvas().setFPS(Config.DEFAULT_FPS);
		
		composite_list.setSelectedItem(CompositeRule.SRC_OVER.name());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			String name = composite_list.getSelectedItem().toString();
			CompositeRule rule = CompositeRule.forName(name);
//			ParticleDisplay.composite_rule = rule.rule;
			System.out.println("Change composite rule : " + rule);
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
	public void setParticleData(EffectEditor data) {
		this.cur_edit = data;
		this.setTitle("粒子查看器" + ": " + data.getData().getName());
		ParticleStage stage = new ParticleStage();
		display_object_panel.getCanvas().changeStage(stage, data);
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
	
//	-------------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {
		ParticleViewer view = new ParticleViewer(){
			@Override
			public void setVisible(boolean b) {
				super.setVisible(b);
				if (!b) {
					System.exit(0);
				}
			}
		};
		view.setVisible(true);
	}
}

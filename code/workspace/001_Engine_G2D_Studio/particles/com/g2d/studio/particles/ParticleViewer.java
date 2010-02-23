package com.g2d.studio.particles;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.g2d.display.particle.ParticleData;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.EffectEditor;
import com.g2d.studio.res.Res;

public class ParticleViewer extends JFrame
{
	DisplayObjectPanel display_object_panel = new DisplayObjectPanel();
	
	private EffectEditor cur_edit;
	
	public ParticleViewer() {
		super.setTitle("粒子查看器");
//		super.setIconImage(Res.icon_scene_graph);
//		super.setLocation(Studio.getInstance().getIconManager().getLocation());
//		super.setSize(Studio.getInstance().getIconManager().getSize());
		super.setSize(800, 600);
		super.add(display_object_panel, BorderLayout.CENTER);
		super.setAlwaysOnTop(true);
		display_object_panel.getCanvas().setFPS(Config.DEFAULT_FPS);
	}

	public void setParticleData(EffectEditor data) {
		this.cur_edit = data;
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

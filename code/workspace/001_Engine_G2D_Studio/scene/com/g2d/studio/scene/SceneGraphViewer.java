package com.g2d.studio.scene;

import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;

public class SceneGraphViewer extends AbstractFrame
{
	public SceneGraphViewer() 
	{
		super.setTitle("场景图查看器");
		super.setIconImage(Res.icon_scene_graph);
		super.setLocation(Studio.getInstance().getIconManager().getLocation());
		super.setSize(Studio.getInstance().getIconManager().getSize());
	}
}

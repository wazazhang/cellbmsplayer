package com.g2d.studio.scene.entity;

import java.io.File;

import com.cell.rpg.scene.Scene;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.scene.SceneManager;
import com.g2d.studio.swing.G2DTreeNodeGroup;

public class SceneGroup extends G2DTreeNodeGroup<SceneNode>
{
	private static final long serialVersionUID = 1L;
	
	public SceneGroup(String name) {
		super(name);
	}
	
	@Override
	protected G2DTreeNodeGroup<?> createGroupNode(String name) {
		return new SceneGroup(name);
	}
	
	@Override
	protected boolean addLeafNode(String name) {
		if (name.endsWith(".xml")) {
			SceneManager manager = SceneManager.getInstance();
			File scene_dir = manager.scene_dir;
			File file = new File(scene_dir, name);
			if (file.exists()) {
				manager.addScene(file, this);
				return true;
			}
		}
		return false;
	}
	
//	@Override
//	protected boolean createObjectNode(String key, Scene data) {
//		SceneManager manager = SceneManager.getInstance();
//		manager.addScene(file, this);
//		return false;
//	}
//	
//	@Override
//	protected boolean addSubNode(String name) {
//		if (name.endsWith(".xml")) {
//			SceneManager manager = SceneManager.getInstance();
//			File scene_dir = manager.scene_dir;
//			File file = new File(scene_dir, name);
//			if (file.exists()) {
//				manager.addScene(file, this);
//				return true;
//			}
//		}
//		return false;
//	}
	
}

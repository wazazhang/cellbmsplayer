package com.g2d.studio.scene.entity;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.rpg.scene.Scene;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.scene.editor.SceneEditor;

final public class SceneNode extends DynamicNode<Scene>
{
	final private	CPJIndex<CPJWorld>	world_index;
	transient		CPJWorld			world_display;
	transient		SceneEditor			world_editor;
	
	
	public SceneNode(IDynamicIDFactory<?> factory, String name, CPJIndex<CPJWorld> world_index) {
		super(factory, name);
		this.world_index = world_index;
		this.bind_data.scene_node = new com.cell.rpg.display.SceneNode(
				world_index.cpj_file_name,
				world_index.set_object_name);
		this.bind_data.scene_node.width		= world_index.getObject().getSetObject().Width;
		this.bind_data.scene_node.height	= world_index.getObject().getSetObject().Height;
	}

	public SceneNode(Scene scene) {
		super(scene, Integer.parseInt(scene.id), scene.name);
		this.world_index = new CPJIndex<CPJWorld>(
				CPJResourceType.WORLD, 
				scene.scene_node.cpj_project_name, 
				scene.scene_node.cpj_object_id);
		try {
			CPJWorld res_world = Studio.getInstance().getCPJResourceManager().getNode(world_index);
			this.bind_data.scene_node.width		= res_world.getSetObject().Width;
			this.bind_data.scene_node.height	= res_world.getSetObject().Height;
			System.out.println("load a scene : " + scene.name + "   (" + scene.id + ")");
		} catch(Exception err) {
			throw new RuntimeException("场景\""+scene.name+"("+scene.id+")\"读取错误！", err);
		}
	}
	
	@Override
	protected Scene newData(IDynamicIDFactory<?> factory, String name, Object... args) {
		return new Scene(getIntID(), name);
	}
	
	public String getResourceName() {
		return world_index.set_object_name;
	}
	
	@Override
	public boolean setName(String name) {
		if(super.setName(name)){
			getData().name = name;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected ImageIcon createIcon() {
		if (getWorldDisplay()!=null) {
			ImageIcon ico = getWorldDisplay().getIcon(false);
			if (ico != null) {
				return Tools.createIcon(Tools.combianImage(60, 40, ico.getImage()));
			}
		}
		return null;
	}
	
	public CPJWorld getWorldDisplay() {
		if (world_display==null) {
			world_display = Studio.getInstance().getCPJResourceManager().getNode(world_index);
		}
		return world_display;
	}

	public void saveSnapshot(BufferedImage image)
	{
		if (getWorldDisplay()!=null) {
			getWorldDisplay().saveSnapshot(image);
			getIcon(true);
		}
	}
	
	public void cleanSceneEditor() {
		this.world_editor = null;
		this.bind_data.scene_node.width		= world_index.getObject().getSetObject().Width;
		this.bind_data.scene_node.height	= world_index.getObject().getSetObject().Height;
	}
	
	public SceneEditor getSceneEditor() {
		if (world_editor==null) {
			world_editor = new SceneEditor(this);
		}
		return world_editor;
	}
	
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
}
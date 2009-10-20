package com.g2d.cell.game.ui;

import com.g2d.cell.game.Scene;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.ScrollBar.ScrollBarPair;
import com.g2d.editor.UIComponentEditor;

public class ScenePanel extends Container
{
	Scene 			scene;
	ScrollBarPair	scrollbar;
	
	public ScenePanel() {
		this.scrollbar 	= new ScrollBarPair();
		this.addChild(scrollbar);
	}
	
	public ScenePanel(Scene scene) {
		this();
		this.setScene(scene);
	}
	
	public void setScene(Scene scene) {
		if (this.scene != null) {
			this.removeChild(scene, true);
		}
		this.scene = scene;
		this.addChild(scene);
		this.setSize(scene.getWidth(), scene.getHeight());
	}
	
	public Scene getScene() {
		return scene;
	}
	
	@Override
	public void update() 
	{
		int view_x		= layout.BorderSize;
		int view_y		= layout.BorderSize;
		int camera_w	= getWidth() -scrollbar.vScroll.getWidth() -(layout.BorderSize<<1);
		int camera_h	= getHeight()-scrollbar.hScroll.getHeight()-(layout.BorderSize<<1);
		int world_w		= scene.getWorld().getWidth();
		int world_h		= scene.getWorld().getHeight();
		double camera_x	= scrollbar.hScroll.getValue();
		double camera_y	= scrollbar.vScroll.getValue();
		
		scrollbar.hScroll.setMax(world_w);
		scrollbar.vScroll.setMax(world_h);
		scrollbar.hScroll.setValue(camera_x, camera_w);
		scrollbar.vScroll.setValue(camera_y, camera_h);
		
		scene.setLocation(view_x, view_y);
		scene.setSize(camera_w, camera_h);
		scene.locateCamera(camera_x, camera_y);

		super.update();
	}
	
	public void locationCameraCenter(double x, double y) {
		scrollbar.hScroll.setValue(x-scene.getCameraWidth()/2);
		scrollbar.vScroll.setValue(y-scene.getCameraHeight()/2);
	}
	
	public void locationCamera(double x, double y) {
		scrollbar.hScroll.setValue(x);
		scrollbar.vScroll.setValue(y);
	}
	
	@Override
	public UIComponentEditor<?> createEditorForm() {
		return null;
	}
	
	public boolean isCatchedScene() {
		return scene!= null && getChildAtPos(getMouseX(), getMouseY()) == scene;
	}
}

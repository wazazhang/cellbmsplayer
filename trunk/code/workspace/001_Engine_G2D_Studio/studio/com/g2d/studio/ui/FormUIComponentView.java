package com.g2d.studio.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import javax.swing.JToggleButton;

import com.g2d.studio.Version;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.ui.Form;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.AFormDisplayObjectViewer;
import com.g2d.studio.ATreeNodeLeaf;


public class FormUIComponentView extends AFormDisplayObjectViewer<UIComponent> 
{

	private static final long serialVersionUID = Version.VersionGS;
	
	class UIStage extends Stage
	{
		public UIStage() {}

		public void added(DisplayObjectContainer parent) {}

		public void removed(DisplayObjectContainer parent) {}
		
		public void update() 
		{
			synchronized (this) {
				if (!this.contains(view_object)) {
					this.addChild(view_object);
				}
			}
			
		}
		
		public void render(Graphics2D g) {
			g.setColor(Color.WHITE);
			g.fill(local_bounds);
		}
		
	}
	
	
	
	public FormUIComponentView(ATreeNodeLeaf<FormUIComponentView> leaf, UIComponent component)
	{
		super(leaf, component);
		
		no_save = true;
		canvas.getCanvasAdapter().setStage(new UIStage());
		canvas.getCanvasAdapter().getStage().addChild(view_object);
		this.setSize(view_object.getWidth()*2, view_object.getHeight()*2);
	}
	
	@Override
	public void loadObject(ObjectInputStream is) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveObject(ObjectOutputStream os) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
	
}

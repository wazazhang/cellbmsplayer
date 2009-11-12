package com.g2d.studio.scene.units;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import com.cell.rpg.scene.Region;
import com.g2d.annotation.Property;
import com.g2d.game.rpg.MoveableUnit;
import com.g2d.game.rpg.Unit;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.Version;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.MenuUnit;


@Property("一个范围，通常用于触发事件")
public class SceneRegion extends com.g2d.game.rpg.Unit implements SceneUnitTag<Region>
{
	private static final long serialVersionUID = Version.VersionGS;

	final SceneEditor		editor;
	final public Region 	region;
	
	@Property("color")
	public Color 			color = new Color(0x8000ff00, true);

//	--------------------------------------------------------------------------------------------------------
	
	public SceneRegion(SceneEditor editor, Rectangle rect) 
	{
		this.editor 	= editor;
		this.local_bounds	= rect;
		this.priority 		= -Integer.MAX_VALUE / 2;
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		this.region = new Region(getID() + "", 
				getX(), getY(),
				getWidth(), getHeight());
	}
	
	public SceneRegion(SceneEditor editor, Region in) throws IOException
	{
		this.editor = editor;
		this.region = in;
		{
			this.setID(
					editor.getGameScene().getWorld(), 
					region.name);
			this.setLocation(
					region.x,
					region.y);
			this.color = new Color(
					region.color, true);
			this.alpha = 
					region.alpha;
			this.setSize(
					region.width, 
					region.height);
		}
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		
	}
	
	public Region onWrite()
	{
		region.name			= getID() + "";
		region.x			= getX();
		region.y			= getY();
		region.color		= color.getRGB();
		region.alpha		= alpha;
		region.width		= getWidth();
		region.height		= getHeight();
		return region;
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {
		enable				= true;
		enable_drag			= true;
		enable_input		= true;
		enable_drag_resize	= true;
		enable_focus 		= true;
		enable_input 		= true;
		super.added(parent);
	}
	

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public void onReadComplete(Vector<SceneUnitTag<?>> all) {}
	@Override
	public void onWriteReady(Vector<SceneUnitTag<?>> all) {}

//	--------------------------------------------------------------------------------------------------------

	@Override
	public Region getUnit() {
		return region;
	}
	
	@Override
	public Unit getGameUnit() {
		return this;
	}

	@Override
	public Color getSnapColor() {
		return null;
	}
	
	@Override
	public Shape getSnapShape() {
		return null;
	}

//	--------------------------------------------------------------------------------------------------------
	
	public javax.swing.ImageIcon getIcon(boolean update) {
		return null;
	}
	
	@Override
	public String getName() {
		return getID() + "";
	}
	
//	--------------------------------------------------------------------------------------------------------
	
//	@Override
//	public Menu getEditMenu() {
//		return new UnitMenu(scene_view, this);
//	}
	
	

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);

		if (editor!=null)
		{
			if (editor.isPageRegion()) {
				g.setColor(color);
				g.fill(local_bounds);
				// 选择了该精灵
				if (editor.getSelectedUnit() == this) {
					g.setColor(Color.WHITE);
					g.draw(local_bounds);
				}
				// 当鼠标放到该精灵上
				else if (isCatchedMouse() && editor.isToolSelect()) {
					g.setColor(Color.GREEN);
					g.draw(local_bounds);
				}
				this.enable = editor.isToolSelect();
			} else {
				Composite composite = g.getComposite();
				setAlpha(g, alpha * 0.5f);
				g.setColor(color);
				g.fill(local_bounds);
				g.setComposite(composite);
				this.enable = false;
			}
		}
	
	}
	
//	@Override
//	public DisplayObjectEditor<?> createEditorForm() 
//	{
//		return new DisplayObjectEditor<SceneRegion>(
//				this,
//				new RPGUnitPanel(region),
//				new AbilityPanel(this, region));
//	}
//	
	@Override
	public String toString() 
	{
		return getID()+"";
	}
	
	
	@Override
	public Menu getEditMenu() {
		return new MenuUnit(editor, this);
	}
}
package com.g2d.studio.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import com.cell.rpg.entity.Region;
import com.g2d.annotation.Property;
import com.g2d.cell.game.SceneUnit;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.Version;
import com.g2d.studio.swing.AbilityPanel;
import com.g2d.studio.swing.RPGUnitPanel;


@Property("一个范围，通常用于触发事件")
public class SceneRegion extends SceneUnit implements SceneUnitTag<Region>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	@Property("color")
	public Color 	color = new Color(0x8000ff00, true);
	
	transient FormSceneViewer scene_view;
	
	final public Region region;
	
	public SceneRegion(Rectangle rect, FormSceneViewer scene) 
	{
		this.scene_view = scene;
		this.region = new Region(getWidth(), getHeight());
		init();
		local_bounds	= rect;
	}
	
	public SceneRegion(Region in, FormSceneViewer scene) throws IOException
	{
		this.scene_view = scene;
		this.region = in;
		init();
		onRead(in) ;
	}
	
	protected void init()
	{
		enable				= true;
		enable_drag			= true;
		enable_input		= true;
		enable_drag_resize	= true;
		
		priority = Integer.MAX_VALUE;
	}
	
	
	public void onRead(Region region)
	{
		setName(scene_view.getViewObject(), 
				region.name);
		setLocation(
				region.pos.x,
				region.pos.y);
		color = new Color(
				region.color, true);
		alpha = region.alpha;
		setSize(
				region.width, 
				region.height);
	}
	
	public Region onWrite()
	{
		region.name			= getName();
		region.pos.x		= getX();
		region.pos.y		= getY();
		region.color		= color.getRGB();
		region.alpha		= alpha;
		region.width		= getWidth();
		region.height		= getHeight();
		return region;
	}
	
	
	@Override
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		if (scene_view!=null)
		{
			if (scene_view.isSelectedRegionBox())
			{
				g.setColor(color);
				g.fill(local_bounds);
			}
			
			if (scene_view.selected_unit == this) // 选择了该精灵
			{
				g.setColor(Color.WHITE);
				g.draw(local_bounds);
			}
			else if (catched_mouse) // 当鼠标放到该精灵上
			{	
				if (scene_view.tool_selector.isSelected())
				{
					g.setColor(Color.GREEN);
					g.draw(local_bounds);
				}
			}
		}
	
	}
	
	@Override
	public DisplayObjectEditor<?> createEditorForm() 
	{
		return new DisplayObjectEditor<SceneRegion>(
				this,
				new RPGUnitPanel(region),
				new AbilityPanel(region));
	}
	
	@Override
	public String toString() 
	{
		return getName()+"";
	}
	
	
}
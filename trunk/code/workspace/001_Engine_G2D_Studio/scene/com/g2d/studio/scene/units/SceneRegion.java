package com.g2d.studio.scene.units;

import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.Actor;
import com.cell.rpg.scene.Region;
import com.cell.rpg.scene.ability.ActorDropItem;
import com.g2d.annotation.Property;
import com.g2d.game.rpg.MoveableUnit;
import com.g2d.game.rpg.Unit;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.Version;

import com.g2d.studio.quest.QuestCellEditAdapter;
import com.g2d.studio.res.Res;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneUnitMenu;
import com.g2d.studio.scene.editor.SceneUnitTagEditor;
import com.g2d.studio.scene.effect.AbilityEffectInfos;


@Property("一个范围，通常用于触发事件")
public class SceneRegion extends com.g2d.game.rpg.Unit implements SceneUnitTag<Region>
{
	private static final long serialVersionUID = Version.VersionGS;

	final SceneEditor		editor;
	final public Region 	region;
	
	@Property("color")
	public Color 			color = new Color(0x8000ff00, true);
	
	AbilityEffectInfos<Region>	effects = new AbilityEffectInfos<Region>(
			new Class<?>[]{
					QuestPublisher.class,
					QuestAccepter.class,
					},
			new  BufferedImage[]{
					Res.img_quest_info,
					Res.img_quest_info2,
			}		
	);
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
	
//	--------------------------------------------------------------------------------------------------------
	
//	@Override
//	public Menu getEditMenu() {
//		return new UnitMenu(scene_view, this);
//	}
	
	@Override
	public void update() {

		super.update();

		effects.updateActor(this);
	}

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);

		if (editor!=null)
		{
			if (editor.getSelectedPage().isSelectedType(getClass())) {
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
	public DisplayObjectEditor<?> createEditorForm() {
		return new SceneUnitTagEditor(this,
				new SceneAbilityAdapters.RegionSpawnNPCNodeAdapter(),
				new QuestCellEditAdapter.QuestTriggerAdapter());
	}
	
	@Override
	public Menu getEditMenu() {
		return new SceneUnitMenu(editor, this);
	}

	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return null;
	}
	@Override
	public String getListName() {
		return getID() + "";
	}
}
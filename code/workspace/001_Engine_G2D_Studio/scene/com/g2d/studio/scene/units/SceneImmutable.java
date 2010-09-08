package com.g2d.studio.scene.units;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.gfx.game.CSprite;
import com.cell.rpg.scene.Immutable;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResource.SpriteSet;
import com.g2d.cell.game.SceneSprite;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.Studio;
import com.g2d.studio.Version;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneUnitMenu;
import com.g2d.studio.scene.editor.SceneUnitTagEditor;
import com.g2d.util.Drawing;

public class SceneImmutable extends SceneSprite implements SceneUnitTag<Immutable>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	final public SceneEditor	editor;
	final Immutable				sprite;
	final public CPJSprite		cpj_spr;
	
	Rectangle					snap_shape = new Rectangle(-2, -2, 4, 4);
	
//	--------------------------------------------------------------------------------------------------------
	
	/**
	 * 新建的单位
	 * @param editor
	 * @param xls_unit
	 * @param x
	 * @param y
	 */
	public SceneImmutable(SceneEditor editor, CPJSprite cpj_spr, int x, int y, int anim) 
	{
		this.editor = editor;
		this.cpj_spr = cpj_spr;
		this.cur_anim = anim;		
		this.setLocation(x, y);
		this.init(cpj_spr.parent.getSetResource(), cpj_spr.name);
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		this.sprite = new Immutable(getID()+"", cpj_spr.getCPJFile().getName(), cpj_spr.name);
	}
	
	/**
	 * 读入的单位
	 * @param editor
	 * @param actor
	 */
	public SceneImmutable(SceneEditor editor, Immutable spr) 
	{
		this.editor = editor;
		this.sprite = spr;
		{
			CPJIndex<CPJSprite> index = Studio.getInstance().getCPJResourceManager().getNode(
						CPJResourceType.ACTOR, 
						spr.getDisplayNode().cpj_project_name,
						spr.getDisplayNode().cpj_object_id);
			index.getObject().getDisplayObject();

			this.cpj_spr = CPJSprite.class.cast(index.getObject());
			this.cur_anim = spr.animate;
			this.setID(
					editor.getGameScene().getWorld(), 
					sprite.id);
			this.setLocation(
					sprite.x,
					sprite.y);
			this.init(
					cpj_spr.getCPJFile().getSetResource(), 
					cpj_spr.name);
		}
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
	}

	public Immutable onWrite()
	{
		sprite.name				= getID() + "";
		sprite.animate			= cur_anim;
		sprite.x				= getX();
		sprite.y				= getY();
		sprite.z				= priority;
		return sprite;
	}

	@Override
	public void added(DisplayObjectContainer parent) {
		this.enable			= true;
		this.enable_focus 	= true;
		this.enable_drag	= true;
		this.enable_input	= true;
		super.added(parent);
	}
	
	@Override
	public void loaded(CellSetResource set, CSprite cspr, SpriteSet spr) {
		super.loaded(set, cspr, spr);
		this.getSprite().setCurrentAnimate(cur_anim);
	}

	protected boolean enable_click_focus() {
		return true;
	}
//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public void onReadComplete(Vector<SceneUnitTag<?>> all) {}
	@Override
	public void onWriteReady(Vector<SceneUnitTag<?>> all) {}
//	--------------------------------------------------------------------------------------------------------

	@Override
	public Immutable getUnit() {
		return sprite;
	}
	
	@Override
	public Unit getGameUnit() {
		return this;
	}
	
	@Override
	public Color getSnapColor() {
		return Color.GREEN;
	}
	
	@Override
	public Shape getSnapShape() {
		return snap_shape;
	}
	
//	@Override
//	public Menu getEditMenu() {
//		return new UnitMenu(scene_view, this);
//	}

//	--------------------------------------------------------------------------------------------------------
	
	
//	--------------------------------------------------------------------------------------------------------
	
	public void update() 
	{
//		alpha = Math.abs((float)Math.sin(timer/10d));
		
		super.update();
		
		if (csprite!=null) 
		{
			csprite.nextCycFrame();
		}
		
//		current selected unit
		if (editor.isToolSelect())
		{
			if (editor.getSelectedUnit() == this)
			{
				if (getRoot().isMouseWheelUP()) {
					getSprite().nextAnimate(-1);
				}
				if (getRoot().isMouseWheelDown()) {
					getSprite().nextAnimate(1);
				}
				cur_anim = getSprite().getCurrentAnimate();
			}
		}
		
	}
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		if (editor != null) 
		{	
			pushObject(g.getStroke());
			try
			{
				g.setStroke(new BasicStroke(2));
				
				if (editor.getSelectedPage().isSelectedType(getClass())) 
				{
					// 选择了该精灵
					if (editor.getSelectedUnit() == this) {
						drawTouchRange(g);
						g.setColor(Color.WHITE);
						g.draw(local_bounds);
						g.setColor(Color.WHITE);
						Drawing.drawStringBorder(g, 
								getSprite().getCurrentAnimate() + "/" + getSprite().getAnimateCount(),
								0, getSprite().getVisibleBotton() + 1,
								Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
					} // 当鼠标放到该精灵上
					else if (isPickedMouse()) {
						drawTouchRange(g);
						if (editor.isToolSelect()) {
							g.setColor(Color.GREEN);
							g.draw(local_bounds);
						}
					}
					this.enable = editor.isToolSelect();
				} else {
					this.enable = false;
				}
			
			} finally {
				g.setStroke(popObject(Stroke.class));
			}
		}
	}
	
	protected void drawTouchRange(Graphics2D g)
	{
		if (sprite!=null) {
			g.setColor(Color.GREEN);
			g.drawArc(
					-sprite.touch_range, 
					-sprite.touch_range, 
					sprite.touch_range<<1, 
					sprite.touch_range<<1,
					0, 360);
		}
	}
		
	@Override
	public String toString() {
		return getID()+"";
	}

	@Override
	public Class<com.cell.rpg.scene.script.entity.Immutable> getEventType() {
		return com.cell.rpg.scene.script.entity.Immutable.class;
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	@Override
	public DisplayObjectEditor<?> getEditorForm() {
		return new SceneUnitTagEditor(this,
				new SceneAbilityAdapters.ActorPathStartAdapter(editor),
				new SceneAbilityAdapters.ActorTransportAdapter(editor)
				);
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
	@Override
	public void onHideFrom() {}
}

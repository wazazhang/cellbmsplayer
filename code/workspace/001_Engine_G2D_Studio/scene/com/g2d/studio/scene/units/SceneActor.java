package com.g2d.studio.scene.units;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.game.CSprite;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.Actor;
import com.cell.rpg.scene.ability.ActorBank;
import com.cell.rpg.scene.ability.ActorJobTrainer;
import com.cell.rpg.scene.ability.ActorSellItem;
import com.cell.rpg.scene.ability.ActorSkillTrainer;
import com.cell.rpg.scene.ability.ActorTalk;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.template.ability.UnitDropItem;
import com.g2d.annotation.Property;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResource.SpriteSet;
import com.g2d.cell.game.SceneSprite;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.Studio;
import com.g2d.studio.Version;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.quest.QuestCellEditAdapter;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneUnitMenu;
import com.g2d.studio.scene.editor.SceneUnitTagEditor;
import com.g2d.studio.scene.effect.AbilityEffectInfos;
import com.g2d.util.Drawing;

/**
 * @author WAZA
 *
 */
@Property("一个游戏角色")
public class SceneActor extends SceneSprite implements SceneUnitTag<Actor>
{
	private static final long serialVersionUID = Version.VersionGS;
	
	final public SceneEditor	editor;
	final Actor					actor;
	final XLSUnit				xls_unit;

	Rectangle					snap_shape = new Rectangle(-2, -2, 4, 4);
	AbilityEffectInfos<Actor>	effects = new AbilityEffectInfos<Actor>(
			new Class<?>[]{
					ActorTalk.class,
					UnitDropItem.class, 
					QuestPublisher.class,
					QuestAccepter.class,
					ActorBank.class,
					ActorSkillTrainer.class,
					ActorJobTrainer.class,
					ActorSellItem.class,
					ActorTransport.class,
					},
			new  BufferedImage[]{
					Res.img_talk,
					Res.img_item_info,
					Res.img_quest_info,
					Res.img_quest_info2,
					Res.img_npc_bank,
					Res.img_skill_trainer,
					Res.img_job_trainer,
					Res.img_sell_item,
					Res.img_transport,
			}		
	);

//	--------------------------------------------------------------------------------------------------------
	
	/**
	 * 新建的单位
	 * @param editor
	 * @param xls_unit
	 * @param x
	 * @param y
	 */
	public SceneActor(SceneEditor editor, XLSUnit xls_unit, int x, int y, int anim) 
	{
		this.editor		= editor;
		this.xls_unit	= xls_unit;
		this.cur_anim	= anim;		
		this.setLocation(x, y);
		this.init(xls_unit.getCPJSprite().getCPJFile().getSetResource(), xls_unit.getCPJSprite().name);
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		this.actor = new Actor(getID()+"", xls_unit.getID());
	}
	
	/**
	 * 读入的单位
	 * @param editor
	 * @param actor
	 */
	public SceneActor(SceneEditor editor, Actor actor) 
	{
		this.editor = editor;
		this.actor = actor;
		{
			this.xls_unit = Studio.getInstance().getObjectManager().getObject(
					XLSUnit.class, 
					actor.template_unit_id);
			this.xls_unit.getCPJSprite().getDisplayObject();
			this.cur_anim = actor.animate;
			this.setID(
					editor.getGameScene().getWorld(), 
					actor.id);
			this.setLocation(
					actor.x,
					actor.y);
			this.init(
					xls_unit.getCPJSprite().getCPJFile().getSetResource(), 
					xls_unit.getCPJSprite().name);
		}
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
	}

	public Actor onWrite()
	{
		actor.name				= getID() + "";
		actor.animate			= cur_anim;
		actor.x					= getX();
		actor.y					= getY();
		actor.z					= priority;
		return actor;
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
	
//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public void onReadComplete(Vector<SceneUnitTag<?>> all) {}
	@Override
	public void onWriteReady(Vector<SceneUnitTag<?>> all) {}
//	--------------------------------------------------------------------------------------------------------

	@Override
	public Actor getUnit() {
		return actor;
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
		
		effects.updateActor(this);
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
						drawLookRange(g);
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
		if (actor!=null) {
			g.setColor(Color.GREEN);
			g.drawArc(
					-actor.touch_range, 
					-actor.touch_range, 
					actor.touch_range<<1, 
					actor.touch_range<<1,
					0, 360);
		}
	}
	protected void drawLookRange(Graphics2D g) 
	{
		if (actor!=null) {
			g.setColor(Color.YELLOW);
			g.drawArc(
					-actor.look_range, 
					-actor.look_range, 
					actor.look_range<<1, 
					actor.look_range<<1,
					0, 360);
			g.setColor(new Color((int)(Color.YELLOW.getRGB() & 0x7fffffff), true));
			g.fillArc(
					-actor.look_range, 
					-actor.look_range, 
					actor.look_range<<1, 
					actor.look_range<<1,
					0, 360);
		}
	}
		
	@Override
	public String toString() {
		return getID()+"";
	}

//	-----------------------------------------------------------------------------------------------------------
	
	@Override
	public DisplayObjectEditor<?> createEditorForm() {
		return new SceneUnitTagEditor(this,
				new SceneAbilityAdapters.ActorPathStartAdapter(editor),
				new SceneAbilityAdapters.ActorTransportAdapter(editor),
				new SceneAbilityAdapters.ActorSkillTrainerAdapter(),
				new SceneAbilityAdapters.ActorTalkAdapter(),
				new ObjectAdapters.ItemListIDSelectAdapter(),
				new QuestCellEditAdapter.QuestTriggerAdapter()
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
}

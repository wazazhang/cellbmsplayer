package com.g2d.studio.scene.units;

import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.Effect;
import com.cell.rpg.scene.Region;
import com.g2d.Tools;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleDisplay;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.Studio;
import com.g2d.studio.Version;
import com.g2d.studio.cpj.CPJEffectImageSelectDialog.TileImage;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.quest.QuestCellEditAdapter;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneUnitMenu;
import com.g2d.studio.scene.editor.SceneUnitTagEditor;
import com.g2d.studio.scene.effect.AbilityEffectInfos;


@Property("一个在场景中渲染的特效")
public class SceneEffect extends com.g2d.game.rpg.Unit implements SceneUnitTag<Effect>
{
	private static final long serialVersionUID = Version.VersionGS;

	final SceneEditor		editor;
	final public Effect 	effect;
	Rectangle				snap_shape = new Rectangle(-1, -2, 2, 2);

	ParticleDisplay			particles;
	
//	--------------------------------------------------------------------------------------------------------
	
	public SceneEffect(SceneEditor editor, int x, int y, DEffect deffect) 
	{
		this.editor 		= editor;
		this.setLocation(x, y);
		this.setLocalBounds(-16, -16, 32, 32);
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
		this.effect = new Effect(getID() + "", deffect.getData().getIntID());
	}
	
	public SceneEffect(SceneEditor editor, Effect in) throws IOException
	{
		this.editor = editor;
		this.effect = in;
		{
			this.setID(
					editor.getGameScene().getWorld(), 
					effect.name);
			this.setLocation(
					effect.x,
					effect.y);
			this.setLocalBounds(-16, -16, 32, 32);
		}
		if (!editor.getGameScene().getWorld().addChild(this)){
			throw new IllegalStateException();
		}
	}
	
	public Effect onWrite()
	{
		effect.name			= getID() + "";
		effect.x			= getX();
		effect.y			= getY();
		return effect;
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {
		enable				= true;
		enable_drag			= true;
		enable_input		= true;
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
	public Effect getUnit() {
		return effect;
	}
	
	@Override
	public Unit getGameUnit() {
		return this;
	}

	@Override
	public Color getSnapColor() {
		return Color.YELLOW;
	}
	
	@Override
	public Shape getSnapShape() {
		return snap_shape;
	}

//	--------------------------------------------------------------------------------------------------------
	
	@Override
	public void update() 
	{
		super.update();

		if (particles == null) {
			try{
				DEffect deffect = Studio.getInstance().getObjectManager().getObject(DEffect.class, effect.template_effect_id);
				for (Layer layer : deffect.getData().particles) {
					if (layer.image == null) {
						TileImage tile_image = new TileImage(
								layer.cpj_project_name, 
								layer.cpj_sprite_name, 
								layer.cpj_image_id
								);
						layer.image	= tile_image.getEffectImage();
					}
				}
				this.particles = new ParticleDisplay(deffect.getData().particles);
				this.addChild(this.particles);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);

		if (editor!=null)
		{
			if (editor.getSelectedPage().isSelectedType(getClass())) {
				g.setColor(Color.YELLOW);
				g.draw(local_bounds);
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
				this.enable = false;
			}
		}
	
	}
	
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
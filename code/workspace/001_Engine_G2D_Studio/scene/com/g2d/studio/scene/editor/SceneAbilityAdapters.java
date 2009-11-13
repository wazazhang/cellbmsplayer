package com.g2d.studio.scene.editor;

import java.lang.reflect.Field;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.Actor;
import com.cell.rpg.scene.ability.ActorPathStart;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.scene.ability.RegionSpawnNPC;
import com.cell.rpg.scene.ability.RegionSpawnNPC.NPCSpawn;
import com.cell.rpg.xls.XLSFile;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEdit;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.scene.units.SceneActor;
import com.g2d.studio.scene.units.ScenePoint;

public class SceneAbilityAdapters
{
	/**
	 * 编辑传送点能力的工具
	 * @author WAZA
	 */
	public static class ActorTransportAdapter extends AbilityCellEditAdapter<ActorTransport>
	{
		final SceneEditor editor ;
		
		public ActorTransportAdapter(SceneEditor editor) {
			this.editor = editor;
		}
		
		@Override
		public Class<ActorTransport> getAbilityType() {
			return ActorTransport.class;
		}
		
		@Override
		public boolean fieldChanged(Abilities abilities, AbstractAbility ability, Field field) {			
			// 场景改变了，清除场景单位的值
			ActorTransport tp = getAbilityType().cast(ability);
			if (field.getName().equals("destination_scene_name")){
				tp.next_scene_object_id = null;
			}
			return true;
		}
		
		@Override
		public PropertyCellEdit<?> getAbilityCellEdit(Abilities abilities, AbstractAbility ability, Field field, Object value) 
		{
			if (field.getName().equals("next_scene_id")){
				return new SceneListCellEdit();
			}
			if (field.getName().equals("next_scene_object_id")){
				ActorTransport tp = (ActorTransport)ability;
				if (tp.next_scene_id!=null) {
					SceneNode scene = Studio.getInstance().getSceneManager().getSceneNode(tp.next_scene_id);
					return new SceneUnitListCellEdit(scene.getSceneEditor(), SceneActor.class);
				}
			}
			return null;
		}
	}
	
	/**
	 * 编辑单位路点行为的工具
	 * @author WAZA
	 */
	public static class ActorPathStartAdapter extends AbilityCellEditAdapter<ActorPathStart>
	{
		final SceneEditor editor ;
		
		public ActorPathStartAdapter(SceneEditor editor) {
			this.editor = editor;
		}
		
		@Override
		public Class<ActorPathStart> getAbilityType() {
			return ActorPathStart.class;
		}
		
		@Override
		public PropertyCellEdit<?> getAbilityCellEdit(Abilities abilities, AbstractAbility ability, Field field, Object value) {
			if (field.getName().equals("point_name")){
				return new SceneUnitListCellEdit(editor, ScenePoint.class);
			}
			return null;
		}
	}

	/**
	 * 产生区域内产生的单位工具
	 * @author WAZA
	 */
	public static class RegionSpawnNPCNodeAdapter extends AbilityCellEditAdapter<NPCSpawn>
	{
		@Override
		public Class<NPCSpawn> getAbilityType() {
			return NPCSpawn.class;
		}
		
		@Override
		public PropertyCellEdit<?> getAbilityCellEdit(Abilities abilities, AbstractAbility ability, Field field, Object value) {
			if (field.getName().equals("template_unit_id")){
				return new ObjectSelectCellEdit<XLSUnit>(XLSUnit.class);
			}
			return null;
		}
		
	}

	
	
//	if (object instanceof AbilitySceneNPCSpawn)
//	{
//		if (field.getName().equals("cpj_actor_name")){
//			return new ActorListComboBox();
//		}
//	}
//	
//	if (object instanceof AbilitySceneNPCTeamNode) {
//		if (field.getName().equals("cpj_actor_name")){
//			return new ActorListComboBox();
//		}
//	}
}

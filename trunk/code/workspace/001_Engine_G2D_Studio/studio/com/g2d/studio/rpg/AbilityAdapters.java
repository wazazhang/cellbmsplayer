package com.g2d.studio.rpg;

import java.lang.reflect.Field;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.xls.XLSFile;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.editor.SceneListCellEdit;
import com.g2d.studio.scene.editor.SceneUnitList;
import com.g2d.studio.scene.editor.SceneUnitListCellEdit;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.scene.units.SceneActor;

public class AbilityAdapters
{
	public static AbilityCellEditAdapter<?>[] getAbilityAdapters()
	{
		return new AbilityCellEditAdapter<?>[]{
			new ActorTransportAdapter(),	
		};
	}
	
	static class ActorTransportAdapter implements AbilityCellEditAdapter<ActorTransport>
	{
		@Override
		public void fieldChanged(AbstractAbility ability, Field field) {			
			// 场景改变了，清除场景单位的值
			ActorTransport tp = getAbilityType().cast(ability);
			if (field.getName().equals("destination_scene_name")){
				tp.next_scene_object_id = null;
			}
		}
		
		@Override
		public PropertyCellEdit<?> getAbilityCellEdit(AbstractAbility ability, Field field, Object value) 
		{
			ActorTransport tp = (ActorTransport)ability;
			if (field.getName().equals("next_scene_id")){
				return new SceneListCellEdit();
			}
			if (field.getName().equals("next_scene_object_id")){
				if (tp.next_scene_id!=null) {
					try{
						SceneNode scene = Studio.getInstance().getSceneManager().getSceneNode(tp.next_scene_id);
						return new SceneUnitListCellEdit(scene.getSceneEditor(), SceneActor.class);
					}catch (Exception e) {}
				}
			}
			return null;
		}
		
		@Override
		public Class<ActorTransport> getAbilityType() {
			return ActorTransport.class;
		}
	}
	
	
	
	
	/*
	void fieldChanged(Object object, Field field)
	{

		
		if (object instanceof AbilitySceneNPCSpawn) 
		{
			// 设置默认的team给Spawn
			AbilitySceneNPCSpawn spawn = (AbilitySceneNPCSpawn)object;
			if (field.getName().equals("cpj_actor_name")){
				System.out.println("set default team from actor !");
				try{
					AbilitySceneNPC template_npc = Studio.getInstance().getActor(
							spawn.getActorCPJName(), 
							spawn.getActorCPJSpriteName()).
							createRPGActor().
							getAbility(AbilitySceneNPC.class);
					spawn.team 				= template_npc.team;
					spawn.xls_file_name		= template_npc.xls_file_name;
					spawn.xls_primary_key	= template_npc.xls_primary_key;
				}catch (Exception e) {}
			}
		}
		
		if (object instanceof AbilitySceneNPCTeamNode) 
		{
			// 设置默认的team给node
			AbilitySceneNPCTeamNode node = (AbilitySceneNPCTeamNode)object;
			if (field.getName().equals("cpj_actor_name")){
				System.out.println("set default team from actor !");
				try{
					AbilitySceneNPC template_npc = Studio.getInstance().getActor(
							node.getActorCPJName(), 
							node.getActorCPJSpriteName()).
							createRPGActor().
							getAbility(AbilitySceneNPC.class);
					node.xls_file_name		= template_npc.xls_file_name;
					node.xls_primary_key	= template_npc.xls_primary_key;
				}catch (Exception e) {}
			}
		}
			
	}
	
	PropertyCellEdit<?> getAbilityCellEdit(Object object, Field field, Object value) 
	{
		// NPC行动开始点
		if (object instanceof AbilitySceneNPCPathPoint) 
		{
			AbilitySceneNPCPathPoint path = (AbilitySceneNPCPathPoint)object;
			if (field.getName().equals("point_name") && scene_unit!=null){
				try{
//					System.out.println(object.getClass().getName());
					return new SceneUnitListComboBox(scene_unit.getViewer(), ScenePoint.class);
				}catch (Exception e) {}
			}
		}
		
		// 如果是XLS数据绑定
		if (object instanceof AbilityXLS)
		{
			AbilityXLS xls = (AbilityXLS)object;
			
			if (field.getName().equals("xls_file_name")){
				return new XLSFileListComboBox();
			}
			
			if (field.getName().equals("xls_primary_key")){
				try{
					return new XLSRowListComboBox(new XLSFile(Studio.getInstance().getFile(
							Config.XLS_ROOT + "/" + xls.xls_file_name)));
				}catch (Exception e) {}
			}
			
			if (object instanceof AbilitySceneNPCSpawn)
			{
				if (field.getName().equals("cpj_actor_name")){
					return new ActorListComboBox();
				}
			}
			
			if (object instanceof AbilitySceneNPCTeamNode) {
				if (field.getName().equals("cpj_actor_name")){
					return new ActorListComboBox();
				}
			}
		}
		
		// 如果是其他能力
		
		return null;
	}

*/

}

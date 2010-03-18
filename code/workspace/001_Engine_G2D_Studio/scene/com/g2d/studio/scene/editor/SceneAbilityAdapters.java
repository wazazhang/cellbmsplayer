package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.ability.ActorJobTrainer;
import com.cell.rpg.scene.ability.ActorPathStart;
import com.cell.rpg.scene.ability.ActorSkillTrainer;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.scene.ability.IActorAbility;
import com.cell.rpg.scene.ability.RegionSpawnNPC.NPCSpawn;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEdit;
import com.g2d.studio.gameedit.ObjectSelectDialog;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.scene.units.SceneImmutable;
import com.g2d.studio.scene.units.ScenePoint;
import com.g2d.studio.sound.SoundSelectDialog;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.talks.TalkFile;
import com.g2d.studio.talks.TalkSelectDialog;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractOptionDialog;

public class SceneAbilityAdapters
{
	public static class SceneBGMAdapter implements CellEditAdapter<Scene>
	{
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue,
				Field field) {
			return false;
		}
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
				Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("bgm_sound_name")) {
				SoundSelectDialog edit = new SoundSelectDialog(owner, (String)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner,
				Object editObject, Object fieldValue, Field field,
				DefaultTableCellRenderer src) {
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject,
				PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		
		@Override
		public Class<Scene> getType() {
			return Scene.class;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			return null;
		}
	}
	
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
		public Class<ActorTransport> getType() {
			return ActorTransport.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("next_scene_id")){
				return new SceneListCellEdit(fieldValue);
			}
			else if (field.getName().equals("next_scene_object_id")){
				ActorTransport tp = (ActorTransport)editObject;
				if (tp.next_scene_id!=null) {
					SceneNode scene = Studio.getInstance().getSceneManager().getSceneNode(tp.next_scene_id);
					return new SceneUnitListCellEdit(scene.getSceneEditor(), SceneImmutable.class);
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("next_scene_id")){
				SceneNode node = null;
				if (fieldValue!=null) {
					String id = (String)fieldValue;
					node = Studio.getInstance().getSceneManager().getSceneNode(id);
				}
				if (fieldValue != null && node != null) {
					src.setText(node.getName() + "(" + node.getID() + ")");
				} else {
//					src.setForeground(Color.RED);
					src.setText("null");
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
		public Class<ActorPathStart> getType() {
			return ActorPathStart.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object editObject,
				Object fieldValue, Field field) {
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
		public Class<NPCSpawn> getType() {
			return NPCSpawn.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("template_unit_id")){
				return new ObjectSelectCellEdit<XLSUnit>(XLSUnit.class);
			}
			return null;
		}
		
		@Override
		public Component getCellRender(
				ObjectPropertyPanel owner,
				Object editObject,
				Object fieldValue,
				Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("template_unit_id")){
				XLSUnit unit = null;
				if (fieldValue != null) {
					String tid = (String)fieldValue;
					unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, tid);
				}
				if (fieldValue != null && unit != null) {
					src.setText(unit.getName()+"(" + unit.getID() + ")");
				} else {
//					src.setForeground(Color.RED);
					src.setText("null");
				}
			}
			return src;
		}
		
	}
	
	public static class ActorSkillTrainerAdapter extends AbilityCellEditAdapter<ActorSkillTrainer>
	{
		@Override
		public Class<ActorSkillTrainer> getType() {
			return ActorSkillTrainer.class;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object editObject,
				Object fieldValue, Field field) {
			if (field.getName().equals("skills_id")){
				SkillListDialog dialog = new SkillListDialog((ArrayList<Integer>)fieldValue);
				dialog.setSize(260, 400);
				dialog.showDialog();
				return dialog;
			}
			return null;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			if (field.getName().equals("skills_id")){
				ArrayList<Integer> list = (ArrayList<Integer>)fieldSrcValue;
				if (list != null) {
					StringBuffer sb = new StringBuffer();
					for (Integer skill_id : list) {
						XLSSkill skill = Studio.getInstance().getObjectManager().getObject(XLSSkill.class, skill_id);
						if (skill != null) {
							sb.append(skill.getName());
						}
					}
					return sb.toString();
				}
			}
			return null;
		}
		
		static class SkillListDialog extends AbstractOptionDialog<ArrayList<Integer>> implements PropertyCellEdit<ArrayList<Integer>>
		{
			private static final long serialVersionUID = 1L;

			Vector<XLSSkill> 	list_data 	= new Vector<XLSSkill>();
			G2DList<XLSSkill> 	list 		= new G2DList<XLSSkill>();
			
			JButton btn_add_skill = new JButton("添加");
			JButton btn_del_skill = new JButton("删除");

			JLabel cell_edit_component = new JLabel();
			
			public SkillListDialog(ArrayList<Integer> init) 
			{
				btn_add_skill.addActionListener(this);
				btn_del_skill.addActionListener(this);
				south.add(btn_add_skill);
				south.add(btn_del_skill);
				
				if (init != null) {
					for (Integer skill_id : init) {
						XLSSkill skill = Studio.getInstance().getObjectManager().getObject(XLSSkill.class, skill_id);
						if (skill != null) {
							list_data.add(skill);
						}
					}
				}
				list.setListData(list_data);
				
				super.add(new JScrollPane(list), BorderLayout.CENTER);
			}
			
			@Override
			protected ArrayList<Integer> getUserObject() {
				return getValue();
			}

			@Override
			public ArrayList<Integer> getValue() {
				ArrayList<Integer> ret = new ArrayList<Integer>(list_data.size());
				for (XLSSkill data : list_data) {
					ret.add(data.getIntID());
				}
				return ret;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn_add_skill) {
					ObjectSelectDialog<XLSSkill> dialog = new ObjectSelectDialog<XLSSkill>(this, XLSSkill.class, 10);
					dialog.setSize(200, 400);
					dialog.setLocation(getX()+getWidth(), getY());
					XLSSkill skill = dialog.showDialog();
					if (skill != null) {
						list_data.add(skill);
						list.setListData(list_data);
						list.repaint();
					}
				} else if (e.getSource() == btn_del_skill) {
					XLSSkill item = list.getSelectedItem();
					if (item != null) {
						list_data.remove(item);
						list.setListData(list_data);
						list.repaint();
					}
				} else {
					super.actionPerformed(e);
				}
			}
			
			@Override
			public Component getComponent(ObjectPropertyPanel panel) {
				cell_edit_component.setText(getValue() + "");
				return cell_edit_component;
			}
			
			@Override
			protected boolean checkOK() {
				return true;
			}
			
		}
		
	}
	
	public static class ActorTalkAdapter extends AbilityCellEditAdapter<AbstractAbility>
	{
		@Override
		public Class<AbstractAbility> getType() {
			return AbstractAbility.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
				Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("npc_talk")){
				if (editObject instanceof IActorAbility) {
					NpcTalkSelecDialog talk = new NpcTalkSelecDialog(owner);
					talk.showDialog();
					return talk;
				}
			}
			return null;
		}
		
		static class NpcTalkSelecDialog extends TalkSelectDialog implements PropertyCellEdit<String>
		{
			private static final long serialVersionUID = 1L;
			
			JLabel talk_name = new JLabel();
			
			public NpcTalkSelecDialog(Component owner) {
				super(owner);
			}
			@Override
			public Component getComponent(ObjectPropertyPanel panel) {
				TalkFile file = getSelectedObject();
				if (file != null) {
					talk_name.setText(file.getListName());
					talk_name.setIcon(file.getListIcon(false));
				} else {
					talk_name.setText("");
					talk_name.setIcon(null);
				}
				return talk_name;
			}
			@Override
			public String getValue() {
				TalkFile file = getSelectedObject();
				if (file != null) {
					return file.talk_file_name;
				}
				return null;
			}
			
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

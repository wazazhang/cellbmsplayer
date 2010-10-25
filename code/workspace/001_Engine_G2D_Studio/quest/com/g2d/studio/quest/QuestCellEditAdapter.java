package com.g2d.studio.quest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.reflect.Parser;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.quest.QuestGenerator.Festival;
import com.cell.rpg.quest.QuestGenerator.Festival.FestivalDate;
import com.cell.rpg.quest.QuestItem.AwardBattle;
import com.cell.rpg.quest.QuestItem.AwardItem;
import com.cell.rpg.quest.QuestItem.AwardTeleport;
import com.cell.rpg.quest.QuestItem.DropItem;
import com.cell.rpg.quest.QuestItem.TagItem;
import com.cell.rpg.quest.QuestItem.TagQuest;
import com.cell.rpg.quest.QuestItem.TagQuestItem;
import com.cell.rpg.quest.QuestItem.TagQuestStateKillMonsterComparison;
import com.cell.rpg.quest.ability.QuestTrigger;
import com.cell.rpg.scene.SceneUnit;
import com.cell.util.EnumManager;
import com.cell.util.DateUtil.DayOfWeek;
import com.cell.util.DateUtil.MonthOfYear;
import com.cell.util.DateUtil.TimeObject;
import com.cell.util.task.CronExpression.DateType;
import com.g2d.editor.property.ListEnumEdit;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.quest.items.QuestItemNode;
import com.g2d.studio.quest.items.QuestItemSelectCellEdit;
import com.g2d.studio.rpg.FormulaEdit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.editor.SceneListCellEdit;
import com.g2d.studio.scene.editor.SceneUnitListCellEdit;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.scene.units.SceneImmutable;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractOptionDialog;

public class QuestCellEditAdapter {

//	-------------------------------------------------------------------------------------------------------------------------
	
	
	
	public static class QuestTriggerAdapter extends AbilityCellEditAdapter<QuestTrigger>
	{
		@Override
		public Class<QuestTrigger> getType() {
			return QuestTrigger.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(
						AbstractDialog.getTopWindow(owner.getComponent()), 
						false,
						(Integer)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
	
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id") && fieldValue!=null) {
				Integer quest_id = (Integer)fieldValue;
				QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
				if (node != null) {
					src.setText(node.toString());
					src.setIcon(node.getIcon(false));
					return src;
				}
			}
			return null;
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------
	
	
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 依赖已完成的任务
	 * @author WAZA
	 *
	 */
	public static class QuestItemTagQuest extends AbilityCellEditAdapter<TagQuest>
	{

		@Override
		public Class<TagQuest> getType() {
			return TagQuest.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(
						owner.getComponent(), 
						true,
						(Integer)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field,
			DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id")) {
				try{
					QuestNode node = Studio.getInstance().getQuestManager().getQuest((Integer)fieldValue);
					src.setText(node.getName());
					src.setIcon(node.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}

	/**
	 * 依赖已完成的任务
	 * @author WAZA
	 *
	 */
	public static class QuestItemTagQuestStateKillMonsterComparison extends AbilityCellEditAdapter<TagQuestStateKillMonsterComparison>
	{

		@Override
		public Class<TagQuestStateKillMonsterComparison> getType() {
			return TagQuestStateKillMonsterComparison.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("kill_unit_id")) {
				ObjectSelectCellEditInteger<XLSUnit> edit = new ObjectSelectCellEditInteger<XLSUnit>(XLSUnit.class);
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field,
			DefaultTableCellRenderer src) {
			if (field.getName().equals("kill_unit_id")) {
				try{
					XLSUnit unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, (Integer)fieldValue);
					src.setText(unit.getName());
					src.setIcon(unit.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	
	/**
	 * 任务条件，道具
	 * @author WAZA
	 */
	public static class QuestItemTagItem extends AbilityCellEditAdapter<TagItem>
	{
		@Override
		public Class<TagItem> getType() {
			return TagItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(XLSItem.class);
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("titem_index")) {
				try{
					XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue);
					src.setText(item.getName());
					src.setIcon(item.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	/**
	 * 任务条件，依赖的任务条件
	 * @author WAZA
	 */
	public static class QuestItemTagQuestItem extends AbilityCellEditAdapter<TagQuestItem>
	{	
		@Override
		public Class<TagQuestItem> getType() {
			return TagQuestItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(owner.getComponent(), true,
						(Integer)fieldValue);
				edit.showDialog();
				return edit;
			}
			else if (field.getName().equals("quest_item_index")) {
				try{
					TagQuestItem tag = (TagQuestItem)editObject;
					QuestItemSelectCellEdit quest_item_edit = new QuestItemSelectCellEdit(
							AbstractDialog.getTopWindow(owner.getComponent()),
							tag.quest_id
							);
					quest_item_edit.showDialog();
					return quest_item_edit;
				}catch(Exception err){}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id")) {
				try{
					QuestNode node = Studio.getInstance().getQuestManager().getQuest((Integer)fieldValue);
					src.setText(node.getName());
					src.setIcon(node.getIcon(false));
				}catch(Exception err){}
			}
			else if (field.getName().equals("quest_item_index")) {
				try{
					TagQuestItem 	tag 	= (TagQuestItem)editObject;
					QuestNode 		qnode 	= Studio.getInstance().getQuestManager().getQuest(tag.quest_id);
					QuestItemNode 	node 	= qnode.getQuestItemManager().get((Integer)fieldValue);
					src.setText(node.getName());
					src.setIcon(node.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
//	/**
//	 * 任务条件，依赖的任务条件
//	 * @author WAZA
//	 */
//	public static class QuestItemTagQuestStateComparison extends AbilityCellEditAdapter<TagQuestStateComparison>
//	{	
//		@Override
//		public Class<TagQuestStateComparison> getType() {
//			return TagQuestStateComparison.class;
//		}
//		
//		@Override
//		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
//			Object editObject, Object fieldValue, Field field) {
//			if (field.getName().equals("quest_id")) {
//				QuestSelectCellEdit edit = new QuestSelectCellEdit(AbstractDialog.getTopWindow(owner));
//				edit.showDialog();
//				return edit;
//			}
//			return null;
//		}
//		
//		@Override
//		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
//			Object fieldValue, Field field, DefaultTableCellRenderer src) {
//			if (field.getName().equals("quest_id") && fieldValue!=null) {
//				Integer quest_id = (Integer)fieldValue;
//				QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
//				if (node != null) {
//					src.setText(node.toString());
//					src.setIcon(node.getIcon(false));
//					return src;
//				}
//			}
//			return null;
//		}
//	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	

	/**
	 * 任务奖励，道具
	 * @author WAZA
	 */
	public static class QuestItemAwardItem extends AbilityCellEditAdapter<AwardItem>
	{
		@Override
		public Class<AwardItem> getType() {
			return AwardItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(XLSItem.class);
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("titem_index")) {
				try{
					XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue);
					src.setText(item.getName());
					src.setIcon(item.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}

	/**
	 * 任务奖励，道具
	 * @author WAZA
	 */
	public static class QuestItemDropItem extends AbilityCellEditAdapter<DropItem>
	{
		@Override
		public Class<DropItem> getType() {
			return DropItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(XLSItem.class);
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("titem_index")) {
				try{
					XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue);
					src.setText(item.getName());
					src.setIcon(item.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	/**
	 * 任务奖励，传送
	 * @author WAZA
	 */
	public static class QuestItemAwardTeleport extends AbilityCellEditAdapter<AwardTeleport>
	{
		@Override
		public Class<AwardTeleport> getType() {
			return AwardTeleport.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("scene_id")){
				return new SceneListCellEdit(fieldValue);
			}
			else if (field.getName().equals("scene_object_id")){
				AwardTeleport tp = (AwardTeleport)editObject;
				if (tp.scene_id!=null) {
					SceneNode scene = Studio.getInstance().getSceneManager().getSceneNode(tp.scene_id+"");
					return new SceneUnitListCellEdit(scene.getData(), SceneUnit.class);
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("scene_id")){
				try{
					SceneNode node = Studio.getInstance().getSceneManager().getSceneNode(fieldValue + "");
					if (fieldValue != null && node != null) {
						src.setText(node.getName() + "(" + node.getID() + ")");
					} else {
						src.setText("null");
					}
				}catch(Exception err){}
			}
			return null;
		}
		
	}
	
	/**
	 * 任务奖励，传送
	 * @author WAZA
	 */
	public static class QuestItemAwardBattle extends AbilityCellEditAdapter<AwardBattle>
	{
		@Override
		public Class<AwardBattle> getType() {
			return AwardBattle.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("unit_id")) {
				ObjectSelectCellEditInteger<XLSUnit> edit = new ObjectSelectCellEditInteger<XLSUnit>(XLSUnit.class);
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("unit_id")) {
				try{
					XLSUnit unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, (Integer)fieldValue);
					src.setText(unit.getName());
					src.setIcon(unit.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	public static class TimeObjectAdapter extends AbilityCellEditAdapter<AbstractAbility>
	{
		@Override
		public Class<AbstractAbility> getType() {
			return AbstractAbility.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (TimeObject.class.equals(field.getType())) {
				DialogTimeObjectEdit edit = new DialogTimeObjectEdit((TimeObject)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
	}
	
	static class DialogTimeObjectEdit extends AbstractOptionDialog<TimeObject> implements PropertyCellEdit<TimeObject>
	{
		JLabel edit_label = new JLabel();
		
		final TimeObject src;
		
		ListEnumEdit<TimeUnit>	combo_time_unit		= new ListEnumEdit<TimeUnit>(TimeUnit.class);
		JSpinner 				combo_time_value	= new JSpinner(new SpinnerNumberModel(
				new Long(1), 
				new Long(Long.MIN_VALUE), 
				new Long(Long.MAX_VALUE), 
				new Long(1)));
		
		public DialogTimeObjectEdit(TimeObject src)
		{
			if (src!=null) {
				this.src = src;
			} else {
				this.src = new TimeObject();
			}
			
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(combo_time_value, BorderLayout.CENTER);
			panel.add(combo_time_unit, BorderLayout.EAST);
			this.add(panel, BorderLayout.CENTER);
			
			try{
				combo_time_unit.setSelectedItem(this.src.time_unit);
				combo_time_value.setValue(this.src.time_value);
			}catch(Exception err){
				err.printStackTrace();
			}
			
			super.setSize(400, 100);
		}
		
		@Override
		protected boolean checkOK() {
			return true;
		}
		
		@Override
		public Component getComponent(ObjectPropertyEdit panel) {
			edit_label.setText(src+"");
			return edit_label;
		}
		
		@Override
		public TimeObject getValue() {
			return src;
		}
		
		@Override
		protected TimeObject getUserObject() {
			src.time_unit = combo_time_unit.getValue();
			src.time_value = Parser.castNumber(combo_time_value.getValue(), Long.class);
			return src;
		}
		
//		@Override
//		protected Object[] getUserObjects()
//		{
//			Object[] objs = new Object[1];				
//			objs[0] = getUserObject();				
//			return objs;
//		}		
	}

//	-------------------------------------------------------------------------------------------------------------------------
	
	public static class AbstractValueAdapter extends AbilityCellEditAdapter<AbstractAbility>
	{
		@Override
		public Class<AbstractAbility> getType() {
			return AbstractAbility.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (AbstractValue.class.equals(field.getType())) {
				FormulaEdit edit = new FormulaEdit(
						owner.getComponent(), 
						(AbstractValue)fieldValue);
				edit.showDialog();
				return edit;
			}
			if (AbstractValue.class.isAssignableFrom(field.getType())) {
				FormulaEdit edit = new FormulaEdit(
						owner.getComponent(), 
						new Class<?>[]{field.getType()}, 
						(AbstractValue)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
	
	public static class QuestFestivalAdapter extends AbilityCellEditAdapter<Festival>
	{
		@Override
		public Class<Festival> getType() {
			return Festival.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("date_time")) {
				DialogFestivalDateEdit edit = new DialogFestivalDateEdit(owner.getComponent(), (FestivalDate)fieldValue);
				edit.setVisible(true);
				return edit;
			}
			return super.getCellEdit(owner, editObject, fieldValue, field);
		}
		
		
	}
//	-------------------------------------------------------------------------------------------------------------------------
	
	
	static class DialogFestivalDateEdit extends AbstractDialog implements PropertyCellEdit<FestivalDate>, ActionListener
	{
		final FestivalDate date;
		
		JLabel		edit_label = new JLabel();
		
		TimePanel	panel_date;
		
		JButton		btn_ok 		= new JButton("确定");
		JButton		btn_cancel 	= new JButton("取消");
		
		public DialogFestivalDateEdit(Component owner, FestivalDate sdate) 
		{
			super(owner);
			
			if (sdate == null) {
				this.date = new FestivalDate();
			} else {
				this.date = sdate;
			}
			
			this.panel_date = new TimePanel(this.date);
			this.add(panel_date, BorderLayout.CENTER);
			
			JPanel btn_pan = new JPanel(new FlowLayout());
			btn_pan.add(btn_ok);
			btn_pan.add(btn_cancel);
			btn_ok.addActionListener(this);
			btn_cancel.addActionListener(this);
			this.add(btn_pan, BorderLayout.SOUTH);

			this.setSize((int)panel_date.getPreferredSize().getWidth()+40, 150);
		}
		
		@Override
		public Component getComponent(ObjectPropertyEdit panel) {
			edit_label.setText(date+"");
			return edit_label;
		}
		
		@Override
		public FestivalDate getValue() {
			return date;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btn_ok) {
				panel_date.setDate(date);
				setVisible(false);
			}
			if (e.getSource() == btn_cancel) {
				setVisible(false);
			}
		}
		
		
//		-----------------------------------------------------------------------------------------
		
		static class TimePanel extends JPanel implements ItemListener, ActionListener
		{
			JLabel 						lbl_type	= new JLabel("日期类型");
			ListEnumEdit<DateType>		combo_type	= new ListEnumEdit<DateType>(DateType.class);
			
			JLabel lbl_year 		= new JLabel("年");
			JLabel lbl_month 		= new JLabel("月");
			JLabel lbl_day_of_month = new JLabel("日");
//			JLabel lbl_week_of_year = new JLabel("周");
			JLabel lbl_week_of_month= new JLabel("周");
			JLabel lbl_day_of_week 	= new JLabel("星期");
			JLabel lbl_hour 		= new JLabel("时");
			JLabel lbl_minute 		= new JLabel("分");
			JLabel lbl_second 		= new JLabel("秒");

			JSpinner 					combo_year  			= new JSpinner();
			ListEnumEdit<MonthOfYear>	combo_month  			= new ListEnumEdit<MonthOfYear>(MonthOfYear.class);
			JSpinner 					combo_day_of_month  	= new JSpinner();
//			JSpinner 					combo_week_of_year  	= new JSpinner();
			JSpinner					combo_week_of_month		= new JSpinner();
			ListEnumEdit<DayOfWeek> 	combo_day_of_week  		= new ListEnumEdit<DayOfWeek>(DayOfWeek.class);
			JSpinner 					combo_hour  			= new JSpinner();
			JSpinner 					combo_minute  			= new JSpinner();
			JSpinner 					combo_second  			= new JSpinner();
			
			JCheckBox every_year			= new JCheckBox("每次");
			JCheckBox every_month			= new JCheckBox("每次");
			JCheckBox every_day_of_month	= new JCheckBox("每次");
//			JCheckBox every_week_of_year	= new JCheckBox("每次");
			JCheckBox every_week_of_month	= new JCheckBox("每次");
			JCheckBox every_day_of_week		= new JCheckBox("每次");
			JCheckBox every_hour			= new JCheckBox("每次");
			JCheckBox every_minute			= new JCheckBox("每次");
			JCheckBox every_second			= new JCheckBox("每次");

			HashMap<JCheckBox, Component> check_map = new HashMap<JCheckBox, Component>();
			
			Component[][] cells;
			
			public TimePanel(FestivalDate date) 
			{
				super(null);

				int sh = 25;
				lbl_type			.setSize(100, sh);
				lbl_year			.setSize(80,  sh);
				lbl_month			.setSize(80,  sh);
				lbl_day_of_month	.setSize(60,  sh);
//				lbl_week_of_year	.setSize(60,  sh);
				lbl_week_of_month	.setSize(60,  sh);
				lbl_day_of_week		.setSize(80,  sh);
				lbl_hour			.setSize(60,  sh);
				lbl_minute			.setSize(60,  sh);
				lbl_second			.setSize(60,  sh);
				
				cells = new Component[][]{
				{lbl_type,			 combo_type			,null				},	
				{lbl_year,			 combo_year			,every_year			},	
				{lbl_month,			 combo_month		,every_month		},	
				{lbl_day_of_month,	 combo_day_of_month	,every_day_of_month	},	
//				{lbl_week_of_year,	 combo_week_of_year	,every_week_of_year	},	
				{lbl_week_of_month,	 combo_week_of_month,every_week_of_month},	
				{lbl_day_of_week,	 combo_day_of_week	,every_day_of_week	},	
				{lbl_hour,			 combo_hour			,every_hour			},	
				{lbl_minute,		 combo_minute		,every_minute		},	
				{lbl_second,		 combo_second		,every_second		},
				};
				
				combo_year			.setModel(new SpinnerNumberModel(1900, 1900, Short.MAX_VALUE, 1));
				combo_day_of_month	.setModel(new SpinnerNumberModel(1, 1, 31, 1));
//				combo_week_of_year	.setModel(new SpinnerNumberModel(1, 1, 54, 1));
				combo_week_of_month	.setModel(new SpinnerNumberModel(1, 1, 5,  1));
				combo_hour			.setModel(new SpinnerNumberModel(0, 0, 23, 1));
				combo_minute		.setModel(new SpinnerNumberModel(0, 0, 59, 1));
				combo_second		.setModel(new SpinnerNumberModel(0, 0, 59, 1));
				
				combo_type.setSelectedItem(date.type);
				combo_type.addItemListener(this);
				
				layoutChilds();
				
				// set default value
				try
				{
					combo_year			.setValue(date.year.getKey());
					combo_month			.setValue(EnumManager.toEnum(MonthOfYear.class, date.month.getKey()));
					combo_day_of_month	.setValue(date.day_of_month.getKey());
//					combo_week_of_year	.setValue(date.week_of_year.getKey());
					combo_week_of_month	.setValue(date.week_of_month.getKey());
					combo_day_of_week	.setValue(EnumManager.toEnum(DayOfWeek.class, date.day_of_week.getKey()));
					combo_hour			.setValue(date.hour.getKey());
					combo_minute		.setValue(date.minute.getKey());
					combo_second		.setValue(date.second.getKey());
					
					every_year			.setSelected(date.year.getValue());
					every_month			.setSelected(date.month.getValue());
					every_day_of_month	.setSelected(date.day_of_month.getValue());
//					every_week_of_year	.setSelected(date.week_of_year.getValue());
					every_week_of_month	.setSelected(date.week_of_month.getValue());
					every_day_of_week	.setSelected(date.day_of_week.getValue());
					every_hour			.setSelected(date.hour.getValue());
					every_minute		.setSelected(date.minute.getValue());
					every_second		.setSelected(date.second.getValue());
					
					for (int c=0; c<cells.length; c++) {
						if (c > 0) {
							JCheckBox check = ((JCheckBox)cells[c][2]);
							cells[c][1].setEnabled(!check.isSelected());
						}
					}
					

					if (every_day_of_week.isSelected()) {
						every_week_of_month.setVisible(!every_day_of_week.isSelected());
						combo_week_of_month.setVisible(!every_day_of_week.isSelected());
						repaint();
					}
					
				} 
				catch (Exception err) {
					err.printStackTrace();
				}
				
			}
			
			
			@Override
			public void itemStateChanged(ItemEvent e) {
					if (e.getSource() == combo_type) {
						layoutChilds();
					}
				
				
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (check_map.containsKey(e.getSource())) {
					JCheckBox check = (JCheckBox)e.getSource();
					Component value = check_map.get(e.getSource());
					value.setEnabled(!check.isSelected());
				}
				if (e.getSource() == every_day_of_week) {
					every_week_of_month.setVisible(!every_day_of_week.isSelected());
					combo_week_of_month.setVisible(!every_day_of_week.isSelected());
					repaint();
				}
			}
			
			private void setDate(FestivalDate date)
			{
				date.type = combo_type.getValue();
				
				date.year			.setKey(Parser.castNumber(combo_year.getValue(), Short.class));
				date.month			.setKey(combo_month.getValue().getValue());
				date.day_of_month	.setKey(Parser.castNumber(combo_day_of_month.getValue(), Byte.class));
//				date.week_of_year	.setKey(Parser.castNumber(combo_week_of_year.getValue(), Byte.class));
				date.week_of_month	.setKey(Parser.castNumber(combo_week_of_month.getValue(), Byte.class));
				date.day_of_week	.setKey(combo_day_of_week.getValue().getValue());
				date.hour			.setKey(Parser.castNumber(combo_hour.getValue(), Byte.class));
				date.minute			.setKey(Parser.castNumber(combo_minute.getValue(), Byte.class));
				date.second			.setKey(Parser.castNumber(combo_second.getValue(), Byte.class));
				
				date.year			.setValue(every_year.isSelected());
				date.month			.setValue(every_month.isSelected());
				date.day_of_month	.setValue(every_day_of_month.isSelected());
//				date.week_of_year	.setValue(every_week_of_year.isSelected());
				date.week_of_month	.setValue(every_week_of_month.isSelected());
				date.day_of_week	.setValue(every_day_of_week.isSelected());
				date.hour			.setValue(every_hour.isSelected());
				date.minute			.setValue(every_minute.isSelected());
				date.second			.setValue(every_second.isSelected());
				
				System.out.println(date.toString());
				System.out.println(date.toCronExpression());
			}
			
//			-----------------------------------------------------------------
			
			private void layoutChilds()
			{
				int sx = 10;
				int mw = 10;

				for (int c=0; c<cells.length; c++) {
					this.remove(cells[c][0]);
					this.remove(cells[c][1]);
					if (c > 0) {
						this.remove(cells[c][2]);
					}
				}
				
				for (int c=0; c<cells.length; c++)
				{
					switch (combo_type.getValue()) {
					case DAY_OF_MONTH:
						if (/*cells[c][0] == lbl_week_of_year ||*/
							cells[c][0] == lbl_week_of_month || 
							cells[c][0] == lbl_day_of_week) {
							continue;
						}
						break;
//					case WEEK_OF_YEAR:
//						if (cells[c][0] == lbl_month || 
//							cells[c][0] == lbl_day_of_month ||
//							cells[c][0] == lbl_week_of_month) {
//							continue;
//						}
//						break;

					case WEEK_OF_MONTH:
						if (cells[c][0] == lbl_day_of_month /*||
							cells[c][0] == lbl_week_of_year*/) {
							continue;
						}
						break;
					}
					
					cells[c][0].setLocation(sx, 0);
					cells[c][1].setBounds(
							cells[c][0].getX(), 
							cells[c][0].getY() + cells[c][0].getHeight(), 
							cells[c][0].getWidth(), 
							cells[c][0].getHeight());
					this.add(cells[c][0]);
					this.add(cells[c][1]);
					if (c > 0) {
						JCheckBox check = ((JCheckBox)cells[c][2]);
						check.setBounds(
								cells[c][1].getX(), 
								cells[c][1].getY() + cells[c][0].getHeight(), 
								cells[c][1].getWidth(), 
								cells[c][1].getHeight());
						this.add(check);
						this.check_map.put(check, cells[c][1]);
						check.removeActionListener(this);
						check.addActionListener(this);
					}
					sx += cells[c][0].getWidth();
					mw = Math.max(mw, cells[c][0].getX() + cells[c][0].getWidth());
				}
				
				mw += 100;
				
				this.setPreferredSize(new Dimension(mw, 50));
				
				this.updateUI();
				this.repaint();
			}
			
			
		}
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
	
}

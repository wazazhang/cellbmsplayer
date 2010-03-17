package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractDialog;


/**
 * @author WAZA
 * 可编辑多个能力的面板
 */
public class AbilityPanel extends JPanel implements MouseListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	// properties	
	final Abilities		abilities;
	CellEditAdapter<?>	adapters[];
	// ui
	JList 				list_cur_ability 	= new JList();
	JButton 			btn_add_ability 	= new JButton("添加能力");
	JButton 			btn_del_ability 	= new JButton("删除能力");
	
	JSplitPane 			split 				= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JPanel 				left 				= new JPanel(new BorderLayout());
	JPanel				right 				= new JPanel();
	
	public AbilityPanel(Abilities abilities, CellEditAdapter<?> ... adapters)
	{
		this.abilities 		= abilities;
		this.adapters		= new CellEditAdapter[adapters.length+2];
		System.arraycopy(adapters, 0, this.adapters, 0, adapters.length);
		this.adapters[adapters.length+0] = new AbilitiesCellEditAdapter();
		this.adapters[adapters.length+1] = new PropertyAdapters.UnitTypeAdapter();
		
		this.setLayout(new BorderLayout());
		
		// left
		{
			// left center
			{
				this.list_cur_ability.setListData(abilities.getAbilities());
				this.list_cur_ability.addMouseListener(this);
				this.list_cur_ability.setCellRenderer(new ListRender());
				left.add(new JScrollPane(list_cur_ability), BorderLayout.CENTER);
			}
			// top tool bar
			{
				JToolBar bpan = new JToolBar();
				this.btn_add_ability.addActionListener(this);
				this.btn_del_ability.addActionListener(this);
				bpan.add(btn_add_ability);
				bpan.add(btn_del_ability);
				left.add(bpan, BorderLayout.NORTH);
			}
			split.setLeftComponent(left);
		}
		// right
		{
			split.setRightComponent(right);
		}
		
		this.add(split, BorderLayout.CENTER);
	}

//	-----------------------------------------------------------------------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_del_ability) {
			deleteSeletedAbility();
		}
		else if (e.getSource() == btn_add_ability) {
			if (abilities.getSubAbilityTypes()!=null && abilities.getSubAbilityTypes().length>0){
				new AddAbilityForm(this).setVisible(true);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == list_cur_ability) {
			Object selected = list_cur_ability.getSelectedValue();
			if (selected instanceof AbstractAbility) {
				resetAbility();
				AbstractAbility ability = (AbstractAbility)selected;
				ObjectPropertyPanel panel = new ObjectPropertyPanel(ability, adapters);
				split.setRightComponent(panel);
				list_cur_ability.setSelectedValue(selected, false);
			}
		} else {
			split.setRightComponent(right);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	
//	-----------------------------------------------------------------------------------------------------------------------------
	public void deleteSeletedAbility() {
		try{
			AbstractAbility data = (AbstractAbility)list_cur_ability.getSelectedValue();
			this.abilities.removeAbility(data);
			this.list_cur_ability.setListData(abilities.getAbilities());
		}catch (Exception err) {}
	}
	

	/**
	 * 得到正在编辑的Abilities
	 * @return
	 */
	public Abilities getAbilities() {
		return abilities;
	}
	
	public void resetAbility() {
		this.list_cur_ability.setListData(abilities.getAbilities());
	}
	
	public void addAbility(AbstractAbility ability) {
		this.abilities.addAbility(ability);
		this.list_cur_ability.setListData(abilities.getAbilities());
	}
	
	@Override
	public String toString(){
		return "Ability";
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	class ListRender extends DefaultListCellRenderer
	{
		private static final long serialVersionUID = 1L;

		public ListRender() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Component ret = super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
			if (value instanceof AbstractAbility) {
				this.setText(AbstractAbility.getName(value.getClass()));
			}
			return ret;
		}
	}
	

//	-----------------------------------------------------------------------------------------------------------------------------

	/**
	 * 添加能力时弹出的框
	 * @author WAZA
	 */
	final class AddAbilityForm extends AbstractDialog implements ListCellRenderer, ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JComboBox		combo_abilities;
		JButton			btn_add			= new JButton("确定");
		JPanel			pan_property	= new JPanel();
		AbstractAbility	current_ability;
		
		public AddAbilityForm(Component owner)
		{
			super(owner);
			super.setTitle("添加能力到 : " + abilities);
			super.setLayout(new BorderLayout());
			this.setIconImage(Res.icon_edit);
			{
				combo_abilities = new JComboBox(abilities.getSubAbilityTypes());
				combo_abilities.setRenderer(this);
				combo_abilities.addActionListener(this);
				this.add(combo_abilities, BorderLayout.NORTH);
			}
			{
				pan_property.setLayout(new BorderLayout());
				this.add(pan_property, BorderLayout.CENTER);
			}
			{
				btn_add.setActionCommand("btn_add");
				btn_add.addActionListener(this);
				this.add(btn_add, BorderLayout.SOUTH);
			}
			combo_abilities.setSelectedIndex(0);
		}
		
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == combo_abilities) {
				Class<? extends AbstractAbility> ability_cls = (Class<? extends AbstractAbility>)combo_abilities.getSelectedItem();
				setAbilityClass(ability_cls);
			} 
			else if (e.getSource() == btn_add) {
				try{
					AbilityPanel.this.addAbility(current_ability);
					AddAbilityForm.this.setVisible(false);
					AddAbilityForm.this.dispose();
				}catch (Exception err) {
					err.printStackTrace();
				}
			}
		}
		

		@SuppressWarnings("unchecked")
		public Component getListCellRendererComponent(JList list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Class<? extends AbstractAbility> ability_cls = (Class<? extends AbstractAbility>)value;
			return new JLabel(AbstractAbility.getName(ability_cls));
		}
		
		
		public void setAbilityClass(Class<? extends AbstractAbility> cls) 
		{
			current_ability = AbstractAbility.createAbility(cls);
			ObjectPropertyPanel obj_pan = new ObjectPropertyPanel(current_ability, adapters);
			pan_property.removeAll();
			pan_property.add(obj_pan, BorderLayout.CENTER);
			pan_property.updateUI();
			
			// 如果该 ability 不允许多个实例
			if (!current_ability.isMultiField() && abilities.getAbility(current_ability.getClass())!=null) {
				btn_add.setForeground(Color.RED);
				btn_add.setEnabled(false);
				btn_add.setText(AbstractAbility.getName(current_ability.getClass())+" (已存在)");
			} else {
				btn_add.setForeground(Color.BLACK);
				btn_add.setEnabled(true);
				btn_add.setText("确定");
			}
		}
		
	}

	
//	---------------------------------------------------------------------------------------------------------

	class AbilitiesCellEditAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() {
			return Object.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field) {
			// 测试是否是集合
			try {
				field.getType().asSubclass(Abilities.class);
				System.out.println("field is Abilities");
				if (fieldValue == null) {
					fieldValue = field.getType().newInstance();
				}
				return new AbilityForm(
						AbstractDialog.getTopWindow(AbilityPanel.this),
						(Abilities) fieldValue,
						adapters);
			} catch (Exception e) {}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
			return null;
		}
		
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue, Field field) {
			return false;
		}
	
		@Override
		public Object getCellValue(Object editObject,
			PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			return null;
		}
	}
	

//	---------------------------------------------------------------------------------------------------------
	
	public static abstract class AbilityCellEditAdapter<T extends AbstractAbility> implements CellEditAdapter<T>
	{
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue,
				Field field) {
			return false;
		}

		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
				Object editObject, Object fieldValue, Field field) {
			return null;
		}

		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject,
			PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			return null;
		}
	}
	
}

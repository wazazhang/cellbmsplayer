package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

import com.cell.rpg.ability.*;
import com.cell.rpg.scene.ability.*;
import com.cell.rpg.template.ability.*;

import com.cell.rpg.xls.XLSFile;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Config;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.studio.scene.editor.SceneListCellEdit;
import com.g2d.studio.scene.editor.SceneUnitListCellEdit;
import com.g2d.studio.swing.G2DListItem;
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
	
	final Hashtable<Class<?>, AbilityCellEditAdapter<?>>	
						edit_adapters 		= new Hashtable<Class<?>, AbilityCellEditAdapter<?>>();
	// ui
	JList 				list_cur_ability 	= new JList();
	JButton 			btn_add_ability 	= new JButton("添加能力");
	JButton 			btn_del_ability 	= new JButton("删除能力");
//	JScrollPane			right 				= new JScrollPane();
	JSplitPane 			split 				= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	public AbilityPanel(Abilities abilities, AbilityCellEditAdapter<?> ... adapters)
	{
		this.abilities 		= abilities;
		for (AbilityCellEditAdapter<?> ad : adapters) {
			edit_adapters.put(ad.getClass(), ad);
		}
		
		this.setLayout(new BorderLayout());
		
		// left
		{
			JPanel left = new JPanel(new BorderLayout());
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
//			split.setRightComponent(right);
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
			new AddAbilityForm().setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == list_cur_ability) {
//	        if (e.getClickCount() == 2) {
//	            AbstractAbility data = (AbstractAbility)list_cur_ability.getSelectedValue();
//	            new AbilityPropertyForm(data).setVisible(true);
//	        }
			Object selected = list_cur_ability.getSelectedValue();
			if (selected instanceof AbstractAbility) {
				resetAbility();
				AbstractAbility ability = (AbstractAbility)selected;
				AbilityPropertyPanel panel = new AbilityPropertyPanel(ability);
//				right.setViewportView(panel);
				split.setRightComponent(panel);
				list_cur_ability.setSelectedValue(selected, false);
			}
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
		
		public AddAbilityForm()
		{
			super.setTitle("添加能力到 : " + abilities);
			super.setLayout(new BorderLayout());
			
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
		
		
		public Component getListCellRendererComponent(JList list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Class<? extends AbstractAbility> ability_cls = (Class<? extends AbstractAbility>)value;
			return new JLabel(AbstractAbility.getName(ability_cls));
		}
		
		
		public void setAbilityClass(Class<? extends AbstractAbility> cls) 
		{
			current_ability = AbstractAbility.createAbility(cls);
			AbilityPropertyPanel obj_pan = new AbilityPropertyPanel(current_ability);
			pan_property.removeAll();
			pan_property.add(obj_pan, BorderLayout.CENTER);
			pan_property.updateUI();
			
			// 如果该 ability 不允许多个实例
			if (!current_ability.isMultiField() && abilities.getAbility(current_ability.getClass())!=null) 
			{
				btn_add.setForeground(Color.RED);
				btn_add.setEnabled(false);
				btn_add.setText(AbstractAbility.getName(current_ability.getClass())+" (已存在)");
			}
			else 
			{
				btn_add.setForeground(Color.BLACK);
				btn_add.setEnabled(true);
				btn_add.setText("确定");
			}
		}
		
	}
	
	/**
	 * @author WAZA
	 * 能力属性编辑器
	 */
	public class AbilityPropertyPanel extends ObjectPropertyPanel
	{
		private static final long serialVersionUID = 1L;
		
		final public AbstractAbility ability;
		
		public AbilityPropertyPanel(AbstractAbility ability)
		{
			super(ability);
			this.ability = ability;
		}

		@Override
		protected PropertyCellEdit<?> getPropertyCellEdit(Object object, Field field, Object value) {
			PropertyCellEdit<?> ret = getAbilityCellEdit(object, field, value);
			if (ret != null) {
				return ret;
			}
			return super.getPropertyCellEdit(object, field, value);
		}
		
		@Override
		protected void onFieldChanged(Object object, Field field) {
			try{
				if (object instanceof AbstractAbility) {
					for (AbilityCellEditAdapter<?> ad : edit_adapters.values()) {
						if (ad.getAbilityType().isInstance(object)) {
							if (ad.fieldChanged(getAbilities(), ad.getAbilityType().cast(object), field)){
								return;
							}
						}
					}
				}
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		
//		--------------------------------------------------------------------------------------------------------
		
		/**
		 * 得到Ability相应的编辑器
		 * @param object
		 * @param field
		 * @param value
		 * @return
		 */
		PropertyCellEdit<?> getAbilityCellEdit(Object object, Field field, Object value) 
		{
			// 测试是否是集合
			try {
				field.getType().asSubclass(Abilities.class);
				System.out.println("field is Abilities");
				if (value == null) {
					value = field.getType().newInstance();
				}
				return new AbilityForm((Abilities) value, edit_adapters.values());
			} catch (Exception e) {}

			// 从适配器里选取
			try {
				if (object instanceof AbstractAbility) {
					for (AbilityCellEditAdapter<?> ad : edit_adapters.values()) {
						if (ad.getAbilityType().isInstance(object)) {
							PropertyCellEdit<?> edit = ad.getAbilityCellEdit(getAbilities(), ad.getAbilityType().cast(object), field, value);
							if (edit!=null) {
								return edit;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
	}
	
	
//	---------------------------------------------------------------------------------------------------------
	
	public static abstract class AbilityCellEditAdapter<T extends AbstractAbility>
	{
		public abstract Class<T> 	getAbilityType();
		
		public PropertyCellEdit<?>	getAbilityCellEdit(Abilities abilities, AbstractAbility ability, Field field, Object value) {
			return null;
		}
		
		public boolean 				fieldChanged(Abilities abilities, AbstractAbility ability, Field field) {
			return false;
		}
	}
	
}

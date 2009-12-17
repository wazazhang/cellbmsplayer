package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.rpg.formula.AbstractMethod;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.Arithmetic;
import com.cell.rpg.formula.MathMethod;
import com.cell.rpg.formula.ObjectProperty;
import com.cell.rpg.formula.SystemMethod;
import com.cell.rpg.formula.TimeDurationValue;
import com.cell.rpg.formula.TimeValue;
import com.cell.rpg.formula.Value;
import com.cell.rpg.formula.Arithmetic.Operator;
import com.cell.rpg.quest.TriggerUnitType;
import com.cell.rpg.quest.formula.QuestStateProperty;
import com.cell.rpg.quest.formula.TriggerUnitProperty;
import com.cell.rpg.quest.formula.QuestStateProperty.QuestStateField;
import com.cell.rpg.xls.XLSColumns;
import com.cell.util.Pair;
import com.cell.util.Properties;
import com.g2d.annotation.Property;
import com.g2d.display.ui.ComboBox;
import com.g2d.editor.property.ListEnumEdit;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.XLSColumnSelectCellEdit;
import com.g2d.studio.quest.QuestSelectCellEdit;
import com.g2d.studio.quest.QuestSelectCellEditComboBox;
import com.g2d.util.AbstractDialog;

public class FormulaEdit extends AbstractDialog implements PropertyCellEdit<AbstractValue>, ItemListener, ActionListener
{
//	-------------------------------------------------------------------------------------------

	Class<?>[]		accept_types;
	AbstractValue	value;

	JLabel 			edit_title = new JLabel();
	JComboBox 		types;
	JSplitPane		split;
	
	JButton			btn_ok		= new JButton("确定");
	JButton			btn_cancel	= new JButton("取消");
	
//	-------------------------------------------------------------------------------------------

	public FormulaEdit(Component owner, AbstractValue src) {
		this(owner, new Class<?>[]{
				Value.class,
				TriggerUnitProperty.class,
				QuestStateProperty.class,
				Arithmetic.class,
				MathMethod.class,
				SystemMethod.class,
				TimeValue.class,
				TimeDurationValue.class,
		}, src);
	}
	
	public FormulaEdit(Component owner, Class<?>[] accept_types, AbstractValue src) 
	{
		super(owner);
		super.setTitle("编辑变量");
		super.setSize(400, 300);
		this.accept_types	= accept_types;
		this.value			= src;
		
		ValueType[] value_types = new ValueType[accept_types.length];
		for (int i = 0; i < accept_types.length; i++) {
			value_types[i] = new ValueType(accept_types[i]);
		}
		
		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		types = new JComboBox(value_types);
		types.addItemListener(this);
		split.setTopComponent(types);
		this.add(split, BorderLayout.CENTER);
		setValue(value);
		
		JPanel panel = new JPanel(new FlowLayout());
		btn_ok.addActionListener(this);
		btn_cancel.addActionListener(this);
		panel.add(btn_ok);
		panel.add(btn_cancel);
		this.add(panel, BorderLayout.SOUTH);
	}

	@Override
	public Component getComponent(ObjectPropertyPanel panel) {
		if (value!=null) {
			edit_title.setText(value + "");
		} else {
			edit_title.setText("null");
		}
		return edit_title;
	}
	
	public AbstractValue getValue() {
		return value;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			if (split.getBottomComponent() instanceof ValueEditor) {
				ValueEditor editor = (ValueEditor)split.getBottomComponent();
				value = editor.onEditOK(value);
				this.setVisible(false);
			}
		} else if (e.getSource() == btn_cancel) {
			this.setVisible(false);
		}
	}
	
	public ValueType getValueType(Class<?> clzz) {
		for (int i=0;i<types.getItemCount();i++) {
			ValueType v = (ValueType)types.getItemAt(i);
			if (v.getKey().equals(clzz)) {
				return v;
			}
		}
		return null;
	}

	public ValueType getSelectedValueType() {
		return (ValueType)types.getSelectedItem();
	}
	
	public void setValue(AbstractValue src) {
		if (src!=null) {
			this.value = src;
			ValueType type = getValueType(src.getClass());
			types.setSelectedItem(type);
			split.setBottomComponent((Component)type.getEditComponent());
		} else {
			try {
				Class<?> value_type = accept_types[0];
				this.value = (AbstractValue)(value_type.newInstance());
				ValueType type = getValueType(value_type);
				types.setSelectedItem(type);
				split.setBottomComponent((Component)type.getEditComponent());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		ValueType type = (ValueType)types.getSelectedItem();
		split.setBottomComponent((Component)type.getEditComponent());
	}
	
//	----------------------------------------------------------------------------------------------------------

	public void showDialog() {
		super.setVisible(true);
	}
	
//	----------------------------------------------------------------------------------------------------------
	
	class ValueType extends Pair<Class<?>, Property>
	{
		ValueEditor edit_comp = null;
		
		public ValueType(Class<?> k) {
			super(k, k.getAnnotation(Property.class));
		}
		
		@Override
		public String toString() {
			return getValue().value()[0];
		}
		
		public ValueEditor getEditComponent() {
			if (edit_comp == null) {
				if (getKey().equals(Value.class)) {
					edit_comp = new PanelValue(value);
				}
				else if (getKey().equals(TriggerUnitProperty.class)) {
					edit_comp = new PanelUnitProperty(value);
				}
				else if (getKey().equals(QuestStateProperty.class)) {
					edit_comp = new PanelQuestState(value);
				}
				else if (getKey().equals(Arithmetic.class)) {
					edit_comp = new PanelArithmetic(value);
				}
				else if (getKey().equals(MathMethod.class)) {
					edit_comp = new PanelAbstractMethod(value, MathMethod.getMethods(), MathMethod.class);
				}
				else if (getKey().equals(SystemMethod.class)) {
					edit_comp = new PanelAbstractMethod(value, SystemMethod.getMethods(), SystemMethod.class);
				}
				else if (getKey().equals(TimeValue.class)) {
					edit_comp = new PanelTimeValue(value);
				}
				else if (getKey().equals(TimeDurationValue.class)) {
					edit_comp = new PanelTimeDurationValue(value);
				}
			}
			return edit_comp;
		}
	}
	
	interface ValueEditor 
	{
		AbstractValue onEditOK(AbstractValue src_value) ;
	}
	
//	----------------------------------------------------------------------------------------------------------
	
	static class PanelValue extends JPanel implements ValueEditor
	{
		JLabel		lbl		= new JLabel("值");
		JSpinner	number	= new JSpinner(new SpinnerNumberModel(1.0d, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		
		public PanelValue(AbstractValue value)
		{
			super.setLayout(new BorderLayout());
			super.add(lbl, BorderLayout.NORTH);
			super.add(number, BorderLayout.SOUTH);
			if (value instanceof Value) {
				number.setValue(((Value) value).value);
			}
		}
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			if (src_value instanceof Value) {
				((Value) src_value).value = (Double)number.getValue();
				return src_value;
			} else {
				return new Value((Double)number.getValue());
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------
	
	static class PanelArithmetic extends JPanel implements ValueEditor, ActionListener
	{
		JButton		btn_left	= new JButton();
		JComboBox	btn_op		= new ListEnumEdit<Operator>(Operator.class);
		JButton		btn_right	= new JButton();
		
		AbstractValue	v_left;
		Operator		v_op;
		AbstractValue	v_right;
		
		public PanelArithmetic(AbstractValue value) {
			super(new FlowLayout());
			btn_left.addActionListener(this);
			btn_right.addActionListener(this);
			add(btn_left);
			add(btn_op);
			add(btn_right);
			if (value instanceof Arithmetic) {
				v_left	= ((Arithmetic)value).left;
				v_op	= ((Arithmetic)value).op;
				v_right	= ((Arithmetic)value).right;
			} else {
				v_left	= new Value(1);
				v_op	= Operator.ADD;
				v_right	= new Value(1);
			}
			btn_left.setText(v_left+"");
			btn_op.setSelectedItem(v_op);
			btn_right.setText(v_right+"");
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			v_op = (Operator)btn_op.getSelectedItem();
			if (src_value instanceof Arithmetic) {
				Arithmetic art = (Arithmetic)src_value;
				art.left 	= v_left;
				art.op		= v_op;
				art.right	= v_right;
				return art;
			} else {
				Arithmetic art = new Arithmetic();
				art.left 	= v_left;
				art.op		= v_op;
				art.right	= v_right;
				return art;
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Window owner = AbstractDialog.getTopWindow(this);
			
			if (e.getSource() == btn_left) {
				FormulaEdit edit = new FormulaEdit(owner, v_left);
				edit.setLocation(owner.getX()+20, owner.getY()+20);
				edit.showDialog();
				v_left = edit.getValue();
				btn_left.setText(v_left+"");
			}
			else if (e.getSource() == btn_right) {
				FormulaEdit edit = new FormulaEdit(owner, v_right);
				edit.setLocation(owner.getX()+20, owner.getY()+20);
				edit.showDialog();
				v_right = edit.getValue();
				btn_right.setText(v_right+"");
			}
		}
	}
	

//	----------------------------------------------------------------------------------------------------------

	static class PanelUnitProperty extends JPanel implements ValueEditor, ItemListener
	{		
		ListEnumEdit<TriggerUnitType> combo_unit_type = new ListEnumEdit<TriggerUnitType>(TriggerUnitType.class);
		
		XLSColumnSelectCellEdit combo_columns_player;
		XLSColumnSelectCellEdit combo_columns_unit;
		
		public PanelUnitProperty(AbstractValue value) {
			super(new BorderLayout());
		
			combo_columns_player	= new XLSColumnSelectCellEdit(Studio.getInstance().getObjectManager().getPlayerXLSColumns());
			combo_columns_unit		= new XLSColumnSelectCellEdit(Studio.getInstance().getObjectManager().getUnitXLSColumns());
			
			super.add(combo_unit_type, BorderLayout.NORTH);
			
			if (value instanceof TriggerUnitProperty) {
				TriggerUnitProperty tup = (TriggerUnitProperty)value;
				combo_columns_player.setSelectedItem(tup.filed_name);
				combo_columns_unit.setSelectedItem(tup.filed_name);
				combo_unit_type.setSelectedItem(tup.trigger_unit_type);
			}
			if (combo_unit_type.getValue()==null) {
				combo_unit_type.setSelectedItem(TriggerUnitType.PLAYER);
			}
			changeUnitType(combo_unit_type.getValue());
			combo_unit_type.addItemListener(this);
		}
		
		void changeUnitType(TriggerUnitType type) {
			this.remove(combo_columns_player);
			this.remove(combo_columns_unit);
			switch(type) {
			case PLAYER:
			case PLAYER_GROUP:
				super.add(combo_columns_player, BorderLayout.SOUTH);
				break;
			case PET:
			case TRIGGERING_NPC:
				super.add(combo_columns_unit, BorderLayout.SOUTH);
				break;
			}
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == combo_unit_type) {
				changeUnitType(combo_unit_type.getValue());
			}
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			TriggerUnitProperty tup ;
			if (src_value instanceof TriggerUnitProperty) {
				tup = (TriggerUnitProperty)src_value;
			} else {
				tup = new TriggerUnitProperty();
			}
			tup.trigger_unit_type = combo_unit_type.getValue();
			switch(tup.trigger_unit_type) {
			case PLAYER:
			case PLAYER_GROUP:
				tup.filed_name = combo_columns_player.getValue();
				break;
			case PET:
			case TRIGGERING_NPC:
				tup.filed_name = combo_columns_unit.getValue();
				break;
			}
			return tup;
		}
	}
	
//	----------------------------------------------------------------------------------------------------------

	//	----------------------------------------------------------------------------------------------------------
	
	static class PanelAbstractMethod extends JPanel implements ValueEditor, ItemListener, ActionListener
	{
		final Class<? extends AbstractMethod> class_type;
		final Map<String, Method> methods_map;
		
		Method						mirror_method;
		AbstractValue[]				mirror_parameters;
		
		MathMethodCellEdit			combo_methods;
	
		JPanel 						btn_group 		= new JPanel(new FlowLayout());
		ArrayList<JButton> 			params_key		= new ArrayList<JButton>();
		ArrayList<AbstractValue> 	params_value	= new ArrayList<AbstractValue>();
		
		public PanelAbstractMethod(
				AbstractValue value, 
				Map<String, Method> methods_map,
				Class<? extends AbstractMethod> type) {
			super(new BorderLayout());
			this.methods_map 	= methods_map;
			this.class_type 	= type;
			this.combo_methods	= new MathMethodCellEdit(methods_map.values());
			super.add(combo_methods, BorderLayout.NORTH);
			this.add(btn_group, BorderLayout.CENTER);
			if (value instanceof AbstractMethod) {
				AbstractMethod mm = (AbstractMethod)value;
				mirror_method		= mm.getMethod();
				mirror_parameters	= CIO.cloneObject(mm.parameters);
			}
			setMethod(mirror_method);
			combo_methods.setSelectedItem(mirror_method);
			combo_methods.addItemListener(this);
		}
		
		void setMethod(Method mt) 
		{
			try
			{
				if (mt == null) {
					mirror_method = methods_map.values().iterator().next();
				} else {
					mirror_method = mt;
				}
				
				Class<?>[] params_type = mirror_method.getParameterTypes();
				
				for (int i=0; i<params_type.length; i++) 
				{
					if (i < params_key.size()) {
						AbstractValue 	value	= params_value.get(i);
						JButton 		key		= params_key.get(i);
						key.setText(value.toString());
					} else {
						AbstractValue 	value;
						if (mirror_parameters!=null && i<mirror_parameters.length && mirror_parameters[i] != null) {
							value = mirror_parameters[i];
						} else {
							value = new Value(1);
						}
						JButton 		key		= new JButton(value.toString());
						key.addActionListener(this);
						params_key.add(key);
						params_value.add(value);
						btn_group.add(key);
					}
				}
				while (params_type.length < params_key.size()) {
					int end = params_key.size() - 1;
					JButton 		key		= params_key.remove(end);
					AbstractValue 	value	= params_value.remove(end);
					btn_group.remove(key);
				}
				btn_group.updateUI();
			}
			catch (Exception err){
				err.printStackTrace();
			}
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			mirror_parameters = params_value.toArray(new AbstractValue[params_value.size()]);
			System.out.println(mirror_method);
			for (AbstractValue v : mirror_parameters) {
				System.out.println(v);
			}
			AbstractMethod mm = null;
			if (src_value instanceof AbstractMethod) {
				mm = (AbstractMethod)src_value;
			} else {
				try {
					mm = class_type.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mm.method_name	= mirror_method.getName();
			mm.parameters	= mirror_parameters;
			return mm;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == combo_methods) {
				setMethod(combo_methods.getValue());
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Window owner = AbstractDialog.getTopWindow(this);
			if (params_key.contains(e.getSource())) {
				int 			index	= params_key.indexOf(e.getSource());
				JButton			key		= (JButton)e.getSource();
				AbstractValue	value	= params_value.get(index);
				FormulaEdit		edit	= new FormulaEdit(owner, value);
				edit.setLocation(owner.getX()+20, owner.getY()+20);
				edit.showDialog();
				value = edit.getValue();
				params_value.set(index, value);
				key.setText(value+"");
			}
		}
		
	}

//	----------------------------------------------------------------------------------------------------------
	

	static class PanelQuestState extends JPanel implements ValueEditor
	{
		ListEnumEdit<QuestStateField> field = new ListEnumEdit<QuestStateField>(QuestStateField.class);
		
		QuestSelectCellEditComboBox quest = new QuestSelectCellEditComboBox();
		
		
		public PanelQuestState(AbstractValue value)
		{
			super.setLayout(new BorderLayout());
			super.add(quest, BorderLayout.NORTH);
			super.add(field, BorderLayout.SOUTH);
			if (value instanceof QuestStateProperty) {
				field.setSelectedItem(((QuestStateProperty) value).trigger_unit_field);
				quest.setValue(((QuestStateProperty) value).quest_id);
			} else {
				field.setSelectedItem(QuestStateField.ACCEPT_TIME_MS);
			}
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			if (src_value instanceof QuestStateProperty) {
				((QuestStateProperty) src_value).quest_id			= quest.getValue();
				((QuestStateProperty) src_value).trigger_unit_field = field.getValue();
				return src_value;
			} else {
				QuestStateProperty ret = new QuestStateProperty(
						quest.getValue(),
						field.getValue());
				return ret;
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------
	static class PanelTimeValue extends JPanel implements ValueEditor
	{
		SpinnerDateModel 	model	= new SpinnerDateModel();
		JSpinner 			spinner	= new JSpinner(model);
		
		public PanelTimeValue(AbstractValue value)
		{
			super.setLayout(new BorderLayout());
			super.add(spinner, BorderLayout.NORTH);
			if (value instanceof TimeValue) {
				spinner.setValue(((TimeValue) value).getDate());
			}
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			if (src_value instanceof TimeValue) {
				((TimeValue) src_value).setDate(model.getDate());
				return src_value;
			} else {
				return new TimeValue(model.getDate().getTime());
			}
		}
	}
//	----------------------------------------------------------------------------------------------------------

	static class PanelTimeDurationValue extends JPanel implements ValueEditor
	{
		ListEnumEdit<TimeUnit> time_unit = new ListEnumEdit<TimeUnit>(TimeUnit.class);
		
		SpinnerNumberModel	model	= new SpinnerNumberModel();
		JSpinner 			spinner	= new JSpinner(model);
		
		public PanelTimeDurationValue(AbstractValue value)
		{
			super.setLayout(new BorderLayout());
			super.add(time_unit, BorderLayout.NORTH);
			super.add(spinner, BorderLayout.SOUTH);
			if (value instanceof TimeDurationValue) {
				time_unit.setSelectedItem(((TimeDurationValue) value).time_unit);
				model.setValue(((TimeDurationValue) value).duration);
			} else {
				time_unit.setSelectedItem(TimeUnit.MINUTES);
			}
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue srcValue) {
			if (srcValue instanceof TimeDurationValue) {
				((TimeDurationValue) srcValue).duration = (Integer)model.getValue();
				((TimeDurationValue) srcValue).time_unit = time_unit.getValue();
				return srcValue;
			} else {
				return new TimeDurationValue(
						(Integer)model.getValue(),
						time_unit.getValue()
				);
			}
		}
	}
	
	
}


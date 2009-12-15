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
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.CObject;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.Arithmetic;
import com.cell.rpg.formula.MathMethod;
import com.cell.rpg.formula.ObjectProperty;
import com.cell.rpg.formula.Value;
import com.cell.rpg.formula.Arithmetic.Operator;
import com.cell.rpg.xls.XLSColumns;
import com.cell.util.Pair;
import com.cell.util.Properties;
import com.g2d.annotation.Property;
import com.g2d.editor.property.ListEnumEdit;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.gameedit.XLSColumnSelectCellEdit;
import com.g2d.util.AbstractDialog;

public class FormulaEdit extends AbstractDialog implements PropertyCellEdit<AbstractValue>, ItemListener, ActionListener
{
//	-------------------------------------------------------------------------------------------

	XLSColumns		columns;
	AbstractValue	value;

	JLabel 			edit_title = new JLabel();
	JComboBox 		types;
	JSplitPane		split;
	
	JButton			btn_ok		= new JButton("确定");
	JButton			btn_cancel	= new JButton("取消");
	
//	-------------------------------------------------------------------------------------------
	
	public FormulaEdit(Component owner, XLSColumns columns) {
		this(owner, columns, null);
	}
	
	public FormulaEdit(Component owner, XLSColumns columns, AbstractValue src) 
	{
		super(owner);
		super.setTitle("编辑变量");
		super.setSize(400, 300);
		this.columns 	= columns;
		this.value		= src;
		
		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		types = new JComboBox(
				new ValueType[]{
						new ValueType(Value.class),
						new ValueType(Arithmetic.class),
						new ValueType(ObjectProperty.class),
						new ValueType(MathMethod.class),
				}
		);
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
			this.value = new Value(0);
			ValueType type = getValueType(Value.class);
			types.setSelectedItem(type);
			split.setBottomComponent((Component)type.getEditComponent());
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		ValueType type = (ValueType)types.getSelectedItem();
		split.setBottomComponent((Component)type.getEditComponent());
		System.out.println(e);
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
				else if (getKey().equals(Arithmetic.class)) {
					edit_comp = new PanelArithmetic(value, columns);
				}
				else if (getKey().equals(ObjectProperty.class)) {
					edit_comp = new PanelObjectProperty(value, columns);
				}
				else if (getKey().equals(MathMethod.class)) {
					edit_comp = new PanelMathMethod(value, columns);
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
				number.setValue(value.getValue());
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
		XLSColumns columns;
		
		JButton		btn_left	= new JButton();
		JComboBox	btn_op		= new ListEnumEdit<Operator>(Operator.class);
		JButton		btn_right	= new JButton();
		
		AbstractValue	v_left;
		Operator		v_op;
		AbstractValue	v_right;
		
		public PanelArithmetic(AbstractValue value, XLSColumns columns) {
			super(new FlowLayout());
			this.columns = columns;
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
				FormulaEdit edit = new FormulaEdit(owner, columns, v_left);
				edit.setLocation(owner.getX()+20, owner.getY()+20);
				edit.showDialog();
				v_left = edit.getValue();
				btn_left.setText(v_left+"");
			}
			else if (e.getSource() == btn_right) {
				FormulaEdit edit = new FormulaEdit(owner, columns, v_right);
				edit.setLocation(owner.getX()+20, owner.getY()+20);
				edit.showDialog();
				v_right = edit.getValue();
				btn_right.setText(v_right+"");
			}
		}
	}
	

//	----------------------------------------------------------------------------------------------------------

	static class PanelObjectProperty extends JPanel implements ValueEditor
	{
		XLSColumnSelectCellEdit combo_columns;
		
		public PanelObjectProperty(AbstractValue value, XLSColumns columns) {
			super(new BorderLayout());
			combo_columns = new XLSColumnSelectCellEdit(columns);
			super.add(combo_columns, BorderLayout.NORTH);
			if (value instanceof ObjectProperty) {
				combo_columns.setSelectedItem(((ObjectProperty) value).filed_name);
			}
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			if (src_value instanceof ObjectProperty) {
				((ObjectProperty) src_value).filed_name = combo_columns.getSelectedItem() + "";
				return src_value;
			} else {
				return new ObjectProperty(combo_columns.getSelectedItem() + "");
			}
		}
	}
	
//	----------------------------------------------------------------------------------------------------------

	static class PanelMathMethod extends JPanel implements ValueEditor, ItemListener, ActionListener
	{
		XLSColumns columns;
		
		MathMethodCellEdit methods = new MathMethodCellEdit();
		
		JPanel btn_group = new JPanel(new FlowLayout());

		MathMethod mirror = new MathMethod();
		
		LinkedHashMap<JButton, AbstractValue> params_map = new LinkedHashMap<JButton, AbstractValue>();
		
		
		public PanelMathMethod(AbstractValue value, XLSColumns columns) {
			super(new BorderLayout());
			this.columns = columns;
			super.add(methods, BorderLayout.NORTH);
			this.add(btn_group, BorderLayout.CENTER);
			if (value instanceof MathMethod) {
				MathMethod mm = (MathMethod)value;
				methods.setSelectedItem(mm.getMethod());
				setMethod(mm.getMethod());
			} else {
				setMethod(MathMethod.methods.values().iterator().next());
			}
			methods.addItemListener(this);
		}
		
		void setMethod(Method method) 
		{
			for (JButton btn : params_map.keySet()) {
				btn_group.remove(btn);
			}
			params_map.clear();
			
			if (method != null) {
				mirror.setMethod(method);
				for (Class<?> p : method.getParameterTypes()) {
					JButton 		key		= new JButton();
					AbstractValue 	value	= new Value(1);
					key.addActionListener(this);
					btn_group.add(key);
					params_map.put(key, value);
					key.setText(value+"");
				}
			}
		}
		
		@Override
		public AbstractValue onEditOK(AbstractValue src_value) {
			mirror.parameters = params_map.values().toArray(new AbstractValue[params_map.size()]);
			System.out.println(mirror.method_name);
			for (AbstractValue v : mirror.parameters) {
				System.out.println(v);
			}
			if (src_value instanceof MathMethod) {
				((MathMethod) src_value).method_name	= mirror.method_name;
				((MathMethod) src_value).parameters		= mirror.parameters;
				return src_value;
			} else {
				return mirror;
			}
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == methods) {
				setMethod(methods.getValue());
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Window owner = AbstractDialog.getTopWindow(this);
			if (params_map.containsKey(e.getSource())) {
				JButton			key		= (JButton)e.getSource();
				AbstractValue	value	= params_map.get(key);
				FormulaEdit		edit	= new FormulaEdit(owner, columns, value);
				edit.setLocation(owner.getX()+20, owner.getY()+20);
				edit.showDialog();
				value = edit.getValue();
				params_map.put(key, value);
				key.setText(value+"");
			}
		}
		
	}
	
//	----------------------------------------------------------------------------------------------------------
	
	
	
}


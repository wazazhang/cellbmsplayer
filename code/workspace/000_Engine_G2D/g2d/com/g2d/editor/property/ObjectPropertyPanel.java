package com.g2d.editor.property;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.reflect.Field;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import com.cell.CUtil;
import com.cell.reflect.Parser;

import com.g2d.annotation.Property;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.editor.Util;
import com.g2d.util.AbstractDialog;


/**
 * 该编辑器可将一个对象内所有的 {@link Property}标注的字段，显示在该控件内，并可以编辑。<br>
 * 用户可以自己写{@link PropertyCellEdit}接口来实现自定义的字段编辑器
 * @author WAZA
 *
 */
public class ObjectPropertyPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	public static int DEFAULT_ROW_HEIGHT = 24;
	
	final public Object 	object;

	final Map<Class<?>, CellEditAdapter<?>>	
							edit_adapters 	= new Hashtable<Class<?>, CellEditAdapter<?>>();
	
	final Vector<Object[]> 	rows			= new Vector<Object[]>();
	final FieldTable		rows_table;
	final JTextPane			anno_text;
	final ValueEditor 		value_editor	= new ValueEditor();
	
	public ObjectPropertyPanel(Object obj, CellEditAdapter<?> ... adapters){
		this(obj, 100, 200, adapters);
	}
	
	public ObjectPropertyPanel(Object obj, int min_w, int min_h, CellEditAdapter<?> ... adapters)
	{
		for (CellEditAdapter<?> ad : adapters) {
			edit_adapters.put(ad.getClass(), ad);
		}
		
		this.setLayout(new BorderLayout());
		
		this.object = obj;
		
		// north
		{
			Property type_property = object.getClass().getAnnotation(Property.class);
			if (type_property!=null) {
				JLabel lbl_name = new JLabel(CUtil.arrayToString(type_property.value(), " - "));
				this.add(lbl_name, BorderLayout.NORTH);
			}
		}

		
		// center
		{
			JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			
			{
				// create fields key value
				Class<?>	cls 			= object.getClass();
				Field[]		fields 			= cls.getFields();
				String[] 	colum_header 	= new String[]{"filed", "value", "type", };
				
				for (int r = 0; r < fields.length; r++) {
					try {
						Field field = fields[r];
						if (field.getAnnotation(Property.class) != null) {
							Object[] row = new Object[] { 
									field.getName(),
									field.get(object),
									field.getType().getName(),
									field };
							rows.add(row);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				Object[][] rowsdata = rows.toArray(new Object[rows.size()][]);
				
				rows_table = new FieldTable(rowsdata, colum_header);
				
				JScrollPane scroll = new JScrollPane(rows_table);
				scroll.setMinimumSize(new Dimension(min_w, min_h));
				split.setTopComponent(scroll);
			}
			
			{
				anno_text = new JTextPane();
				
				split.setBottomComponent(anno_text);
			}
			
			
			this.add(split, BorderLayout.CENTER);
		}
		
	}
	
	public CellEditAdapter<?>[] getAdapters() {
		return edit_adapters.values().toArray(new CellEditAdapter<?>[edit_adapters.size()]);
	}
	
	/**
	 * 显示字段的表格，第一列为字段名，第二列为字段值，第三列为字段类型
	 * @author WAZA
	 *
	 */
	class FieldTable extends JTable implements ListSelectionListener
	{
		private static final long serialVersionUID = 1L;

		public FieldTable(final Object[][] rowData, final Object[] columnNames) 
		{
			super(rowData, columnNames);
			super.setRowHeight(DEFAULT_ROW_HEIGHT);
			
			super.getColumn(columnNames[1]).setCellRenderer(new TableRender());

			super.getColumn("filed").setCellEditor(new NullEditor());
			super.getColumn("value").setCellEditor(value_editor);
			super.getColumn("type").setCellEditor(new NullEditor());
			
			super.setMinimumSize(new Dimension(100, 100));

			super.getSelectionModel().addListSelectionListener(this);
		}
		
		public void valueChanged(ListSelectionEvent event) {
			try {
				int r = rows_table.getSelectedRow();
				if (r>=0 && r<rows.size()) {
					int c = 3;
					Field	field 		= (Field) rows.elementAt(r)[c];
					Object	field_value	= field.get(object);
					String	field_text	= getPropertyCellText(object, field, field_value);

					Property doc = field.getAnnotation(Property.class);
					if (doc != null) {
						anno_text.setText(
								CUtil.arrayToString(doc.value(), "\n") + 
								"--------------------------\n" + 
								field_text);
					} else {
						anno_text.setText(
								field.getName() + "\n" +
								"--------------------------\n" + 
								field_text);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			rows_table.refresh();
			repaint();
		}
		
		public void refresh()
		{
			for (int r=0; r<rows.size(); r++) 
			{
				Object[] row = rows.elementAt(r);
				Field field = (Field)row[3];
				try {
					row[1] = field.get(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.setValueAt(row[1], r, 1);
			}
		}
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 通知单元格编辑器，已经完成了编辑
	 */
	final public void fireEditingStopped(){
		value_editor.stopCellEditing();
		value_editor.getCellEditorValue();
		rows_table.repaint();
	}
	
	/**
	 * 用户自定义该字段显示的内容
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param field_value	被编辑的对象类的字段当前值
	 * @return
	 */
	final protected Component getPropertyCellRender(DefaultTableCellRenderer src, Object object, Field field, Object field_value) {
		try {
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					Component ret = ad.getCellRender(this, object, field_value, field, src);
					if (ret != null) {
						return ret;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return src;
	}
	
	/**
	 * 用户自定义该字段显示的内容(下面的信息栏里)
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param field_value	被编辑的对象类的字段当前值
	 * @return
	 */
	final protected String getPropertyCellText(Object object, Field field, Object field_value) {
		try {
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					String ret = ad.getCellText(object, field, field_value);
					if (ret != null) {
						return ret;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field_value + "";
	}
	
	/**
	 * 创建一个单元格的编辑器，该编辑器必须实现 PropertyCellEdit<br>
	 * 用于用户自自定义子类的编辑器
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param field_value	被编辑的对象类的字段当前值
	 * @return
	 */
	final protected PropertyCellEdit<?> getPropertyCellEdit(Object object, Field field, Object field_value)
	{
		// 从适配器里选取
		try {
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					PropertyCellEdit<?> edit = ad.getCellEdit(this, object, field_value, field);
					if (edit != null) {
						return edit;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (field.getType().isEnum()) 
		{
			@SuppressWarnings("unchecked")
			ListEnumEdit edit = new ListEnumEdit(field.getType());
			return edit;
		}
		else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
			ComboBooleanEdit edit = new ComboBooleanEdit((Boolean)field_value);
			return edit;
		}
		else if (field.getType().equals(Color.class))
		{
			PopupCellEditColor edit_color 		= new PopupCellEditColor();
			edit_color.setValue(field_value!=null ? (Color)field_value : null, ObjectPropertyPanel.this);
			return edit_color;
		}
		else if (field.getType().equals(UILayout.class))
		{
			PopupCellEditUILayout edit_ui_layout	= new PopupCellEditUILayout();
			edit_ui_layout.setValue(field_value!=null ? (UILayout)field_value : null, ObjectPropertyPanel.this);
			return edit_ui_layout;
		}
		else if (Parser.isNumber(field.getType())) 
		{
			NumberCellEdit numedit = new NumberCellEdit(field.getType(), field_value);
			return numedit;
		}
		else {
			TextCellEdit text_edit = new TextCellEdit();
			text_edit.setText(Util.fromObject(field_value));
			return text_edit;
		}
	}
	
	/**
	 * 当字段属性编辑器完成编辑后，得到单元格的值<br>
	 * 用于用户自自定义子类的编辑器
	 * @param object		被编辑的对象
	 * @param field			被编辑的对象类的字段
	 * @param edit			字段属性编辑器
	 * @param src_value		被编辑的对象类的字段原值
	 * @return
	 */
	final protected Object getPropertyCellEditValue(Object object, Field field, PropertyCellEdit<?> edit, Object src_value)
	{
		try{
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					Object ret = ad.getCellValue(object, edit, field, src_value);
					if (ret!=null){
						return ret;
					}
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		
		Object obj = null;
		if (edit instanceof NumberCellEdit) {
			obj = ((NumberCellEdit)edit).getValue();
		}
		else if (edit instanceof TextCellEdit) {
			obj = Util.parseObject(((TextCellEdit) edit).getValue(), src_value == null ? field.getType() : src_value.getClass());
		}
		else {
			obj = edit.getValue();
		}
		return obj;
	}

	/**
	 * 当单月格的值被改变时回调
	 * @param object 当前被改变的对象
	 * @param field 该对象在其所有者中的字段
	 */
	final protected void onFieldChanged(Object object, Field field){
		try{
			for (CellEditAdapter<?> ad : edit_adapters.values()) {
				if (ad.getType().isInstance(object)) {
					if (ad.fieldChanged(
							object, field.get(object), field)){
						return;
					}
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			try{
				rows_table.valueChanged(null);
			}catch(Exception err){}
		}
	}


//	--------------------------------------------------------------------------------------------------------------------------------------
	

	
//	--------------------------------------------------------------------------------------------------------------------------------------
	

	class TableRender extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = 1L;
		Icon old_icon;
		public TableRender() {
			old_icon = super.getIcon();
		}
		
		@Override
		public Component getTableCellRendererComponent(
				JTable table,
				Object value,
				boolean isSelected,
				boolean hasFocus, 
				int row,
				int column) 
		{
			super.setIcon(old_icon);
			
			Component src = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			try{
				Field field = (Field) rows.elementAt(row)[3];
				Component comp = getPropertyCellRender(this, object, field, field.get(object));
				return comp;
			}catch(Exception err){
				err.printStackTrace();
			}
			return src;
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 空编辑器
	 * @author WAZA
	 *
	 */
	class NullEditor extends AbstractCellEditor implements TableCellEditor
	{
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(EventObject e) {return false;}
		public Object getCellEditorValue() {return null;}
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){return null;}
	}
//	--------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 单元格值编辑器
	 * @author WAZA
	 *
	 */
	class  ValueEditor extends AbstractCellEditor implements TableCellEditor
	{
		private static final long serialVersionUID = 1L;

		int 		editrow;
		Object 		src_value;
		
		PropertyCellEdit<?> current_cell_edit;
		
		// -----------------------------------
		// editors
		
		public ValueEditor(){}
		
		
		public Object getCellEditorValue() 
		{
			try {
				Field field = (Field) rows.elementAt(editrow)[3];
				Object obj = getPropertyCellEditValue(object, field, current_cell_edit, src_value);
//				System.out.println("getCellEditorValue");
				if (obj != null) {
					field.set(object, obj);
					rows.elementAt(editrow)[1] = obj;
					onFieldChanged(object, field);
					rows_table.refresh();
					rows_table.repaint();
					return obj;
				} else {
					return src_value;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return src_value;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
		{
			src_value 	= value;
			editrow 	= row;
			Field field = (Field) rows.elementAt(editrow)[3];
			try {
				current_cell_edit = getPropertyCellEdit(object, field, value);
				Component ret = current_cell_edit.getComponent(ObjectPropertyPanel.this);
//				System.out.println("getTableCellEditorComponent");
				return ret;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		
		
	}
//	------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @author WAZA
	 *
	 * @param <T> Filed type
	 */
	public static interface CellEditAdapter<T>
	{
		public abstract Class<T> getType();

		public Component getCellRender(
				ObjectPropertyPanel owner,
				Object edit_object, 
				Object field_value, 
				Field field, 
				DefaultTableCellRenderer src);
		
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object edit_object, 
				Object field_value, 
				Field field) ;

		public Object getCellValue(
				Object edit_object, 
				PropertyCellEdit<?> field_edit, 
				Field field, 
				Object field_src_value);
		
		public boolean fieldChanged(
				Object edit_object,
				Object field_value, 
				Field field);
		
		public String getCellText(
				Object edit_object, 
				Field field, 
				Object field_src_value);

	}
	
}

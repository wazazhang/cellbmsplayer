package com.g2d.editor.property;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
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
import javax.swing.table.TableCellEditor;

import com.cell.CUtil;
import com.g2d.annotation.Property;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.editor.Util;


/**
 * 该编辑器可将一个对象内所有的 {@link Property}标注的字段，显示在该控件内，并可以编辑。<br>
 * 用户可以自己写{@link PropertyCellEdit}接口来实现自定义的字段编辑器
 * @author WAZA
 *
 */
public class ObjectPropertyPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	final public Object 	object;
	
	final Vector<Object[]> 	rows			= new Vector<Object[]>();
	final FieldTable		rows_table;
	final JTextPane			anno_text;
	final ValueEditor 		value_editor	= new ValueEditor();
	
	public ObjectPropertyPanel(Object obj)
	{
		this.setLayout(new BorderLayout());
		
		this.object = obj;
		
		// north
		{
			Property type_property = object.getClass().getAnnotation(Property.class);
			if (type_property!=null) {
				JLabel lbl_name = new JLabel(type_property.value()[0]);
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
				
				for (int r = 0; r < fields.length; r++) 
				{
					try 
					{
						Field field = fields[r];
						
						if (field.getAnnotation(Property.class)!=null) {
							Object[] row = new Object[]{
									field.getName(),
									field.get(object),
									field.getType().getName(),
									field
							};
							rows.add(row);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				Object[][] rowsdata = rows.toArray(new Object[rows.size()][]);
				
				rows_table = new FieldTable(rowsdata, colum_header);
				rows_table.getColumn("filed").setCellEditor(new NullEditor());
				rows_table.getColumn("value").setCellEditor(value_editor);
				rows_table.getColumn("type").setCellEditor(new NullEditor());
				
				rows_table.setMinimumSize(new Dimension(100,100));
				rows_table.getSelectionModel().addListSelectionListener(
				        new ListSelectionListener() {
				            public void valueChanged(ListSelectionEvent event) {
				               try {
				            	   int r = rows_table.getSelectedRow();
				            	   int c = 3;
				            	   Field field = (Field)rows.elementAt(r)[c];
				            	   
				            	   Property doc = field.getAnnotation(Property.class);
				            	   if (doc!=null) {
				            		   anno_text.setText(CUtil.arrayToString(doc.value(), "\n"));
				            	   }else{
				            		   anno_text.setText(field.getName());
				            	   }
								} catch (Exception e) {
									e.printStackTrace();
								}
								rows_table.refresh();
				            }
				        }
				);
	
				JScrollPane scroll = new JScrollPane(rows_table);
				scroll.setMinimumSize(new Dimension(100,250));
				split.setTopComponent(scroll);
			}
			
			{
				anno_text = new JTextPane();
				
				split.setBottomComponent(anno_text);
			}
			
			
			this.add(split, BorderLayout.CENTER);
		}
		
	}
	
	/**
	 * 显示字段的表格，第一列为字段名，第二列为字段值，第三列为字段类型
	 * @author WAZA
	 *
	 */
	class FieldTable extends JTable
	{
		private static final long serialVersionUID = 1L;

		public FieldTable(final Object[][] rowData, final Object[] columnNames) 
		{
			super(rowData, columnNames);
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
	
//	------------------------------------------------------------------------------------------------------
	
	/**
	 * 通知单元格编辑器，已经完成了编辑
	 */
	final public void fireEditingStopped(){
		value_editor.fireEditingStopped();
	}
	
	/**
	 * 创建一个单元格的编辑器，该编辑器必须实现 PropertyCellEdit<br>
	 * 用于用户自自定义子类的编辑器
	 * @param object
	 * @param field
	 * @param field_value
	 * @return
	 */
	public PropertyCellEdit<?> getPropertyCellEdit(Object object, Field field, Object field_value)
	{
		if (field.getType().equals(Color.class))
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
		else {
			TextCellEdit text_edit = new TextCellEdit();
			text_edit.setText(Util.fromObject(field_value));
			return text_edit;
		}
	}
	
	/**
	 * 得到单元格的值<br>
	 * 用于用户自自定义子类的编辑器
	 * @param object
	 * @param field
	 * @param edit
	 * @param src_value
	 * @return
	 */
	public Object getPropertyCellEditValue(Object object, Field field, PropertyCellEdit<?> edit, Object src_value)
	{
		Object obj = null;
		if (edit instanceof TextCellEdit) {
			obj = Util.parseObject(((TextCellEdit) edit).getValue(), src_value == null ? field.getType() : src_value.getClass());
		} else {
			obj = edit.getValue();
		}
		return obj;
	}
	
	/**
	 * 当单月格的值被改变时回调
	 * @param object
	 * @param field
	 */
	protected void onFieldChanged(Object object, Field field){}
	
	
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
		
		public void fireEditingStopped(){
			super.fireEditingStopped();
		}
		
		public Object getCellEditorValue() 
		{
//			System.out.println("set value ");
			
			try
			{
				Field field = (Field)rows.elementAt(editrow)[3];
				
				Object obj = getPropertyCellEditValue(object, field, current_cell_edit, src_value);
				
				if (obj != null) {
					field.set(object, obj);
					rows.elementAt(editrow)[1] = obj;
					onFieldChanged(object, field);
					rows_table.refresh();
					return obj;
				}
				else{
					return src_value;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return src_value;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
		{
			src_value 	= value;
			editrow 	= row;
			
			Field field = (Field)rows.elementAt(editrow)[3];
			
			try
			{
				PropertyCellEdit<?> edit = getPropertyCellEdit(object, field, value);
				current_cell_edit = edit;
				Component ret = edit.getComponent();
				edit.beginEdit(ObjectPropertyPanel.this);
//				System.out.println(e.paramString());
//				fireEditingStopped();
				
				return ret;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return null;
		}

		
		
	}
	
}

package com.g2d.editor.property;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractOptionDialog;

/**
 * 对多个同一类型的单位进行编辑，可进行数据填充等操作。
 * 用户可以自己写{@link PropertyCellEdit}接口来实现自定义的字段编辑器
 * @author WAZA
 * @param <T>
 */
@SuppressWarnings("serial")
public class ObjectPropertyRowPanel<T> extends BaseObjectPropertyPanel
{
	public static int DEFAULT_ROW_HEIGHT = 24;
	
	final private Class<? extends T>data_type;
	final private Field[]			column_fields;
	final private ArrayList<T> 		datas;
	final private String			primary_key;

	
	private Vector<String>			column_headers;
	private Vector<Vector<Object>>	column_datas;
	private FieldTable				table;
	
	Hashtable<TableColumn, Field>	column_map		= new Hashtable<TableColumn, Field>();
	ArrayList<ColumnFiller>			column_filler	= new ArrayList<ColumnFiller>();

	/**
	 * @param data_type		数据类型
	 * @param datas			数据集合
	 * @param primary_key	第一列的名字
	 * @param adapters		
	 */
	public ObjectPropertyRowPanel(
			Class<? extends T>	data_type, 
			Collection<T>		datas,
			String				primary_key,
			CellEditAdapter<?> ... adapters) 
	{
		super(adapters);
		super.setLayout(new BorderLayout());
		
		this.data_type		= data_type;
		this.primary_key	= primary_key;
		this.column_fields	= Util.getEditFields(data_type);
		this.datas			= new ArrayList<T>(datas);
		
		this.column_headers	= toTableHeadData();
		this.column_datas	= toTableRowData();
		this.table 			= new FieldTable();
		
		this.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	/**
	 * 通知单元格编辑器，已经完成了编辑
	 */
	final public void fireEditingStopped(){
		table.repaint();
	}
	
	/**
	 * 获得正在编辑的行
	 * @return
	 */
	final public T getSelectedRow() {
		int row = table.getSelectedRow();
		try{
			return datas.get(row);
		}catch(Exception err){
			return null;
		}
	}

	/**
	 * 得到指定行
	 * @param row
	 * @return
	 */
	final public T getRow(int row) {
		try{
			return datas.get(row);
		}catch(Exception err){
			return null;
		}
	}
	
	/**
	 * 设置指定列的填充器
	 * @param column
	 * @param filler
	 */
	public void addColumnFiller(ColumnFiller filler) {
		column_filler.add(filler);
	}
	
	final public JTable getTable() {
		return table;
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
	private Vector<String> toTableHeadData() {
		Vector<String> header = new Vector<String>(2);
		header.add(primary_key);
		for (Field field : column_fields) {
			header.add(Util.getEditFieldName(field));
		}
		return header;
	}
	
	private Vector<Vector<Object>> toTableRowData() {
		Vector<Vector<Object>> ret = new Vector<Vector<Object>>();
		int row_count = 0;
		for (T row_data : datas) {
			Vector<Object> row = new Vector<Object>(column_fields.length + 1);
			row.add(row_count);
			for (Field field : column_fields) {
				try {
					Object cdata = field.get(row_data);
					row.add(cdata);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ret.add(row);
			row_count++;
		}
		ret.trimToSize();
		return ret;
	}

//	------------------------------------------------------------------------------------------------------------------
	
	class FieldTable extends JTable implements ListSelectionListener, MouseListener
	{
		public FieldTable()
		{
			super(column_datas, column_headers);
			super.setRowHeight(DEFAULT_ROW_HEIGHT);
			super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			super.setColumnSelectionAllowed(true);
			super.setRowSelectionAllowed(true);
			super.setCellSelectionEnabled(true);
			
			int c = 0;
			for (String header : column_headers) {
				if (!header.equals(primary_key)) {
					TableColumn column = super.getColumn(header);
					column.setCellRenderer(new TableRender(c, header, column));
					column.setCellEditor(new ValueEditor(c, header, column));
					column_map.put(column, column_fields[c]);
					c++;
				}
			}
			
			super.getColumn(primary_key).setCellEditor(new NullEditor());
			
			super.getSelectionModel().addListSelectionListener(this);
			
			super.getTableHeader().addMouseListener(this);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				showColumnHeaderMenu(
						super.getTableHeader().columnAtPoint(e.getPoint()),
						e.getX(),
						e.getY());
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

	}

//	--------------------------------------------------------------------------------------------------------------------------------------
	void showColumnHeaderMenu(int column, int x, int y)
	{
		String cn = table.getColumnName(column);
		if (cn != null) {
			TableColumn tc = table.getColumn(cn);
			if (tc != null) {
				Field field	= column_map.get(tc);
				for (ColumnFiller f : column_filler) {
					String command = f.getCommand(field);
					if (command != null) {
						ColumnHeaderMenu menu = new ColumnHeaderMenu(command, field, f, x, y);
						menu.show(table.getTableHeader(), x, y);
						return;
					}
				}
			}
		}
	}
	
	class ColumnHeaderMenu extends JPopupMenu implements ActionListener
	{	
		final Field				field;
		final ColumnFiller		filler;
		final JMenuItem 		cmd;
		final ArrayList<T>		filling_rows;
		final int start;
		final int count;
		
		public ColumnHeaderMenu(String command, Field field, ColumnFiller filler, int x, int y) 
		{
			this.field		= field;
			this.filler		= filler;
		
			this.start = Math.max(table.getSelectedRow(), 0);
			this.count = table.getRowCount() - start;
			this.filling_rows = new ArrayList<T>(count);
			for (int i = 0; i < count; i++) {
				this.filling_rows.add(getRow(i+start));
			}
			
			this.cmd 		= new JMenuItem(command);
			this.cmd.addActionListener(this);
			this.add(cmd);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new ColumnFillerDialog(filler, field, start, filling_rows).setVisible(true);
		}
	}

	class TableRender extends DefaultTableCellRenderer
	{
		final int			column;
		final Field			field;
		final String		header;
		final TableColumn 	table_column;
		
		final Icon 			old_icon;
		
		public TableRender(int column, String header, TableColumn tc) {
			this.header 		= header;
			this.column 		= column;
			this.table_column	= tc;
			this.field			= column_fields[column];
			
			this.old_icon		= super.getIcon();
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
				Object object = getSelectedRow();
				if (object != null) {
					Component comp = getPropertyCellRender(this, object, field, field.get(object));
					return comp;
				}
			}catch(Exception err){
				err.printStackTrace();
			}
			return src;
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 单元格值编辑器
	 * @author WAZA
	 *
	 */
	class ValueEditor extends DefaultCellEditor implements TableCellEditor
	{
		final int			column;
		final Field			field;
		final String		header;
		final TableColumn 	table_column;
		
		Object 				src_field_value;
		PropertyCellEdit<?> current_field_edit;
		
		public ValueEditor(int column, String header, TableColumn tc) {
			super(new JTextField());
			this.header 		= header;
			this.column 		= column;
			this.table_column	= tc;
			this.field			= column_fields[column];
		}
		
		public Object getCellEditorValue() 
		{
			Object object = getSelectedRow();
			if (object != null) {
				try {
					Object field_value = getPropertyCellEditValue(object, field, current_field_edit, src_field_value);
					if (field_value != null) {
						field.set(object, field_value);
						onFieldChanged(object, field);
						table.repaint();
						return field_value;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return src_field_value;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
		{
			Object object = getSelectedRow();
			if (object != null) {
				try {
					src_field_value 	= value;
					current_field_edit	= getPropertyCellEdit(object, field, value);
					Component ret		= current_field_edit.getComponent(ObjectPropertyRowPanel.this);
					return ret;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

	}
//	------------------------------------------------------------------------------------------------------------------------------------------

	public class ColumnFillerDialog extends AbstractOptionDialog<ArrayList<Object>>
	{
		ColumnFiller	filler;
		
		ArrayList<Object> data = new ArrayList<Object>();
		
		public ColumnFillerDialog(
				ColumnFiller	filler, 
				Field			column_field,
				int 			start_row, 
				ArrayList<T>	row_datas) 
		{
			super(ObjectPropertyRowPanel.this);
			this.filler = filler;
			super.add(filler.startFill(
					ObjectPropertyRowPanel.this,
					column_field, 
					start_row,
					row_datas,
					data), 
					BorderLayout.CENTER);
		}
		
		@Override
		protected boolean checkOK() {
			return true;
		}
		
		@Override
		protected ArrayList<Object> getUserObject() {
			return data;
		}
	}
	
	/**
	 * 给一个列填充数据用
	 * @author WAZA
	 *
	 */
	public static interface ColumnFiller
	{
		/**
		 * 列标题弹出菜单的标题
		 * @return
		 */
		public String		getCommand(Field column_type);
		
		/**
		 * 开始填充一段数据
		 * @param panel				编辑器
		 * @param column_type		该列的类型
		 * @param start_row			开始编辑的行号
		 * @param row_datas			被编辑的行
		 * @param row_column_datas	被编辑的行对应列的数据(由用户向里面填充数据)
		 * @return
		 */
		public Component	startFill(
				ObjectPropertyRowPanel<?> panel, 
				Field 				column_type,
				int 				start_row, 
				ArrayList<?>		row_datas, 
				ArrayList<Object>	row_column_datas
				);
	}
	
}

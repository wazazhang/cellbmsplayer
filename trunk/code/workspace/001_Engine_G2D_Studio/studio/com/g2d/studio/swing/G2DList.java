package com.g2d.studio.swing;

import java.awt.Component;
import java.util.Collection;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class G2DList<T extends G2DListItem> extends JList
{
	private static final long serialVersionUID = 1L;
	
	public G2DList()
	{
		this.setCellRenderer(new ListRender());
	}
	
	public G2DList(T[] items)
	{
		super(items);
		this.setCellRenderer(new ListRender());
	}
	
	public G2DList(Collection<T> items)
	{
		super(new Vector<T>(items));
		this.setCellRenderer(new ListRender());
	}
	
	
	@SuppressWarnings("unchecked")
	public T getSelectedItem()
	{
		try{
			return (T)super.getSelectedValue();
		}catch(Exception err){}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Object[] getSelectedItems()
	{
		try{
			return super.getSelectedValues();
		}catch(Exception err){}
		return null;
	}	
	
	class ListRender extends DefaultListCellRenderer implements ListCellRenderer
	{
		private static final long serialVersionUID = 1L;

		public ListRender() {
	         setOpaque(true);
	     }
		
		public Component getListCellRendererComponent(
				JList list, 
				Object value,
				int index, 
				boolean isSelected,
				boolean cellHasFocus) 
		{
			DefaultListCellRenderer render = (DefaultListCellRenderer)super.getListCellRendererComponent(
					list, value, index, isSelected, cellHasFocus);
			
			if (value instanceof G2DListItem) {
				G2DListItem item = (G2DListItem) value;
				Component comp = item.getListComponent(list, value, index, isSelected, cellHasFocus);
				if (comp != null) {
					return comp;
				} else {
					this.setIcon(item.getListIcon(false));
					this.setText(item.getListName());
				}
			}
			
			return render;

		}
		 

	}
}

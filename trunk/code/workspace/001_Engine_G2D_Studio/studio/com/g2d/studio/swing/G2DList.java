package com.g2d.studio.swing;

import java.awt.Color;
import java.awt.Component;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToggleButton;
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
	
	
	class ListRender extends JToggleButton implements ListCellRenderer
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
			if (value instanceof G2DListItem) {
				G2DListItem item = (G2DListItem) value;
				this.setIcon(item.getIcon());
				this.setText(item.getName());
			} else {
				setText(value.toString());
			}
			
			Color background;
	         Color foreground;

	         // check if this cell represents the current DnD drop location
	         JList.DropLocation dropLocation = list.getDropLocation();
	         if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) 
	         {
	             background = Color.BLUE;
	             foreground = Color.WHITE;
	         // check if this cell is selected
	         } 
	         else if (isSelected) {
	             background = Color.RED;
	             foreground = Color.WHITE;
	         // unselected, and not the DnD drop location
	         }
	         else {
	             background = Color.WHITE;
	             foreground = Color.BLACK;
	         }
	         setBackground(background);
	         setForeground(foreground);			
	         
	         return this;
		}
		 

	}
}

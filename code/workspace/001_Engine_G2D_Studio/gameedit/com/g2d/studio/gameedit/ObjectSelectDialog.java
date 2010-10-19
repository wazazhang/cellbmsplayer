package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DList;
import com.g2d.util.AbstractOptionDialog;

public class ObjectSelectDialog<T extends ObjectNode<?>> extends AbstractOptionDialog<T> implements PropertyCellEdit<T>
{
	private static final long serialVersionUID = 1L;
	
	Vector<T>	list_data;
	G2DList<T>	list;
	
	JLabel cell_edit_component = new JLabel();
	
	public ObjectSelectDialog(Component owner, Class<T> type, int wcount) 
	{
		super(owner);
		
		this.list_data	= Studio.getInstance().getObjectManager().getObjects(type);
		this.list		= new G2DList<T>();
		this.list.setListData(list_data);
		super.add(new JScrollPane(list), BorderLayout.CENTER);
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	protected T getUserObject() {
		return list.getSelectedItem();
	}
	
	@Override
	protected Object[] getUserObjects()
	{
		return list.getSelectedItems();
	}
	
	@Override
	public T getValue() {
		return getUserObject();
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		cell_edit_component.setText(getUserObject()+"");
		return cell_edit_component;
	}
}

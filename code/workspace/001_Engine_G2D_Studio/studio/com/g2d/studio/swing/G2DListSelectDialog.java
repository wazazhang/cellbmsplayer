package com.g2d.studio.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.util.AbstractDialog;

public abstract class G2DListSelectDialog<T extends G2DListItem>  extends AbstractDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	G2DList<T> list;
	
	JButton ok = new JButton("确定");
	
	T object;
	
	public G2DListSelectDialog(Component owner, G2DList<T> list)
	{
		super(owner);
		super.setLayout(new BorderLayout());
		super.setSize(600, 400);
		super.setCenter();
		
		this.list = list;
		this.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);		
		this.list.setVisibleRowCount(list.getModel().getSize()/5+1);
		this.add(new JScrollPane(list), BorderLayout.CENTER);

		this.add(ok, BorderLayout.SOUTH);
		this.ok.addActionListener(this);
	}
	
	public T getSelectedObject() {
		return list.getSelectedItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (checkOK()) {
			object = list.getSelectedItem();
			this.setVisible(false);
		}
	}
	
	public T showDialog() {
		super.setVisible(true);
		return object;
	}
	
	protected boolean checkOK() {
		return true;
	}
}

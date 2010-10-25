package com.g2d.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;



public abstract class AbstractOptionDialog<T> extends AbstractDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	protected T 		selected_object = null;
	
	protected JPanel	south 	= new JPanel(new FlowLayout());
	protected JButton 	ok 		= new JButton("确定");
	protected JButton 	cancel 	= new JButton("取消");
	
	public AbstractOptionDialog(){
		init();
	}
	
	public AbstractOptionDialog(Component owner) {
		super(owner);
		init();
	}
	
	private void init() {
		ok.addActionListener(this);
		cancel.addActionListener(this);
		south.add(ok);
		south.add(cancel);
		this.add(south, BorderLayout.SOUTH);
	}
	
	public T showDialog(){
		super.setVisible(true);
		return selected_object;
	}
	


//	
//	想要返回多个结果，只需要将T声明为数组或集合。
	static class Dialog2 extends AbstractOptionDialog<Object[]>
	{
		private static final long serialVersionUID = 1L;
		
		@Override
		protected boolean checkOK() {
			return false;
		}
		
		@Override
		protected Object[] getUserObject() {
			return null;
		}
	}
//	想要返回多个结果，只需要将T声明为数组或集合。
//	public Object[] showDialog2()
//	{
//		super.setVisible(true);
//		return getUserObjects();		
//	}
//	abstract protected Object[] getUserObjects();
	
	abstract protected T getUserObject();
	
	abstract protected boolean checkOK() ;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			if (checkOK()) {			
				selected_object = getUserObject();
				this.setVisible(false);
			}
		}
		else if (e.getSource() == cancel) {
			selected_object = null;
			this.setVisible(false);
		}
	}
	
}

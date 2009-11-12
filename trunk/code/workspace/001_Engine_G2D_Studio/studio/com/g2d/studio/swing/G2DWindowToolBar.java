package com.g2d.studio.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.g2d.Tools;
import com.g2d.studio.res.Res;


public class G2DWindowToolBar extends JToolBar implements Externalizable
{
	private static final long serialVersionUID = 1L;
	
	final public JButton save = new JButton(Tools.createIcon(Res.save_tool_bar[2]));
	
	public G2DWindowToolBar(ActionListener listener) {
		save.setToolTipText("保存");
		save.addActionListener(listener);
		super.add(save);
		
		super.addSeparator();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException {}
	
}

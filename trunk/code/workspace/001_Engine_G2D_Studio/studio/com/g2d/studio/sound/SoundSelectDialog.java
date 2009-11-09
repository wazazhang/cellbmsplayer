package com.g2d.studio.sound;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceList;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.swing.G2DListSelectDialog;
import com.g2d.util.AbstractDialog;

public class SoundSelectDialog extends G2DListSelectDialog<SoundFile>
{
	private static final long serialVersionUID = 1L;
	
	public SoundSelectDialog()
	{
		super(new SoundList());
		super.setTitle("选择一个声音");
	}
	
}

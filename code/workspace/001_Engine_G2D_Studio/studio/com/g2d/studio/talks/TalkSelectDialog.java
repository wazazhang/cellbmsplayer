package com.g2d.studio.talks;

import java.awt.Component;
import java.awt.Window;

import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DListSelectDialog;

public class TalkSelectDialog extends G2DListSelectDialog<TalkFile>
{
	private static final long serialVersionUID = 1L;
	
	public TalkSelectDialog()
	{
		super(Studio.getInstance().getTalkManager(), new TalkList());
		super.setTitle("选择一个对话");
	}
	
	public TalkSelectDialog(Component owner) {
		super(owner, new TalkList());
		super.setTitle("选择一个对话");
	}
}

package com.g2d.studio.scene.script;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.g2d.studio.swing.G2DTree;


@SuppressWarnings("serial")
public class TriggersEditor extends JPanel
{
	
	TriggerTreeView tree_view;
	
	JPanel			edit_panel;
	
	public TriggersEditor(TriggerGenerateNode root) 
	{
		super(new BorderLayout());
		
		tree_view	= new TriggerTreeView(root);
		
		edit_panel	= new JPanel(new BorderLayout());
		
		super.add(new JScrollPane(tree_view), BorderLayout.WEST);
		super.add(edit_panel, BorderLayout.CENTER);
		
	}
	
	class TriggerTreeView extends G2DTree
	{
		public TriggerTreeView(TriggerGenerateNode root) {
			super(root);
		}
		
		public void onSelectChanged(TriggerGenerateNode node) {
			
		}
	}
	
	
}

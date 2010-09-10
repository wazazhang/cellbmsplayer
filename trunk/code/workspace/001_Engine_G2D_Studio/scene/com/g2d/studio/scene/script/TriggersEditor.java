package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.tree.TreeNode;

import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;


@SuppressWarnings("serial")
public class TriggersEditor extends JPanel
{
	JSplitPane		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	TriggerTreeView tree_view;
	
	JPanel			edit_panel;
	
	public TriggersEditor(TriggerGenerateTreeNode root) 
	{
		super(new BorderLayout());
		
		tree_view	= new TriggerTreeView(root);
		
		edit_panel	= new JPanel(new BorderLayout());
		
		split.setLeftComponent(new JScrollPane(tree_view));
		split.setRightComponent(edit_panel);
		
		super.add(split, BorderLayout.CENTER);
		
	}
	
	class TriggerTreeView extends G2DTree
	{
		public TriggerTreeView(TriggerGenerateTreeNode root) {
			super(root);
			super.setPreferredSize(new Dimension(200, 200));
		}
		
		protected void onSelectChanged(TreeNode node) {
			if (node instanceof TriggerGenerateTreeNode.TriggerNode) {
				TriggerGenerateTreeNode.TriggerNode tn = (TriggerGenerateTreeNode.TriggerNode)node;
				JPanel page = tn.getEditPage();
				if (page != null) {
					split.setRightComponent(page);
				} else {
					split.setRightComponent(edit_panel);
				}
			} else {
				split.setRightComponent(edit_panel);
			}
		}
		
	}
	
	
}

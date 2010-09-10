package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Component;
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
		
		JScrollPane left = new JScrollPane(tree_view);
		left.setPreferredSize(new Dimension(250, 200));
		split.setLeftComponent(left);
		split.setRightComponent(edit_panel);
		
		super.add(split, BorderLayout.CENTER);
		
		tree_view.reload();
	}
	
	class TriggerTreeView extends G2DTree
	{
		public TriggerTreeView(TriggerGenerateTreeNode root) {
			super(root);
		}
		
		protected void onSelectChanged(TreeNode node) {
			Component right = edit_panel;
			if (node instanceof TriggerGenerateTreeNode.TriggerNode) {
				TriggerGenerateTreeNode.TriggerNode tn = (TriggerGenerateTreeNode.TriggerNode)node;
				JPanel page = tn.getEditPage();
				if (page != null) {
					right = page;
				}
			}
			if (right != split.getRightComponent()) {
				split.setRightComponent(right);
			}
		}
		
	}
	
	
}

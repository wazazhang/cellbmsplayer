package com.g2d.studio.quest;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.cell.rpg.quest.QuestItem;
import com.g2d.Tools;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.ObjectAdapters.QuestItemAdapter;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.quest.QuestNode.QuestMenu;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;
import com.g2d.util.AbstractDialog;

public class QuestItemNode extends DynamicNode<QuestItem>
{
	public QuestItemNode(IDynamicIDFactory<QuestItemNode> factory, String name) {
		super(factory, name);
	}
	
	public QuestItemNode(QuestItem item) {
		super(item, item.getType(), item.name);
	}
	
	@Override
	protected QuestItem newData(IDynamicIDFactory<?> factory, String name) {
		return new QuestItem(this.getIntID(), name);
	}

	@Override
	public boolean setName(String name) {
		if(super.setName(name)){
			getData().name = name;
			return true;
		}
		return false;
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new QuestItemMenu(tree, this).show(tree, e.getX(), e.getY());
	}
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	

	class QuestItemMenu extends NodeMenu<QuestItemNode>
	{
		public QuestItemMenu(JTree tree, QuestItemNode node) {
			super(node);
			super.remove(open);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rename) {
				String name = JOptionPane.showInputDialog(
						AbstractDialog.getTopWindow(getInvoker()), 
						"输入名字！",
						node.getData().name);
				if (name!=null && name.length()>0) {
					node.setName(name);
				}
				if (tree!=null) {
					tree.reload(node);
				}
			}
			else if (e.getSource() == delete) {
				TreeNode parent = node.getParent();
				this.node.removeFromParent();
				if (tree!=null) {
					tree.reload(parent);
				}
			}
		}
	}

//	---------------------------------------------------------------------------------------------------------------

}

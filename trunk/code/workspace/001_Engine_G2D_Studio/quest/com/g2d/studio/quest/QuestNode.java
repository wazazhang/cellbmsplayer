package com.g2d.studio.quest;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import com.cell.rpg.quest.Quest;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;
import com.g2d.util.AbstractDialog;

public class QuestNode extends DynamicNode<Quest>
{
	transient IconFile 	icon_file;
	
	public QuestNode(IDynamicIDFactory<QuestNode> factory, String name) {
		super(factory, name);
	}

	@Override
	protected Quest newData(IDynamicIDFactory<?> factory, String name) {
		return new Quest(getIntID(), name);
	}
	
	public QuestNode(Quest data) {
		super(data, data.getIntID(), data.name);
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_file==null || !icon_file.icon_file_name.equals(getData())) {
			resetIcon();
		} 
		return super.getIcon(update);
	}
	
	@Override
	protected ImageIcon createIcon() {
		icon_file = Studio.getInstance().getIconManager().getIcon(getData().icon_index);
		if (icon_file!=null) {
			return new ImageIcon(Tools.combianImage(20, 20, icon_file.image));
		} else {
			return new ImageIcon(Tools.combianImage(20, 20, Res.icon_quest));
		}
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new QuestMenu(tree, this).show(tree, e.getX(), e.getY());
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
	public ObjectViewer<?> getEditComponent() {
		if (edit_component == null) {
			edit_component = new QuestEditor(this);
		}
		return edit_component;
	}
	
//	---------------------------------------------------------------------------------------------------------------

	class QuestMenu extends NodeMenu<QuestNode>
	{
		public QuestMenu(JTree tree, QuestNode node) {
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
				this.node.removeFromParent();
				if (tree!=null) {
					tree.reload(node);
				}
			}
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------

	
}

package com.g2d.studio.scene.script;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cell.CUtil;
import com.cell.rpg.scene.Actor;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.display.ui.TreeView;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.units.SceneActor;
import com.g2d.studio.swing.G2DDefaultTreeNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;

@SuppressWarnings("serial")
public class TriggerGenerateTreeNode extends G2DTreeNode<G2DTreeNode<?>>
{
	TriggerGenerator 				root_object;
	Class<? extends Scriptable>		trigger_object_type;

//	-------------------------------------------------------------------------------------
	public TriggerGenerateTreeNode(SceneEditor se)
	{
		this(se.getSceneNode().getData(), com.cell.rpg.scene.script.entity.Scene.class);	
		
		ArrayList<SceneUnit> sub_nodes = se.getRuntimeUnits();
		if (!sub_nodes.isEmpty()) {
			Collections.sort(sub_nodes, new Comparator<SceneUnit>() {
				@Override
				public int compare(SceneUnit o1, SceneUnit o2) {
					return CUtil.getStringCompare().compare(o1.name, o2.name);
				}
			});
			G2DDefaultTreeNode group = new G2DDefaultTreeNode(new ImageIcon(Res.icons_bar[4]), "场景物体");
			for (SceneUnit su : sub_nodes) {
				TriggerGenerateTreeNode tn = new TriggerGenerateTreeNode(su, su.getTriggerObjectType());
				group.add(tn);
			}
			this.add(group);
		}
	}
	
	public TriggerGenerateTreeNode(SceneUnit su) {
		this(su, su.getTriggerObjectType());
	}
	
	protected TriggerGenerateTreeNode(
			TriggerGenerator su, 
			Class<? extends Scriptable> tot) {
		this.root_object 			= su;
		this.trigger_object_type 	= tot;
		for (SceneTrigger st : su.getTriggers()) {
			TriggerNode en = new TriggerNode(st);
			this.add(en);
		}
	}
//	-------------------------------------------------------------------------------------
	@Override
	protected ImageIcon createIcon() {
		if (root_object instanceof Scene) {
			return new ImageIcon(Res.icon_scene);
		}
		if (root_object instanceof SceneUnit) {
			return new ImageIcon(Res.icon_hd);
		}
		return null;
	}
	
	@Override
	public String getName() {
		return root_object.getTriggerObjectName();
	}

	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			new GenerateMenu((G2DTree)tree).show(tree, e.getX(), e.getY());
		}
	}

	private void addTrigger(TriggerNode tn) {
		this.insert(tn, 0);
		this.root_object.addTrigger(tn.trigger);
	}
	
	private void removeTrigger(TriggerNode tn) {
		this.remove(tn);
		this.root_object.removeTrigger(tn.trigger);
	}
	
	class GenerateMenu extends JPopupMenu implements ActionListener
	{
		G2DTree tree;
		
		JMenuItem item_add_scriptable_trigger 	= new JMenuItem("添加触发器");
		
		public GenerateMenu(G2DTree tree) {
			this.tree = tree;
			item_add_scriptable_trigger.addActionListener(this);
			add(item_add_scriptable_trigger);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == item_add_scriptable_trigger) {
				Object res = JOptionPane.showInputDialog(tree, "输入名字", "未命名触发器");
				if (res != null) {
					SceneTriggerScriptable sts = new SceneTriggerScriptable();
					sts.name = res.toString();
					TriggerNode trigger = new TriggerNode(sts);
					addTrigger(trigger);
					tree.reload(TriggerGenerateTreeNode.this);
				}
			}
		}
		
	}
	

//	------------------------------------------------------------------------------------------------------
	
	/**
	 * 编辑一个触发器
	 * @author WAZA
	 */
	public class TriggerNode extends G2DTreeNode<G2DTreeNode<?>>
	{
		SceneTrigger 	trigger;
		
		TriggerPanel<?> edit_page;
		
		public TriggerNode(SceneTrigger trigger) {
			this.trigger = trigger;
			if (trigger instanceof SceneTriggerScriptable) {
				edit_page = new TriggerPanelScriptable(
						(SceneTriggerScriptable)trigger,
						trigger_object_type
						);
			} else {
				
			}
		}
		
		@Override
		protected ImageIcon createIcon() {
			return new ImageIcon(Res.icon_action);
		}
		
		@Override
		public String getName() {
			if (trigger != null) {
				return trigger.name+"";
			}
			return "null";
		}

		@Override
		public void onRightClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new TriggerNodeMenu((G2DTree)tree).show(tree, e.getX(), e.getY());
			}
		}

		TriggerPanel<?> getEditPage() {
			return edit_page;
		} 
		
		class TriggerNodeMenu extends JPopupMenu implements ActionListener
		{
			G2DTree tree;
			
			JMenuItem rename 	= new JMenuItem("重命名");
			JMenuItem delete 	= new JMenuItem("删除");
			
			public TriggerNodeMenu(G2DTree tree) {
				this.tree = tree;
				rename.addActionListener(this);
				delete.addActionListener(this);
				add(rename);
				add(delete);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == delete) {
					if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(tree, "confirm ?")) {
						removeTrigger(TriggerNode.this);
						tree.reload(TriggerGenerateTreeNode.this);
					}
				}
				else if (e.getSource() == rename) {
					Object res = JOptionPane.showInputDialog(tree, "输入名字", TriggerNode.this.getName());
					if (res != null) {
						TriggerNode.this.trigger.name = res.toString();
						tree.reload(TriggerNode.this);
						tree.repaint();
					}
				}
			}
		}
		
	}
	

//	------------------------------------------------------------------------------------------------------
	
//	/**
//	 * 编辑一个触发器事件
//	 * @author WAZA
//	 */
//	public class TriggerEventNode extends G2DTreeNode<G2DTreeNode<?>>
//	{
//		Class<? extends Event>	event_type;
//		EventType				event_type_anno;
//		
//		public TriggerEventNode(Class<? extends Event> evt) {
//			this.event_type			= evt;
//			this.event_type_anno	= evt.getAnnotation(EventType.class);
//		}
//		
//		@Override
//		protected ImageIcon createIcon() {
//			return new ImageIcon(Res.icon_run);
//		}
//		
//		@Override
//		public String getName() {
//			return event_type_anno.comment();
//		}
//
//		
//	}

}

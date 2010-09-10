package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.script.TriggerGenerateTreeNode.TriggerNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.util.TextEditor;

@SuppressWarnings("serial")
public abstract class TriggerPanel<T extends SceneTrigger> extends JPanel
{
	final protected T 			trigger;
	final protected Class<? extends Scriptable> 
								trigger_object_type;
	
	final protected TextEditor	comment		= new TextEditor();
	
	final protected TriggerTreeView 			tree_view;
	final protected DefaultMutableTreeNode		tree_root;
	
	final protected TriggerEventRoot 			group_events;
	final protected TriggerConditionsRoot 		group_conditions;
	final protected TriggerActionRoot			group_actions;

	JSplitPane		split		= new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	JSplitPane		split_h		= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JPanel			right_h		= new JPanel();
	
	public TriggerPanel(T trigger, Class<? extends Scriptable> trigger_object_type)
	{
		super(new BorderLayout());
		
		this.trigger 				= trigger;
		this.trigger_object_type	= trigger_object_type;
		
		this.tree_root			= new DefaultMutableTreeNode(trigger.name);
		this.group_events		= new TriggerEventRoot();
		this.group_conditions	= new TriggerConditionsRoot();
		this.group_actions		= new TriggerActionRoot();
		this.tree_root			.add(group_events);
		this.tree_root			.add(group_conditions);
		this.tree_root			.add(group_actions);
		
		this.tree_view			= new TriggerTreeView();
		
		split.setTopComponent(split_h);
		split.setBottomComponent(new JScrollPane(comment));
		
		split_h.setLeftComponent(new JScrollPane(tree_view));
		split_h.setRightComponent(right_h);
		
		this.add(split);
		
	}
	
	protected class TriggerTreeView extends G2DTree
	{
		public TriggerTreeView() {
			super(tree_root);
			super.setPreferredSize(new Dimension(200, 200));
			super.setRootVisible(false);
		}
		@Override
		protected void onSelectChanged(TreeNode node) {
			onTreeSelectChanged(this, node);
		}
	}
	
	abstract protected void onTreeSelectChanged(TriggerTreeView tree_view, TreeNode node);
	
	
//	--------------------------------------------------------------------------------------------------------
	
	protected class TriggerEventRoot extends G2DTreeNode<G2DTreeNode<?>>
	{
		public TriggerEventRoot() {}
		
		@Override
		protected ImageIcon createIcon() {
			return new ImageIcon(Res.icon_trigger);
		}
		
		@Override
		public String getName() {
			return "事件";
		}

		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new RootMenu().show(tree_view, e.getX(), e.getY());
			}
		}

		protected void addEvent(Class<? extends Event> evt) {
			if (trigger.addTriggerEvent(evt)) {
				EventNode en = new EventNode(evt);
				this.add(en);
			}
		}
		
		class RootMenu extends JPopupMenu implements ActionListener
		{
			JMenuItem add_event = new JMenuItem("添加事件");
			
			public RootMenu() {
				add_event.addActionListener(this);
				super.add(add_event);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == add_event) {
					SelectEventDialog dlg = new SelectEventDialog(tree_view, trigger_object_type);
					Class<? extends Event> evt = dlg.showDialog();
					if (evt != null) {
						addEvent(evt);
						tree_view.reload(TriggerEventRoot.this);
					}
				}
			}
		}
		
		protected class EventNode extends G2DTreeNode<G2DTreeNode<?>>
		{
			Class<? extends Event> 	evt;
			EventType 				type ;
			
			public EventNode(Class<? extends Event> evt) {
				this.evt 	= evt;
				this.type 	= evt.getAnnotation(EventType.class);
			}
			
			@Override
			protected ImageIcon createIcon() {
				return new ImageIcon(Res.icon_condition);
			}
			
			@Override
			public String getName() {
				return type.comment();
			}
		}
		
	}
	

//	--------------------------------------------------------------------------------------------------------
	
	protected class TriggerConditionsRoot extends G2DTreeNode<G2DTreeNode<?>>
	{

		@Override
		protected ImageIcon createIcon() {
			return new ImageIcon(Res.icon_condition);
		}
		
		@Override
		public String getName() {
			return "条件";
		}

		@Override
		public void onClicked(JTree tree, MouseEvent e) {
		
		}	
		
		
	}
//	--------------------------------------------------------------------------------------------------------
	
	protected class TriggerActionRoot extends G2DTreeNode<G2DTreeNode<?>>
	{
		@Override
		protected ImageIcon createIcon() {
			return new ImageIcon(Res.icon_run);
		}
		
		@Override
		public String getName() {
			return "动作";
		}

		@Override
		public void onClicked(JTree tree, MouseEvent e) {
		
		}	
	}
}

package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.script.TriggerGenerateTreeNode.TriggerNode;
import com.g2d.studio.scene.script.TriggerPanel.TriggerEventRoot.EventNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.util.TextEditor;

@SuppressWarnings("serial")
public abstract class TriggerPanel<T extends SceneTrigger> extends JPanel implements AncestorListener
{
	final protected TriggerGenerator			root_object;
	final protected T 							trigger;
	final protected Class<? extends Scriptable> trigger_object_type;
	
	final protected TextEditor					comment		= new TextEditor();
	
	final protected TriggerTreeView 			tree_view;
	final protected DefaultMutableTreeNode		tree_root;
	
	final protected TriggerEventRoot 			group_events;
	final protected TriggerConditionsRoot 		group_conditions;
	final protected TriggerActionRoot			group_actions;

	private JSplitPane		split		= new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JSplitPane		split_h		= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JPanel			south_h		= new JPanel(new BorderLayout());
	
	public TriggerPanel(T trigger, Class<? extends Scriptable> trigger_object_type, TriggerGenerator root_object)
	{
		super(new BorderLayout());
		this.root_object			= root_object;
		this.trigger 				= trigger;
		this.trigger_object_type	= trigger_object_type;
		
		this.tree_root			= new DefaultMutableTreeNode(trigger.getName());
		this.group_events		= new TriggerEventRoot();
		this.group_conditions	= new TriggerConditionsRoot();
		this.group_actions		= new TriggerActionRoot();
		this.tree_root			.add(group_events);
		this.tree_root			.add(group_conditions);
		this.tree_root			.add(group_actions);
		
		this.tree_view			= new TriggerTreeView();
		
		this.comment			.setText(trigger.comment);
		
		split.setTopComponent(split_h);
		split.setBottomComponent(south_h);
		
		JScrollPane left = new JScrollPane(tree_view);
		left.setMinimumSize(new Dimension(200, 120));
		split_h.setLeftComponent(left);
		split_h.setRightComponent(new JScrollPane(comment));
		split_h.setMinimumSize(new Dimension(200, 160));
		
		tree_view.expandAll();
		
		this.add(split);
		super.addAncestorListener(this);
		
	}
	
	protected JPanel getMainPanel() {
		return south_h;
	}
	
	@Override
	public void ancestorAdded(AncestorEvent event) {}
	@Override
	public void ancestorMoved(AncestorEvent event) {}
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		trigger.comment = comment.getText();
//		System.out.println(comment.getText());
	}
	
	protected class TriggerTreeView extends G2DTree
	{
		public TriggerTreeView() {
			super(tree_root);
			super.setRootVisible(false);
		}
		@Override
		protected void onSelectChanged(TreeNode node) {
			onTreeSelectChanged(this, node);
		}
	}
	
	abstract protected void onTreeSelectChanged(TriggerTreeView tree_view, TreeNode node);
	
	abstract protected void onAddEventNode(TriggerEventRoot.EventNode en);
	
	abstract protected void onRemoveEventNode(TriggerEventRoot.EventNode en);
	
//	--------------------------------------------------------------------------------------------------------
	
	protected class TriggerEventRoot extends G2DTreeNode<G2DTreeNode<?>>
	{
		public TriggerEventRoot() {
			for (Class<? extends Event> evt : trigger.getEventTypes()) {
				EventNode en = new EventNode(evt);
				this.add(en);
			}
		}
		
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
		}
		
		@Override
		public void onRightClicked(JTree tree, MouseEvent e) {
			new RootMenu().show(tree_view, e.getX(), e.getY());
		}
		
		protected void addEvent(Class<? extends Event> evt) {
			if (trigger.addTriggerEvent(evt)) {
				EventNode en = new EventNode(evt);
				this.add(en);
				onAddEventNode(en);
			} else {
				JOptionPane.showMessageDialog(tree_view, "该事件已经存在！");
			}
		}

		protected void removeEvent(EventNode evt) {
			if (trigger.removeTriggerEvent(evt.evt)) {
				this.remove(evt);
				onRemoveEventNode(evt);
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
			final protected Class<? extends Event> 	evt;
			final protected EventType 				type ;
			
			public EventNode(Class<? extends Event> evt) {
				this.evt 	= evt;
				this.type 	= evt.getAnnotation(EventType.class);
			}
			
			@Override
			protected ImageIcon createIcon() {
				return new ImageIcon(Res.icon_event);
			}
			
			@Override
			public String getName() {
				return type.comment();
			}
			

			@Override
			public void onRightClicked(JTree tree, MouseEvent e) {
				new EventMenu().show(tree_view, e.getX(), e.getY());
			}
			
			class EventMenu extends JPopupMenu implements ActionListener
			{
				JMenuItem remove = new JMenuItem("删除");
				
				public EventMenu() {
					remove.addActionListener(this);
					super.add(remove);
				}
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == remove) {
						if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(tree_view, "确定?")) {
							removeEvent(EventNode.this);
							tree_view.reload(TriggerEventRoot.this);
						}
					}
				}
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
		public void onRightClicked(JTree tree, MouseEvent e) {
			
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
		public void onRightClicked(JTree tree, MouseEvent e) {
			
		}
	}
}

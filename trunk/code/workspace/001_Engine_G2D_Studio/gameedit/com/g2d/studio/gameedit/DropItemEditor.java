package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;

import com.cell.CUtil;
import com.cell.rpg.template.ability.UnitDropItem.DropItemNode;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractFrame;
import com.g2d.util.AbstractOptionDialog;

public class DropItemEditor extends AbstractDialog implements PropertyCellEdit<DropItemNode[]> , ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JLabel 						edit_label 	= new JLabel();
	
	//
	G2DList<Node>				value_list	= new G2DList<Node>();
	Vector<Node>				node_list	= new Vector<Node>();
	JLabel						total		= new JLabel();
	
	JButton						value_add	= new JButton("添加");
	JButton						value_edt	= new JButton("编辑");
	JButton						value_del	= new JButton("移除");
	JButton 					ok			= new JButton("确定");
	
	
	public DropItemEditor(Component owner, DropItemNode[] src) 
	{
		super(owner);
		super.setIconImage(Res.icon_edit);
		super.setTitle("掉落道具编辑器");
		super.setSize(260, 400);

		this.add(total, BorderLayout.NORTH);
		this.add(new JScrollPane(value_list), BorderLayout.CENTER);
		{
			JPanel bp = new JPanel(new FlowLayout());
			bp.add(ok);
			bp.add(value_add);
			bp.add(value_edt);
			bp.add(value_del);
			this.add(bp, BorderLayout.SOUTH);
			
			value_add.addActionListener(this);
			value_edt.addActionListener(this);
			value_del.addActionListener(this);
			ok.addActionListener(this);
		}

		if (src!=null) {
			for (DropItemNode node : src) {
				XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, node.titem_id+"");
				node_list.add(new Node(item, node));
			}
			resetList(null);
		}
	}
	
	protected void resetList(Node edit_node)
	{
		double total_percent = 0;
		if (!node_list.isEmpty()) {
			for (Node e : node_list) {
				total_percent += e.value.drop_rate_percent;
			}
			if (total_percent > 100) {
				double div = (double)((100 - total_percent) / (node_list.size() - (edit_node==null?0:1)));
				for (Node e : node_list) {
					if (!e.equals(edit_node)) {
						e.value.drop_rate_percent += div;
						e.value.drop_rate_percent = Math.round(e.value.drop_rate_percent * 10000) / 10000;
					}
				}
			}
			total_percent = 0;
			for (Node e : node_list) {
				total_percent += e.value.drop_rate_percent;
			}
		}
		total.setText("全部几率 : " + total_percent + "%");
		
		value_list.setListData(node_list);
		value_list.repaint(100);
		
		
	}
	
	@Override
	public Component getComponent(ObjectPropertyPanel panel) {		
		StringBuffer sb = new StringBuffer();
		for (Node node : node_list) {
			sb.append(node.item.getName()+", ");
		}
		edit_label.setText(sb.toString());
//		edit_label.setText(node_list.size()+"个道具 : ");	
		if (panel != null) {
			panel.fireEditingStopped();
		}
		return edit_label;
	}
	
	@Override
	public DropItemNode[] getValue() {
		Vector<DropItemNode> values = new Vector<DropItemNode>(node_list.size());		
		for (Node e : node_list) {
			values.add(e.value);
		}
		return values.toArray(new DropItemNode[values.size()]);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == value_add) {
			Node node = new EditNodeDialog("选择道具", null).showDialog();
			if (node != null) {
				node_list.add(node);
				resetList(node);
			}
		}
		else if (e.getSource() == value_edt) {
			try{
				int si = value_list.getSelectedIndex();
				Node node = (Node)value_list.getModel().getElementAt(si);
				new EditNodeDialog("编辑道具", node).showDialog();
				resetList(node);
			}catch(Exception er){}
		}
		else if (e.getSource() == value_del) {
			try{
				int si = value_list.getSelectedIndex();
				Node node = (Node)value_list.getModel().getElementAt(si);
				node_list.remove(node);
				resetList(null);
			}catch(Exception er){}
		}
		else if (e.getSource() == ok) {
			this.setVisible(false);
		}
	}
	
	
	
	class Node extends JLabel implements G2DListItem
	{
		private static final long serialVersionUID = 1L;
		
		XLSItem			item;
		DropItemNode	value;
		
		public Node(XLSItem item, DropItemNode value) 
		{
			setFocusable(true);
			this.item		= item;
			this.value		= value;			
		}
		
		@Override
		public ImageIcon getListIcon(boolean update) {
			return item.getIcon(update);
		}
	
		@Override
		public String getListName() {
			return CUtil.snapStringRightSize("(" + value.drop_rate_percent + "%) ", 8, ' ') + item.getName();
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		
		@Override
		public String toString() {
			return getName();
		}
	}
	
	class EditNodeDialog extends AbstractOptionDialog<Node>
	{
		private static final long serialVersionUID = 1L;
		
		ObjectSelectList<XLSItem> 	item_list 	= new ObjectSelectList<XLSItem>(XLSItem.class, 10);
		JSpinner					percent		= new JSpinner(new SpinnerNumberModel(100.d, 0.d, 100.d, 1.d));
		Node						edit_node	= null;
		
		public EditNodeDialog(String title, Node node) 
		{
			super(DropItemEditor.this);
			super.setIconImage(Res.icon_edit);
			super.setTitle(title);
			super.setSize(260, 400);
			super.setLocation(
					DropItemEditor.this.getLocation().x + DropItemEditor.this.getWidth(),
					DropItemEditor.this.getLocation().y);

			this.edit_node = node;
			
			if (edit_node != null) {
				item_list.setSelectedUnit(edit_node.item);
				percent.setValue(edit_node.value.drop_rate_percent);
			} else {
				percent.setValue(100.d / (node_list.size()+1));
			}
			
			JPanel num_panel = new JPanel(new BorderLayout());
			num_panel.add(percent, BorderLayout.CENTER);
			num_panel.add(new JLabel("百分比"), BorderLayout.WEST);
			
			this.add(num_panel, BorderLayout.NORTH);
			this.add(item_list, BorderLayout.CENTER);
			
			
		}
		
		@Override
		protected boolean checkOK() {
			XLSItem titem = item_list.getSelectedUnit();
			if (titem==null) {
				JOptionPane.showMessageDialog(this, "未选择道具类型！");
				return false;
			}
			return true;
		}
		
		@Override
		protected Node getUserObject() {
			try{
				XLSItem titem = item_list.getSelectedUnit();
				if (titem!=null) {
					Number perc = (Number)percent.getValue();
					DropItemNode value = new DropItemNode(titem.getIntID(), perc.floatValue());
					if (edit_node!=null) {
						edit_node.item = titem;
						edit_node.value = value;
						return edit_node;
					} else {
						Node node = new Node(titem, value);
						return node;
					}
				}
			}catch(Exception er){
				er.printStackTrace();
			}
			return null;
		}

	}
	
	
}

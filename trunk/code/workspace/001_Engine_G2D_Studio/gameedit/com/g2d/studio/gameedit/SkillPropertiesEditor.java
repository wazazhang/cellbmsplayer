package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.CUtil;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.item.ItemPropertyTemplate;
import com.g2d.annotation.Property;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.item.property.ItemPropertyNode;
import com.g2d.studio.item.property.ItemPropertySelectDialog;

import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;

@SuppressWarnings("serial")
public class SkillPropertiesEditor extends JPanel implements ActionListener
{
	final XLSSkill skill;
	
	JToolBar	tools			= new JToolBar();
	JButton		tool_add_column	= new JButton("添加属性");
	JButton		tool_del_column	= new JButton("删除属性");
	JButton		tool_set_level	= new JButton("设置等级");
	
	JSplitPane	split			= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JScrollPane split_left		= new JScrollPane();
	JScrollPane split_right		= new JScrollPane();

//	------------------------------------------------------------------------------------------------------------------
	
	public SkillPropertiesEditor(XLSSkill skill) 
	{
		super(new BorderLayout());
		this.skill = skill;
		
		this.add(tools, BorderLayout.NORTH);
		{
			tool_add_column.addActionListener(this);
			tool_del_column.addActionListener(this);
			tool_set_level.addActionListener(this);
			tools.add(tool_add_column);
			tools.add(tool_del_column);
			tools.addSeparator();
			tools.add(tool_set_level);
		}
		
		this.add(split, BorderLayout.CENTER);
		{
			split.setLeftComponent(split_left);
			split.setRightComponent(split_right);
		}
		
		refreshColumns();
	}

//	------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (tool_add_column == e.getSource()) {
			ItemPropertyNode node = new ItemPropertySelectDialog(this).showDialog();
			if (node != null) {
				ItemPropertyTemplate tt = node.getItemPropertyTemplate();
				if (tt != null) {
					skill.getData().addColumn(tt.getClass());
					refreshColumns();
				}
			}
		}
		else if (tool_del_column == e.getSource()) {
			ColumnItem item = ((ColumnList)split_left.getViewport().getView()).getSelectedItem();
			if (item != null) {
				skill.getData().removeColumn(item.column);
				refreshColumns();
			}
		}
		else if (tool_set_level == e.getSource()) {
			Object value = JOptionPane.showInputDialog(this, "输入等级数！", skill.getData().getMaxLevel());
			if (value!=null) {
				try {
					int new_level = Integer.parseInt(value.toString());
					if (new_level > 0) {
						skill.getData().setMaxLevel(new_level);
						refreshColumns();
						Studio.getInstance().getObjectManager().refresh(XLSSkill.class);
					} else {
						JOptionPane.showMessageDialog(this, "等级不能小于 1 ！");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(this, err.getClass() + " : " + err.getMessage());
				}
			}
		}
	}

	private void refreshColumns() {
		split_left.setViewportView(new ColumnList());
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
	class ColumnList extends G2DList<ColumnItem> implements ListSelectionListener
	{
		public ColumnList() {
			Vector<ColumnItem> list_data = new Vector<ColumnItem>(skill.getData().getMaxColumn());
			for (int column = 0; column < skill.getData().getMaxColumn(); column++) {
				list_data.add(new ColumnItem(column));
			}
			this.setListData(list_data);
			split_right.setViewportView(new JPanel());
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			ColumnItem selected = getSelectedItem();
			if (selected != null) {
				split_right.setViewportView(selected.levels);
			} else {
				split_right.setViewportView(new JPanel());
			}
		}
	}

//	------------------------------------------------------------------------------------------------------------------
	
	class ColumnItem implements G2DListItem
	{
		final private int 			column;
		final private Class<? extends ItemPropertyTemplate> 
									column_type;
		final private FieldTable 	levels;
		
		public ColumnItem(int column) {
			this.column 		= column;
			this.column_type	= skill.getData().getColumnType(column);
			this.levels 		= new FieldTable();
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		
		@Override
		public ImageIcon getListIcon(boolean update) {
			return null;
		}
		
		@Override
		public String getListName() {
			return AbstractAbility.getName(column_type);
		}

//		------------------------------------------------------------------------------------------------------------------

		class FieldTable extends JTable
		{
			public FieldTable() 
			{
				
			}
		}
	}
	
	
//	------------------------------------------------------------------------------------------------------------------
	
}

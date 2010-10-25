package com.g2d.studio.instancezone;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.struct.InstanceZoneScriptCode;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractOptionDialog;

@SuppressWarnings("serial")
public class InstanceZoneScriptCodeEditor extends AbstractOptionDialog<InstanceZoneScriptCode> implements PropertyCellEdit<InstanceZoneScriptCode>
{
	JToolBar	tool_bar		= new JToolBar();
	JButton		btn_change_zone	= new JButton("改变副本数据");
	
	JSplitPane	center 			= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JList		var_list		= new JList();
	JTextPane 	text_pane 		= new JTextPane();
	InstanceZoneNode zone;

	JLabel		label			= new JLabel();
	
	
	public InstanceZoneScriptCodeEditor(InstanceZoneScriptCode old) 
	{
		this.resetTitle(old);
		
		{
			this.tool_bar.setFloatable(false);
			this.tool_bar.add(btn_change_zone);
			this.btn_change_zone.addActionListener(this);
		}
		super.add(tool_bar, BorderLayout.NORTH);
		
		{
			var_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			var_list.setMinimumSize(new Dimension(100, 50));
			var_list.setPreferredSize(var_list.getMinimumSize());
			center.setLeftComponent(var_list);
			
			center.setRightComponent(new JScrollPane(text_pane));
		}
		super.add(center, BorderLayout.CENTER);
		
		if (old != null) {
			text_pane.setText(old.script+"");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == btn_change_zone) {
			zone = new InstanceZoneSelectDialog(this, null).showDialog();
			if (zone != null) {
				InstanceZoneScriptCode data = getUserObject();
				resetTitle(data);
			}
		}
	}
	
	private void resetTitle(InstanceZoneScriptCode old) {
		if (old != null) {
			zone = Studio.getInstance().getInstanceZoneManager().getNode(old.instance_zone_id);
			if (zone != null) {
				super.setTitle(zone.getListName() + " :: " + old);
				this.var_list.setListData(new Vector<String>(zone.getData().getData().keySet()));
			} else {
				super.setTitle(old.instance_zone_id + " :: " + old);
				this.var_list.setListData(new Object[]{});
			}
		} else {
			super.setTitle("null");
			this.var_list.setListData(new Object[]{});
		}
	}
	
	public static String getTitle(InstanceZoneScriptCode old) {
		if (old != null) {
			InstanceZoneNode zone = Studio.getInstance().getInstanceZoneManager().getNode(old.instance_zone_id);
			if (zone != null) {
				return (zone.getListName() + " :: " + old);
			} else {
				return (old.instance_zone_id + " :: " + old);
			}
		} else {
			return "null";
		}
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		label.setText(getTitle());
		return label;
	}

	@Override
	protected InstanceZoneScriptCode getUserObject() {
		InstanceZoneScriptCode data = new InstanceZoneScriptCode();
		data.script = text_pane.getText();
		if (zone != null) {
			data.instance_zone_id = zone.getIntID();
		}
		return data;
	}

	@Override
	public InstanceZoneScriptCode getValue() {
		return selected_object;
	}
	
}

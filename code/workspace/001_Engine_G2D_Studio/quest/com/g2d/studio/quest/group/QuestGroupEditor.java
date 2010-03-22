package com.g2d.studio.quest.group;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.cell.rpg.quest.QuestGroup;
import com.cell.rpg.template.TEffect;
import com.g2d.Tools;
import com.g2d.annotation.Property;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.OriginShape;
import com.g2d.display.particle.ParticleAffect;
import com.g2d.display.particle.Layer.TimeNode;
import com.g2d.display.particle.affects.Gravity;
import com.g2d.display.particle.affects.Vortex;
import com.g2d.display.particle.affects.Wander;
import com.g2d.display.particle.affects.Wind;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.studio.cpj.CPJEffectImageSelectDialog;
import com.g2d.studio.cpj.CPJEffectImageSelectDialog.TileImage;
import com.g2d.studio.cpj.entity.CPJImages;
import com.g2d.studio.particles.ParticleViewer;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DListSelectDialog;


public class QuestGroupEditor extends JSplitPane implements ActionListener, ListSelectionListener
{
	final QuestGroup data;
	
	public QuestGroupEditor(QuestGroup e) 
	{
		this.data = e;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {}
		
	@Override
	public void actionPerformed(ActionEvent e) 
	{}
		


//	--------------------------------------------------------------------------------------------------------------------

}


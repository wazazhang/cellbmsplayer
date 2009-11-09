package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.gameedit.template.TNpc;
import com.g2d.studio.gameedit.template.TemplateTreeNode;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ObjectManager extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	DefaultMutableTreeNode unit_root;
	
	G2DWindowToolBar toolbar = new G2DWindowToolBar(this);
	
	public ObjectManager(ProgressForm progress) 
	{
		super.setSize(800, Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setTitle("物体编辑器");
		super.setIconImage(Res.icon_edit);

		File xls_path = Studio.getInstance().getFile(Config.XLS_ROOT);
		
		JTabbedPane table = new JTabbedPane();
		// TNPC
		{
			table.addTab("NPC", 
					Tools.createIcon(Res.icon_res_2), 
					new Split<TNpc>("NPC模板", TNpc.class, xls_path));
		}

		this.add(table, BorderLayout.CENTER);

		
		this.add(toolbar, BorderLayout.NORTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toolbar.save) {
			saveAll();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void saveAll()
	{
		Enumeration<TNpc> npcs = unit_root.children();
		while (npcs.hasMoreElements()) {
			TNpc npc = npcs.nextElement();
			npc.save();
		}
		System.out.println(getClass().getSimpleName() + " : save all");
	}
	
	class Split<T extends TemplateTreeNode> extends JSplitPane implements TreeSelectionListener
	{
		private static final long serialVersionUID = 1L;

		final public Class<T> type;
		
		final JScrollPane right = new JScrollPane();
		
		public Split(String title, Class<T> type, File xls_path) 
		{
			this.type = type;
			unit_root = new DefaultMutableTreeNode("NPC模板");
			ArrayList<T> files = TemplateTreeNode.listXLSRows(
					new File(xls_path.getPath()+File.separatorChar+type.getSimpleName().toLowerCase()+Config.XLS_SUFFIX),
					type);
			for (T npc : files) {
				npc.load();
				unit_root.add(npc);
			}
			G2DTree tree = new G2DTree(unit_root);
			tree.addTreeSelectionListener(this);
			tree.setMinimumSize(new Dimension(200, 200));
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			this.setOrientation(HORIZONTAL_SPLIT);
			this.setLeftComponent(tree);
			this.setRightComponent(right);
		}
	
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			if (type.isInstance(e.getPath().getLastPathComponent())) {
				T node = type.cast(e.getPath().getLastPathComponent());
				right.setViewportView(node.getEditComponent());
			}
		}
		
		
	}
	
	
}

package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.gameedit.template.TNpc;
import com.g2d.studio.gameedit.template.TemplateTreeNode;
import com.g2d.studio.old.Studio;
import com.g2d.studio.old.swing.G2DTree;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;

public class ObjectManager extends AbstractFrame
{
	private static final long serialVersionUID = 1L;
	
	DefaultMutableTreeNode unit_root;
	public ObjectManager() 
	{
		super.setSize(800, 600);
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY()+20);
		super.setTitle("物体编辑器");
		super.setIconImage(Res.icon_edit);
		
		File xls_path = Studio.getInstance().getFile(Config.ROOT_XLS);
		
		JTabbedPane table = new JTabbedPane();
		// TNPC
		{
			Class<TNpc> clazz = TNpc.class;
			unit_root = new DefaultMutableTreeNode("NPC模板");
			ArrayList<TNpc> files = TemplateTreeNode.listXLSRows(
					new File(xls_path.getPath()+File.separatorChar+clazz.getSimpleName()+".xls"),
					clazz
					);
			for (TNpc npc : files) {
				unit_root.add(npc);
			}
			G2DTree tree = new G2DTree(unit_root);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("NPC", Tools.createIcon(Res.icon_res_2), scroll);
		}
		
		this.add(table, BorderLayout.CENTER);
		
	}
	
	public void loadAll()
	{
		
	}
	
}

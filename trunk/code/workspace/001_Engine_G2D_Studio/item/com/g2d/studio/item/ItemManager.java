package com.g2d.studio.item;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.cell.CUtil;
import com.cell.classloader.jcl.JavaCompiler;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.item.ItemPropertyTypes;
import com.cell.rpg.quest.QuestItem;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSColumns;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.IDFactoryInteger;
import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.CPJResourceList;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DEffect;
//import com.g2d.studio.gameedit.dynamic.DQuestItem;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DListSelectDialog;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thoughtworks.xstream.XStream;

public class ItemManager extends ManagerForm implements ActionListener
{
	private static final long serialVersionUID = 1L;

	G2DWindowToolBar 		toolbar		= new G2DWindowToolBar(this);
	
	ItemPropertiesTreeView	tree_view;
	
	public ItemManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "道具属性管理器", Res.icon_res_4);
		
		this.add(toolbar, BorderLayout.NORTH);
		
//		File javaFile = new File(studio.plugins_dir, Config.PLUGIN_ITEM_TYPES.replace(".", "/")+".java");
//		try {
//			ArrayList<Class<?>> all_class = JavaCompiler.compileAndLoadAllClass(Config.PLUGIN_ITEM_TYPES, javaFile);
//			ItemPropertyTypes.setItemPropertyTypes(all_class);
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		
		try {
			Class<?> properties_type = Class.forName(Config.DYNAMIC_ITEM_PROPERTIES_CLASS);
			ItemPropertyTypes.setItemPropertyTypes(properties_type.getClasses());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
		File item_properties_list_file = new File(studio.project_save_path, "item_properties/item_properties.list");
		this.tree_view = new ItemPropertiesTreeView(item_properties_list_file);
		this.add(tree_view, BorderLayout.CENTER);
	}

	public ItemPropertiesNode getNode(int id) {
		return tree_view.getNode(id);
	}
	
	public Vector<ItemPropertiesNode> getAllNodes() {
		return tree_view.getAllObject();
	}
	
	public void saveAll() throws Throwable
	{
		System.out.println(getClass().getSimpleName() + " : save all");
		this.tree_view.saveAll();
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toolbar.save) {
			try {
				saveAll();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}


//	-------------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
}

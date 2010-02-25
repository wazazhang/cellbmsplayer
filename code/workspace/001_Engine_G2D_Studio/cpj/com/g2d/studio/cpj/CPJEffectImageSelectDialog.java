package com.g2d.studio.cpj;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cell.game.CCD;
import com.cell.game.CSprite;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.j2se.CGraphics;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource.ImagesSet;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.StudioResource.StreamTypeTiles;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJImages;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTree;
import com.g2d.util.AbstractDialog;

public class CPJEffectImageSelectDialog extends AbstractDialog implements ActionListener, ListSelectionListener
{
	JSplitPane	split	= new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	
	JList		list	= new JList();

	ImageList	childs;
	
	JButton 	ok		= new JButton("确定");
	
	Vector<CPJImages> list_data = new Vector<CPJImages>();
	
	TileImage	reslut_data;
	
	public CPJEffectImageSelectDialog(Component owner) 
	{
		super(owner);
		super.setLayout(new BorderLayout());
		super.setSize(600, 400);
		super.setCenter();
		
		this.add(split, BorderLayout.CENTER);
		this.add(ok, BorderLayout.SOUTH);
		this.ok.addActionListener(this);

		{
			ArrayList<CPJFile> files = CPJFile.listFile(
					Studio.getInstance().project_path.getPath(), 
					Config.RES_EFFECT_ROOT, 
					CPJResourceType.EFFECT);
			for (int i=0; i<files.size(); i++) {
				CPJFile file = files.get(i);
				for (ImagesSet imgset : file.getSetResource().ImgTable.values()) {
					CPJImages images = new CPJImages(file, imgset.Name);
					list_data.add(images);
				}
			}
		}
		this.list.setListData(list_data);
		this.list.addListSelectionListener(this);
		
		JScrollPane top = new JScrollPane(list);
		top.setPreferredSize(new Dimension(400, 200));
		top.setMinimumSize(new Dimension(400, 200));
		
		split.setTopComponent(top);
		split.setBottomComponent(new JPanel());
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (list.getSelectedValue() instanceof CPJImages) {
			CPJImages images = (CPJImages)list.getSelectedValue();
			childs = new ImageList(images);
			split.setBottomComponent(new JScrollPane(childs));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CPJImages object = (CPJImages)list.getSelectedValue();
		if (object!=null && childs!=null) {
			SubImage index = (SubImage)childs.getSelectedValue();
			this.reslut_data = new TileImage(object.parent.name, object.images_name, index.index);
		}
		this.setVisible(false);
	}
	
	public TileImage showDialog() {
		super.setVisible(true);
		return reslut_data;
	}
	
//	------------------------------------------------------------------------------------------------------

	public static class TileImage
	{
		final public String 		parent_name;
		final public String 		images_name;
		final public int			index;
		
		public TileImage(String parent_name, String images_name, int index) 
		{
			this.parent_name	= parent_name;
			this.images_name	= images_name;
			this.index			= index;
		}
		
		@Override
		public String toString() {
			return this.parent_name + "/" + this.images_name + "[" + index + "]";
		}
		
		public BufferedImage getEffectImage() {
			for (CPJFile file : CPJFile.listFile(
					Studio.getInstance().project_path.getPath(), 
					Config.RES_EFFECT_ROOT, 
					CPJResourceType.EFFECT)) {
				if (file.name.equals(this.parent_name)) {
					for (ImagesSet imgset : file.getSetResource().ImgTable.values()) {
						if (imgset.Name.equals(this.images_name)) {
							CPJImages images = new CPJImages(file, imgset.Name);
							synchronized (images.parent.getSetResource()) {
								boolean unload = !images.parent.getSetResource().isLoadImages();
								try{
									images.parent.getSetResource().initAllStreamImages();
									IImages tiles = images.parent.getSetResource().getImages(images.images_name);
									for (int s=0; s<tiles.getCount(); s++) {
										IImage tile = tiles.getImage(s);
										if (tile!=null && s == this.index) {
											return Tools.createImage(tile);
										}
									}
								} finally {
									if (unload) {
										images.parent.getSetResource().destoryAllStreamImages();
									}
								}
							}
							
						}
					}
				}
			}
			return null;
		}
	}
	
	
	
//	------------------------------------------------------------------------------------------------------
	
	class SubImage extends JLabel implements G2DListItem
	{
		final BufferedImage		image;
		final ImageIcon 		icon;
		final int 				index;
		
		public SubImage(IImage img, int index) {
			this.image	= Tools.createImage(img);
			this.icon	= Tools.createIcon(image);
			this.index	= index;
			this.setIcon(icon);
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		
		@Override
		public ImageIcon getListIcon(boolean update) {
			return icon;
		}
		
		@Override
		public String getListName() {
			return "" + index;
		}
	}
	
	class ImageList extends G2DList<SubImage>
	{
		public ImageList(CPJImages images) 
		{
			Vector<SubImage> items = new Vector<SubImage>();
			synchronized (images.parent.getSetResource()) 
			{
				boolean unload = !images.parent.getSetResource().isLoadImages();
				try{
					images.parent.getSetResource().initAllStreamImages();
					IImages tiles = images.parent.getSetResource().getImages(images.images_name);
					for (int i=0; i<tiles.getCount(); i++) {
						IImage tile = tiles.getImage(i);
						if (tile != null) {
							items.add(new SubImage(tile, i));
						}
					}
				} finally {
					if (unload) {
						images.parent.getSetResource().destoryAllStreamImages();
					}
				}
			}
			this.setListData(items);
		}
		
	}

}

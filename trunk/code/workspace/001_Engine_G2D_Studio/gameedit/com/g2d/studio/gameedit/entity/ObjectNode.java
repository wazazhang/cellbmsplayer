package com.g2d.studio.gameedit.entity;

import java.awt.Component;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.rpg.RPGObject;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTreeNode;
import com.thoughtworks.xstream.XStream;

public abstract class ObjectNode<T extends RPGObject> extends G2DTreeNode<ObjectNode<?>> implements G2DListItem
{
	public ObjectNode() {}

	public abstract String getID();
	
	public abstract T getData();

	public abstract int getIntID() ;
	
	
//	----------------------------------------------------------------------------------------
	@Override
	protected ImageIcon createIcon() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return false;
	}
	
	public Component getEditComponent(){
		return null;
	}
	
	
}

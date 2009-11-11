package com.g2d.studio.gameedit.dynamic;

import java.awt.Component;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

import com.cell.rpg.template.TAvatar;
import com.cell.util.MarkedHashtable;
import com.g2d.Tools;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.AvatarEditor;
import com.g2d.studio.res.Res;


final public class DAvatar extends DynamicNode<TAvatar>
{
	static AvatarEditor 					editor = new AvatarEditor();
	
	private CPJIndex<CPJSprite>				body;
	private ArrayList<CPJIndex<CPJSprite>>	avatars;
	
	public DAvatar(IDynamicIDFactory<DAvatar> factory, String name, CPJIndex<CPJSprite> body) 
	{
		super(factory, name, null);
		this.body		= body;
		this.avatars 	= new ArrayList<CPJIndex<CPJSprite>>();
	}
	
	@Override
	protected TAvatar newData(IDynamicIDFactory<?> factory, String name) {
		return new TAvatar(getID(), name);
	}
	
	@Override
	protected ImageIcon createIcon() {
		return Tools.createIcon(Res.icon_res_3);
	}
	
	public CPJIndex<CPJSprite> getBody() {
		return body;
	}
	
	public void setBody(CPJIndex<CPJSprite> body) {
		if (body!=null) {
			this.body = body;
		}
	}
	
	public ArrayList<CPJIndex<CPJSprite>> getParts() {
		synchronized(avatars) {
			return new ArrayList<CPJIndex<CPJSprite>>(avatars);
		}
	}
	public void addAvatarPart(CPJIndex<CPJSprite> part) {
		synchronized(avatars) {
			avatars.add(part);
		}
	}
	public void removeAvatarPart(int index) {
		synchronized(avatars) {
		avatars.remove(index);
		}
	}
	public void removeAvatarPart(CPJIndex<CPJSprite> part) {
		synchronized(avatars) {
		avatars.remove(part);
		}
	}

	public void moveAvatarPart(CPJIndex<CPJSprite> src, int direct) {
		try {
			synchronized(avatars) {
				int srci = avatars.indexOf(src);
				if (srci==0 && direct<0) {
					return;
				}
				if (srci==avatars.size()-1 && direct>0) {
					return;
				}
				int dsti = srci + direct;
				CPJIndex<CPJSprite> dst = avatars.get(dsti);
				avatars.set(dsti, src);
				avatars.set(srci, dst);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	@Override
	public Component getEditComponent() {
		editor.setAvatar(this);
		return editor;
	}
	
	
	
	
//	----------------------------------------------------------------------------------------------------------------------
	
	
}

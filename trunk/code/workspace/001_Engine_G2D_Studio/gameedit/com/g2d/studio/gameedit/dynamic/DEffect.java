package com.g2d.studio.gameedit.dynamic;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

import com.cell.rpg.RPGObject;
import com.cell.rpg.display.Node;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;

final public class DEffect extends DynamicNode<TEffect>
{
	public DEffect(IDynamicIDFactory<DAvatar> factory, String name) {
		super(factory, name);
	}
	
	DEffect(TEffect effect) {
		super(effect, Integer.parseInt(effect.id), effect.name);
	}
	
	@Override
	protected TEffect newData(IDynamicIDFactory<?> factory, String name) {
		return null;
	}
	
}

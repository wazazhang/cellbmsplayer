package com.cell.gameedit.output;


import java.awt.Color;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.gameedit.Output;
import com.cell.gameedit.SetObject;
import com.cell.gameedit.SetResource;

import com.cell.gameedit.object.*;
import com.cell.gameedit.object.WorldSet.*;

import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CAnimates;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CCollides;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.io.TextDeserialize;
import com.cell.util.MarkedHashtable;
import com.cell.util.PropertyGroup;
import com.cell.util.concurrent.ThreadPoolService;


/**
 * 如何将编辑器资源解析成单位
 * @author WAZA
 */
public class OutputPropertiesDir extends OutputProperties
{
	final public String root;
	final public String file_name;
	
	public OutputPropertiesDir(String file) throws Exception
	{
		super(file);
		this.root 			= path.substring(0, path.lastIndexOf("/")+1);
		this.file_name		= path.substring(root.length());
		
		// 读入基础属性
		byte[] conf_data = loadRes(file_name, null);
		if (conf_data == null) {
			throw new FileNotFoundException(path);
		}
		String conf = new String(conf_data, CIO.ENCODING);
		PropertyGroup config = new PropertyGroup(conf, "=");
		
		super.init(config);
	}
	
	@Override
	public void dispose() {}
	
	/**
	 * 读取本目录资源
	 * @param path
	 * @return
	 */
	public byte[] loadRes(String path, AtomicReference<Float> percent)
	{
		byte[] data = CIO.loadData(root + path);
		if (data == null) {
			System.err.println("SetResource : read error : " + root + path);
		} 
		return data;
	}
	
}




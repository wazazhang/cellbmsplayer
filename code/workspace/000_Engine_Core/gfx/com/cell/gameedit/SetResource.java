package com.cell.gameedit;


import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.MapSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.TableSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.util.MarkedHashtable;
import com.cell.util.concurrent.ThreadPoolService;

/**
 * 对应output.properties的资源文件
 * @author WAZA
 */
abstract public class SetResource
{
//	-------------------------------------------------------------------------------------
	
	final public Hashtable<String, ImagesSet>		ImgTable;
	final public Hashtable<String, SpriteSet>		SprTable;
	final public Hashtable<String, MapSet>			MapTable;
	final public Hashtable<String, WorldSet>		WorldTable;
	final public Hashtable<String, TableSet>		TableGroups;

	final protected Output							output_adapter;
	
	final protected	MarkedHashtable 				resource_manager;
	final protected	ThreadPoolService				loading_service;
	
//	-------------------------------------------------------------------------------------
	
	public SetResource(Output adapter, ThreadPoolService loading_service) throws Exception
	{
		this.output_adapter		= adapter;
		this.loading_service	= loading_service;
		this.resource_manager	= new MarkedHashtable();
		
		this.ImgTable 			= output_adapter.getImgTable();
		this.SprTable 			= output_adapter.getSprTable();
		this.MapTable 			= output_adapter.getMapTable();
		this.WorldTable			= output_adapter.getWorldTable();
		this.TableGroups		= output_adapter.getTableGroups();
	}
	
	public Output getOutput() {
		return output_adapter;
	}
	
	public void dispose() {
		for (Object obj : resource_manager.values()) {
			if (obj instanceof StreamTiles) {
				((StreamTiles) obj).unloadAllImages();
			}
		}
		output_adapter.dispose();
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	Resources
//	-------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 同步获取图片方法<br>
	 * 如果有特殊需要，可以重载此方法
	 * @param img
	 * @return
	 * @throws IOException
	 */
	abstract protected StreamTiles getLocalImage(ImagesSet img) throws IOException ;
	
	/**
	 * 异步获取图片方法<br>
	 * 如果有特殊需要，可以重载此方法
	 * @param img
	 * @return
	 * @throws IOException
	 */
	abstract protected StreamTiles getStreamImage(ImagesSet img) throws IOException ;
	
//	-------------------------------------------------------------------------------------------------------------------------------

	synchronized
	final public StreamTiles getImages(ImagesSet img) 
	{
		StreamTiles stuff = resource_manager.get("IMG_" + img.Index, StreamTiles.class);
		if (stuff != null) {
			if (!stuff.isLoaded()) {
				if (loading_service != null) {
					loading_service.executeTask(stuff);
				} else {
					stuff.run();
				}
			}
			return stuff;
		}

		try {
			if (loading_service != null) {
				stuff = getStreamImage(img);
				loading_service.executeTask(stuff);
			} else {
				stuff = getLocalImage(img);
				stuff.run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resource_manager.put("IMG_" + img.Index, stuff);
		
		return stuff;
	}
	
	
	synchronized
	final public StreamTiles getImages(String key){
		ImagesSet img = ImgTable.get(key);
		return getImages(img);
			
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	synchronized
	final public CSprite getSprite(SpriteSet spr){
		IImages tiles = getImages(spr.ImagesName);
		return getSprite(spr, tiles);
	}
	
	synchronized
	final public CSprite getSprite(SpriteSet spr, IImages tiles)
	{
		CSprite cspr = resource_manager.get("SPR_"+spr.Index, CSprite.class);
		if (cspr != null) {
			return new CSprite(cspr);
		}
		cspr = output_adapter.createSpriteFromSet(spr, tiles);
		resource_manager.put("SPR_"+spr.Index, cspr);
		return new CSprite(cspr);
	}
	
	synchronized
	final public CSprite getSprite(String key){
		SpriteSet spr = SprTable.get(key);
		return getSprite(spr);
	}
	
	synchronized
	final public CSprite getSprite(String key, IImages images){
		SpriteSet spr = SprTable.get(key);
		return getSprite(spr, images);
	}

	synchronized 
	final public AtomicReference<CSprite> getSpriteAsync(String key, LoadSpriteListener ... listener)
	{
		SpriteSet spr = SprTable.get(key);
		if (spr != null) {
			AtomicReference<CSprite> ret = new AtomicReference<CSprite>();
			CSprite obj = resource_manager.get("SPR_"+key, CSprite.class);
			if (obj != null) {
				CSprite cspr = new CSprite(obj);
				ret.set(cspr);
				for (LoadSpriteListener l : listener) {
					l.loaded(this, cspr, spr);
				}
			} else {
				LoadSpriteTask task = new LoadSpriteTask(spr, ret, listener);
				if (loading_service != null) {
					loading_service.executeTask(task);
				} else {
					new Thread(task, "get-sprite-" + key).start();
				}
			}
			return ret;
		} else {
			throw new NullPointerException("sprite not found : " + key);
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------------

	//
	synchronized
	final public CWayPoint[] getWorldWayPoints(String key)
	{
		CWayPoint[] points = resource_manager.get("WPS_"+key, CWayPoint[].class);
		if (points != null) {
			return points;
		}
		WorldSet world = WorldTable.get(key);
		points = output_adapter.createWayPointsFromSet(world.WayPoints);
		resource_manager.put("WPS_"+key, points);
		return points;
	}
	
	synchronized
	final public CCD[] getWorldRegions(String key)
	{
		CCD[] regions = resource_manager.get("WRS_"+key, CCD[].class);
		if (regions != null) {
			return regions;
		}
		WorldSet world = WorldTable.get(key);
		regions = output_adapter.createRegionsFromSet(world.Regions);
		resource_manager.put("WRS_"+key, regions);
		return regions;
	}

//	-------------------------------------------------------------------------------------------------------------------------------
//	Set Object in <setfile>.properties
//	-------------------------------------------------------------------------------------------------------------------------------

	
	
	final public WorldSet getSetWorld(String key) {
		return WorldTable.get(key);
	}

	final public SpriteSet getSetSprite(String key) {
		return SprTable.get(key);
	}

	final public MapSet getSetMap(String key) {
		return MapTable.get(key);
	}

	final public ImagesSet getSetImages(String key) {
		return ImgTable.get(key);
	}
	
	final public TableSet getTableGroup(String key) {
		return TableGroups.get(key);
	}
	
	final public<T extends SetObject> T getSetObject(Class<T> cls, String key)
	{
		SetObject ret = null;
		if (ImagesSet.class.isAssignableFrom(cls)) {
			ret = ImgTable.get(key);
		}
		else if (SpriteSet.class.isAssignableFrom(cls)) {
			ret = SprTable.get(key);
		}
		else if (MapSet.class.isAssignableFrom(cls)) {
			ret = MapTable.get(key);
		}
		else if (WorldSet.class.isAssignableFrom(cls)) {
			ret = WorldTable.get(key);
		}
		else if (TableSet.class.isAssignableFrom(cls)) {
			ret = TableGroups.get(key);
		}
		if (ret!=null) {
			return cls.cast(ret);
		}
		return null;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------------
//	utils
//	--------------------------------------------------------------------------------------------------------------------------------------------------
	synchronized
	final public void initAllResource(SetLoading progress)
	{
		// images 
		{
			int count = ImgTable.size();
			int index = 0;
			Enumeration<ImagesSet> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				ImagesSet ts = imgs.nextElement();
				IImages images = getImages(ts);
				if (progress!=null) {
					progress.progress(this, images, index, count);
				}
				index ++;
			}
		}
		
		// sprites
		{
			int count = SprTable.size();
			int index = 0;
			Enumeration<SpriteSet> sprs = SprTable.elements();
			while (sprs.hasMoreElements()) {
				SpriteSet ss = sprs.nextElement();
				CSprite sprite = getSprite(ss);
				if (progress!=null) {
					progress.progress(this, sprite, index, count);
				}
				index ++;
			}
		}
		
		// worlds
		{
			int count = WorldTable.size();
			int index = 0;
			Enumeration<WorldSet> worlds = WorldTable.elements();
			while (worlds.hasMoreElements()) {
				WorldSet ws = worlds.nextElement();
				CWayPoint[] points = getWorldWayPoints(ws.Name);
				CCD[] regions = getWorldRegions(ws.Name);
				if (progress!=null) {
					progress.progress(this, points, index, count);
					progress.progress(this, regions, index, count);
				}
				index ++;
			}
		}
	}
	
	synchronized
	final public boolean isStreamingImages(String images_name) {
		ImagesSet img = ImgTable.get(images_name);
		if (img!=null) {
			StreamTiles tiles = resource_manager.get("IMG_" + img.Index, StreamTiles.class);
			if (tiles!=null) {
				return !tiles.isLoaded();
			}
		}
		return false;
	}
	
	
	


//	-------------------------------------------------------------------------------------
	

	
	
	public static interface LoadSpriteListener
	{
		public void loaded(SetResource set, CSprite cspr, SpriteSet spr);
	}
	
	protected class LoadSpriteTask implements Runnable
	{
		final LoadSpriteListener[] listener;
		
		final SpriteSet spr;
		
		final AtomicReference<CSprite> ref;
		
		public LoadSpriteTask(
				SpriteSet spr, 
				AtomicReference<CSprite> ref,
				LoadSpriteListener ... listener) {
			this.spr		= spr;
			this.listener 	= listener;
			this.ref		= ref;
		}
		
		public void run() {
			try {
				CSprite cspr = getSprite(spr);
				ref.set(cspr);
				for (LoadSpriteListener l : listener) {
					l.loaded(SetResource.this, cspr, spr);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	
}

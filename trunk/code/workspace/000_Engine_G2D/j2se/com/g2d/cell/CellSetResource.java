package com.g2d.cell;


import java.awt.Color;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.game.CAnimates;
import com.cell.game.CCD;
import com.cell.game.CCollides;
import com.cell.game.CMap;
import com.cell.game.CSprite;
import com.cell.game.CWayPoint;

import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.io.TextDeserialize;
import com.cell.j2se.CGraphics;
import com.cell.j2se.CImage;
import com.cell.util.PropertyGroup;
import com.g2d.Version;

import com.g2d.cell.CellSetResource.WorldSet.RegionObject;
import com.g2d.cell.CellSetResource.WorldSet.WaypointObject;

import com.g2d.display.DisplayObject;
import com.g2d.util.Drawing;

public class CellSetResource
{
//	-------------------------------------------------------------------------------------
	
	final public String				Path;
	final public String				PathDir;
	final public String				PathName;
	final public String				Name;

	transient public Hashtable<String, ImagesSet>		ImgTable;
	transient public Hashtable<String, SpriteSet>		SprTable;
	transient public Hashtable<String, MapSet>			MapTable;
	transient public Hashtable<String, WorldSet>		WorldTable;
	
	final
	transient protected	Map<String, Object> 			ResourceManager;
	final 
	transient private	ThreadPoolExecutor				loading_service;
	
//	-------------------------------------------------------------------------------------
	
	public CellSetResource(String file) throws Exception
	{
		this(file, file, null);
	}
	
	public CellSetResource(String file, ThreadPoolExecutor loading_service) throws Exception
	{
		this(file, file, loading_service);
	}

	public CellSetResource(File file, String name, ThreadPoolExecutor loading_service) throws Exception
	{
		this(file.getPath().replace('\\', '/'), file.getPath().replace('\\', '/'), loading_service);
	}
	
	public CellSetResource(String file, String name, ThreadPoolExecutor loading_service) throws Exception
	{
		this.Path				= file;
		this.PathDir 			= file.substring(0, file.lastIndexOf("/")+1);
		this.PathName			= file.substring(file.lastIndexOf("/")+1);
		
		this.Name 				= name;
		
		this.ResourceManager	= new ConcurrentHashMap<String, Object>();
		
		this.loading_service	= loading_service;
		
//		System.out.println("read set : " + Path);
		
		// 读入基础属性
		byte[] conf_data = loadRes(PathName);
		if (conf_data == null) {
			throw new FileNotFoundException(Path);
		}
		
		String conf = new String(conf_data);
		PropertyGroup Config = new PropertyGroup(conf, "=");

		
		// 解吸所有对象
		int ImagesCount 	= Config.getInteger("ImagesCount", 0);
		int SpriteCount 	= Config.getInteger("SpriteCount", 0);
		int MapCount 		= Config.getInteger("MapCount", 0);
		int WorldCount 		= Config.getInteger("WorldCount", 0);

		ImgTable 		= new Hashtable<String, ImagesSet>();
		SprTable 		= new Hashtable<String, SpriteSet>();
		MapTable 		= new Hashtable<String, MapSet>();
		WorldTable		= new Hashtable<String, WorldSet>();
		
		for (int i=0; i<ImagesCount; i++){
			ImagesSet img = new ImagesSet(
					Config.getString("Images_" + i), 
					Config.getString("Images_" + i + "_tiles"));
			ImgTable.put(img.Name, img);
		}

		for (int i = 0; i < SpriteCount; i++) {
			try{
				SpriteSet spr = new SpriteSet(
						Config.getString("Sprite_" + i),
						Config.getString("Sprite_" + i + "_parts"),
						Config.getString("Sprite_" + i + "_frames"),
						Config.getString("Sprite_" + i + "_cds"),
						Config.getString("Sprite_" + i + "_cd_frames"),
						Config.getString("Sprite_" + i + "_frame_counts"),
						Config.getString("Sprite_" + i + "_frame_name"),
						Config.getString("Sprite_" + i + "_frame_animate"),
						Config.getString("Sprite_" + i + "_frame_cd_map"),
						Config.getString("Sprite_" + i + "_frame_cd_atk"),
						Config.getString("Sprite_" + i + "_frame_cd_def"),
						Config.getString("Sprite_" + i + "_frame_cd_ext")
				);
				SprTable.put(spr.Name, spr);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		for (int i = 0; i < MapCount; i++) {
			try{
				MapSet map = new MapSet(
						Config.getString("Map_" + i),
						Config.getString("Map_" + i + "_parts"),
						Config.getString("Map_" + i + "_frames"),
						Config.getString("Map_" + i + "_cds"),
						Config.getString("Map_" + i + "_tile_matrix"),
						Config.getString("Map_" + i + "_cd_matrix")
				);
				MapTable.put(map.Name, map);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < WorldCount; i++) {
			try{
				WorldSet world = new WorldSet(
						Config.getString("World_" + i),
						Config.getString("World_" + i + "_maps"),
						Config.getString("World_" + i + "_sprs"),
						Config.getString("World_" + i + "_waypoints"),
						Config.getString("World_" + i + "_waypoint_link"),
						Config.getString("World_" + i + "_regions"),
						Config.getString("World_" + i + "_data"),
						Config.getString("World_" + i + "_terrain")
				);
				WorldTable.put(world.Name, world);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dispose() {
		try{
			ImgTable.clear();
			SprTable.clear();
			MapTable.clear();
			WorldTable.clear();
			ResourceManager.clear();
		}catch(Throwable err){
			err.printStackTrace();
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public IImages getImages(ImagesSet img) 
	{
		IImages stuff = null;

		if (ResourceManager != null) {
			Object obj = ResourceManager.get("IMG_" + img.Index);
			if (obj instanceof IImages) {
				stuff = (IImages) obj;
			}
		}

		if (stuff == null) {
			try {
				if (loading_service != null) {
					stuff = getStreamImage(img);
					loading_service.purge();
					loading_service.execute((Runnable)stuff);
				} else {
					stuff = getLocalImage(img);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (ResourceManager != null) {
			ResourceManager.put("IMG_" + img.Index, stuff);
		}

		return stuff;
	}
	
	
	
	public IImages getImages(String key){
		ImagesSet img = ImgTable.get(key);
		return getImages(img);
			
	}
	
	/**
	 * 如果有特殊需要，可以重载此方法
	 * @param img
	 * @return
	 * @throws IOException
	 */
	protected IImages getLocalImage(ImagesSet img) throws IOException {
		StreamTiles tiles = new StreamTiles(img);
		tiles.run();
		return tiles;
	}
	
	/**
	 * 如果有特殊需要，可以重载此方法
	 * @param img
	 * @return
	 * @throws IOException
	 */
	protected IImages getStreamImage(ImagesSet img) throws IOException
	{
		StreamTiles tiles = new StreamTiles(img);
		return tiles;
	}
	
	
	public CSprite getSprite(SpriteSet spr){
		IImages tiles = getImages(spr.ImagesName);
		return getSprite(spr, tiles);
	}
	
	public CSprite getSprite(SpriteSet spr, IImages tiles)
	{
		CSprite cspr = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("SPR_"+spr.Index);
			if (obj instanceof CSprite) {
				cspr = (CSprite)obj;
			}
		}
		
		if (cspr==null){
			cspr = createSpriteFromSet(spr, tiles);
//			LoadedFileCount ++;
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("SPR_"+spr.Index, cspr);
		}
		
		return new CSprite(cspr);
	}
	
	public CSprite getSprite(String key){
		SpriteSet spr = SprTable.get(key);
		return getSprite(spr);
	}

	public CSprite getSprite(String key, IImages images){
		SpriteSet spr = SprTable.get(key);
		return getSprite(spr, images);
	}

	public void getSpriteAsync(String key, LoadSpriteListener listener)
	{
		SpriteSet spr = SprTable.get(key);
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("SPR_"+key);
			if (obj instanceof CSprite) {
				listener.loaded(this, (CSprite)obj, spr);
				return;
			}
		}
		if (loading_service != null) {
			loading_service.execute(new LoadSpriteTask(spr, listener));
		} else {
			new Thread(new LoadSpriteTask(spr, listener), "get-sprite-" + key).start();
		}
	}

	
//	--------------------------------------------------------------------------------------------------------------------------------------------------

	//
	public CWayPoint[] getWorldWayPoints(String key)
	{
		WorldSet world = WorldTable.get(key);
		
		CWayPoint[] points = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("WPS_"+key);
			if (obj instanceof CWayPoint[]) {
				points = (CWayPoint[])obj;
			}
		}
		
		if (points==null) {
			points = createWayPointsFromSet(world.WayPoints);
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("WPS_"+key, points);
		}
		
		return points;
	}
	
	public CCD[] getWorldRegions(String key)
	{
		WorldSet world = WorldTable.get(key);
		
		CCD[] regions = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("WRS_"+key);
			if (obj instanceof CCD[]) {
				regions = (CCD[])obj;
			}
		}
		
		if (regions==null) {
			regions = createRegionsFromSet(world.Regions);
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("WRS_"+key, regions);
		}
		
		return regions;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------------

	
	public WorldSet getSetWorld(String key) {
		return WorldTable.get(key);
	}

	public SpriteSet getSetSprite(String key) {
		return SprTable.get(key);
	}

	public MapSet getSetMap(String key) {
		return MapTable.get(key);
	}

	public ImagesSet getSetImages(String key) {
		return ImgTable.get(key);
	}
	
	public<T extends CellSetObject> T getSetObject(Class<T> cls, String key)
	{
		CellSetObject ret = null;
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
		if (ret!=null) {
			return cls.cast(ret);
		}
		return null;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------------

	public void initAllResource(SetLoading progress)
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
	
	public void destoryAllResource(){
		if (ResourceManager!=null) {
			ResourceManager.clear();
		}
	}

	
	public boolean isStreamingResource() {
		if (loading_service!=null) {
			return (loading_service.getActiveCount()!=0);
		} else {
			return false;
		}
	}
	

	protected byte[] loadRes(String path)
	{
		byte[] data = CIO.loadData(PathDir+path);
		if (data == null) {
			System.err.println("SetResource : read error : " + path);
		} 
		return data;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * input "{1234},{5678}"
	 * return [1234][5678]
	 */
	protected static String[] getArray2D(String text)
	{
		text = text.replace('{', ' ');
		String[] texts = CUtil.splitString(text, "},");
		for (int i=texts.length-1; i>=0; --i) {
			texts[i] = texts[i].trim();
		}
		return texts;
	}
	
	/**
	 * input 3,123,4,5678
	 * return [123] [5678]
	 * @param text
	 * @return
	 */
	static String[] getArray1D(String text)
	{
		StringReader reader = new StringReader(text);
		ArrayList<String> list = new ArrayList<String>();
		try{
			while(true){
				String line = TextDeserialize.getString(reader);
				list.add(line);
			}
		}catch (Exception e) {}
		return list.toArray(new String[list.size()]);
	}
	
	
//	--------------------------------------------------------------------------------------------------------------------------------------------------

	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static interface CellSetObject extends Serializable
	{
		public int 		getIndex();
		
		public String 	getName();
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	Images_<IMAGES INDEX>		=<IMAGES INDEX>,<NAME>,<COUNT>
//	Images_<IMAGES INDEX>_tiles	=#<CLIP>{<INDEX>,<X>,<Y>,<W>,<H>,<DATA>,},#<END CLIP>
	public static class ImagesSet implements CellSetObject
	{
		private static final long serialVersionUID = Version.VersionG2D;

		final public int		Index;
		final public String		Name;
		final public int		Count;
		
		final public int ClipsX[];
		final public int ClipsY[];
		final public int ClipsW[];
		final public int ClipsH[];
		final public String ClipsKey[];
		
		public ImagesSet(String images, String tiles)
		{
			String[]	args0 = CUtil.splitString(images, ",");
			Index 		= Integer.parseInt(args0[0]);
			Name	 	= args0[1];
			Count		= Integer.parseInt(args0[2]);
			
			String[]	clips = getArray2D(tiles);
			ClipsX		= new int[Count];
			ClipsY		= new int[Count];
			ClipsW		= new int[Count];
			ClipsH		= new int[Count];
			ClipsKey	= new String[Count];
			for (int i=0; i<Count; i++)
			{
				String[] clip = CUtil.splitString(clips[i], ",");
				ClipsX[i]	= Integer.parseInt(clip[1]);
				ClipsY[i]	= Integer.parseInt(clip[2]);
				ClipsW[i]	= Integer.parseInt(clip[3]);
				ClipsH[i]	= Integer.parseInt(clip[4]);
				ClipsKey[i]	= clip[5];
			}
		}
		
		public int getIndex() {
			return Index;
		}
		
		public String getName() {
			return Name;
		}
	}
	
	
//	Sprite_<SPR INDEX>					=<SPR INDEX>,<NAME>,<IMAGES NAME>,<SCENE PART COUNT>,<SCENE FRAME COUNT>,<CD PART COUNT>,<CD FRAME COUNT>,<ANIMATE COUNT>
//	Sprite_<SPR INDEX>_parts			=#<SCENE PART>{<INDEX>,<TILE>,<X>,<Y>,<TRANS>},#<END SCENE PART>
//	Sprite_<SPR INDEX>_frames			=#<SCENE FRAME>{<INDEX>,<DATA SIZE>,<DATA>},#<END SCENE FRAME>
//	Sprite_<SPR INDEX>_cds				=#<CD PART>{<INDEX>,<TYPE>,<MASK>,<X1>,<Y1>,<X2>,<Y2>,<W>,<H>},#<END CD PART>
//	Sprite_<SPR INDEX>_cd_frames		=#<CD FRAME>{<INDEX>,<DATA SIZE>,<DATA>},#<END CD FRAME>
//	Sprite_<SPR INDEX>_frame_counts		=<FRAME COUNTS>
//	Sprite_<SPR INDEX>_frame_name		=<FRAME NAME>
//	Sprite_<SPR INDEX>_frame_animate	=<FRAME ANIMATE>
//	Sprite_<SPR INDEX>_frame_cd_map		=<FRAME CD MAP>
//	Sprite_<SPR INDEX>_frame_cd_atk		=<FRAME CD ATK>
//	Sprite_<SPR INDEX>_frame_cd_def		=<FRAME CD DEF>
//	Sprite_<SPR INDEX>_frame_cd_ext		=<FRAME CD EXT>
	public 	static class SpriteSet implements CellSetObject
	{
		private static final long serialVersionUID = Version.VersionG2D;

		final public int 		Index;
		final public String 	Name;
		final public String 	ImagesName;
		
		final public short[]	PartX;
		final public short[]	PartY;
		final public short[]	PartTileID;
		final public byte[]		PartTileTrans;
		final public short[][]	Parts;
		
		final public int[]		BlocksMask;
		final public short[]	BlocksX1;
		final public short[]	BlocksY1;
		final public short[]	BlocksW;
		final public short[]	BlocksH;
		final public short[][]	Blocks;
		
		final public int		AnimateCount;
		final public String[]	AnimateNames;
		final public short[][]	FrameAnimate ;
		final public short[][]	FrameCDMap ;
		final public short[][]	FrameCDAtk ;
		final public short[][]	FrameCDDef ;
		final public short[][]	FrameCDExt ;
		
		public SpriteSet(String spr,
				String _parts, 
				String _frames,
				String _cds, 
				String _cd_frames, 
				String _frame_counts, 
				String _frame_name,
				String _frame_animate,
				String _frame_cd_map,
				String _frame_cd_atk,
				String _frame_cd_def,
				String _frame_cd_ext
				) throws IOException
		{
			String[]	args = CUtil.splitString(spr, ",");
			
			Index 		= Integer.parseInt(args[0]);
			Name		= args[1];
			ImagesName	= args[2];
			
			int scenePartCount 	= Integer.parseInt(args[3]);
			int sceneFrameCount = Integer.parseInt(args[4]);
			int cdCount 		= Integer.parseInt(args[5]);
			int collidesCount 	= Integer.parseInt(args[6]);
			int animateCount 	= Integer.parseInt(args[7]);
			
//			--------------------------------------------------------------------------------------------------------------
//			scene parts
			String parts[] = getArray2D(_parts);
			PartX = new short[scenePartCount];
			PartY = new short[scenePartCount];
			PartTileID = new short[scenePartCount];
			PartTileTrans = new byte[scenePartCount];
		    for(int i=0;i<scenePartCount;i++){
		    	String[] tile 	= CUtil.splitString(parts[i], ",");
		    	PartTileID[i] 	=  Short.parseShort(tile[1]);
		    	PartX[i] 		=  Short.parseShort(tile[2]);
		    	PartY[i] 		=  Short.parseShort(tile[3]);
		    	PartTileTrans[i] =  Byte.parseByte(tile[4]);
		    }
//			--------------------------------------------------------------------------------------------------------------
//			scene frames
			String frames[] = getArray2D(_frames);
			Parts = new short[sceneFrameCount][];
			for (int i = 0; i < sceneFrameCount; i++) {
				String[] frame 	= frames[i].split(",", 3);
				int frameCount 	= Integer.parseInt(frame[1]);
				Parts[i] = new short[frameCount];
				if (frameCount>0) {
					String[] data 	= CUtil.splitString(frame[2], ",");
					for (int f = 0; f < frameCount; f++) {
						Parts[i][f] = Short.parseShort(data[f]);
					}
				}
			}
//			--------------------------------------------------------------------------------------------------------------
//			cd parts
			String cds[] = getArray2D(_cds);
			BlocksMask = new int[cdCount];
			BlocksX1 = new short[cdCount];
			BlocksY1 = new short[cdCount];
			BlocksW = new short[cdCount];
			BlocksH = new short[cdCount];
			for(int i=0;i<cdCount;i++){
				String[] cd = CUtil.splitString(cds[i], ",");
				BlocksMask[i]	= Integer.parseInt(cd[2]);
				BlocksX1[i] 	= Short.parseShort(cd[3]);
				BlocksY1[i] 	= Short.parseShort(cd[4]);
				BlocksW[i] 		= Short.parseShort(cd[7]);
				BlocksH[i] 		= Short.parseShort(cd[8]);
			}
//			--------------------------------------------------------------------------------------------------------------
//			cd frames
			String cd_frames[] = getArray2D(_cd_frames);
			Blocks = new short[collidesCount][];
			for (int i = 0; i < collidesCount; i++) {
				String[] frame 	= cd_frames[i].split(",", 3);
				int frameCount = Integer.parseInt(frame[1]);
				Blocks[i] = new short[frameCount];
				if (frameCount>0) {
					String[] data = CUtil.splitString(frame[2], ",");
					for (int f = 0; f < frameCount; f++) {
						Blocks[i][f] = Short.parseShort(data[f]);
					}
				}
			}
//			--------------------------------------------------------------------------------------------------------------
//			animates
			AnimateCount = animateCount;
			AnimateNames = new String[animateCount];
			StringReader AnimateNamesReader = new StringReader(_frame_name);
			for (int i = 0; i < animateCount; i++){
				AnimateNames[i] = TextDeserialize.getBytesString(AnimateNamesReader);
			}
			
			String frame_counts[] = CUtil.splitString(_frame_counts, ",");
			String frame_animate[] = getArray2D(_frame_animate);
			String frame_cd_map[] = getArray2D(_frame_cd_map);
			String frame_cd_atk[] = getArray2D(_frame_cd_atk);
			String frame_cd_def[] = getArray2D(_frame_cd_def);
			String frame_cd_ext[] = getArray2D(_frame_cd_ext);
			FrameAnimate = new short[animateCount][];
			FrameCDMap = new short[animateCount][];
			FrameCDAtk = new short[animateCount][];
			FrameCDDef = new short[animateCount][];
			FrameCDExt = new short[animateCount][];
			for (int i = 0; i < animateCount; i++){
				int frameCount = Integer.parseInt(frame_counts[i]);
				String[] animate	= CUtil.splitString(frame_animate[i], ",");
				String[] cd_map		= CUtil.splitString(frame_cd_map[i], ",");
				String[] cd_atk		= CUtil.splitString(frame_cd_atk[i], ",");
				String[] cd_def		= CUtil.splitString(frame_cd_def[i], ",");
				String[] cd_ext		= CUtil.splitString(frame_cd_ext[i], ",");
				FrameAnimate[i] = new short[frameCount];
				FrameCDMap[i] = new short[frameCount];
				FrameCDAtk[i] = new short[frameCount];
				FrameCDDef[i] = new short[frameCount];
				FrameCDExt[i] = new short[frameCount];
				for (int f = 0; f < frameCount; f++) {
					FrameAnimate[i][f]	= Short.parseShort(animate[f]);
					FrameCDMap[i][f]	=  Short.parseShort(cd_map[f]);
					FrameCDAtk[i][f]	=  Short.parseShort(cd_atk[f]);
					FrameCDDef[i][f]	=  Short.parseShort(cd_def[f]);
					FrameCDExt[i][f]	=  Short.parseShort(cd_ext[f]);
				}
			}
//			--------------------------------------------------------------------------------------------------------------
		}
		
		public int getIndex() {
			return Index;
		}
		
		public String getName() {
			return Name;
		}
		
		//images[PartTileID[Parts[FrameAnimate[anim][frame]][subpart]]];
		
		public int getPartImageIndex(int anim, int frame, int subpart){
			return PartTileID[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public int getPartTrans(int anim, int frame, int subpart){
			return PartTileTrans[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public int getPartX(int anim, int frame, int subpart){
			return PartX[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public int getPartY(int anim, int frame, int subpart){
			return PartY[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
	}
	
	
	
//	Map_<MAP INDEX>             =<MAP INDEX>,<NAME>,<IMAGES NAME>,<X COUNT>,<Y COUNT>,<CELL W>,<CELL H>,<SCENE PART COUNT>,<SCENE FRAME COUNT>,<CD PART COUNT>
//	Map_<MAP INDEX>_parts		=#<SCENE PART>{<INDEX>,<TILE>,<TRANS>},#<END SCENE PART>
//	Map_<MAP INDEX>_frames		=#<SCENE FRAME>{<INDEX>,<DATA SIZE>,<DATA>},#<END SCENE FRAME>
//	Map_<MAP INDEX>_cds			=#<CD PART>{<INDEX>,<TYPE>,<MASK>,<X1>,<Y1>,<X2>,<Y2>,<W>,<H>},#<END CD PART>
//	Map_<MAP INDEX>_tile_matrix	=<TILE MATRIX>
//	Map_<MAP INDEX>_cd_matrix	=<FLAG MATRIX>
	public 	static class MapSet implements CellSetObject
	{
		private static final long serialVersionUID = Version.VersionG2D;

		final public int 		Index;
		final public String 	Name;
		final public String 	ImagesName;
		
		
		final public int 		XCount;
		final public int 		YCount;
		final public int		CellW;
		final public int		CellH;
		
		/** TImages.Clips*[index] */
		final public int[] 		TileID;
		final public int[] 		TileTrans;
		/** TileID[index] & TileTrans[index] */
		final public int[][] 	Animates;
		/** TerrainScene2D[y][x] == Animates[index] */
		final public int[][] 	TerrainScene2D;
		
		
		final public int[] 		BlocksType;
		final public int[] 		BlocksMask;
		final public int[] 		BlocksX1;
		final public int[] 		BlocksY1;
		final public int[] 		BlocksX2;
		final public int[] 		BlocksY2;
		final public int[] 		BlocksW;
		final public int[] 		BlocksH;
		
		/** TerrainBlock2D[y][x] == BlocksType[index] */
		final public int[][] 	TerrainBlock2D;
		
		public MapSet(String map, String _parts, String _frames, String _cds, String _tile_matrix, String _cd_matrix)
		{
			String[] args = CUtil.splitString(map, ",");
			
			Index 		= Integer.parseInt(args[0]);
			Name		= args[1];
			ImagesName	= args[2];
			
			XCount 		= Integer.parseInt(args[3]);
			YCount 		= Integer.parseInt(args[4]);
			CellW 		= Integer.parseInt(args[5]);
			CellH 		= Integer.parseInt(args[6]);

			int scenePartCount 	= Integer.parseInt(args[7]);
			int animateCount 	= Integer.parseInt(args[8]);
			int cdCount 		= Integer.parseInt(args[9]);
			
//			--------------------------------------------------------------------------------------------------------------
//			parts
			String parts[] = getArray2D(_parts);
			TileID		= new int[scenePartCount];
			TileTrans 	= new int[scenePartCount];
			for (int i = 0; i < scenePartCount; i++) {
				String[] tile 	= CUtil.splitString(parts[i], ",");
		    	TileID[i] 		= Integer.parseInt(tile[1]);
		    	TileTrans[i] 	= Integer.parseInt(tile[2]);
		    }
//			--------------------------------------------------------------------------------------------------------------
//			frames
			String frames[] = getArray2D(_frames);
			Animates = new int[animateCount][];
			for (int i = 0; i < animateCount; i++) {
				String[] frame 	= frames[i].split(",", 3);
				int frameCount 	= Integer.parseInt(frame[1]);
				Animates[i] = new int[frameCount];
				if (frameCount>0) {
					String[] data 	= CUtil.splitString(frame[2], ",");
					for (int f = 0; f < frameCount; f++) {
						Animates[i][f] = Integer.parseInt(data[f]);
					}
				}
			}
//			--------------------------------------------------------------------------------------------------------------
//			cds
			String cds[] = getArray2D(_cds);
			BlocksType = new int[cdCount];
			BlocksMask = new int[cdCount];
			BlocksX1 = new int[cdCount];
			BlocksY1 = new int[cdCount];
			BlocksX2 = new int[cdCount];
			BlocksY2 = new int[cdCount];
			BlocksW = new int[cdCount];
			BlocksH = new int[cdCount];
			for(int i=0;i<cdCount;i++){
				String[] cd = CUtil.splitString(cds[i], ",");
				BlocksType[i] 	= "rect".equals(cd[1]) ? CCD.CD_TYPE_RECT : CCD.CD_TYPE_LINE;
				BlocksMask[i] 	= Integer.parseInt(cd[2]);
				BlocksX1[i] 	= Integer.parseInt(cd[3]);
				BlocksY1[i] 	= Integer.parseInt(cd[4]);
				BlocksX2[i] 	= Integer.parseInt(cd[5]);
				BlocksY2[i] 	= Integer.parseInt(cd[6]);
				BlocksW[i] 		= Integer.parseInt(cd[7]);
				BlocksH[i] 		= Integer.parseInt(cd[8]);
			}
//			--------------------------------------------------------------------------------------------------------------
//			tile matrix
			String tile_matrix[] = getArray2D(_tile_matrix);
			TerrainScene2D = new int[YCount][XCount];
			for (int y = 0; y < YCount; y++) {
				String[] hline = CUtil.splitString(tile_matrix[y], ",");
				for (int x = 0; x < XCount; x++) {
					TerrainScene2D[y][x] = Integer.parseInt(hline[x]);
				}
			}
//			--------------------------------------------------------------------------------------------------------------
//			cd matrix
			String cd_matrix[] = getArray2D(_cd_matrix);
			TerrainBlock2D = new int[YCount][XCount];
			for (int y = 0; y < YCount; y++) {
				String[] hline = CUtil.splitString(cd_matrix[y], ",");
				for (int x = 0; x < XCount; x++) {
					TerrainBlock2D[y][x] = Integer.parseInt(hline[x]);
				}
			}
//			--------------------------------------------------------------------------------------------------------------
	
	    
		}
		
		public int getIndex() {
			return Index;
		}
		
		public String getName() {
			return Name;
		}

		
		public int getLayerImagesIndex(int x, int y, int layer){
			return TileID[Animates[TerrainScene2D[y][x]][layer]];
		}
		
		public int getLayerTrans(int x, int y, int layer){
			return TileTrans[Animates[TerrainScene2D[y][x]][layer]];
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
//	--------------------------------------------------------------------------------------------------------------
	
	
//	World_<WORLD INDEX>					=<WORLD INDEX>,<NAME>,<GRID X COUNT>,<GRID Y COUNT>,<GRID W>,<GRID H>,<WIDTH>,<HEIGHT>,<UNIT MAP COUNT>,<UNIT SPRITE COUNT>,<WAYPOINT COUNT>,<REGION COUNT>
//	World_<WORLD INDEX>_maps			=#<UNIT MAP>{<INDEX>,<MAP NAME>,<IDENTIFY>,<X>,<Y>,<SUPER>,<MAP DATA>},#<END UNIT MAP>
//	World_<WORLD INDEX>_sprs			=#<UNIT SPRITE>{<INDEX>,<SPR NAME>,<IDENTIFY>,<ANIMATE ID>,<FRAME ID>,<X>,<Y>,<SUPER>,<SPR DATA>},#<END UNIT SPRITE>
//	World_<WORLD INDEX>_waypoints		=#<WAYPOINT>{<INDEX>,<X>,<Y>,<PATH DATA>},#<END WAYPOINT>
//	World_<WORLD INDEX>_waypoint_link	=#<WAYPOINT LINK>{<START>,<END>},#<END WAYPOINT LINK>
//	World_<WORLD INDEX>_regions			=#<REGION>{<INDEX>,<X>,<Y>,<W>,<H>,<REGION DATA>},#<END REGION>
//	World_<WORLD INDEX>_data			=<DATA>
//	World_<WORLD INDEX>_terrain			=<TERRAIN>
	public 	static class WorldSet implements CellSetObject
	{
		private static final long serialVersionUID = Version.VersionG2D;

		final public int		Index;
		final public String		Name;
		final public int		GridXCount;
		final public int		GridYCount;
		final public int		GridW;
		final public int		GridH;
		final public int		Width;
		final public int		Height;
		
		final public Vector<SpriteObject> 		Sprs 			= new Vector<SpriteObject>();
		final public Vector<MapObject> 		Maps 			= new Vector<MapObject>();
		final public Vector<WaypointObject> 	WayPoints		= new Vector<WaypointObject>();
		final public Vector<RegionObject> 	Regions			= new Vector<RegionObject>();
		
		final public String		Data;
		final public int[][]	Terrian;
		
		public WorldSet(String world, 
				String _maps,
				String _sprs, 
				String _waypoints, 
				String _waypoint_link, 
				String _regions, 
				String _data, 
				String _terrain) throws IOException
		{
			String[] args = CUtil.splitString(world, ",");

			Index	= Integer.parseInt(args[0]);
			Name	= args[1];
			
			GridXCount	= Integer.parseInt(args[2]);
			GridYCount	= Integer.parseInt(args[3]);
			GridW		= Integer.parseInt(args[4]);
			GridH		= Integer.parseInt(args[5]);
			Width		= Integer.parseInt(args[6]);
			Height		= Integer.parseInt(args[7]);

			//------------------------------------------------------------------------
			// units
			int maps_count	= Integer.parseInt(args[8]);
			int sprs_count	= Integer.parseInt(args[9]);
			int wpss_count	= Integer.parseInt(args[10]);
			int wrss_count	= Integer.parseInt(args[11]);
			
			String[] maps	= getArray2D(_maps);
			String[] sprs	= getArray2D(_sprs);
			String[] wpss	= getArray2D(_waypoints);
			String[] wpsl	= getArray2D(_waypoint_link);
			String[] wrss	= getArray2D(_regions);
			
			for (int i=0; i<maps_count; i++){
				Maps.add(new WorldSet.MapObject(maps[i]));
			}
			for (int i=0; i<sprs_count; i++){
				Sprs.add(new WorldSet.SpriteObject(sprs[i]));
			}
			for (int i=0; i<wpss_count; i++) {
				WayPoints.add(new WorldSet.WaypointObject(wpss[i]));
			}
			for (int i=0; i<wrss_count; i++) {
				Regions.add(new WorldSet.RegionObject(wrss[i]));
			}
			
			for (int i = 0; i < wpsl.length; i++) {
				String[] link = CUtil.splitString(wpsl[i], ",");
				if (link.length>=2){
					int start	= Integer.parseInt(link[0]);
					int end		= Integer.parseInt(link[1]);
					WayPoints.get(start).Nexts.add(WayPoints.get(end));
				}
			}
			
			//------------------------------------------------------------------------
			// data
			Data		= _data;
			
			int terrains_count = GridXCount * GridYCount;
			Terrian		= new int[GridXCount][GridYCount];
			try {
				String terrains[] = CUtil.splitString(_terrain, ",");
				for (int i = 0; i < terrains_count; i++) {
					int x = i / GridYCount;
					int y = i % GridYCount;
					Terrian[x][y] = Integer.parseInt(terrains[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public int getIndex() {
			return Index;
		}
		
		public String getName() {
			return Name;
		}
		
		
		public int getTerrainCell(int grid_x, int grid_y)
		{
			return Terrian[grid_x][grid_y];
		}
		
		
		

		public 	static class MapObject implements Serializable
		{
			private static final long serialVersionUID = Version.VersionG2D;
			
			final public 	int 		Index;
			final public 	String 		UnitName;
			final public 	String 		MapID;
			final public 	String 		ImagesID;
			final public 	int 		X;
			final public 	int 		Y;
			final public	String		Data;
			
			// <INDEX>,<MAP NAME>,<IDENTIFY>,<X>,<Y>,<SUPER>,<MAP DATA>
			public MapObject(String segment) throws IOException
			{
				String[] args = segment.split(",", 7);
				
				Index 		= Integer.parseInt(args[0]);
				UnitName 	= args[1];
				MapID 		= args[2];
				X 			= Integer.parseInt(args[3]);
				Y 			= Integer.parseInt(args[4]);
				ImagesID 	= args[5];
				Data		= args[6];
			}
		}
		
		public 	static class SpriteObject implements Serializable
		{
			private static final long serialVersionUID = Version.VersionG2D;
			
			final public 	int 		Index;
			final public 	String 		UnitName;
			final public 	String 		SprID;
			final public 	String 		ImagesID;
			final public 	int 		Anim;
			final public 	int 		Frame;
			final public 	int 		X;
			final public 	int 		Y;
			final public	String		Data;
			
			// <INDEX>,<SPR NAME>,<IDENTIFY>,<ANIMATE ID>,<FRAME ID>,<X>,<Y>,<SUPER>,<SPR DATA>
			public SpriteObject(String segment) throws IOException
			{
				String[] args = segment.split(",", 9);
				
				Index 		= Integer.parseInt(args[0]);
				UnitName 	= args[1];
				SprID 		= args[2];
				Anim		= Integer.parseInt(args[3]);
				Frame		= Integer.parseInt(args[4]);
				X 			= Integer.parseInt(args[5]);
				Y 			= Integer.parseInt(args[6]);
				ImagesID 	= args[7];
				Data		= args[8];
			}
		}
		
		public static class WaypointObject implements Serializable
		{
			private static final long serialVersionUID = Version.VersionG2D;
			
			final public 	int 		Index;
			final public	int			X;
			final public	int			Y;
			final public	String		Data;
			
			final public ArrayList<WaypointObject> Nexts = new ArrayList<WaypointObject>();
		
			// <INDEX>,<X>,<Y>,<PATH DATA>
			public WaypointObject(String segment) throws IOException
			{
				String[] args = segment.split(",", 4);
				
				Index 		= Integer.parseInt(args[0]);
				X 			= Integer.parseInt(args[1]);
				Y 			= Integer.parseInt(args[2]);
				Data		= args[3];
			}
			
		}
		
		
		public static class RegionObject implements Serializable
		{
			private static final long serialVersionUID = Version.VersionG2D;
			
			final public 	int 		Index;
			final public	int			X;
			final public	int			Y;
			final public	int			W;
			final public	int			H;
			final public	String		Data;
			
			//<INDEX>,<X>,<Y>,<W>,<H>,<REGION DATA>
			public RegionObject(String segment) throws IOException
			{
				String[] args = segment.split(",", 6);
				
				Index 		= Integer.parseInt(args[0]);
				X 			= Integer.parseInt(args[1]);
				Y 			= Integer.parseInt(args[2]);
				W 			= Integer.parseInt(args[3]);
				H 			= Integer.parseInt(args[4]);
				Data		= args[5];
			}
			
		}
		
	}

//	-------------------------------------------------------------------------------------
	

	public static IImages createImagesFromSet(ImagesSet img, IImage image, IImages stuff)
	{
		try{
			if (img != null)
			{
				int count = img.Count;
				stuff.buildImages(image,count);
				for(int i=0;i<count;i++){
					int x = img.ClipsX[i];	// --> s16 * count
					int y = img.ClipsY[i];	// --> s16 * count
					int w = img.ClipsW[i];	// --> s16 * count
					int h = img.ClipsH[i];	// --> s16 * count
					stuff.addTile(x,y,w,h);
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		
		if(stuff!=null){
//			System.out.println("SetInput : Load TilesSet ^_^!");
		}else{
			System.err.println("SetInput : Load TilesSet -_-!");
		}
		
		return stuff;
	}
	
//	########################################################################################################################

	public static CMap createMapFromSet(MapSet tmap, IImages tiles, boolean isAnimate, boolean isCyc)
	{

		CMap ret = null;
		
		try{
			
			if(tmap!=null)
			{
//				--------------------------------------------------------------------------------------------------------------
//				property
				int xcount = tmap.XCount;	// --> u16
				int ycount = tmap.YCount;	// --> u16
				int cellw = tmap.CellW;		// --> u16
				int cellh = tmap.CellH;		// --> u16
//				--------------------------------------------------------------------------------------------------------------
//				parts
				int scenePartCount = tmap.TileID.length;	// --> u16
			    CAnimates animates = new CAnimates(scenePartCount,tiles);
			    for(int i=0;i<scenePartCount;i++){
			    	int tileID = tmap.TileID[i];	// --> s16 * count
			    	int trans = tmap.TileTrans[i];	// --> s8  * count
			    	animates.addPart(0,0,tileID,trans);
			    }
//				--------------------------------------------------------------------------------------------------------------
//				frames
				int animateCount = tmap.Animates.length;		// --> u16
				short[][] animates_frame = new short[animateCount][];
				for(int i=0;i<animateCount;i++){
					int frameCount =  tmap.Animates[i].length;	// --> u16 * count
					animates_frame[i] = new short[frameCount];
					for(int f=0;f<frameCount;f++){
						animates_frame[i][f] = (short)tmap.Animates[i][f];// --> s16 * length * count
					}
				}
				animates.setFrames(animates_frame);
//				--------------------------------------------------------------------------------------------------------------
//				tile matrix
				short[][] tileMatrix = new short[ycount][xcount];
				for(int y=0; y<ycount; y++){
					for(int x=0; x<xcount; x++){
						tileMatrix[y][x] = (short)tmap.TerrainScene2D[y][x];// --> s16 * xcount * ycount
					}
				}
//				--------------------------------------------------------------------------------------------------------------
//				cds
				int cdCount = tmap.BlocksType.length;	// --> u16
				CCollides collides = new CCollides(cdCount);
				for(int i=0;i<cdCount;i++){
					byte type = (byte)tmap.BlocksType[i];	// --> s8 * count
					int mask = tmap.BlocksMask[i];		// --> s32 * count
					int x1 = tmap.BlocksX1[i];		// --> s16 * count
					int y1 = tmap.BlocksY1[i];		// --> s16 * count
					int x2 = tmap.BlocksX2[i];		// --> s16 * count
					int y2 = tmap.BlocksY2[i];		// --> s16 * count
					int w = tmap.BlocksW[i];		// --> s16 * count
					int h = tmap.BlocksH[i];		// --> s16 * count
					if(type==0) collides.addCDRect(mask, x1, y1, w, h);
					if(type==1) collides.addCDLine(mask, x1, y1,x2,y2);
				}
//				--------------------------------------------------------------------------------------------------------------
//				cd matrix
				short[][] flagMatrix = new short[ycount][xcount];
				for(int y=0; y<ycount; y++){
					for(int x=0; x<xcount; x++){
						flagMatrix[y][x] = (short)tmap.TerrainBlock2D[y][x];// --> s16 * xcount * ycount
					}
				}
//				--------------------------------------------------------------------------------------------------------------
				ret = new CMap(
						animates, 
						collides, 
						cellw, cellh, 
						tileMatrix, 
						flagMatrix, 
						isCyc 
						);
				ret.IsAnimate = isAnimate;
			}

		}catch(Exception err){
			err.printStackTrace();
		}
		
		if(ret!=null){
//			System.out.println("SetInput : Load Map ^_^!");
		}else{
			System.err.println("SetInput : Load Map -_-!");
		}
	    
	    return ret;
	}
	
//	########################################################################################################################
	
	public static CSprite createSpriteFromSet(SpriteSet tsprite, IImages tiles){
		
		CSprite ret = null;
			 
		try{

			if(tsprite!=null)
			{
//				--------------------------------------------------------------------------------------------------------------
//				scene parts
				int scenePartCount = tsprite.PartTileID.length;	// --> u16
			    CAnimates animates = new CAnimates(scenePartCount,tiles);
			    for(int i=0;i<scenePartCount;i++){
			    	animates.addPart(
			    			tsprite.PartX[i], 
			    			tsprite.PartY[i],
							tsprite.PartTileID[i], 
							tsprite.PartTileTrans[i]);
			    }
//				--------------------------------------------------------------------------------------------------------------
//				scene frames
				animates.setFrames(tsprite.Parts);
//				--------------------------------------------------------------------------------------------------------------
//				cd parts
				int cdCount = tsprite.BlocksMask.length;	// --> u16
				CCollides collides = new CCollides(cdCount);
				for(int i=0;i<cdCount;i++){
					collides.addCDRect(
							tsprite.BlocksMask[i],
							tsprite.BlocksX1[i], 
							tsprite.BlocksY1[i],
							tsprite.BlocksW[i], 
							tsprite.BlocksH[i]);
				}
//				--------------------------------------------------------------------------------------------------------------
//				cd frames
				collides.setFrames(tsprite.Blocks);
//				--------------------------------------------------------------------------------------------------------------
//				animates
				short[][] frameAnimate = tsprite.FrameAnimate;
				short[][] frameCDMap = tsprite.FrameCDMap;
				short[][] frameCDAtk = tsprite.FrameCDAtk;
				short[][] frameCDDef = tsprite.FrameCDDef;
				short[][] frameCDExt = tsprite.FrameCDExt;
//				--------------------------------------------------------------------------------------------------------------
				ret = new CSprite(
			            animates, 
			            collides, 
			            tsprite.AnimateNames,
			            frameAnimate, 
			            frameCDMap, 
			            frameCDAtk, 
			            frameCDDef, 
			            frameCDExt 
			            );
			}

		}catch(Exception err){
			err.printStackTrace();
		}

		if(ret!=null){
//			System.out.println("SetInput : Load Spr ^_^!");
		}else{
			System.err.println("SetInput : Load Spr -_-!");
		}
		
	    return ret;
	}
	
//	########################################################################################################################

	public static CWayPoint[] createWayPointsFromSet(Vector<WaypointObject> waypoints)
	{
		CWayPoint wayPoints[] = new CWayPoint[waypoints.size()];
		for (int i = waypoints.size() - 1; i >= 0; --i) {
			WaypointObject src = waypoints.get(i);
			CWayPoint wp = new CWayPoint(src.X, src.Y);
			wp.SetData = getArray1D(src.Data);
			wayPoints[i] = wp;
		}
		for (int i = waypoints.size() - 1; i >= 0; --i) {
			WaypointObject src = waypoints.get(i);
			CWayPoint wp = wayPoints[i];
			for (int j = src.Nexts.size() - 1; j >= 0; --j) {
				WaypointObject next = src.Nexts.get(i);
				wp.link(wayPoints[next.Index]);
			}
		}
		
		return wayPoints;
	}
	
	
	public static CCD[] createRegionsFromSet(Vector<RegionObject> regions)
	{
		CCD cds[] = new CCD[regions.size()];
		for (int i = regions.size() - 1; i >= 0; --i) {
			RegionObject src = regions.get(i);
			CCD cd = CCD.createCDRect(0, src.X, src.Y, src.W, src.H);
			cd.SetData = getArray1D(src.Data);
			cds[i] = cd;
		}
		return cds;
	}

	
	
	
//	-------------------------------------------------------------------------------------
	
	// 对set里所有文件进行缓冲
	public static interface SetLoading
	{
		public void progress(CellSetResource set, IImages images, int progress, int maxcount);
		public void progress(CellSetResource set, CSprite spr, int progress, int maxcount);
		public void progress(CellSetResource set, CWayPoint[] points, int progress, int maxcount);
		public void progress(CellSetResource set, CCD[] regions, int progress, int maxcount);
	}


//	-------------------------------------------------------------------------------------

	public static interface LoadSpriteListener
	{
		public void loaded(CellSetResource set, CSprite cspr, SpriteSet spr);
	}
	
	protected class LoadSpriteTask implements Runnable
	{
		final LoadSpriteListener listener;
		
		final SpriteSet spr;
		
		public LoadSpriteTask(SpriteSet spr, LoadSpriteListener listener) {
			this.spr		= spr;
			this.listener 	= listener;
		}
		
		public void run() {
			synchronized (listener) {
				try {
//					System.out.println("start load spr : " + spr.SprID);
					CSprite cspr = getSprite(spr);
					listener.loaded(CellSetResource.this, cspr, spr);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
	
//	-------------------------------------------------------------------------------------
	
	/**
	 * @author WAZA
	 * 支持网络缓冲的图片组
	 */
	protected class StreamTiles implements IImages, Runnable
	{
		final public ImagesSet		img;
		final public IImage[]		images;

		private boolean 			loaded			= false;
		private int					render_timer;
		
		public StreamTiles(ImagesSet img) throws IOException {
			this.images = new CImage[img.Count];
			this.img = img;
		}
		
		protected void initImages()
		{
			byte[] idata = loadRes(img.Name+".png");
			CImage src = new CImage(new ByteArrayInputStream(idata));
			for (int i=0; i<images.length; i++){
				if (img.ClipsW[i]>0 && img.ClipsH[i]>0){
					images[i] = src.subImage(img.ClipsX[i], img.ClipsY[i], img.ClipsW[i], img.ClipsH[i]);
				}
			}
		}
		
		public void run() 
		{
			try 
			{
				synchronized (this) 
				{
					if(!loaded)
					{
						initImages();
						loaded = true;
					}
				}
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		public void unloadAllImages() {
			for (int i=0; i<images.length; i++){
				images[i] = null;
			}
			loaded = false;
			System.gc();
		}
		
		public IImage getImage(int index){
			return images[index];
		}
		
		public int getWidth(int Index) {
			return img.ClipsW[Index];
		}

		public int getHeight(int Index) {
			return img.ClipsH[Index];
		}

		public int getCount(){
			return images.length;
		}
		
		public void render(IGraphics g, int Index, int PosX, int PosY) {
			if (images[Index] != null) {
				g.drawImage(images[Index], PosX, PosY, 0);
			}else{
				drawLoading(g, PosX, PosY, img.ClipsW[Index], img.ClipsH[Index]);
			}
		}

		public void render(IGraphics g, int Index, int PosX, int PosY, int Style) {
			if (images[Index] != null) {
				g.drawImage(images[Index], PosX, PosY, Style);
			}else{
				drawLoading(g, PosX, PosY, img.ClipsW[Index], img.ClipsH[Index]);
			}
		}

		protected void drawLoading(IGraphics g, int x, int y, int w, int h) 
		{
			if (g instanceof CGraphics) 
			{
				Graphics2D cg = ((CGraphics)g).getGraphics();
				
				float d = Math.abs((float)Math.sin(render_timer++/20d));
				
				cg.setColor(new Color(1,1,1,d));
				cg.drawRect(x, y, w, h);
				Drawing.drawStringShadow(cg, "loading...", x, y, w, h, 
						Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER);
				
				
			}else{
				g.setColor(0xff808080);
				g.fillRect(x, y, w, h);
				g.setColor(0xffffffff);
				g.drawString("loading...", x, y);
			}
		}
		
		public int getPixel(int index, int x, int y){
			if (images[index] != null) {
				int[] rgb = new int[1];
				images[index].getRGB(rgb, 0, 1, x, y, 1, 1);
				return rgb[0];
			}
			return 0;
		}
		
		public boolean	addTile() {return false;}
		public boolean	addTile(int TileX, int TileY, int TileWidth, int TileHeight) {return false;}
		public void		addTile(int ClipX, int ClipY, int ClipWidth, int ClipHeight, int TileWidth, int TileHeight) {}
		
		public void buildImages(IImage srcImage, int count) {}
		public int setMode(int mode){return 0;}
		
		
		
	}
	
	
	
}

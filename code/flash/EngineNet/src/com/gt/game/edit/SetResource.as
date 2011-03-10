package com.gt.game.edit
{
	import com.gt.game.CImage;
	import com.gt.game.CImages;
	import com.gt.game.CMap;
	import com.gt.game.CSprite;
	import com.gt.game.CWorld;
	import com.gt.game.edit.textset.IMG;
	import com.gt.game.edit.textset.SPR;
	import com.gt.game.edit.textset.WORLD;
	import com.gt.util.PropertyGroup;
	
	import flash.utils.Dictionary;
	
	
	/*
	 * 
	
	IMG=<NAME>@<IMAGES INDEX>
	MAP=<NAME>@<MAP INDEX>@<IMAGES NAME>
	SPR=<NAME>@<SPR INDEX>@<IMAGES NAME>@anim_name_1$anim_name_2$anim_name_3$
	WORLD=<NAME>@<WORLD INDEX>
	
	WORLD_<NAME>_MAP=<INDEX>@<MAP NAME>@<IDENTIFY>@<SUPER>@<X>@<Y>
	WORLD_<NAME>_SPR=<INDEX>@<SPR NAME>@<IDENTIFY>@<SUPER>@<ANIMATE ID>@<FRAME ID>@<X>@<Y>
	
	 *
	 */
	
	public class SetResource 
	{
		
		public static const TS : String = "@";
		public static const DS : String = "$";
		
	//	-------------------------------------------------------------------------------------

		
	//	-------------------------------------------------------------------------------------
	
		
	
	//	-------------------------------------------------------------------------------------
		
		public var KeyRegionIMG : Array ;
		public var KeyRegionMAP : Array ;
		public var KeyRegionSPR : Array ;
		public var KeyRegionWorld : Array ;
	
		public var Path : String;
	
		private var Config : PropertyGroup;
	
		public var SprTable : Dictionary		= new Dictionary();
		public var ImgTable : Dictionary 		= new Dictionary();
		public var MapTable : Dictionary 		= new Dictionary();
		public var WorldTable : Dictionary		= new Dictionary();
		
		public var FileCount : int = 0;
		public var LoadedFileCount : int;
		
		public var SpriteTypeCount : int;
		public var ImagesTypeCount : int;
		public var MapTypeCount : int;
		public var WorldTypeCount : int;
		
	
		private var CachedValTable : Dictionary = new Dictionary(); 
		
		
		
		public function SetResource(path:String, imgKeyRegion:Array=null, mapKeyRegion:Array=null, sprKeyRegion:Array=null, worldKeyRegion:Array=null)
		{
			KeyRegionIMG = imgKeyRegion;
			KeyRegionMAP = mapKeyRegion;
			KeyRegionSPR = sprKeyRegion;
			KeyRegionWorld = worldKeyRegion;
			
			Path = path;
		
			var conf : String = new String(loadRes("/SetOutput.conf"));
		
		}
		
		private function loaded(conf:String)
		{
			Config = new PropertyGroup(conf,"=");
			
			var imgs : Array = Config.getStringArray("IMG");
			var maps : Array = Config.getStringArray("MAP");
			var sprs : Array = Config.getStringArray("SPR");
			var worlds : Array = Config.getStringArray("WORLD");
			
			
			if (imgs!=null){
				for (var i:int=imgs.size()-1; i>=0; --i){
					var value : Array = imgs.getArray(i).split(TS);
					var img : IMG = new IMG(value, this);
					ImgTable.put(img.ImagesID, img);
					ImgTableIndex.put(img.Index, img);
					FileCount ++ ;// .ts
				}
				ImagesTypeCount = imgs.size();
			}else{
				ImagesTypeCount = 0;
			}
			
			if (maps!=null){
				for (var i:int=maps.size()-1; i>=0; --i){
					var value : Array = maps.get(i).split(TS);
					var map : MAP = new MAP(value, this);
					MapTable.put(map.MapID, map);
					MapTableIndex.put(map.Index, map);
					FileCount ++ ;// .ms
				}
				MapTypeCount = maps.size();
			}else{
				MapTypeCount = 0;
			}
	
			if(sprs!=null){
				for (var i:int=sprs.size()-1; i>=0; --i){
					var value : Array = sprs.get(i).split(TS);
					var spr : SPR = new SPR(value, this);
					SprTable.put(spr.SprID, spr);
					SprTableIndex.put(spr.Index, spr);
					FileCount ++ ;// .ss
				}
				SpriteTypeCount = sprs.size();
			}else{
				SpriteTypeCount = 0;
			}
			
			
			if (worlds!=null){
				for (var i:int=worlds.size()-1; i>=0; --i){
					var value : Array = worlds.get(i).split(TS);
					var world : WORLD = new WORLD(value, Config, this);
					WorldTable.put(world.WorldID, world);
					WorldTableIndex.put(world.Index, world);
					FileCount += 3;// .wps .wrs .ws
				}
				WorldTypeCount = worlds.size();
			}else{
				WorldTypeCount = 0;
			}
		}
		
		//------------------------------------------------------------------------------------
		
		public function getImages(key : String) : CImages
		{
			var img : IMG = ImgTable.get(key);
			var image : CImage = CObject.AppBridge.createImage(new ByteArrayInputStream(loadRes("/set/"+img.File+".png")));
			var stuff : CImages = new CImages();
			//byte[] data = loadRes("/set/"+img.File+".ts");
			//SetInput.createImagesFromSet(data, image, stuff);
			return stuff;
		}	
		
		public function getMap(key : String) : CMap 
		{
			var map : MAP = MapTable.get(key);
			var tiles : CImages = getImages(map.ImagesID);
			//byte[] data = loadRes("/set/"+map.File+".ms");
			//CMap cmap = SetInput.createMapFromSet(data, tiles, isAnimate, isCyc);
			return cmap;
		}
	
		public function getSprite(key : String) : CSprite
		{
			var spr : SPR = SprTable.get(key);
			var tiles : CImages = getImages(spr.ImagesID);
			//byte[] data = loadRes("/set/"+spr.File+".ss");
			//CSprite sprite = SetInput.createSpriteFromSet(data, tiles);
			return sprite;
		}
		
		//
		public function getWorldWayPoints(key : String) : Array
		{
			var world : WORLD = WorldTable.get(key);
			//byte[] data = loadRes("/set/"+world.File+".wps");
			//CWayPoint[] points = SetInput.createWayPointsFromSet(data);
			return points;
		}
		
		public function getWorldRegions(key : String) : Array
		{
			var world : WORLD = WorldTable.get(key);
			//byte[] data = loadRes("/set/"+world.File+".wrs");
			//CCD[] regions = SetInput.createRegionsFromSet(data);
			return regions;
		}
		
		//------------------------------------------------------------------------------------
		
		public function makeWorld(key : String, cworld : CWorld) : void
		{
			var world : WORLD = WorldTable.get(key);
			
			maker.begin(this, cworld, world);
			
			for (var i=world.Maps.size()-1; i>=0; --i){
				var wmap : WORLD.MAP = world.Maps.elementAt(i);
				var map : MAP = MapTable.get(wmap.MapID);
				maker.putMap(this, cworld, wmap, map);
			}
		
			for (var i=world.Sprs.size()-1; i>=0; --i){
				var wspr : WORLD.SPR = world.Sprs.elementAt(i);
				var spr : SPR = SprTable.get(wspr.SprID);
				maker.putSprite(this, cworld, wspr, spr);
			}
			
			maker.end(this, cworld, world);
	
		}
		
	//	----------------------------------------------------------------------------------------------------
		
		/*
		public function loadBuffer() : void
		{
			Enumeration<IMG> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				var ts : IMG = imgs.nextElement();
				loadRes("/set/"+ts.File+".ts");
				loadRes("/set/"+ts.File+".png");
			}
		
			Enumeration<SPR> sprs = SprTable.elements();
			while (sprs.hasMoreElements()) {
				var ss : SPR = sprs.nextElement();
				loadRes("/set/"+ss.File+".ss");
			}
		
			Enumeration<MAP> maps = MapTable.elements();
			while (maps.hasMoreElements()) {
				var ms : MAP = maps.nextElement();
				loadRes("/set/"+ms.File+".ms");
			}
		
			Enumeration<WORLD> worlds = WorldTable.elements();
			while (worlds.hasMoreElements()) {
				var ws : WORLD = worlds.nextElement();
				loadRes("/set/"+ws.File+".wps");
				loadRes("/set/"+ws.File+".wrs");
			}
		
			
		}
		
		
		private byte[] loadRes(String path)
		{
			byte[] data = CIO.loadData(Path+path);
			if (data == null) {
				System.err.println("SetResource : read error : " + path);
			} 
			return data;
		}
		
		*/
		
		
	}

}
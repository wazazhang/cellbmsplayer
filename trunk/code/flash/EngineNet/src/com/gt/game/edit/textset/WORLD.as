package com.gt.game.edit.textset
{
	import com.gt.game.edit.SetResource;
	
	import com.gt.util.PropertyGroup;
	
	
		public class WORLD
		{
	
			public var File : String;
			public var WorldID : String;
			public var Index : int;
			
			public var Sprs = new Array();
			public var Maps = new Array();
		
			public var WayPointDatas	= new Array();
			public var RegionDatas		= new Array();
			
			public function WORLD(args:Array, config:PropertyGroup, res:SetResource)
			{
				File = args[0];
				WorldID = replaceRegion(args[0], res.KeyRegionWorld);
				Index = Integer.parseInt(args[1]);
	
				Vector<String> maps = config.get("WORLD_"+args[0]+"_MAP");
				Vector<String> sprs = config.get("WORLD_"+args[0]+"_SPR");
				Vector<String> wpss = config.get("WORLD_"+args[0]+"_WPS");
				Vector<String> wrss = config.get("WORLD_"+args[0]+"_WRS");
				
				if (maps!=null){
					for (var i=maps.size()-1; i>=0; --i){
						var value : Array = maps.get(i).split(TS);
						Maps.insertElementAt(new WORLD.MAP(value, res), 0);
					}
				}
				if (sprs!=null){
					for (var i=sprs.size()-1; i>=0; --i){
						var value : Array = sprs.get(i).split(TS);
						Sprs.insertElementAt(new WORLD.SPR(value, res), 0);
					}
				}
				if (wpss!=null){
					for (var i=wpss.size()-1; i>=0; --i){
						var value : Array = wpss.get(i).split(TS);
						var wpsd : Array;
						if (value.length>1){
							wpsd = CUtil.splitString(value[1], DS);
						}else{
							wpsd = new String[0];
						}
						WayPointDatas.insertElementAt(wpsd, 0);
					}
				}
				if (wrss!=null){
					for (var i=wrss.size()-1; i>=0; --i){
						var value : Array = wrss.get(i).split(TS);
						var wrsd : Array;
						if (value.length>1){
							wrsd = CUtil.splitString(value[1], DS);
						}else{
							wrsd = new String[0];
						}
						RegionDatas.insertElementAt(wrsd, 0);
					}
				}
			}
			
		}
}
package com.gt.game.edit.textset
{
	import com.gt.game.edit.SetResource;
	
		public class MAP
		{
			public var File : String;
			public var MapID : String;
			public var Index : int;
			public var ImagesID : String;
			
			public function MAP(args:Array, res:SetResource ){
				File = args[0];
				MapID = replaceRegion(args[0], res.KeyRegionMAP);
				Index = Integer.parseInt(args[1]);
				ImagesID = args[2];
			}
		}
}
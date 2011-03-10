package com.gt.game.edit.textset
{
	import com.gt.game.edit.SetResource;
	import com.gt.util.Util;
	
		public class SPR
		{
			public var File : String;
			public var SprID : String;
			public var Index : int;
			public var ImagesID : String;
			
			public var AnimateCount : int;
			public var AnimateNames : Array;
			
			public function SPR(args:Array, res:SetResource)
			{
				File = args[0];
				SprID = Util.getRegionWithArray(args[0], res.KeyRegionSPR);
				Index = new int(args[1]);
				
				ImagesID = args[2];
				
				if (args.length>4){
					AnimateNames = Util.splitString(args[4], SetResource.DS);
					AnimateCount = AnimateNames.length;
				}else{
					AnimateCount = 0;
					AnimateNames = new String[0];
				}
			
			}
			
		}
}
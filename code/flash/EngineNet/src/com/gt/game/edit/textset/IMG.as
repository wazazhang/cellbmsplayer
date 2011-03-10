package com.gt.game.edit.textset
{
	import com.gt.game.edit.SetResource;
	import com.gt.util.Util;
	
		public class IMG
		{
			public var File : String;
			public var ImagesID : String;
			public var Index : int;
			
			public function IMG(args : Array, res : SetResource){
				File = args[0];
				ImagesID = Util.getRegionWithArray(args[0], res.KeyRegionIMG);
				Index = new int(args[1]);
			}
		}
}
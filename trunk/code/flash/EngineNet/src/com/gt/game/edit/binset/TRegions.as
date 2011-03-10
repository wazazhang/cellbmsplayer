package com.gt.game.edit.binset
{
	import com.gt.game.CCD;
	
	import flash.utils.ByteArray;
	
	import com.gt.game.edit.SetInput;
	
	public class TRegions
	{
		public var Regions : Array;
		
		public function TRegions(buffer : ByteArray)
		{
			// regions
			var regionCount : int = buffer.readInt(); // --> u16
			Regions = new Array(regionCount);
			for (var i : int = 0; i < regionCount; i++) {
				var x : int = buffer.readShort(); // --> s16 * count
				var y : int = buffer.readShort(); // --> s16 * count
				var w : int = buffer.readShort(); // --> s16 * count
				var h : int = buffer.readShort(); // --> s16 * count
				Regions[i] = CCD.createCDRect(0, x, y, w, h);
			}
			
		}

	}
}
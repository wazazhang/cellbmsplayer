package com.gt.game.edit.binset
{
	import com.gt.game.CWayPoint;
	import com.gt.game.edit.SetInput;
	
	import flash.utils.ByteArray;
	
	public class TWayPoints
	{
		public var Points : Array;
		
		public function TWayPoints(buffer : ByteArray)
		{
			buffer.endian = SetInput.LITTLE_ENDIAN;
			
			// waypoints
			var waypointCount : int = buffer.readInt(); 	// --> u16
			
			Points = new Array(waypointCount);
			for (var i:int= 0; i<waypointCount; i++) {
				var x:int = buffer.readInt(); 				// --> s32 * count
				var y:int = buffer.readInt(); 				// --> s32 * count
				Points[i] = new CWayPoint(x, y);
			}
			
			// links
			var start : int = buffer.readShort(); 			// <-- s16 first test
			while (start >= 0) {
				var end : int = buffer.readShort(); 		// <-- s16
				Points[start].linkTo(Points[end]);
				start = buffer.readShort(); 				// <-- s16
			}
		

		}
		

	}
}
package com.gt.game.edit.binset
{
	import com.gt.game.edit.SetInput;
	
	import flash.utils.ByteArray;
	
	import com.gt.game.edit.SetInput;
	
	public class TImages 
	{
		public var FileName : String; 
		
		public var TileCount : int;	
		
		// int[]
		public var ClipsX : Array ;
		public var ClipsY : Array ;
		public var ClipsW : Array ;
		public var ClipsH : Array ;
		
		
		
		public function TImages(buffer : ByteArray, fileName:String = null)
		{
			buffer.endian = SetInput.LITTLE_ENDIAN;
			
			FileName = fileName;
			
			TileCount = buffer.readInt();		// --> u16
			
			ClipsX = new Array(TileCount);
			ClipsY = new Array(TileCount);
			ClipsW = new Array(TileCount);
			ClipsH = new Array(TileCount);
			
			for(var i:int=0; i<TileCount; i++)
			{
				ClipsX[i] = buffer.readShort();	// --> s16 * count
				ClipsY[i] = buffer.readShort();	// --> s16 * count
				ClipsW[i] = buffer.readShort();	// --> s16 * count
				ClipsH[i] = buffer.readShort();	// --> s16 * count
			}
			
		}

	}
}
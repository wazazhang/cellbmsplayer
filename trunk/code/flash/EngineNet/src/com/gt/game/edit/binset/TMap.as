package com.gt.game.edit.binset
{
	import flash.utils.ByteArray;
	
	import com.gt.game.edit.SetInput;
	
	public class TMap
	{
		public var XCount : int;
		public var YCount : int;
		public var CellW : int;
		public var CellH : int;

		// int[]
		public var TileID : Array;
		public var TileTrans : Array;
		
		// int[]
		public var Animates : Array;
		
		// int[][]
		public var TerrainScene2D : Array;
		
		// int[][]
		public var BlocksType : Array;
		public var BlocksMask : Array;
		public var BlocksX1 : Array;
		public var BlocksY1 : Array;
		public var BlocksX2 : Array;
		public var BlocksY2 : Array;
		public var BlocksW : Array;
		public var BlocksH : Array;
		
		// int[][]
		public var TerrainBlock2D : Array;
		
		
		public function getLayerImagesIndex(x:int, y:int, layer:int) : int {
			return TileID[Animates[TerrainScene2D[y][x]][layer]];
		}
		
		public function getLayerTrans(x:int, y:int, layer:int) : int {
			return TileTrans[Animates[TerrainScene2D[y][x]][layer]];
		}
		
		public function TMap(buffer : ByteArray)
		{
			buffer.endian = SetInput.LITTLE_ENDIAN;
			
			// property
			XCount = buffer.readInt();	// --> u16
			YCount = buffer.readInt();	// --> u16
			CellW = buffer.readInt();	// --> u16
			CellH = buffer.readInt();	// --> u16

			// parts
			var scenePartCount : int = buffer.readInt();		// --> u16
			TileID = new Array(scenePartCount);
			TileTrans = new Array(scenePartCount);
		    for(var i:int=0; i<scenePartCount; i++){
		    	TileID[i] = buffer.readShort();					// --> s16 * count
		    	TileTrans[i] = buffer.readByte();				// --> s8  * count
		    }

			// frames
			var animateCount : int = buffer.readInt();			// --> u16
			Animates = new Array(animateCount);
			for(var i:int=0; i<animateCount; i++){
				var frameCount:int =  buffer.readInt();			// --> u16 * count
				Animates[i] = new Array(frameCount);
				for(var f:int=0; f<frameCount; f++){
					Animates[i][f] = buffer.readShort();		// --> s16 * length * count
				}
			}
			
			// tile matrix
			TerrainScene2D = new Array(YCount);
			for(var y:int=0; y<YCount; y++){
				TerrainScene2D[y] = new Array(XCount);
				for(var x:int=0; x<XCount; x++){
					TerrainScene2D[y][x] = buffer.readShort();	// --> s16 * xcount * ycount
				}
			}
			
			// cds
			var cdCount : int = buffer.readInt();		// --> u16
			BlocksType = new Array(cdCount);
			BlocksMask = new Array(cdCount);
			BlocksX1 = new Array(cdCount);
			BlocksY1 = new Array(cdCount);
			BlocksX2 = new Array(cdCount);
			BlocksY2 = new Array(cdCount);
			BlocksW = new Array(cdCount);
			BlocksH = new Array(cdCount);
			for(var i:int=0; i<cdCount; i++){
				BlocksType[i] = buffer.readByte();		// --> s8 * count
				BlocksMask[i] = buffer.readInt();		// --> s32 * count
				BlocksX1[i] = buffer.readShort();		// --> s16 * count
				BlocksY1[i] = buffer.readShort();		// --> s16 * count
				BlocksX2[i] = buffer.readShort();		// --> s16 * count
				BlocksY2[i] = buffer.readShort();		// --> s16 * count
				BlocksW[i] = buffer.readShort();		// --> s16 * count
				BlocksH[i] = buffer.readShort();		// --> s16 * count
			}
			
			// cd matrix
			TerrainBlock2D = new Array(YCount);
			for(var y:int=0; y<YCount; y++){
				TerrainBlock2D[y] = new Array(XCount);
				for(var x:int=0; x<XCount; x++){
					TerrainBlock2D[y][x] = buffer.readShort();// --> s16 * xcount * ycount
				}
			}
	    
		}
		
	}
	
}
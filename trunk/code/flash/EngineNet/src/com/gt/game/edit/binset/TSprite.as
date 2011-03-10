package com.gt.game.edit.binset
{
	import flash.utils.ByteArray;
	
	import com.gt.game.edit.SetInput;
	
	public class TSprite
	{
		// short[]
		public var PartX : Array;
		public var PartY : Array;
		public var PartTileID : Array;
		public var PartTileTrans : Array;

		// short[][]
		public var Parts : Array;
		
		// int[]
		public var BlocksMask : Array;
		
		// short[]
		public var BlocksX1 : Array;
		public var BlocksY1 : Array;
		public var BlocksW : Array;
		public var BlocksH : Array;

		// short[][]
		public var Blocks : Array;
		
		// short[][]
		public var FrameAnimate : Array;
		public var FrameCDMap : Array;
		public var FrameCDAtk : Array;
		public var FrameCDDef : Array;
		public var FrameCDExt : Array;
		
		//images[PartTileID[Parts[FrameAnimate[anim][frame]][subpart]]];
		
		public function getPartImageIndex(anim:int, frame:int, subpart:int) : int {
			return PartTileID[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public function getPartTrans(anim:int, frame:int, subpart:int) : int {
			return PartTileTrans[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public function getPartX(anim:int, frame:int, subpart:int) : int {
			return PartX[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public function getPartY(anim:int, frame:int, subpart:int) : int {
			return PartY[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		
		public function TSprite(buffer : ByteArray)
		{
			buffer.endian = SetInput.LITTLE_ENDIAN;
			
				// scene parts
				var scenePartCount : int = buffer.readInt();	// --> u16
				PartX = new Array(scenePartCount);
				PartY = new Array(scenePartCount);
				PartTileID = new Array(scenePartCount);
				PartTileTrans = new Array(scenePartCount);
			    for(var i:int=0;i<scenePartCount;i++){
			    	PartX[i] =  buffer.readShort();			// --> s16 * count
			    	PartY[i] =  buffer.readShort();			// --> s16 * count
			    	PartTileID[i] =  buffer.readShort();	// --> s16 * count
			    	PartTileTrans[i] =  buffer.readByte();	// --> s8  * count
			    }

				// scene frames
				var animatesCount:int = buffer.readInt();	// --> u16
				Parts = new Array(animatesCount);
				for(var i:int=0;i<animatesCount;i++){
					var frameCount:int =  buffer.readInt();	// --> u16 * count
					Parts[i] = new Array(frameCount);
					for(var f:int=0;f<frameCount;f++){
						Parts[i][f] = buffer.readShort();	// --> s16 * length * count
					}
				}

				// cd parts
				var cdCount:int = buffer.readInt();			// --> u16
				BlocksMask = new Array(cdCount);
				BlocksX1 = new Array(cdCount);
				BlocksY1 = new Array(cdCount);
				BlocksW = new Array(cdCount);
				BlocksH = new Array(cdCount);
				for(var i:int=0;i<cdCount;i++){
					BlocksMask[i] = buffer.readInt();		// --> s32 * count
					BlocksX1[i] = buffer.readShort();		// --> s16 * count
					BlocksY1[i] = buffer.readShort();		// --> s16 * count
					BlocksW[i] = buffer.readShort();		// --> s16 * count
					BlocksH[i] = buffer.readShort();		// --> s16 * count
				}

				// cd frames
				var collidesCount:int = buffer.readInt();	// --> u16
				Blocks = new Array(collidesCount);
				for(var i:int=0;i<collidesCount;i++){
					var frameCount:int =  buffer.readInt();	// --> u16 * count
					Blocks[i] = new Array(frameCount);
					for(var f:int=0;f<frameCount;f++){
						Blocks[i][f] = buffer.readShort();	// --> s16 * length * count
					}
				}

				// animates
				var animateCount:int = buffer.readInt();			// --> u16
				FrameAnimate = new Array(animateCount);
				FrameCDMap = new Array(animateCount);
				FrameCDAtk = new Array(animateCount);
				FrameCDDef = new Array(animateCount);
				FrameCDExt = new Array(animateCount);
				for(var i:int=0;i<animateCount;i++){
					var frameCount:int = buffer.readInt();		// --> u16 * animateCount
					FrameAnimate[i] = new Array(frameCount);
					FrameCDMap[i] = new Array(frameCount);
					FrameCDAtk[i] = new Array(frameCount);
					FrameCDDef[i] = new Array(frameCount);
					FrameCDExt[i] = new Array(frameCount);
					for(var f:int=0;f<frameCount;f++){
						FrameAnimate[i][f] =  buffer.readShort();		// --> s16 * animateCount * frameCount
						FrameCDMap[i][f] =  buffer.readShort();		// --> s16 * animateCount * frameCount
						FrameCDAtk[i][f] =  buffer.readShort();		// --> s16 * animateCount * frameCount
						FrameCDDef[i][f] =  buffer.readShort();		// --> s16 * animateCount * frameCount
						FrameCDExt[i][f] =  buffer.readShort();		// --> s16 * animateCount * frameCount
					}
				}

		}
	}
}
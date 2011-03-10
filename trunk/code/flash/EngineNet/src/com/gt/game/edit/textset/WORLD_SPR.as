package com.gt.game.edit.textset
{
	public class WORLD_SPR
	{
		final public 	int Index;
				final public 	String UnitName;
				final public 	String SprID;
				final public 	String ImagesID;
				final public 	int Anim;
				final public 	int Frame;
				final public 	int X;
				final public 	int Y;
				
				public SPR(String[] args, SetResource res)
				{
					Index 		= Integer.parseInt(args[0]);
					UnitName 	= args[1];
					SprID 		= replaceRegion(args[2], res.KeyRegionSPR);
					ImagesID 	= replaceRegion(args[3], res.KeyRegionIMG);
					Anim		= Integer.parseInt(args[4]);
					Frame		= Integer.parseInt(args[5]);
					X 			= Integer.parseInt(args[6]);
					Y 			= Integer.parseInt(args[7]);
				}
				
		public function WORLD_SPR()
		{
		}

	}
}
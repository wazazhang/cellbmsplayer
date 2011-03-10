package com.gt.game.edit.textset
{
	public class WORLD_MAP
	{
		final public 	int Index;
				final public 	String UnitName;
				final public 	String MapID;
				final public 	String ImagesID;
				final public 	int X;
				final public 	int Y;
				
				public MAP(String[] args, SetResource res)
				{
					Index 		= Integer.parseInt(args[0]);
					UnitName 	= args[1];
					MapID 		= replaceRegion(args[2], res.KeyRegionMAP);
					ImagesID 	= replaceRegion(args[3], res.KeyRegionIMG);
					X 			= Integer.parseInt(args[4]);
					Y 			= Integer.parseInt(args[5]);
				}
		

	}
}
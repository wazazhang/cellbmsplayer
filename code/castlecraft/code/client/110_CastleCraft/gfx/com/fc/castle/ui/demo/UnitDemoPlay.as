package com.fc.castle.ui.demo
{
	import com.cell.gameedit.ResourceLoader;
	import com.fc.castle.res.CResourceManager;

	public class UnitDemoPlay extends BaseDemoPlay
	{
		public function UnitDemoPlay(csprite_id:String)
		{
			super(csprite_id);
		}
		
		protected override function getResourceLoader():void
		{
			res = CResourceManager.createActorResource(resUrl);
		}
	}
}
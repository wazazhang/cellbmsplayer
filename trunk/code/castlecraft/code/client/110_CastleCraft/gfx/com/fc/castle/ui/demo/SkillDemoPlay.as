package com.fc.castle.ui.demo
{
	import com.fc.castle.res.CResourceManager;

	public class SkillDemoPlay extends BaseDemoPlay
	{
		public function SkillDemoPlay(csprite_id:String)
		{
			super(csprite_id);
		}
		protected override function getResourceLoader():void
		{
			res = CResourceManager.createEffectResource(resUrl);
		}
	}
}
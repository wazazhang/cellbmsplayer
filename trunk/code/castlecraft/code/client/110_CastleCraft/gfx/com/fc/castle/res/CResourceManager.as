package com.fc.castle.res
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gfx.game.IImages;
	import com.cell.io.UrlManager;
	import com.cell.io.UrlWrapper;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.display.Sprite;
	
	/**
	 * 网络加载的动态资源
	 */
	public class CResourceManager implements UrlWrapper
	{
		
		public function CResourceManager()
		{
			UrlManager.setUrlWrapper(this);
		}
		
		public function getWrapperUrl(src_url:String):String
		{
//			return "/Volumes/Macintosh HD 2/projects/fightclub/castlecraft/data/resource/" + src_url;
			return AutoLogin.RES_ROOT + src_url;
		}
		
//		--------------------------------------------------------------------------------------------------------
		
		private static var icons : ResourceLoader;
		private static var icons_tiles : IImages;
		
		public static function createIcons() : ResourceLoader
		{
			icons = new ResourceLoader("icons/output/icons.xml");
			icons.addEventListener(ResourceEvent.LOADED, onIconLoaded);
			return icons;
		}
		
		private static function onIconLoaded(e:ResourceEvent) : void
		{
			icons_tiles = icons.getImages("icons");
		}
		
//		private static var icons : Map = new Map();
		
		public static function getIcon(icon:String) : Bitmap
		{
			return new Bitmap(getIconSrc(icon));
		}
		
		public static function getIconSrc(icon:String) : BitmapData
		{
			return icons_tiles.getImage(int(icon)).getSrc();
		}
		
//		--------------------------------------------------------------------------------------------------------
		
		/**
		 * name like "000020"
		 */
		public static function createActorHead(ut:UnitTemplate) : Loader
		{
			var ret : Loader = new Loader();
			ret.load(UrlManager.getUrl(createActorHeadUrl(ut)));
			return ret;
		}
		
		public static function createActorHeadUrl(ut:UnitTemplate) : String
		{
			return "edit/units/" + ut.csprite_id + ".png";
		}
		
//		--------------------------------------------------------------------------------------------------------
		
		/**
		 * name like "000020"
		 */
		public static function createActorResource(name:String) : ResourceLoader
		{
			return new ResourceLoader("edit/units/" + name + "/output/actor.xml");
		}
		
		/**
		 * name like "000020"
		 */
		public static function createEffectResource(name:String) : ResourceLoader
		{
			return new ResourceLoader("edit/effect/" + name + "/output/effect.xml");
		}
		
		/**
		 * name like "000020"
		 */
		public static function createMapResource(name:String) : ResourceLoader
		{
			return new ResourceLoader("edit/maps/" + name + "/output/map.xml");
		}
		
//		--------------------------------------------------------------------------------------------------------
//		scene resource
//		-----------------------------------------------------------------------------------------------------
		
		private static var res_owner_scene : ResourceLoader;
		
		public static function createOwnerScene() : ResourceLoader
		{
			CResourceManager.res_owner_scene = CResourceManager.createSceneResource(
				StringUtil.splitString(Screens.client.getPlayer().homeScene, "/")[0]);
			return CResourceManager.res_owner_scene;
		}
		
		public static function getOwnerScene() : ResourceLoader
		{
			return res_owner_scene;
		}
		
		public static function createSceneResource(name:String) : ResourceLoader
		{
			return new ResourceLoader("edit/scene/"+name+"/output/scene.xml");
		}
		
//		-----------------------------------------------------------------------------------------------------
//		common resource
//		-----------------------------------------------------------------------------------------------------
		
		private static var res_common_object : ResourceLoader;
		
		public static function createCommonObject() : ResourceLoader
		{
			if (res_common_object == null) {
				res_common_object = new ResourceLoader("edit/common/object/output/object.xml");
			}
			return res_common_object;
		}
		public static function getCommonObject() : ResourceLoader
		{
			return res_common_object;
		}
	}
}
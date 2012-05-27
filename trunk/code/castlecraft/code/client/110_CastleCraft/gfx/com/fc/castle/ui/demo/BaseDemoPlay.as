package com.fc.castle.ui.demo
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.ResourceLoaderQueue;
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSprite;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	import com.cell.gfx.game.worldcraft.CellWorld;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.gfx.battle.res.CBattleResource;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	
	import flash.display.Bitmap;
	import flash.events.Event;
	
	import mx.core.IFactory;

	public class BaseDemoPlay extends CellSprite
	{
		
		protected var res:ResourceLoader;
		protected var cell:CellCSpriteGraphics;
		protected var loading:Bitmap;
		
		
		protected var resUrl:String;
		protected var spriteName:String;
		
		public function BaseDemoPlay(csprite_id:String)
		{		
			super();	
			var kv : Array = StringUtil.splitString(csprite_id, "/");
			resUrl =  kv[0];
			spriteName = kv[1];
			getResourceLoader();
		}
		
		protected function getResourceLoader():void
		{
	
		}
		
		override protected function added(e:Event):void
		{
			// TODO Auto Generated method stub
			super.added(e);
			res.addEventListener(ResourceEvent.LOADED,loaded);
			res.addEventListener(ResourceEvent.ERROR,error);
			res.load();
			
			loading = new Res.ui_loading() as Bitmap;
			
			loading.x = loading.width * -0.5
			loading.y = -100;	
			addChild(loading);
		}
		
		private function loaded(event:ResourceEvent):void
		{
			res.removeEventListener(ResourceEvent.LOADED,loaded);
			res.removeEventListener(ResourceEvent.ERROR,error);
			removeChild(loading);
			
			var buff:CSprite =  res.getSpriteTemplate(spriteName);

		   	cell = new CellCSpriteGraphics(buff);
			cell.setCurrentAnimate(1,true);
			addChild(cell);
			//trace("loaded ok");
		}
		
		private function error(event:ResourceEvent):void
		{
			res.removeEventListener(ResourceEvent.LOADED,loaded);
			res.removeEventListener(ResourceEvent.ERROR,error);
			//trace("loaded error");
		}
		
		private var timer:int = 0;
		override protected function update(e:Event):void
		{
			super.update(e);	
			if(cell!=null)
			{
				
				if(cell.isBeginFrame())
				{
					timer ++;
					if(timer==5)
					{
						if( cell.getCurrentAnimate() == 1)
						{
							cell.setCurrentAnimate(2);
							cell.setCurrentFrame(0);
							timer = 0
						}
						else
						{
							cell.setCurrentAnimate(1);
							cell.setCurrentFrame(0);
							timer = 0; 
						}	
					}
					else
						cell.nextCycFrame();
				}
				else
				{
					cell.nextCycFrame();
				}
				
				cell.renderSelf();
			}
		}
	}
}
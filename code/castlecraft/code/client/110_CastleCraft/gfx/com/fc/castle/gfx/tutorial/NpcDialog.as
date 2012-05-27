package com.fc.castle.gfx.tutorial
{
	import com.cell.util.Map;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.smartfoxserver.v2.requests.ManualDisconnectionRequest;
	
	import flash.display.Bitmap;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.text.AntiAliasType;
	import flash.text.TextField;
	import flash.text.TextFormat;
	
	import mx.controls.Image;
	import mx.controls.Text;
	import mx.messaging.channels.StreamingAMFChannel;
	
	public class NpcDialog extends Sprite
	{
		
		private var _dialogText:String;
		private var text:TextField = new TextField();
		
		private var npc:Bitmap;
		private var dialog_bg:Bitmap;
		private var npcNameText:TextField = new TextField();
		private var npcName:String;
		
		private var textMap:Map;
		
		private var step:int = 1;
		private var _isEnd:Boolean = false;
		private var _lock:Boolean = false; //锁住防止点击其他的.
		
		private var _dialog_y:int = 180;
		
		public function NpcDialog(textMap:Map)
		{
			super();
			this.textMap = textMap;
			this.mouseEnabled = false;
			this.mouseChildren = false;
		/*	this.graphics.beginFill(0x0000000,0.01);	
			this.graphics.drawRect(0,80,Screens.WIDTH,Screens.HEIGHT);*/
			
			//this.graphics.beginFill(0x0000000,0.5);	
			//this.graphics.drawRect(0,80,Screens.WIDTH,130);
			dialog_bg = new Res.ui_dialog_bg();
			lock = false;
			dialog_bg.y = _dialog_y;
			this.addChild(dialog_bg);
			
		//	this.y = 80;		
			text.x = 150;
			text.y = _dialog_y+30;
			text.width = 500;
			text.wordWrap = true;

			text.multiline = true;
			addChild(text);
			npcNameText.x = 150;
			npcNameText.y = _dialog_y+5;

			npcNameText.width = 90;
			addChild(npcNameText);
			//this.addEventListener(Event.ADDED_TO_STAGE,addHandle);
		}
		
		
		
		public function get lock():Boolean
		{
			return _lock;
		}

		public function set lock(value:Boolean):void
		{
			_lock = value;
			
			this.graphics.clear();
			if(_lock)
			{
				this.graphics.beginFill(0x0000000,0.0001);	
				this.graphics.drawRect(0, 0,Screens.WIDTH,Screens.HEIGHT);
				
//				this.graphics.beginFill(0x0000000,0.5);	
//				this.graphics.drawRect(0,_dialog_y,Screens.WIDTH,130);
			}
			else
			{
//				this.graphics.beginFill(0x0000000,0.5);	
//				this.graphics.drawRect(0,_dialog_y,Screens.WIDTH,130);
			}
			
			this.graphics.endFill();
		}
		
		
		

		public function get isEnd():Boolean
		{
			return _isEnd;
		}

		public function get dialogText():String
		{
			return _dialogText;
		}

		private function setdialogText(value:String):void
		{
			_dialogText = value;
			text.htmlText = value;
//			text.setTextFormat(new TextFormat("黑体","14",0x000000));
			//addChild(text);
		}
		
		private function setNpcImage(bitmap:Bitmap):void
		{
			if(bitmap==null)
			{
				if(npc!=null)
				{
					removeChild(npc);
					npc = null;
				}
			}
			else
			{
				if(npc!=null)
				{
					removeChild(npc);
				}
				
				npc = bitmap;
				npc.x = 20;
				npc.y = _dialog_y;
				addChild(npc);
			}
		}
		private function setNpcNameHtml(html:String):void
		{
			npcNameText.htmlText = html;
			npcNameText.setTextFormat(new TextFormat(null,null,null,null,null,null,null,null,"center"));
		}
		
		public function setDialogY(y:int):void
		{
			_dialog_y = y;
			text.y = _dialog_y+30;
			npcNameText.y = _dialog_y+5;
			lock = lock;
			dialog_bg.y = _dialog_y;
		}
		
		
		public function next():void
		{
			if(_isEnd)
				return;
			
			var name:String = textMap.get(step+".name");
			var head:String = textMap.get(step+".head");
			var text:String = textMap.get(step+".dialog");
			
			npcNameText.visible = isGamer(name);
			
			setNpcImage(getHeadImage(head));
			
			
			setNpcNameHtml(dealText(name));
			
			setdialogText(dealText(text));
			
			step ++;
			if(textMap.get(step+".name") == null)
				_isEnd = true;
				
		}
		
		public function getStep():int
		{
			return step;
		}
		
		private function getHeadImage(head:String):Bitmap
		{
			if(head=="/res/ui/npc.png")
			{
				return new Res.ui_npc();
			}
			return null;
		}	
		
		private function dealText(str:String):String
		{
			str = str.replace("%ID%",Screens.client.getPlayer().player_name);
			return str;
		}
		private function isGamer(name:String):Boolean
		{
			return name.indexOf("%ID%") == -1;
		}
		
	}
}
package com.fc.castle.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.ImageNumber;
	import com.cell.ui.TextPan;
	import com.cell.util.ImageUtil;
	import com.fc.castle.data.PlayerData;
	import com.fc.castle.gfx.world.StageWorld;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.mail.FormMailList;
	
	import flash.display.Bitmap;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	
	public class HUDMain extends CellSprite
	{
		private var world : StageWorld;
		
		
		private var btn_card		: ImageButton;
		private var btn_ability		: ImageButton;
		private var btn_bag			: ImageButton;
		private var btn_mail		: ImageButton;
		private var btn_defence		: ImageButton;
		private var btn_shop		: ImageButton;
		private var btn_task		: ImageButton;
		private var btn_friend		: ImageButton;
		
		private var exp:Bitmap;
//		private var coin:Bitmap;
		private var coin:ImageNumber;
//		private var level:ImageNumber;
		private var level:TextPan;
		private var exp_text:TextPan;
		
		public function HUDMain(world:StageWorld)
		{
			super();
			
			this.world = world;
			
			var bottom:Bitmap =new  Res.ui_main_column() as Bitmap;
			bottom.y = Screens.HEIGHT-bottom.height;
			bottom.x = (Screens.WIDTH - bottom.width)/ 2;
			addChild(bottom);
			
			///////////////////////////////////////////////////
			//////功能按钮
			///////////////////////////////////////////////////
			
			var btn_dis:int = 5;
			btn_card = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_main_card).bitmapData,1.1);
			btn_card.anchor = Anchor.ANCHOR_CENTER;
			btn_card.x = bottom.x+267;
			btn_card.y = bottom.y+44;
			btn_card.addEventListener(MouseEvent.CLICK, onMouseClick);
			addChild(btn_card);
			
			btn_defence= com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_main_defence).bitmapData,1.1);
			btn_defence.x = bottom.x+359;
			btn_defence.y = bottom.y+44;
			btn_defence.anchor = Anchor.ANCHOR_CENTER;
			btn_defence.addEventListener(MouseEvent.CLICK, onMouseClick);
			addChild(btn_defence);
			
			btn_shop = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_main_shop).bitmapData,1.1);
			btn_shop.x = bottom.x+542;
			btn_shop.y = bottom.y+44;
			btn_shop.anchor = Anchor.ANCHOR_CENTER;
			btn_shop.addEventListener(MouseEvent.CLICK, onMouseClick);
			addChild(btn_shop);
			
			btn_bag = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_main_bag).bitmapData,1.1);
			btn_bag.x = bottom.x+449;
			btn_bag.y = bottom.y+44;
			btn_bag.anchor = Anchor.ANCHOR_CENTER;
			btn_bag.addEventListener(MouseEvent.CLICK, onMouseClick);
			addChild(btn_bag);
			

			
			
			btn_mail = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_main_mail).bitmapData,1.1);
			btn_mail.x = bottom.x+83;
			btn_mail.y = bottom.y+44;
			btn_mail.anchor = Anchor.ANCHOR_CENTER;
			btn_mail.addEventListener(MouseEvent.CLICK, onMouseClick);
			addChild(btn_mail);
			
			btn_task = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_main_task).bitmapData,1.1);
			btn_task.x = bottom.x+173;
			btn_task.y = bottom.y+44;
			btn_task.anchor = Anchor.ANCHOR_CENTER;
			btn_task.addEventListener(MouseEvent.CLICK, onMouseClick);
			addChild(btn_task);
			
			
			///////////////////////////////////////////////////
			//////顶部信息
			///////////////////////////////////////////////////
			
			var expslot:Bitmap = new Res.ui_main_expslot() as Bitmap;
			expslot.x = Screens.WIDTH-176;
			expslot.y = 3;
			addChild(expslot);
			
			exp = new Res.ui_main_value() as Bitmap;
			exp.x = expslot.x+5;
			exp.y = expslot.y+19;
			addChild(exp);
			
			var levelicon:Bitmap = new Res.ui_main_expicon() as Bitmap;
			levelicon.x = expslot.x+122;
			levelicon.y = expslot.y+4;
			addChild(levelicon);
			
//			level = Res.number.copy(1);
//			level.x = levelicon.x+8;
//			level.y = levelicon.y+13;
//			level.setColor(0xffe7c00);
			level = new TextPan("11");
			level.resize(39, 18);
			level.x = levelicon.x+0;
			level.y = levelicon.y+12;
//			level.textAnchor = Anchor.ANCHOR_CENTER;
			addChild(level);

			exp_text = new TextPan("1/10");
			exp_text.x = expslot.x+5;
			exp_text.y = expslot.y+19;
			exp_text.resize(exp.width, 14);
			addChild(exp_text);
			
//			var goldsolt:Bitmap = new Res.ui_main_solt() as Bitmap;
//			goldsolt.x = 510;
//			goldsolt.y = 18;
//			addChild(goldsolt);
			
//			coin = new Res.ui_main_value() as Bitmap;
//			coin.x = 522;
//			coin.y = 25;
//			addChild(coin);
			
				
			var goldslot:Bitmap = new Res.ui_main_goldslot() as Bitmap;
			goldslot.x = Screens.WIDTH-157;
			goldslot.y = 55;
			addChild(goldslot);
			
			coin = Res.number.copy(0);
			coin.setColor(0xfff6d936);
			coin.x = goldslot.x+6;
			coin.y = goldslot.y+14;
			addChild(coin);
			
			//右边信息
//			btn_friend = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
//				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_main_friend).bitmapData,1.1);
//			btn_friend.x = bottom.x+58;
//			btn_friend.y = bottom.y+19;
//			btn_friend.anchor = Anchor.ANCHOR_CENTER;
//			btn_friend.addEventListener(MouseEvent.CLICK, onMouseClick);
//			addChild(btn_friend);
		}
		
		public function setPlayerData(playerdata:PlayerData):void
		{
			setLevel(playerdata.level);
			setExp(playerdata.cur_exp, playerdata.next_exp);
			setCoin(playerdata.coin);
		}
		
		private function setLevel(value:int):void
		{
			level.setHTMLText("<font color=\"#fffe7c00\">"+value+"</font>");
//			level.textAnchor = Anchor.ANCHOR_CENTER;
		}
		
		private function setExp(value:int, max:int):void
		{
			exp.width = value*135/max;
			exp.x = Screens.WIDTH-39-exp.width;
			exp_text.setHTMLText("<font color=\"#ff1dd0b5\">"+value+"/"+max+"</font>");
		}
		
		private function setCoin(value:int):void
		{
//			coin.width = value*135/max;
			coin.number = value;
		}
		
		private function onMouseClick(event:MouseEvent):void
		{
			if (event.currentTarget == btn_card)
			{
				addChild(new FormHandBook());
			}
			else if (event.currentTarget == btn_mail)
			{
				addChild(new FormMailList());
			}
			else if (event.currentTarget == btn_friend)
			{
				addChild(new FormFriendList(world));
			}
			else if (event.currentTarget == btn_bag)
			{
				addChild(new FormItems());
			}
			else if (event.currentTarget == btn_shop)
			{
				addChild(new FormShop());
			}
			else if (event.currentTarget == btn_defence) 
			{
				addChild(new FormOrganizeDefense());
			}
			
		}
		
	}
}
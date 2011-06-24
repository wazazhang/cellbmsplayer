package Class
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.utils.Dictionary;
	
	import mx.controls.Alert;

	public class Resource
	{		
		[Bindable]
		[Embed(source='image/logo.png')]
		public static var logo:Class;
		
		[Bindable]
		[Embed(source='image/roomicon.png')]
		public static var roomicon:Class;
		
		
		[Bindable]
		[Embed(source='image/deskicon.png')]
		public static var deskicon:Class;
		
		
		[Bindable]
		[Embed(source='image/pf.png')]
		public static var picon_female:Class;	
		
		[Bindable]
		[Embed(source='image/pm.png')]
		public static var picon_male:Class;	
		
		[Embed(source='image/card/b1.png')]
		private static var b1:Class;
		
		[Embed(source='image/card/b2.png')]
		private static var b2:Class;
		
		[Embed(source='image/card/b3.png')]
		private static var b3:Class;
		
		[Embed(source='image/card/b4.png')]
		private static var b4:Class;
		
		[Embed(source='image/card/b5.png')]
		private static var b5:Class;
		
		[Embed(source='image/card/b6.png')]
		private static var b6:Class;
		
		[Embed(source='image/card/b7.png')]
		private static var b7:Class;
		
		[Embed(source='image/card/b8.png')]
		private static var b8:Class;
		
		[Embed(source='image/card/b9.png')]
		private static var b9:Class;
		
		[Embed(source='image/card/b10.png')]
		private static var b10:Class;
		
		[Embed(source='image/card/b11.png')]
		private static var b11:Class;
		
		[Embed(source='image/card/b12.png')]
		private static var b12:Class;
		
		[Embed(source='image/card/b13.png')]
		private static var b13:Class;
		

		
		[Embed(source='image/card/c1.png')]
		private static var c1:Class;
		
		[Embed(source='image/card/c2.png')]
		private static var c2:Class;
		
		[Embed(source='image/card/c3.png')]
		private static var c3:Class;
		
		[Embed(source='image/card/c4.png')]
		private static var c4:Class;
		
		[Embed(source='image/card/c5.png')]
		private static var c5:Class;
		
		[Embed(source='image/card/c6.png')]
		private static var c6:Class;
		
		[Embed(source='image/card/c7.png')]
		private static var c7:Class;
		
		[Embed(source='image/card/c8.png')]
		private static var c8:Class;
		
		[Embed(source='image/card/c9.png')]
		private static var c9:Class;
		
		[Embed(source='image/card/c10.png')]
		private static var c10:Class;
		
		[Embed(source='image/card/c11.png')]
		private static var c11:Class;
		
		[Embed(source='image/card/c12.png')]
		private static var c12:Class;
		
		[Embed(source='image/card/c13.png')]
		private static var c13:Class;
		
		
		
		[Embed(source='image/card/h1.png')]
		private static var h1:Class;
		
		[Embed(source='image/card/h2.png')]
		private static var h2:Class;
		
		[Embed(source='image/card/h3.png')]
		private static var h3:Class;
		
		[Embed(source='image/card/h4.png')]
		private static var h4:Class;
		
		[Embed(source='image/card/h5.png')]
		private static var h5:Class;
		
		[Embed(source='image/card/h6.png')]
		private static var h6:Class;
		
		[Embed(source='image/card/h7.png')]
		private static var h7:Class;
		
		[Embed(source='image/card/h8.png')]
		private static var h8:Class;
		
		[Embed(source='image/card/h9.png')]
		private static var h9:Class;
		
		[Embed(source='image/card/h10.png')]
		private static var h10:Class;
		
		[Embed(source='image/card/h11.png')]
		private static var h11:Class;
		
		[Embed(source='image/card/h12.png')]
		private static var h12:Class;
		
		[Embed(source='image/card/h13.png')]
		private static var h13:Class;
		
		
		
		[Embed(source='image/card/l1.png')]
		private static var l1:Class;
		
		[Embed(source='image/card/l2.png')]
		private static var l2:Class;
		
		[Embed(source='image/card/l3.png')]
		private static var l3:Class;
		
		[Embed(source='image/card/l4.png')]
		private static var l4:Class;
		
		[Embed(source='image/card/l5.png')]
		private static var l5:Class;
		
		[Embed(source='image/card/l6.png')]
		private static var l6:Class;
		
		[Embed(source='image/card/l7.png')]
		private static var l7:Class;
		
		[Embed(source='image/card/l8.png')]
		private static var l8:Class;
		
		[Embed(source='image/card/l9.png')]
		private static var l9:Class;
		
		[Embed(source='image/card/l10.png')]
		private static var l10:Class;
		
		[Embed(source='image/card/l11.png')]
		private static var l11:Class;
		
		[Embed(source='image/card/l12.png')]
		private static var l12:Class;
		
		[Embed(source='image/card/l13.png')]
		private static var l13:Class;

		[Embed(source='image/card/g.png')]
		private static var g:Class;
		
		[Embed(source='image/card/g2.png')]
		private static var g2:Class;
	
		
		[Bindable]
		[Embed(source='image/bg/xtbg.png')] //系统信息背景
		public static var xtbg:Class;
		
		[Bindable]
		[Embed(source='image/bg/chat_bg.png')] //系统信息背景
		public static var talkbg:Class;
		
		[Bindable]
		[Embed(source='image/guang2.swf')] //系统信息背景
		public static var guang:Class;
		
		


		[Bindable]
		[Embed(source='image/sjbt1.png')] //快速加入1
		public static var sjbt1:Class;
		
		[Bindable]
		[Embed(source='image/sjbt2.png')] //快速加入2
		public static var sjbt2:Class;
		
		[Bindable]
		[Embed(source='image/time.swf')] //时间
		public static var time:Class;
		
		[Bindable]
		[Embed(source='image/bg/desk.png')] //桌面背景
		public static var newbg:Class;
		

		
		[Bindable]
		[Embed(source='image/bg/deskItemBg.png')] //桌子项
		public static var deskItemBg:Class;
		
		[Bindable]
		[Embed(source='image/bg/deskItemBg2.png')] //桌子项
		public static var deskItemBg2:Class;
		
		
		[Bindable]
		[Embed(source='image/bg/playerItbg.png')] //玩家项
		public static var playerItemBg:Class;
		
		[Bindable]
		[Embed(source='image/bg/playerItbg2.png')] //玩家项
		public static var playerItemBg2:Class;
		
		[Bindable]
		[Embed(source='image/bg/itbg.png')] //桌子项
		public static var itbg:Class;
		
		[Bindable]
		[Embed(source='image/bg/itbg2.png')] //桌子项
		public static var itbg2:Class;
		
		[Bindable]
		[Embed(source='image/button/fastBt.png')] //快速开始按钮
		public static var fastBt:Class;
		
		[Bindable]
		[Embed(source='image/button/fastBt2.png')] //快速开始按钮
		public static var fastBt2:Class;
		
		[Bindable]
		[Embed(source='image/button/fastBt3.png')] //快速开始按钮
		public static var fastBt3:Class;
		
		[Bindable]
		[Embed(source='image/card/b.png')] //牌
		public static var pai:Class;
		
		[Bindable]
		[Embed(source='image/bg/top_bar.png')] //游戏顶部木纹
		public static var top_bar:Class;
		
		[Bindable]
		[Embed(source='image/gold.png')] //金币
		public static var gold:Class;
		
		[Bindable]
		[Embed(source='image/bg/wood_bar.png')] //操作区木纹
		public static var wood_bar:Class;
		
		
		[Bindable]
		[Embed(source='image/bg/chat_send.png')] //操作区木纹
		public static var chat_send:Class;
		
		
		[Bindable]
		[Embed(source='image/bg/deskbg2.png')] //操作区木纹
		public static var deskbg2:Class;

		
		[Bindable]
		[Embed(source='image/bg/head_bar.png')] //操作区木纹
		public static var head_bar:Class;
		
		[Bindable]
		[Embed(source='image/gold2.png')] //金币2
		public static var gold2:Class;
		
		[Bindable]
		[Embed(source='image/bg/ProgressBarBg.png')] //进度条
		public static var ProgressBarBg:Class;
		
		[Bindable]
		[Embed(source='image/cardsleft.png')]
		public static var cardsleft : Class;
		
		
		[Embed(source='image/cards.png')]
		private static var cards : Class;
		private static var cards_image : BitmapData;
		private static var cards_loader : Loader = new Loader();
		private static var cards_map : Dictionary = new Dictionary();
		
		public static function init():void
		{
			cards_loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onLoadCardsComplete);
			cards_loader.load(new URLRequest(cards+""));
		}
		

		private static function onLoadCardsComplete(event:Event):void 
		{
			var bitmap:Bitmap = Bitmap(cards_loader.content);
			cards_image = bitmap.bitmapData;
			
			for (var y:int=0; y<5; y++) {
				for (var x:int=0; x<14; x++) {
					var card : BitmapData = new BitmapData(25, 31, true, 0x00ffffff);
					card.copyPixels(cards_image, 
						new Rectangle(x*26, y*52, 25, 31), 
						new Point(0, 0));
						cards_map[y+"_"+x] = card;
				}
			}
		}

		
		public static function getCardImg(type:int,point:int):Class
		{
			//var bd : BitmapData = cards_map[type+"_"+point];
			
			switch (point)
			{
				case 0:
					if (type==0)
						return g;
					else
						return g2;
					break;
				case 1:
					switch (type)
					{
						case 1:return b1;
						case 2:return c1;
						case 3:return h1;
						case 4:return l1;
					}
					break;
				case 2:
					switch (type)
					{
						case 1:return b2;
						case 2:return c2;
						case 3:return h2;
						case 4:return l2;
					}
					break;
				case 3:
					switch (type)
					{
						case 1:return b3;
						case 2:return c3;
						case 3:return h3;
						case 4:return l3;
					}
					break;
				case 4:
					switch (type)
					{
						case 1:return b4;
						case 2:return c4;
						case 3:return h4;
						case 4:return l4;
					}
					break;
				case 5:
					switch (type)
					{
						case 1:return b5;
						case 2:return c5;
						case 3:return h5;
						case 4:return l5;
					}
					break;
				case 6:
					switch (type)
					{
						case 1:return b6;
						case 2:return c6;
						case 3:return h6;
						case 4:return l6;
					}
					break;
				case 7:
					switch (type)
					{
						case 1:return b7;
						case 2:return c7;
						case 3:return h7;
						case 4:return l7;
					}
					break;
				case 8:
					switch (type)
					{
						case 1:return b8;
						case 2:return c8;
						case 3:return h8;
						case 4:return l8;
					}
					break;
				case 9:
					switch (type)
					{
						case 1:return b9;
						case 2:return c9;
						case 3:return h9;
						case 4:return l9;
					}
					break;
				case 10:
					switch (type)
					{
						case 1:return b10;
						case 2:return c10;
						case 3:return h10;
						case 4:return l10;
					}
					break;
				case 11:
					switch (type)
					{
						case 1:return b11;
						case 2:return c11;
						case 3:return h11;
						case 4:return l11;
					}
					break;
				case 12:
					switch (type)
					{
						case 1:return b12;
						case 2:return c12;
						case 3:return h12;
						case 4:return l12;
					}
					break;
				case 13:
					switch (type)
					{
						case 1:return b13;
						case 2:return c13;
						case 3:return h13;
						case 4:return l13;
					}
					break;
			}
			return g;
		}
			
		
		
		
	}
}
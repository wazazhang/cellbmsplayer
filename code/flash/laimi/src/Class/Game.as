package Class
{
	import Class.Model.Card;
	import Class.Model.Line;
	import Class.Model.Player;
	
	import Component.Card_Cpt;
	import Component.Lami;
	import Component.Matrix_Cpt;
	
	import com.fc.lami.Messages.CardData;
	
	import flash.events.KeyboardEvent;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.controls.Alert;
	import mx.core.Application;
	
	
	[Bindable]
	public class Game
	{
		public static var app:Lami;
		
		//列数
		public static var lineCount:int=7; 
		
		//列数组
		public static var lineArray:ArrayCollection = new ArrayCollection();
		
		//中央矩形
		public static var matrix:Matrix_Cpt;
		
		//数组
		public static var cards:ArrayCollection = new ArrayCollection();
		
		//玩家
		public static var gamer:Player= new Player();
		
		//当前合法性
		public static var legaled:Boolean = true;
		
		//是否有出牌
		public static var haveSendCard:Boolean = false;
		
		//是否已经开始
		public static var isStarted:Boolean = false;
		
		//是否可以出牌
		//public static var canSendCard:Boolean = false;
		
		
		
		//牌堆坐标
		public static var cardspostion_x:int = 600;
		public static var cardspostion_y:int = 20; 
		
		public function Game()
		{
			
		}
		
		public static function initGame():void
		{
			initMatrix();
			gamer.initMatrix();
			initCard();
			TimesCtr.init();
			Game.app.addEventListener(KeyboardEvent.KEY_DOWN,keydown);
			Game.app.addEventListener(KeyboardEvent.KEY_UP,keyup);
		}
		
		public static function start(startCards:ArrayCollection):void
		{
			
			gamer.getStartCard(startCards);
			app.optCpt.Start(); 
			
		}
		
		
		public static function get canSendCard():Boolean
		{
			return legaled&&haveSendCard;
		}
		
		//初始矩阵
		public static function initMatrix():void
		{
			var curline:Line;
			for(var i:int=0;i<lineCount;i++)
			{			
				var line:Line = new Line(20,false,i+1);
				
				if(curline != null)
				{
					curline.nextLine = line;
				}
				
				curline = line;
				
				if(i == lineCount-1)
				{
					curline.nextLine = lineArray[0];
				}
				
				lineArray.addItem(line);
				line.fill(i,matrix);
			}
			var cardcpt:Card_Cpt = (lineArray[0] as Line).firstCard;
			matrix.width = cardcpt.width*(lineArray[0] as Line).lineLength+4;
			matrix.height = cardcpt.height*lineCount+4;
		}
		
		//初始牌
		public static function initCard():void
		{
			var cid:int = 0;
			for(var i:int=1;i<=13;i++)
			{
				for(var j:int=1;j<=4;j++)
				{
					var card:Card = new Card(i,j, cid++);
					card.order = Math.random();
					cards.addItem(card);
					card = new Card(i,j, cid++);
					card.order = Math.random();
					cards.addItem(card);
				}
			}
			card = new Card(0,0, cid++);
			card.order = Math.random();
			cards.addItem(card);
			card = new Card(0,0, cid++);
			card.order = Math.random();
			cards.addItem(card);
			
			var sort:Sort = new Sort();
			sort.fields = [new SortField("order",false)];
			cards.sort = sort;
			cards.refresh();
		}
		
		public static function getCardFromServer():void
		{
			turnOver();
			Server.getCard();
			//return cards.removeItemAt(0) as Card; 
		}
		
		//确定合法性
		public static function check():Boolean
		{
			haveSendCard = checkHaveSendCard();
			
			for each(var line:Line in lineArray)
			{
				if(!line.check())
				{
					matrix.setStyle("borderColor",0xff0000);
					legaled = false;	
					return false;
				}
			}
			matrix.setStyle("borderColor",0x000000);
			legaled = true;
			return true;
		}
		
		public static function get getPublicCards():Array
		{
			var cards:Array = new Array()
	
			for each(var line:Line in lineArray)
			{
				var cardcpt:Card_Cpt = line.firstCard
				do{	
					
					if(cardcpt.card!=null)
					{
						cards.push(cardcpt.card.cardData);
					}
					cardcpt = cardcpt.nextCardCpt;
				}	
				while(cardcpt!=null)	
				
			}	
			return cards;	
		}
		
		//主牌区的变化
		public static function publicCardChange(cards:Array):void
		{
			if(gamer.isMyturn)
				return;
			
			for each(var line:Line in lineArray)
			{
				var cardcpt:Card_Cpt = line.firstCard;
				do{	
					
					for each(var carddata:CardData in cards)
					{
						if(cardcpt.cardX == carddata.x&&cardcpt.cardY == carddata.y)
						{
							cardcpt.card = Card.createCardByData(carddata);
							break;
						}
						else
						{
							cardcpt.card = null;
						}
					}
					cardcpt = cardcpt.nextCardCpt;
				}	
				while(cardcpt!=null)
			}	
		}
		
		//获得打出点数
		public static function getSendPoint():int
		{
			var point:int=0;
			for each(var line:Line in lineArray)
			{
				if(!line.check())
				{
					matrix.setStyle("borderColor",0xff0000);
					legaled = false;
					return 0;
				}
				point += line.getPoint();
			}
			matrix.setStyle("borderColor",0x000000);
			legaled = true;
			return point;
		}
		
		//判定是否打出
		public static function checkHaveSendCard():Boolean
		{
			for each(var line:Line in lineArray)
			{
				var cardcpt:Card_Cpt = line.firstCard;
				
				while(cardcpt!=null)
				{
					if(cardcpt.card!=null&&!cardcpt.card.isSended)
						return true;
					else
						cardcpt = cardcpt.nextCardCpt;	
				}	
			}
			return false
		}
		
		//撤销
		public static function reset():void
		{
			for each(var line:Line in lineArray)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					cardctp.card = null;
					cardctp.card = cardctp.confimcard;
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp != null);
			}
		}
		
		//提交
		public static function submit():void
		{
			if(!canSendCard)
			{
				Alert.show("不合法");
				return;
			}
			
			for each(var line:Line in lineArray)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					cardctp.confimcard = null;
					cardctp.confimcard = cardctp.card;
					
					if(cardctp.card!=null)
						cardctp.card.isSended = true;
						
					cardctp = cardctp.nextCardCpt;	
				}
				while(cardctp != null);
			}
			Server.submit();
		}
		
		public static function turnStart():void
		{
			//gamer.canOpearation = true;
			gamer.isMyturn = true;
			TimesCtr.start();
		}
		
		public static function turnOver():void
		{
			//gamer.canOpearation = false;
			gamer.isMyturn = false;
			TimesCtr.stop();
		}
		
		private static function keydown(event:KeyboardEvent):void
		{
			if(event.keyCode==16)
			gamer.keydwon = true;
		}
		private static function keyup(event:KeyboardEvent):void
		{
			gamer.keydwon = false;	
		}
		
		
		public static function addGameInfo(str:String):void
		{
			app.gameinfo.text = str + "\n"+ app.gameinfo.text  ;
		}
	}
}
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
		public  var lami:Lami;
		
		//列数
		public  var lineCount:int=10; 
		
		//列数组
		public  var lineArray:ArrayCollection = new ArrayCollection();
		
		//中央矩形
		public  var matrix:Matrix_Cpt;
		
		//数组
		public  var cards:ArrayCollection = new ArrayCollection();
		
		//玩家
		public  var gamer:Player= new Player();
		
		//当前合法性
		public  var legaled:Boolean = true;
		
		//是否有出牌
		public  var haveSendCard:Boolean = false;
		
		//是否已经开始
		public  var isStarted:Boolean = false;
		
		//牌堆坐标
		public  var cardspostion_x:int = 600;
		public  var cardspostion_y:int = 20; 
		
		public var timeCtr:TimesCtr;
		
		public var leftCard:int;
		
		public function Game()
		{
			timeCtr = new TimesCtr();
			lami = new Lami();
			lami.game = this;
		}
		
		public function initGame():void
		{
			//matrix = lami.mx;
			initMatrix();
			gamer.game = this;
			gamer.initMatrix();
			//initCard();
			//TimesCtr.init();
			lami.addEventListener(KeyboardEvent.KEY_DOWN,keydown);
			lami.addEventListener(KeyboardEvent.KEY_UP,keyup);
		}
		
		public  function start(startCards:ArrayCollection):void
		{
			lami.onGameStart();
			gamer.getStartCard(startCards);
			lami.optCpt.Start(); 
		}
		
		public  function get canSubmitCard():Boolean
		{
			return legaled&&haveSendCard;
		}
		
		//初始矩阵
		public  function initMatrix():void
		{
			var curline:Line;
			for(var i:int=0;i<lineCount;i++)
			{			
				var line:Line = new Line(24,false,i+1,this);
				
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
		
		//清空矩阵
		public  function cleanMatrix():void
		{
			for each (var line:Line in lineArray)
			{
				line.clean();
			}
		}
		
		
		
		//初始牌
		public  function initCard():void
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
		
		public  function getCardFromServer():void
		{
			turnOver();
			Server.getCard();
			//return cards.removeItemAt(0) as Card; 
		}
		
		//确定合法性
		public  function check():Boolean
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
		
		public  function get getPublicCards():Array
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
		public  function publicCardChange(is_hardhanded:Boolean, cards:Array):void
		{
			if(!is_hardhanded && gamer.isMyturn)
				return;
			
			for each(var line:Line in lineArray)
			{
				var cardcpt:Card_Cpt = line.firstCard;
				if (cards.length == 0){
					do{
						cardcpt.card = null;
						cardcpt = cardcpt.nextCardCpt;
					}
					while(cardcpt!=null);
				}else{
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
		}
		
		//获得打出点数
		public  function getSendPoint():int
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
				point += line.getPointWithOutGuest();
			}
			matrix.setStyle("borderColor",0x000000);
			legaled = true;
			return point;
		}
		
		//判定是否打出
		public  function checkHaveSendCard():Boolean
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
		public  function reset():void
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
		
		public  function setAllCardIssend():void
		{
			for each(var line:Line in lineArray)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					//cardctp.confimcard = null;
					cardctp.confimcard = cardctp.card;
					
					if(cardctp.card!=null)
						cardctp.card.isSended = true;
					
					cardctp = cardctp.nextCardCpt;	
				}
				while(cardctp != null);
			}
		}
		
		//提交
		public  function submit():void
		{
			if(!canSubmitCard)
			{
				Alert.show("不合法");
				return;
			}
			setAllCardIssend();
			timeCtr.stop();
			gamer.isMyturn = false;
			Server.submit();
		}
		
		public  function turnStart():void
		{
			//gamer.canOpearation = true;
			gamer.confiomCard();
			gamer.isMyturn = true;
			
			
			
			haveSendCard = false;
			lami.turnStartImg.visible = true;
			lami.turnStartEft.play();
			timeCtr.start();
		}
		
		public  function turnOver():void
		{
			//gamer.canOpearation = false;
			gamer.isMyturn = false;
			if(gamer.selectedArrayCard != null)
			{
				for each(var card:Card in gamer.selectedArrayCard)
				{
					card.cardUI.isSelected = false;
				}
				gamer.selectedArrayCard = null
			}	
			if(gamer.selectedCard != null)
			{
				gamer.selectedCard.cardUI.isSelected = false;
				gamer.selectedCard = null;
			}
			gamer.reset();
			timeCtr.reset();
			timeCtr.stop();
		}
		
		private  function keydown(event:KeyboardEvent):void
		{
			if(event.keyCode==16)
				gamer.keydwon = true;
		}
		
		private  function keyup(event:KeyboardEvent):void
		{
			gamer.keydwon = false;	
		}
		
		public  function addGameInfo(str:String):void
		{
			lami.gameinfo.text = str + "\n"+ lami.gameinfo.text  ;
		}
		
		public  function otherPlayerStart(playerid:int):void
		{
			lami.onPlayerStart(playerid);
		}
		
		
		
	}
}
package Class
{
	import Class.Model.Card;
	import Class.Model.Line;
	import Class.Model.Player;
	
	import Component.CardMatrixCpt;
	import Component.Card_Cpt;
	import Component.Card_Move;
	import Component.Lami;
	import Component.Login_Cpt;
	
	import Interface.ICardOwner;
	
	import com.fc.lami.Messages.CardData;
	import com.fc.lami.Messages.MoveCardToDeskRequest;
	import com.fc.lami.Messages.MoveCardToDeskResponse;
	import com.fc.lami.ui.LamiAlert;
	import com.net.client.ClientEvent;
	
	import flash.display.MovieClip;
	import flash.events.KeyboardEvent;
	import flash.sampler.NewObjectSample;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.controls.Alert;
	import mx.core.Application;
	
	
	[Bindable]
	public class Game
	{
		public  var lami:Lami;
		
		public var cardOwner:ICardOwner;
		//列数
		public  const lineCount:int=8; 
		public  const linewidth:int=24; 
		
		public var cards:ArrayCollection; //游戏介绍使用的数组
		
		//列数组
		public  var lineArray:ArrayCollection = new ArrayCollection();
		
		//中央矩形
		public  var matrix:CardMatrixCpt;
		
		//玩家
		public  var gamer:Player= new Player();
		
		//当前合法性
		public  var legaled:Boolean = true;
		
//		//是否有出牌
//		public  var haveSendCard:Boolean = false;
		
		//是否已经开始
		public  var isStarted:Boolean = false;
		
		public var timeCtr:TimesCtr;
		
		public var leftCard:int;
		
		//服务器端公共牌
		public var serverCards:Array;
		
		public function Game()
		{
			timeCtr = new TimesCtr();
			lami = new Lami();
			lami.game = this;
			
			//cardOwner = lami;
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
			lami.removeReset();
			gamer.orderType = true;
			gamer.getStartCard(startCards);
			lami.optCpt.Start(); 
		}
		
//		public  function get canSubmitCard():Boolean
//		{
//			return legaled&&haveSendCard;
//		}
		
		//初始矩阵
		public  function initMatrix():void
		{
			for(var i:int=0;i<lineCount;i++)
			{			
				var line:Line = new Line(linewidth,false,i,this);
				
								
				lineArray.addItem(line);
				line.fill(i,matrix);
				line.clean();
			}
			var cardcpt:Card_Cpt = new Card_Cpt();
			matrix.width = (cardcpt.width)*linewidth+4;
			matrix.height = (cardcpt.height)*lineCount+4;
		}
		
		//清空矩阵
		public  function cleanMatrix():void
		{
			for each (var line:Line in lineArray)
			{
				line.clean();
			}
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
//			haveSendCard = checkHaveSendCard();
			
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
		
		
		public function findError():void
		{
			for each(var line:Line in lineArray)
			{
				line.findError();
			}
		}
		
		
		public  function get getPublicCards():Array
		{
			var cards:Array = new Array()
			var cardcpt:Card_Cpt;
			for each(var line:Line in lineArray)
			{
				for (var i:int=0;i<line.length;i++)
				{
					cardcpt = line.getCardByIndex(i);
					if(cardcpt.getCard()!=null)
					{
						cards.push(cardcpt.getCard().cardData);
					}
				}
			}	
			return cards;	
		}
		
		//主牌区的变化
		public  function publicCardChange(is_hardhanded:Boolean, cards:Array):void
		{
			if(!is_hardhanded && gamer.isMyturn)
				return;
			
			if (cards.length == 0){
				cleanMatrix();
			}else{
				for each(var line:Line in lineArray)
				{
					for each(var cardcpt:Card_Cpt in line){
						var have_card:Boolean = false;
						for each(var carddata:CardData in cards)
						{
							if(cardcpt.cardX == carddata.x&&cardcpt.cardY == carddata.y)
							{
								cardcpt.setCard(Card.createCardByData(carddata));
								have_card = true;
								break;
							}
						}
						if (!have_card){
							cardcpt.setCard(null);;
						}
					}
				}
			}
			cleanShadow();
			check();
			
		}
//		
		public function getCardCpt(x:int, y:int):Card_Cpt
		{
			var line:Line = lineArray.getItemAt(y) as Line;
			var cardcpt:Card_Cpt = line.getCardByIndex(x);
			return cardcpt;
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
				point += line.getPoint();
			}
			matrix.setStyle("borderColor",0x000000);
			legaled = true;
			return point;
		}
		
//		//判定是否打出
//		public  function checkHaveSendCard():Boolean
//		{
//			for each(var line:Line in lineArray)
//			{
//				for each (var cardcpt:Card_Cpt in line){
//					if(cardcpt.card!=null&&!cardcpt.card.isSended){
//						return true;
//					}
//				}	
//			}
//			return false
//		}
		

		
		public  function turnStart():void
		{
			gamer.onTurnStart();
//			gamer.isMyturn = true;
			lami.playRoundStartMoiveClip();
			timeCtr.start();
		}
		
		//检测是都在牌区中
		public function checkHaveSameInPublic(card:Card):Boolean
		{
			for each(var c:Line in lineArray)
			{
				for each(var cardcpt:Card_Cpt in c)
				{
					if(cardcpt.getCard()!=null&&cardcpt.getCard().id == card.id)
					{
						return true;
					}
				}
			}
			return false;
		}
		
		//检测是否在服务器区牌堆中
		public function checkInServerCards(card:Card):Boolean
		{
			for each(var c:CardData in serverCards)
			{
				if(c.id == card.id)
					return true
			}
			return false;
		}
		
		public  function turnOver():void
		{
			//gamer.canOpearation = false;
			gamer.isMyturn = false;
			
			//处理结束时候还有选中的牌组
//			if(gamer.selectedArrayCard != null)
//			{
//				for each(var cardm:Card_Move in gamer.selectedArrayCard)
//				{
//					cardm.curCardCpt.isShow = true;
//					lami.removeChild(cardm);
//				}
//				gamer.selectedArrayCard = null;
//
//			}	

			//gamer.reset();
			lami.cleanCardState();
			timeCtr.reset();
			timeCtr.stop();
		}
		
		private  function keydown(event:KeyboardEvent):void
		{
			if(event.keyCode==16)
				gamer.keydown = true;
		}
		
		private  function keyup(event:KeyboardEvent):void
		{
			gamer.keydown = false;	
		}
		
		public  function addGameInfo(str:String):void
		{
			lami.addInfo(str);
		}
		
		public  function playerTurnStart(playerid:int):void
		{
			
			lami.nextPlayer1.gameing = false;
			lami.nextPlayer2.gameing = false;
			lami.nextPlayer3.gameing = false;
			lami.nextPlayer4.gameing = false;
			
			lami.optCpt.openOrder();
			
			if(lami.resetCpt!=null)
			   lami.removeReset();
			
			timeCtr.stop();
			if(playerid == Server.player.player_id )
			{
				turnStart();
			}
			else
			{
				lami.onPlayerStart(playerid);
			}
		}
		
		public function getCardGroup(x:int, y:int):ArrayCollection
		{
			var line:Line = lineArray[y];
			return line.getCardGroup(x);
		}
		
		private function moveCardResponseListener(event:ClientEvent):void
		{
			var res:Object = event.getResponse();
			
			if (res is MoveCardToDeskResponse){
				var mcpr : MoveCardToDeskResponse = res as MoveCardToDeskResponse;
				var mcp : MoveCardToDeskRequest = event.getRequest() as MoveCardToDeskRequest;
				if (mcpr.result == MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_SUCCESS){
					var array : ArrayCollection = lami.selected_cards.getSelectCards();
					var cards:ArrayCollection = new ArrayCollection();
					for each (var cardm:Card_Move in array){
						cards.addItem(cardm.curCardCpt.getCard());
					}
					var line:Line = lineArray[mcp.y];
					var x1:int = mcp.x;
					for each(var cardm:Card_Move in array){
						if (cardm.curCardCpt.isPlayerOwner){
							var i:int = gamer.handCard.getItemIndex(cardm.curCardCpt.getCard());
							if (i!=-1){
								gamer.handCard.removeItemAt(i);
								gamer.is_sended_card = gamer.isSendCard();
							}
						}
						cardm.curCardCpt.setCard(null);
						cardm.curCardCpt.isSelected = false;
					}
					for each(var card:Card in cards){
						card.x = x1;
						card.y = mcp.y;
						line.getCardByIndex(x1).setCard(card);
						x1++;
					}
					lami.cleanCardState();
				}else{
					
				}
			}
		}
		
		public function putCards(array:ArrayCollection, x:int, y:int):Boolean
		{
			var cards:ArrayCollection = new ArrayCollection();
			for each (var cardm:Card_Move in array){
				cards.addItem(cardm.curCardCpt.getCard());
			}
			
			var line:Line = lineArray[y];
			var cardcpt:Card_Cpt = line.getCardByIndex(x);
			if (cardcpt.getCard()!=null && !cardcpt.isSelected){
				return false;
			}
			
			var x1:int = line.getBlankStart(x, array.length);
			if (x1==-1){
				return false;
			}else{
				for each(var cardm:Card_Move in array){
					if (cardm.curCardCpt.isPlayerOwner){
						var i:int = gamer.handCard.getItemIndex(cardm.curCardCpt.getCard());
						if (i!=-1){
							gamer.handCard.removeItemAt(i);
							gamer.is_sended_card = gamer.isSendCard();
						}
					}
					cardm.curCardCpt.setCard(null);
					cardm.curCardCpt.isSelected = false;
				}
				for each(var card:Card in cards){
					card.x = x1;
					card.y = y;
					line.getCardByIndex(x1).setCard(card);
					x1++;
				}
			}
			Server.sendPublicMatrix();
			return true;
		}
		
//		public function putCards(array:ArrayCollection, x:int, y:int):void
//		{
//			var cards:ArrayCollection = new ArrayCollection();
//			for each (var cardm:Card_Move in array){
//				cards.addItem(cardm.curCardCpt.card);
//			}
//			
//			var line:Line = lineArray[y];
//			var cardcpt:Card_Cpt = line.getCardByIndex(x);
//			if (cardcpt.card!=null && !cardcpt.isSelected){
//				return;
//			}
//			
//			var x1:int = line.getBlankStart(x, array.length);
//			var X:int = x1;
//			if (x1==-1){
//				return;
//			}else{
//				var cds:Array = new Array();
//				for each(var card:Card in cards){
//					cds.push(card.id);
//				}
//				Server.sendMoveCardToDesk(cds, X, y, moveCardResponseListener);
//			}
//			return;
//		}
		
		public function putCardsShadow(array:ArrayCollection, x:int, y:int):void
		{
			var cards:ArrayCollection = new ArrayCollection();
			for each (var cardm:Card_Move in array){
				cards.addItem(cardm.curCardCpt.getCard());
			}
			
			var line:Line = lineArray[y];
			var cardcpt:Card_Cpt = line.getCardByIndex(x);
			if (cardcpt.getCard()!=null && !cardcpt.isSelected){
				return;
			}
			
			var x1:int = line.getBlankStart(x, array.length);
			if (x1==-1){
				return;
			}else{
				for each(var card:Card in cards){
					line.getCardByIndex(x1).setShadow(true);
					x1++;
				}
			}
		}
		
		public function cleanShadow():void
		{
			for each (var line:Line in lineArray)
			{
				for each(var cardcpt:Card_Cpt in line){
					cardcpt.setShadow(false);
				}
			}
		}
	}
}
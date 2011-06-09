package Class.Model
{
	import Class.Game;
	import Class.Server;
	import Class.TimesCtr;
	
	import Component.Card_Cpt;
	import Component.UserMatrix_Cpt;
	
	import com.fc.lami.Messages.CardData;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.controls.Alert;
	import mx.effects.Move;
	import mx.events.EffectEvent;

	[Bindable]
	public class Player
	{
		public var handCard:ArrayCollection = new ArrayCollection(); //当前手牌数组
		
		public var matrix:UserMatrix_Cpt    //用户矩阵
		public var matrix_length:int = 18;  //用户矩阵宽度
		public var matrix_height:int = 4;    //用户矩阵高度
		
		public var cardLines:ArrayCollection = new ArrayCollection();
		public var selectedCard:Card; //选中的牌
		public var selectedArrayCard:Array; //选中的牌组
		
	   
		public var isCold:Boolean = true;	 //是否已经破冰
			
		public var orderType:Boolean = true;   //排序类型	
			
		public var keydwon:Boolean = false;	   //当前是否按下SHIFT
			
		//private var startCard:int = 14; //起手牌数 	
		
		public var canOpearation:Boolean = false;//当前是否能操作
		
	//	public var isSendCard:Boolean = false; //是否有出牌
		
		public var isMyturn:Boolean = false; //是否轮到我
		
		public var isReady:Boolean	= false; //是否准备好了
		
		public var player_id:int;
		
		public var game:Game;
		
		public function Player()
		{
			
		}
		//初始化矩阵
		public function initMatrix():void
		{
			var curline:Line;
			for(var i:int=0;i<matrix_height;i++)
			{
				var line:Line = new Line(matrix_length,true,i+1,game);
				if(curline != null)
				{
					curline.nextLine = line;
				}
				curline = line;
				if(i == matrix_height-1)
				{
					curline.nextLine = cardLines[0];
				}
				
				cardLines.addItem(line);
				fill(i,line);
			}
		}
		
		//牌区的变化
		public function myCardChange(cards:Array):void
		{
			if(isMyturn)
				return;
//			cleanMatrix();
//			handCard.removeAll();
			for each(var cd:CardData in cards){
				if (!isHandCard(cd)){
					getCard(Card.createCardByData(cd));
				}
			}
			var h2 :ArrayCollection = new ArrayCollection();
			for each(var c:Card in handCard){
				for each(var cd:CardData in cards){
					if (c.id == cd.id){
						h2.addItem(c);
						break;
					}
				}
			}
			handCard = h2;
//			handCard.refresh();
			
//			var line:Line = cardLines[0];
//			var cardcpt:Card_Cpt = line.firstCard;
//			var precard:Card;
			
//			for each(var card:Card in handCard)
//			{
//				cardcpt.card = card;
//				cardcpt.confimcard = card;
////				cardcpt.isShow = false;
//				cardcpt = cardcpt.nextCardCpt;
//				if(precard!=null)
//				{
//					precard.nextCard = card;
//				}
//				precard = card;
//			}

//			if(orderType)
//			{
//				orderCardByPoint();
//			}
//			else
//			{
//				orderCardByColor();
//			}
//			for each(var line:Line in cardLines)
//			{
//				var cardcpt:Card_Cpt = line.firstCard;
//				do{	
//					
//					for each(var carddata:CardData in cards)
//					{
//						if(cardcpt.cardX == carddata.x&&cardcpt.cardY == carddata.y)
//						{
//							cardcpt.card = Card.createCardByData(carddata);
//							break;
//						}
//						else
//						{
//							cardcpt.card = null;
//						}
//					}
//					cardcpt = cardcpt.nextCardCpt;
//				}	
//				while(cardcpt!=null)
//			}	
		}
		
		//清空矩阵
		public function cleanMatrix():void
		{
			handCard = new ArrayCollection();
			for each (var line:Line in cardLines)
			{
				line.clean();
			}
		}
		
		
		public function fill(lie:int,line:Line):void
		{
			var top:int = 50; //距离顶部
			var jtop:int = 10; //间隔
			var fontx:int = 0;
			
			var cardcpt:Card_Cpt = line.firstCard;
			var y:int =top+ lie*(cardcpt.height+1);
			
			for(var i:int=0;i<line.lineLength;i++)
			{
				cardcpt.x = i*(cardcpt.width+1)+(lie*fontx);
				cardcpt.y = y;
				matrix.addChild(cardcpt);
				cardcpt = cardcpt.nextCardCpt;	
			}
		}
		
		public function getOneCardFromCardpile():void
		{
			if(handCard.length==matrix_length*matrix_height)
			{
				//Alert.show("牌数已达到上限");
				return;
			}
			//canOpearation = false;
			game.getCardFromServer();
			//getCard(Game.getCardFromCard());
			
		}
		
		
		public function getStartCard(startcards:ArrayCollection):void
		{
			cleanMatrix();
			canOpearation = false;
			handCard =  startcards;
			/*
			for(var i:int=0;i<startCard;i++)
			{
				handCard.addItem(Game.getCardFromCard());
			}
			*/
			var sort:Sort = new Sort();
			sort.fields = [new SortField("point",false),new SortField("type",false)];
			handCard.sort = sort;
			handCard.refresh();
			
			var line:Line = cardLines[0];
			var cardcpt:Card_Cpt = line.firstCard;
			var precard:Card;
			
			for each(var card:Card in handCard)
			{
				cardcpt.card = card;
				cardcpt.confimcard = card;
				cardcpt.isShow = false;
				cardcpt = cardcpt.nextCardCpt;
				if(precard!=null)
				{
					precard.nextCard = card;
				}
				precard = card;
			}
			addCardMotion(handCard[0]);
		}
		
		public function removeAllCard():void
		{
			
		}
		
		
		public function getCard(card:Card):void
		{
			handCard.addItem(card);
			var line:Line;
			if(card.type==0)
			{
				line = cardLines[0];
			}
			else
			{
				line = cardLines[card.type-1]
			}
			var cardcpt:Card_Cpt = line.lastCard;
			do{
				do
				{
					if(cardcpt.card ==null&&cardcpt.confimcard ==null)
					{	
						cardcpt.isShow = false;
						cardcpt.card = card;
						cardcpt.confimcard = card;
						addCardMotion(card)
						return;
					}
					cardcpt = cardcpt.preCardCpt
				}
				while(cardcpt!=null)
				line = line.nextLine;
				cardcpt = line.lastCard;
			}
			while(true)
		}
		
		public function getCards(cards:ArrayCollection):void
		{
			for each(var card:Card in cards)
			{
				getCard(card);
			}
		}
		
		public function submit():Boolean
		{
			/*
			if(!game.check())
			{
				Alert.show("出牌不符合规则");
				return false;
			}
			*/
			
			if(isCold)
			{
				var sum:int = game.getSendPoint();
				
				if(sum<30)
				{
					Alert.show('尚未破冰')
					return false;
				}
				else
				{
					isCold = false;
				}
			}
			
			game.submit();
			confiomCard();
			//Game.canSendCard = false;
			return true;
		}
		
		//提交
		public function confiomCard():void
		{
			var array:ArrayCollection = new ArrayCollection();
			
			for each(var line:Line in cardLines)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					if(cardctp.card!=null)
					{
						array.addItem(cardctp.card);
					}
					cardctp.confimcard = cardctp.card;
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp != null);
			}
			handCard = array;
		}
		
		
		public function isHandCard(card:CardData):Boolean
		{
			for each(var c:Card in handCard){
				if (c.id == card.id){
					return true;
				}
			}
			return false;
		}
		//计算手牌数
		public function countHandCard():void
		{
			var array:ArrayCollection = new ArrayCollection();
			
			for each(var line:Line in cardLines)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					if(cardctp.card!=null)
					{
						array.addItem(cardctp.card);
					}
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp != null);
			}
			handCard = array;
		}
		
		
		public function reset():void
		{
			for each(var line:Line in cardLines)
			{
				var cardctp:Card_Cpt = line.firstCard;
				
				do{
					cardctp.card = null;
					cardctp.card = cardctp.confimcard;
					
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp != null);
			}
			game.reset();
			Server.sendPublicMatrix();
			game.check();
		}
		
		public function orderCard():void
		{
			if(orderType)
			{
				orderType = false;
				orderCardByColor();
			}
			else
			{
				orderType = true;
				orderCardByPoint();
			}
		}
		//按照颜色排序
		private function orderCardByColor():void
		{
			var cards:ArrayCollection = clearAllCardToArray();
			
			if(cards.length==0)
				return;
				
			for each(var card:Card in cards)
			{
				
				if(card.point==0)
				{
					var lines:Line = cardLines[0] as Line
					
					if(lines.lastCard.card==null)
						(cardLines[0] as Line).lastCard.card = card;
					else if((cardLines[1] as Line).lastCard.card==null)	
						(cardLines[1] as Line).lastCard.card = card;
					else if((cardLines[2] as Line).lastCard.card==null)	
						(cardLines[2] as Line).lastCard.card = card;
					else((cardLines[3] as Line).lastCard.card==null)	
						(cardLines[3] as Line).lastCard.card = card;
						
					continue;			
				}
				var line:Line = cardLines[card.type-1] as Line;
				
				var point:int = 12;
				
				var cardcpt:Card_Cpt = line.firstCard;
				
				while(point!=(13-card.point)) 
				{
					cardcpt = cardcpt.nextCardCpt;
					point --;
				}
				
				if(cardcpt.card==null)
				{
					cardcpt.card = card;
				}
				else
				{
					cardcpt = cardcpt.nextCardCpt;
					
					while(point!=0) 
					{
						point --;
						cardcpt = cardcpt.nextCardCpt;
					}
					while(cardcpt.card!=null) 
					{
						cardcpt = cardcpt.nextCardCpt;
						
						if(cardcpt==null)
						{
							cardcpt = line.nextLine.firstCard;
						}
					}
					cardcpt.card = card;
				}
			}
		}
		
		//按照点数排序
		private function orderCardByPoint():void
		{
			var cards:ArrayCollection = clearAllCardToArray();		
			
			if(cards.length==0)
				return;

			var sort:Sort = new Sort();
			sort.fields = [new SortField("point",false),new SortField("type",false)];
			cards.sort = sort;
			cards.refresh();
			var index:int=0;
			for each(var line:Line in cardLines)
			{
				var cardctp:Card_Cpt = line.firstCard;	
				do{
					if(cardctp.card==null)
					{
						cardctp.card = cards[index];
						index++;
						if(index == cards.length)
						{
							return;		
						}
					}
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp!=null)
			}
		}
		
		protected function clearAllCardToArray():ArrayCollection
		{
			var array:ArrayCollection = new ArrayCollection();
			for each(var line:Line in cardLines)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					if(cardctp.card !=null)
					{
						array.addItem(cardctp.card);
						cardctp.card = null;
					}
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp != null);
			}
			return array;
		}
		
		
		protected function addCardMotion(card:Card):void
		{
			var moveCard:Card_Cpt = new Card_Cpt();
			matrix.addChild(moveCard);
			moveCard.isShow = true;
			moveCard.x = game.cardspostion_x;
			moveCard.y = game.cardspostion_y;
			moveCard.card = new Card(card.point,card.type, card.id);
			moveCard.card.nextCard  = card.nextCard;
			moveCard.nextCardCpt = card.cardUI;
			
			var move:Move = new Move();
			
			move.target = moveCard;
			move.duration = 500;

			move.xTo = card.cardUI.x;
			move.yTo = card.cardUI.y;
			
			move.addEventListener(EffectEvent.EFFECT_END,addCardMotionComplate);
			move.play();
		}
		
		protected function addCardMotionComplate(event:EffectEvent):void
		{
			var card:Card = ((event.target as Move).target as Card_Cpt).card;
			((event.target as Move).target as Card_Cpt).nextCardCpt.isShow = true;
		    matrix.removeChild((event.target as Move).target as Card_Cpt)
					
			if(card.nextCard!=null)
			{
				addCardMotion(card.nextCard);
			}
			else
			{
				game.lami.showReset();
				canOpearation = true;
			}
		}
		
		public function myTurnStart():void
		{
			
		}
		
	}
}
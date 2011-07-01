package Class.Model
{
	import Class.Game;
	import Class.LanguageManager;
	import Class.Server;
	import Class.TimesCtr;
	
	import Component.CardMatrixCpt;
	import Component.Card_Cpt;
	import Component.Card_Move;
	
	import com.fc.lami.Messages.CardData;
	import com.fc.lami.Messages.MoveCardToPlayerRequest;
	import com.fc.lami.Messages.MoveCardToPlayerResponse;
	import com.fc.lami.ui.LamiAlert;
	import com.net.client.ClientEvent;
	
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
		
		public var matrix:CardMatrixCpt    //用户矩阵
		public var matrix_length:int = 18;  //用户矩阵宽度
		public var matrix_height:int = 4;    //用户矩阵高度
		
		public var cardLines:ArrayCollection = new ArrayCollection();
//		public var selectedCard:Card; //选中的牌
//		public var selectedArrayCard:Array; //选中的牌组
		
	   
		public var isCold:Boolean = true;	 //是否已经破冰
			
		public var orderType:Boolean = true;   //排序类型	
			
		public var keydown:Boolean = false;	   //当前是否按下SHIFT
			
		//private var startCard:int = 14; //起手牌数 	
		
		public var canOpearation:Boolean = false;//当前是否能操作
		
	//	public var isSendCard:Boolean = false; //是否有出牌
		
		public var isMyturn:Boolean = false; //是否轮到我
		
		public var isReady:Boolean	= false; //是否准备好了
		
		//public var player_id:int;
		
		public var game:Game;
		
		public function Player()
		{
			
		}
		//初始化矩阵
		public function initMatrix():void
		{
			for(var i:int=0;i<matrix_height;i++)
			{
				var line:Line = new Line(matrix_length,true,i,game);
				
				cardLines.addItem(line);
				fill(i,line);
				line.clean();
			}
		}
		
		public function getCardUI(card:Card):Card_Cpt
		{
			for each(var line:Line in cardLines)
			{
				for each(var cardcpt:Card_Cpt in line)
				{
					if(cardcpt.getCard() == card)
					{
						return cardcpt;
					}
				}
			}
			return null;
		}
		
		//牌区的变化
		public function myCardChange(cards:Array):void
		{
//			if(isMyturn)
//				return;
//			cleanMatrix();
//			handCard.removeAll();
			for each(var cd:CardData in cards){
				if (cd!=null && !isHandCard(cd)){
//					getCard(Card.createCardByData(cd));
					var card:Card = Card.createCardByData(cd);
					handCard.addItem(card);
					setCardInToMatrix(card);
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
			is_sended_card = isSendCard();
		}
		
		//清空矩阵
		private function cleanMatrix():void
		{
			for each (var line:Line in cardLines)
			{
				line.clean();
			}
		}
		
		
		public function fill(lie:int,line:Line):void
		{
			var top:int = 0; //距离顶部
			var fontx:int = 0;
			
			var cardcpt:Card_Cpt = new Card_Cpt();
			var y:int =top+ lie*(cardcpt.height);
			
			for(var i:int=0;i<line.length;i++)
			{
				cardcpt = line.getCardByIndex(i);
				cardcpt.x = i*(cardcpt.width)+(lie*fontx);
				cardcpt.y = y;
				matrix.addChild(cardcpt);
			}
		}
		
		public function getOneCardFromCardpile():void
		{
			if(handCard.length==matrix_length*matrix_height)
			{
				//LamiLamiAlert.show("牌数已达到上限");
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

			var sort:Sort = new Sort();
			sort.fields = [new SortField("point",false),new SortField("type",false)];
			handCard.sort = sort;
			handCard.refresh();
			
			var y:int = 0;
			var x:int = 0;
			var line:Line;
			var cardcpt:Card_Cpt;
//			var precard:Card;
			var card_motion_array:ArrayCollection  = new ArrayCollection();
			
			for each(var card:Card in handCard)
			{
				if (x>=matrix_length){
					x = 0;
					y++;
				}
				cardcpt=cardLines[y][x++];
				cardcpt.setCard(card);
				cardcpt.isShow = false;
				card_motion_array.addItem(cardcpt);
			}
			game.lami.addCardMotionList(card_motion_array);
		}
		
		public function removeAllCard():void
		{
			
		}
		
		public function removeCardByID(id:int):void
		{
			for each(var card:Card in handCard){
				if (card.id == id){
					var i = handCard.getItemIndex(card);
					if (i!=-1){
						handCard.removeItemAt(i);
						is_sended_card = isSendCard();
						var cardcpt:Card_Cpt = getCardUI(card);
						cardcpt.setCard(null);
					}
				}
			}
			
		}
		
		private function setCardInToMatrix(card:Card):Card_Cpt
		{
			var line:Line;
			var line_index:int;
			var cardcpt:Card_Cpt;
			if (!orderType){
				if(card.type==0)
				{
					line_index = 0;
					line = cardLines[0];
				}
				else
				{
					line_index = card.type-1;
					line = cardLines[card.type-1]
				}
				if (card.point==0){
					cardcpt = line.getLastBlank();
					cardcpt.setCard(card);
					return cardcpt;
				}else if(line.getCardByIndex(card.point-1).getCard()==null){
					cardcpt = line.getCardByIndex(card.point-1);
					cardcpt.setCard(card);
					return cardcpt;
				}else{
					cardcpt = line.getLastBlank();
					if (cardcpt!=null){
						cardcpt.setCard(card);
						return cardcpt;
					}else{
						line_index = 3;
						while (line_index>=0){
							line = cardLines[line_index];
							cardcpt = line.getLastBlank();
							if (cardcpt!=null){
								cardcpt.setCard(card);
								return cardcpt;
							}else{
								line_index--;
							}
						}
						// 运行到这里就是所有行都没空格了
						return cardcpt;
					}
				}
			}else{
				line_index = 0;
				while(line_index<4){
					line = cardLines[line_index];
					cardcpt = line.getFrontBlank();
					if (cardcpt!=null){
						cardcpt.setCard(card);
						return cardcpt;
					}else{
						line_index++;
					}
				}
				// 运行到这里就是所有行都没空格了
				return null;
			}
			
		}
		
		public function getCard(card:Card):void
		{
			handCard.addItem(card);
			is_sended_card = isSendCard();
			var cardcpt:Card_Cpt = setCardInToMatrix(card);
			if (cardcpt!=null){
				game.lami.addCardMotion(cardcpt);
			}
		}
		
		public function getCardsByArray(cards:Array):void
		{
			for each(var card:Card in cards)
			{
				getCard(card);
			}
		}
		
		public function getCards(cards:ArrayCollection):void
		{

			for each(var card:Card in cards)
			{
				getCard(card);
			}
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
		
		
		public function orderCard():void
		{
			//选中牌的时候点击排序做处理
			
//			if(selectedArrayCard != null)
//			{
//				for each(var card:Card in selectedArrayCard)
//				{
//					card.cardUI.isSelected = false;
//				}
//				selectedArrayCard = null
//				
//			}	
			
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
			cleanMatrix();
			if(handCard.length==0){
				return;
			}
			for each(var card:Card in handCard)
			{
				setCardInToMatrix(card);
			}
		}
		
		//按照点数排序
		private function orderCardByPoint():void
		{
			cleanMatrix();
			if(handCard.length==0)
				return;

			var sort:Sort = new Sort();
			sort.fields = [new SortField("point",false),new SortField("type",false)];
			handCard.sort = sort;
			handCard.refresh();
			for each(var card:Card in handCard)
			{
				setCardInToMatrix(card);
			}
		}
		
		public function cleanAllCard():void
		{
			cleanMatrix();
			handCard = new ArrayCollection();
		}
		
		private var start_card_number:int;
		
		public function onTurnStart():void
		{
			isMyturn = true;
			start_card_number = handCard.length;
			is_sended_card = false;
		}
		
		[Bindable]
		public var is_sended_card :Boolean;
		
		public function isSendCard():Boolean
		{
			return start_card_number>handCard.length;
		}
		
		public function getCardGroup(x:int, y:int):ArrayCollection
		{
			var line:Line = cardLines[y];
			return line.getCardGroup(x);
		}
		
		private function tackCardBackResponseListener(event:ClientEvent):void
		{
			var res:Object = event.getResponse();
			
			if (res is MoveCardToPlayerResponse){
				var mcpr : MoveCardToPlayerResponse = res as MoveCardToPlayerResponse;
				var mcp:MoveCardToPlayerRequest = event.getRequest() as MoveCardToPlayerRequest;
				if (mcpr.result == MoveCardToPlayerResponse.MOVE_CARD_TO_PLAYER_RESULT_SUCCESS){
					var array : ArrayCollection = game.lami.selected_cards.getSelectCards();
					var cards:ArrayCollection = new ArrayCollection();
					for each (var cardm:Card_Move in array){
						cards.addItem(cardm.curCardCpt.getCard());
					}
					var line:Line = cardLines[mcp.y];
					var x1:int = mcp.x;
					for each(var cardm:Card_Move in array){
						if (!cardm.curCardCpt.isPlayerOwner){
							handCard.addItem(cardm.curCardCpt.getCard());
							is_sended_card = isSendCard();
						}else{
							
						}
						cardm.curCardCpt.setCard(null);
						cardm.curCardCpt.isSelected = false;
					}
					for each(var card:Card in cards){
						line.getCardByIndex(x1).setCard(card);
						x1++;
					}
					game.lami.cleanCardState();
				}else{
					
				}
			}
		}
		
		public function putCards(array:ArrayCollection, x:int, y:int):Boolean
		{
			var cards:ArrayCollection = new ArrayCollection();
			for each (var cardm:Card_Move in array){
				if (cardm.curCardCpt.getCard().isSended){
					LamiAlert.show(LanguageManager.getText("server.canotBack"));
					return false;
				}
				cards.addItem(cardm.curCardCpt.getCard());
			}
			
			var line:Line = cardLines[y];
			var cardcpt:Card_Cpt = line.getCardByIndex(x);
			if (cardcpt.getCard()!=null && !cardcpt.isSelected){
				return false;
			}
			
			var x1:int = line.getBlankStart(x, array.length);
			var X:int = x1;
			var is_change_public_matrix:Boolean = false;
			if (x1==-1){
				return false;
			}else{
				for each(var cardm:Card_Move in array){
					if (!cardm.curCardCpt.isPlayerOwner){
						is_change_public_matrix = true;
						handCard.addItem(cardm.curCardCpt.getCard());
						is_sended_card = isSendCard();
					}else{
						
					}
					cardm.curCardCpt.setCard(null);
					cardm.curCardCpt.isSelected = false;
				}
			}
			for each(var card:Card in cards){
				line.getCardByIndex(x1).setCard(card);
				x1++;
			}
			if (is_change_public_matrix){
				Server.sendPublicMatrix();
			}
			return true;
		}
		
//		public function putCards(array:ArrayCollection, x:int, y:int):void
//		{
//			var cards:ArrayCollection = new ArrayCollection();
//			for each (var cardm:Card_Move in array){
//				if (cardm.curCardCpt.card.isSended){
//					LamiAlert.show(LanguageManager.getText("server.canotBack"));
//					return ;
//				}
//				cards.addItem(cardm.curCardCpt.card);
//			}
//			
//			var line:Line = cardLines[y];
//			var cardcpt:Card_Cpt = line.getCardByIndex(x);
//			if (cardcpt.card!=null && !cardcpt.isSelected){
//				return ;
//			}
//			
//			var x1:int = line.getBlankStart(x, array.length);
//			var X:int = x1;
//			var is_change_public_matrix:Boolean = false;
//			if (x1==-1){
//				return ;
//			}else{
//				for each(var cardm:Card_Move in array){
//					if (!cardm.curCardCpt.isPlayerOwner){
//						is_change_public_matrix = true;
//					}
//				}
//			}
//			if (is_change_public_matrix){
//				var cds:Array = new Array();
//				for each(var card:Card in cards){
//					cds.push(card.id);
//				}
//				Server.sendMoveCardToPlayer(cds, tackCardBackResponseListener);
//			}else{
//				for each(var cardm:Card_Move in array){
//					cardm.curCardCpt.card = null;
//					cardm.curCardCpt.isSelected = false;
//				}
//				for each(var card:Card in cards){
//					line.getCardByIndex(x1).card = card;
//					x1++;
//				}
//				game.lami.cleanCardState();
//			}
//		}
		
		public function putCardsShadow(array:ArrayCollection, x:int, y:int):void
		{
			var cards:ArrayCollection = new ArrayCollection();
			for each (var cardm:Card_Move in array){
				cards.addItem(cardm.curCardCpt.getCard());
			}
			
			var line:Line = cardLines[y];
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
			for each (var line:Line in cardLines)
			{
				for each(var cardcpt:Card_Cpt in line){
					cardcpt.setShadow(false);
				}
			}
		}
	}
}
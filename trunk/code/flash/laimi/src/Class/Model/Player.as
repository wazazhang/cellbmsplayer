package Class.Model
{
	import Class.Game;
	
	import Component.Card_Cpt;
	import Component.UserMatrix_Cpt;
	
	import mx.collections.ArrayCollection;
	public class Player
	{
		public var handCard:ArrayCollection = new ArrayCollection();
		public var matrix:UserMatrix_Cpt
		public var matrix_length:int = 18;
		public var matrix_height:int = 4;
		
		public var cardLines:ArrayCollection = new ArrayCollection();
		
		public var selectedCard:Card_Cpt;
		public var nextPlayer:Player;		
		public var isCold:Boolean = true;	
			
		public function Player()
		{
			
		}
		
		public function initMatrix():void
		{
			for(var i:int=0;i<matrix_height;i++)
			{
				var line:Line = new Line(matrix_length,true);
				cardLines.addItem(line);
				fill(i,line);
			}
		}
		
		public function fill(lie:int,line:Line):void
		{
			var top:int = 20; //距离顶部
			var jtop:int = 10; //间隔
			var fontx:int = 20;
			
			var cardcpt:Card_Cpt = line.firstCard;
			var y:int =20+ lie*(cardcpt.height+10);
			
			for(var i:int=0;i<line.lineLength;i++)
			{
				cardcpt.x = i*cardcpt.width+(80-lie*fontx);
				cardcpt.y = y;
				matrix.addChild(cardcpt);
				cardcpt = cardcpt.nextCardCpt;	
			}
			
			
		}
		
		public function getOneCardFromCardpile():void
		{
			getCard(Game.getCardFromCard());
		}
		
		public function getCard(card:Card):void
		{
			handCard.addItem(card);
			for(var i:int=0;i<cardLines.length;i++ )
			{
				var line:Line = cardLines[i];
				var cardcpt:Card_Cpt = line.firstCard;
				
				do{
					if(cardcpt.card ==null)
					{
						cardcpt.card = card;
						return;
					}
					cardcpt = cardcpt.nextCardCpt;
				}
				while(cardcpt!=line.lastCard) 
			}
		}
		
		public function submit():Boolean
		{
			if(!Game.check())
			{
				return false;
			}
			Game.submit();
			confiomCard();
			return true;
		}
		
		
		protected function confiomCard():void
		{
			var array:ArrayCollection = new ArrayCollection();
			for each(var line:Line in cardLines)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					array.addItem(cardctp.card);
					cardctp.confimcard = cardctp.card;
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp = line.lastCard);
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
				}
				while(cardctp = line.lastCard);
			}
			Game.reset();
		}
		
		
		public function orderCardByColor():void
		{
			var cards:Array = clearAllCardToArray();
			for each(var card:Card in cards)
			{
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
					while(point!=0) 
					{
						point --;
						cardcpt = cardcpt.nextCardCpt;
					}
					while(cardcpt.card!=null) 
					{
						cardcpt = cardcpt.nextCardCpt;
					}
					cardcpt.card = card;
				}
			}
		}
		
		protected function clearAllCardToArray():Array
		{
			var array:Array = new Array();
			for each(var line:Line in cardLines)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					array.push(cardctp.card);
					cardctp.card = null;
					cardctp = cardctp.nextCardCpt;
				}
				while(cardctp = line.lastCard);
			}
			return array;
		}
	}
}
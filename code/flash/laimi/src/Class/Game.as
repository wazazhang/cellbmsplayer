package Class
{
	import Class.Model.Card;
	import Class.Model.Line;
	import Class.Model.Player;
	
	import Component.Card_Cpt;
	import Component.Matrix_Cpt;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.core.Application;
	public class Game
	{
		public static var app:Application;
		
		//列数
		public static var lineCount:int=13; 
		
		//列数组
		public static var lineArray:ArrayCollection = new ArrayCollection();
		
		//中央矩形
		public static var matrix:Matrix_Cpt;
		
		//数组
		public static var cards:ArrayCollection = new ArrayCollection();
		
		//玩家
		public static var gamer:Player = new Player();
		
		public function Game()
		{
			
		}
		
		public static function initGame():void
		{
			initMatrix();
			gamer.initMatrix();
			initCard();
		}
		//初始矩阵
		public static function initMatrix():void
		{
			for(var i:int=0;i<lineCount;i++)
			{
				var line:Line = new Line(26,false)
				lineArray.addItem(line);
				line.fill(i,matrix);
			}
			var cardcpt:Card_Cpt = (lineArray[0] as Line).firstCard;
			matrix.width = cardcpt.width*(lineArray[0] as Line).lineLength+2;
			matrix.height = cardcpt.height*lineCount+2;
		}
		//初始牌
		public static function initCard():void
		{
			for(var i:int=1;i<=13;i++)
			{
				for(var j:int=1;j<=4;j++)
				{
					var card:Card = new Card(i,j);
					card.order = Math.random();
					cards.addItem(card);
					card = new Card(i,j);
					card.order = Math.random();
					cards.addItem(card);
				}
			}
			card = new Card(0,0);
			card.order = Math.random();
			cards.addItem(card);
			card = new Card(0,0);
			card.order = Math.random();
			cards.addItem(card);
			
			var sort:Sort = new Sort();
			sort.fields = [new SortField("order",false)];
			cards.sort = sort;
			cards.refresh();
		}
		
		//
		public static function getCardFromCard():Card
		{
			return cards.removeItemAt(0) as Card; 
		}
		
		//确定合法性
		public static function check():Boolean
		{
			for each(var line:Line in lineArray)
			{
				if(!line.check())
				{
					return false
				}
			}
			return true;
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
				}
				while(cardctp = line.lastCard);
			}
		}
		
		//提交
		public static function submit():void
		{
			for each(var line:Line in lineArray)
			{
				var cardctp:Card_Cpt = line.firstCard;
				do{
					cardctp.confimcard = null;
					cardctp.confimcard = cardctp.card;
					cardctp.card.isSended = true;
				}
				while(cardctp = line.lastCard);
			}
		}
	}
}
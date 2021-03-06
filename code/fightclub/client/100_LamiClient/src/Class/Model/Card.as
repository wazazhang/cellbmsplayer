package Class.Model
{
	import Class.LanguageManager;
	
	import Component.Card_Cpt;
	
	import com.fc.lami.Messages.CardData;
	
	[Bindable]
	public class Card
	{
		public var id:int;		// 唯一ID
		public var point:int;  //点数(0为任意牌)
		public var type:int;   //颜色
		public var order:Number; //随机排序数
		public var x:int;
		public var y:int;
		
		[Bindable]
		public var isSended:Boolean = false; //是否已经打出
		
//		public var cardUI:Card_Cpt;  //前台展示框
		
//		public var nextCard:Card;//用于卡片顺序添加
		
		public var comfidcard:Card;//确认卡
		
		
		public function Card(point:int,type:int,id:int)
		{
			this.point = point;
			this.type = type;
			this.id = id;
		}
		
		public  function get Color():Number
		{
			if(type==1)
				return 0x070500;
			else if(type==2)
				return 0xb6753b;
			else if(type==3)
				return 0x537a41;	
			else if(type==4)
				return 0xa42921;	
			else(type==0)
				return 0xffffff;			
		}
		
//		public function get x():int
//		{
//			if(cardUI==null)
//				return 0;
//			
//			return cardUI.cardX;
//		}
//		public function get y():int
//		{
//			if(cardUI==null)
//				return 0;
//			
//			return cardUI.cardY;
//		}
		
		
		public function get cardData():CardData
		{
			var carddata:CardData = new CardData();
			carddata.id = id;
			carddata.point = this.point;
			carddata.type = this.type;
			carddata.x = this.x;
			carddata.y = this.y;
			carddata.isSended = this.isSended;
			return carddata;
		}
		
		public static function createCardByData(carddata:CardData):Card
		{
			var card:Card = new Card(carddata.point,carddata.type,carddata.id);
			card.x = carddata.x;
			card.y = carddata.y;
			card.isSended = carddata.isSended;	
			return card;
		}
		
		public static function cardToString(card:CardData):String
		{
			switch (card.type){
				case 0:
					return LanguageManager.getText("card.g"); //鬼牌
				case 1:
					return LanguageManager.getText("card.r")+card.point; //红色
				case 2:
					return LanguageManager.getText("card.y")+card.point; //黄色
				case 3:
					return LanguageManager.getText("card.b")+card.point; //黑色
				case 4:
					return LanguageManager.getText("card.bl")+card.point; //蓝色
			}
			return "";
		}
	}
}
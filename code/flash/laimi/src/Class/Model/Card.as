package Class.Model
{
	import Component.Card_Cpt;
	
	[Bindable]
	public class Card
	{
		public var point:int;  //点数(0为任意牌)
		public var type:int;   //颜色
		public var order:Number; //随机排序数
		
		public var isSended:Boolean = false; //是否已经打出
		
		public var cardUI:Card_Cpt;  //前台展示框
		
		public var nextCard:Card;//用于卡片顺序添加
		
		public function Card(point:int,type:int)
		{
			this.point = point;
			this.type = type;
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
		
	}
}
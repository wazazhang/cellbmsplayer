package Class.Model
{
	import Component.Card_Cpt;
	
	import mx.core.Container;
	
	public class Line
	{
		public var firstCard:Card_Cpt;
		//public var lastCard:Card_Cpt;
		public var lineLength:int=26;
		private var _lastCard:Card_Cpt;
		public var nextLine:Line;
		
		
		//单列检测
		public function Line(length:int,isplayer:Boolean)
		{
			lineLength = length;
			firstCard = new Card_Cpt();
			firstCard.isPlayerOwner = isplayer;
			
			var buff:Card_Cpt = firstCard;
			for(var i:int=1;i<lineLength;i++)
			{
				buff.nextCardCpt = new Card_Cpt();
				buff.nextCardCpt.preCardCpt = buff;
				
				buff.nextCardCpt.isPlayerOwner = isplayer;
				buff = buff.nextCardCpt;	
			}
			lastCard = buff;	
		}
		
		public function set lastCard(card:Card_Cpt):void
		{
			_lastCard = card;
		}
		public function get lastCard():Card_Cpt
		{
			return _lastCard;
		}
		
		public function fill(lie:int,ct:Container):void
		{
			
			var cardcpt:Card_Cpt = firstCard;
			var y:int = lie*cardcpt.height;
			
			for(var i:int=0;i<lineLength;i++)
			{
				cardcpt.x = i*cardcpt.width;
				cardcpt.y = y;
				ct.addChild(cardcpt);
				cardcpt = cardcpt.nextCardCpt;	
			}
		}
		
		public function check():Boolean
		{
			var array:Array;
			var curnode:Card_Cpt = firstCard;
			
			do{
				if(curnode.card == null)
				{
					if(array==null)
					{
						
					}
					else
					{
						if(checkZu(array))
						{
							array = null;
						}
						else
						{
							return false;
						}	
					}
				}
				else
				{
					if(array==null)
					{
						array = new Array();
						array.push(curnode);
					}
					else
					{
						array.push(curnode);
					}	
				}
				
				curnode = curnode.nextCardCpt;
			}
			while(curnode!=lastCard)
			return true;
		}
		
		public function getPoint():int
		{
			var array:Array;
			var curnode:Card_Cpt = firstCard;
			
			var point:int = 0;
			
			do{

				if(!(curnode.card != null&&!curnode.card.isSended))
				{
					if(array==null)
					{
						
					}
					else
					{
						var p:int = getZuPoint(array);
						if(p!=0)
						{
							point += p;
							array = null;
						}
						else
						{
							return 0;
						}	
					}
				}
				else
				{
					if(array==null)
					{
						array = new Array();
						array.push(curnode);
					}
					else
					{
						array.push(curnode);
					}	
				}
				
				curnode = curnode.nextCardCpt;
			}
			while(curnode!=lastCard)
			
			return point;
		}
		
		private function getZuPoint(arr:Array):int
		{
			if(arr.length<3)
				return 0;
			
			var avicards:Array = new Array()
			
			
			for(var i:int=0;i<arr.length;i++)
			{
				var card:Card = (arr[i] as Card_Cpt).card;
				if(card.point!=0)
				{
					avicards.push(card);
				}
				
				if(avicards.length==2)
					break;
			}		
			
			if(avicards.length==1)  //双鬼的时候
				return (avicards[0] as Card).point*3;
			
			
			var percard:Card = avicards[0]; 
			var aftcard:Card = avicards[1];
			
			if(percard.type == aftcard.type)
			{
				return getSunPoint(arr);
			}
			else if(percard.point==aftcard.point)
			{
				return getTongPoint(arr);
			}
			else
			{
				return 0;
			}
		}
		private function getSunPoint(arr:Array):int
		{
			if(arr.length>13)
				return 0;
			var n:int = arr.length;	
			var percar:Card = (arr.shift() as Card_Cpt).card;
			var firstGUST:int = 0;//数组头部的鬼牌
			
			while(percar.point==0)
			{
				firstGUST ++;
				percar = (arr.shift() as Card_Cpt).card;
				if(percar.point<=firstGUST)
				{
					return 0;
				}
			}

			var index:int=0;			
			while(arr.length>0)
			{
				index++;
				var aftcar:Card = (arr.shift() as Card_Cpt).card;
				if(!((percar.point==aftcar.point-index && percar.type == aftcar.type)||aftcar.point==0))
				{
					return 0;
				}
			}
			return (percar.point-firstGUST)*n+n*(n-1)/2;  //等差数列求和公式
		}
		
		
		private function getTongPoint(arr:Array):int
		{
			if(arr.length>4)
				return 0;
				
			var n:int = arr.length;
			var huaArray:Array = new Array();
		
			var card:Card = (arr.shift() as Card_Cpt).card;
			
			while(card.point==0)
			{
				card = (arr.shift() as Card_Cpt).card;
			}

			huaArray.push(card.type);
			var point:int = card.point;

			while(arr.length>0)
			{
				card = (arr.shift() as Card_Cpt).card;
				if(!((huaArray.indexOf(card.type)==-1&&card.point==point)||card.point==0))
				{
					return 0;
				}
				if(card.point!=0)
				huaArray.push(card.type);

			}
			
			return point*n;
		}
		
		
		//检测一个组
		private function checkZu(arr:Array):Boolean
		{
			if(arr.length<3)
				return false;
			
			var avicards:Array = new Array()
			
			
			for(var i:int=0;i<arr.length;i++)
			{
				var card:Card = (arr[i] as Card_Cpt).card;
				if(card.point!=0)
				{
					avicards.push(card);
				}
				
				if(avicards.length==2)
					break;
			}		
			
			if(avicards.length==1)
				return true;
			
			
			var percard:Card = avicards[0]; 
			var aftcard:Card = avicards[1];
			
			if(percard.type == aftcard.type)
			{
				return checkSun(arr);
			}
			else if(percard.point==aftcard.point)
			{
				return checkTong(arr);
			}
			else
			{
				return false;
			}
		}
		
		//检查顺列
		private function checkSun(arr:Array):Boolean
		{
			if(arr.length>13)
				return false;
				
			var percar:Card = (arr.shift() as Card_Cpt).card;
			
			while(percar.point==0)
			{
				percar = (arr.shift() as Card_Cpt).card;
				if(percar.point==1)
				{
					return false;
				}
			}

			var index:int=0;			
			while(arr.length>0)
			{
				index++;
				var aftcar:Card = (arr.shift() as Card_Cpt).card;
				if(!((percar.point==aftcar.point-index && percar.type == aftcar.type)||aftcar.point==0))
				{
					return false;
				}
			}
			return true;
		}
		
		//检查同点
		private function checkTong(arr:Array):Boolean
		{
			if(arr.length>4)
				return false;
			
			var huaArray:Array = new Array();
		
			var card:Card = (arr.shift() as Card_Cpt).card;
			
			while(card.point==0)
			{
				card = (arr.shift() as Card_Cpt).card;
			}

			huaArray.push(card.type);
			var point:int = card.point;
			
			while(arr.length>0)
			{
				card = (arr.shift() as Card_Cpt).card;
				if(!((huaArray.indexOf(card.type)==-1&&card.point==point)||card.point==0))
				{
					return false;
				}
				if(card.point!=0)
				huaArray.push(card.type);
			}
			return true;
		}
	}
}
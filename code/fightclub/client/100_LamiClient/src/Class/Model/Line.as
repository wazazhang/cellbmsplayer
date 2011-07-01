package Class.Model
{
	import Class.Game;
	
	import Component.Card_Cpt;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Container;
	
	public class Line extends ArrayCollection
	{
		public var y:int;
		
		/** 
		 * 初始化1行
		 * */
		public function Line(length:int,isplayer:Boolean,y:int,game:Game)
		{
			this.y = y;
			
			for(var i:int=0;i<length;i++)
			{
				var buff:Card_Cpt = new Card_Cpt();
				buff.cardX = i;
				buff.cardY = y;
				buff.game = game;
				buff.isPlayerOwner = isplayer;
				this.addItem(buff);
			}
		}
		
		public function getFirstCard():Card_Cpt
		{
			var cardcpt:Card_Cpt = this.getItemAt(0) as Card_Cpt;
			return cardcpt;
		}
		
		public function getLastCard():Card_Cpt
		{
			var cardcpt:Card_Cpt = this.getItemAt(this.length-1) as Card_Cpt;
			return cardcpt;
		}
		
		public function getCardByIndex(index:int):Card_Cpt
		{
			var cardcpt:Card_Cpt = this.getItemAt(index) as Card_Cpt;
			return cardcpt;
		}
		
		/** 得到该行最后一个空格 */
		public function getLastBlank():Card_Cpt
		{
			var i:int = this.length-1;
			while(i>=0){
				var cardcpt:Card_Cpt = getCardByIndex(i);
				if (cardcpt.getCard()==null){
					return cardcpt;
				}
				i--;
			}
			return null;
		}
		
		/** 得到该行最前一个空格 */
		public function getFrontBlank():Card_Cpt
		{
			var i:int = 0;
			while(i<this.length){
				var cardcpt:Card_Cpt = getCardByIndex(i);
				if (cardcpt.getCard()==null){
					return cardcpt;
				}
				i++;
			}
			return null;
		}
		
		/** 得到一个成立的牌组 */
		public function getCardGroup(x:int):ArrayCollection
		{
			var cardcpt:Card_Cpt = getCardByIndex(x);
			if (cardcpt.getCard()==null){
				return null;
			}
			var array:ArrayCollection = new ArrayCollection();
			array.addItem(cardcpt);
			return array;
			// 待完成
		}
		
		public function getBlankStart(x:int, length:int):int
		{
			var l:int = length;
			var x2:int = x;
			while(x2<this.length && l>0){
				var cardcpt:Card_Cpt = getCardByIndex(x2);
				if (cardcpt.getCard()!=null && !cardcpt.isSelected){
					break;
				}
				x2++;
				l--;
			}
			
			if (l>0){
				var x1:int = x-1;
				while(x1>=0 && l>0){
					var cardcpt:Card_Cpt = getCardByIndex(x1);
					if (cardcpt.getCard()!=null && !cardcpt.isSelected){
						break;
					}
					x1--;
					l--;
				}
				if (l>0){
					return -1;
				}else{
					return x1+1;
				}
			}else{
				return x;
			}
		}
		
		public function fill(lie:int,ct:Container):void
		{
			
			var cardcpt:Card_Cpt = getFirstCard();
			var y:int = lie*(cardcpt.height);
			
			for(var i:int=0;i<this.length;i++)
			{
				cardcpt=getCardByIndex(i);
				cardcpt.x = i*(cardcpt.width);
				cardcpt.y = y;
				ct.addChild(cardcpt);
			}
		}
		
		//清空当列
		public function clean():void
		{
			for each(var cardcpt:Card_Cpt in this){
				cardcpt.setCard(null);
			}
		}
		
		
		public function check():Boolean
		{
			var array:Array;
	
			for each(var curnode:Card_Cpt in this){
				
				if(curnode.getCard() == null)
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
			}
			if (array!=null){
				return checkZu(array);
			}
				
			return true;
		}
		
		public function findError():void
		{
			
			var array:Array;
			
			for each(var curnode:Card_Cpt in this){
				if(curnode.getCard() == null)
				{
					if(array!=null)
					{
						var buffarr:Array = copyArray(array)
						if(!checkZu(array))
							shanErrorCard(buffarr);
							
						array = null;
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
			}
			
			if (array!=null){
				buffarr = copyArray(array)
				if(!checkZu(array))
					shanErrorCard(buffarr);
			}
		}
		
		
		public function shanErrorCard(arr:Array):void
		{
			for each(var card:Card_Cpt in arr)
			{
				card.shan();
			}
		}
		
		private function copyArray(arr:Array):Array
		{
			var newarr:Array = new Array();
			for each(var obj:Object in arr)
			{
				newarr.push(obj)
			}
			return newarr;
		}
		
		
		//获取没有鬼牌的点数
		public function getPointWithOutGuest():int
		{
			var point:int = 0;

			for each(var curnode:Card_Cpt in this){
				if(curnode.getCard()!=null&&(!curnode.getCard().isSended))
				{
					point +=curnode.getCard().point;
				}
			}

			return point;
		}
		
		
		
		public function getPoint():int
		{
			var array:Array;

			var point:int = 0;
			
			for each(var curnode:Card_Cpt in this){

				if(!(curnode.getCard() != null&&!curnode.getCard().isSended))
				{
					if(array==null)
					{
						
					}
					else
					{
						var p:int = getZuPoint(array);
						point += p;
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
			}
			
			if(array!=null)
			{
				p = getZuPoint(array);
				point += p;
			}
			return point;
		}
		
		private function getZuPoint(arr:Array):int
		{
			if(arr.length<3)
				return 0;
			
			var avicards:Array = new Array()
			
			
			for(var i:int=0;i<arr.length;i++)
			{
				var card:Card = (arr[i] as Card_Cpt).getCard();
				
				
				//确定是否算带鬼组的值
				if(card.point==0)
				{
					return 0;
				}
					
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
			var percar:Card = (arr.shift() as Card_Cpt).getCard();
			var firstGUST:int = 0;//数组头部的鬼牌
			
			while(percar.point==0)
			{
				firstGUST ++;
				percar = (arr.shift() as Card_Cpt).getCard();
				if(percar.point<=firstGUST)
				{
					return 0;
				}
			}

			var index:int=0;			
			while(arr.length>0)
			{
				index++;
				var aftcar:Card = (arr.shift() as Card_Cpt).getCard();
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
		
			var card:Card = (arr.shift() as Card_Cpt).getCard();
			
			while(card.point==0)
			{
				card = (arr.shift() as Card_Cpt).getCard();
			}

			huaArray.push(card.type);
			var point:int = card.point;

			while(arr.length>0)
			{
				card = (arr.shift() as Card_Cpt).getCard();
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
				var card:Card = (arr[i] as Card_Cpt).getCard();
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
				
			var percar:Card = (arr.shift() as Card_Cpt).getCard();
			
			while(percar.point==0)
			{
				percar = (arr.shift() as Card_Cpt).getCard();
				if(percar.point==1)
				{
					return false;
				}
			}

			var index:int=0;			
			while(arr.length>0)
			{
				index++;
				var aftcar:Card = (arr.shift() as Card_Cpt).getCard();
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
		
			var card:Card = (arr.shift() as Card_Cpt).getCard();
			
			while(card.point==0)
			{
				card = (arr.shift() as Card_Cpt).getCard();
			}

			huaArray.push(card.type);
			var point:int = card.point;
			
			while(arr.length>0)
			{
				card = (arr.shift() as Card_Cpt).getCard();
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
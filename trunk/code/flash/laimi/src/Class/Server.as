package Class
{
	import mx.collections.ArrayCollection;

	public class Server
	{
		public function Server()
		{
			
			
		}
		
		//获得服务器端得初始牌
		public function receiveStartCard()
		{
			var startCard:ArrayCollection = new ArrayCollection();
			for(var i:int=0;i<startCard;i++)
			{
				startCard.addItem(Game.getCardFromCard());
			}
			//模拟服务器拿牌
			
			Game.gamer.getStartCard(startCard);
		}
		
		
		
		
		
		
	}
}
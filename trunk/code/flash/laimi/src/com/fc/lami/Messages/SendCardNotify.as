package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [24] [com.fc.lami.Messages.SendCardNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class SendCardNotify extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.CardData[]</font> */
		public var cards :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		public var player_id :  int;

		/**
		 * @param cards as <font color=#0000ff>com.fc.lami.CardData[]</font>
		 * @param player_id as <font color=#0000ff>int</font>		 */
		public function SendCardNotify(
			cards :  Array = null,
			player_id :  int = 0) 
		{
			this.cards = cards;
			this.player_id = player_id;
		}
	}
}
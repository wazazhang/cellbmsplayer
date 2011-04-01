package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.fc.lami.CardData;

	/**
	 * Java Class [6] [com.fc.lami.Messages.GetCardResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class GetCardResponse extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.CardData</font> */
		public var card :  com.fc.lami.CardData;

		/**
		 * @param card as <font color=#0000ff>com.fc.lami.CardData</font>		 */
		public function GetCardResponse(
			card :  com.fc.lami.CardData = null) 
		{
			this.card = card;
		}
	}
}
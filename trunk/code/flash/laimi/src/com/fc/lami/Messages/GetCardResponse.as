package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [10] [com.fc.lami.Messages.GetCardResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class GetCardResponse extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.CardData</font> */
		public var card :  com.fc.lami.Messages.CardData;

		/**
		 * @param card as <font color=#0000ff>com.fc.lami.Messages.CardData</font>		 */
		public function GetCardResponse(
			card :  com.fc.lami.Messages.CardData = null) 
		{
			this.card = card;
		}
	}
}
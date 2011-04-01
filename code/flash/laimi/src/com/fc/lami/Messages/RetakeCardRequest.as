package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [20] [com.fc.lami.Messages.RetakeCardRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class RetakeCardRequest extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.CardData[]</font> */
		public var cards :  Array;

		/**
		 * @param cards as <font color=#0000ff>com.fc.lami.CardData[]</font>		 */
		public function RetakeCardRequest(
			cards :  Array = null) 
		{
			this.cards = cards;
		}
	}
}
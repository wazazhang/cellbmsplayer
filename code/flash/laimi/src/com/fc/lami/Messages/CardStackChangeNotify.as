package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [2] [com.fc.lami.Messages.CardStackChangeNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class CardStackChangeNotify extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		public var card_stack_number :  int;

		/**
		 * @param card_stack_number as <font color=#0000ff>int</font>		 */
		public function CardStackChangeNotify(
			card_stack_number :  int = 0) 
		{
			this.card_stack_number = card_stack_number;
		}
	}
}
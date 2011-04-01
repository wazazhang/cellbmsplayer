package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [10] [com.fc.lami.Messages.MoveCardRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MoveCardRequest extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.CardData[]</font> */
		public var cards :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		public var nx :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		public var ny :  int;

		/**
		 * @param cards as <font color=#0000ff>com.fc.lami.CardData[]</font>
		 * @param nx as <font color=#0000ff>int</font>
		 * @param ny as <font color=#0000ff>int</font>		 */
		public function MoveCardRequest(
			cards :  Array = null,
			nx :  int = 0,
			ny :  int = 0) 
		{
			this.cards = cards;
			this.nx = nx;
			this.ny = ny;
		}
	}
}
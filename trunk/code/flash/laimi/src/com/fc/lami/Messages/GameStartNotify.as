package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [18] [com.fc.lami.Messages.GameStartNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class GameStartNotify extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.CardData[]</font> */
		[JavaType(name="com.fc.lami.Messages.CardData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var cards :  Array;

		/**
		 * @param cards as <font color=#0000ff>com.fc.lami.Messages.CardData[]</font>		 */
		public function GameStartNotify(
			cards :  Array = null) 
		{
			this.cards = cards;
		}
	}
}
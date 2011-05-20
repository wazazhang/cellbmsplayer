package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [23] [com.fc.lami.Messages.GameStartNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class GameStartNotify extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.CardData[]</font> */
		[JavaType(name="com.fc.lami.Messages.CardData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var cards :  Array;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var is_can_reset :  Boolean;

		/**
		 * @param cards as <font color=#0000ff>com.fc.lami.Messages.CardData[]</font>
		 * @param is_can_reset as <font color=#0000ff>boolean</font>		 */
		public function GameStartNotify(
			cards :  Array = null,
			is_can_reset :  Boolean = false) 
		{
			this.cards = cards;
			this.is_can_reset = is_can_reset;
		}
	}
}
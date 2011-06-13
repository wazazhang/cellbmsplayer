package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [38] [com.fc.lami.Messages.MainMatrixChangeNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class MainMatrixChangeNotify extends Message
	{
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var is_hardhanded :  Boolean;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.CardData[]</font> */
		[JavaType(name="com.fc.lami.Messages.CardData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var cards :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;

		/**
		 * @param is_hardhanded as <font color=#0000ff>boolean</font>
		 * @param cards as <font color=#0000ff>com.fc.lami.Messages.CardData[]</font>
		 * @param player_id as <font color=#0000ff>int</font>		 */
		public function MainMatrixChangeNotify(
			is_hardhanded :  Boolean = false,
			cards :  Array = null,
			player_id :  int = 0) 
		{
			this.is_hardhanded = is_hardhanded;
			this.cards = cards;
			this.player_id = player_id;
		}
	}
}
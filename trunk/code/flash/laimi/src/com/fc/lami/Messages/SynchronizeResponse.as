package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [55] [com.fc.lami.Messages.SynchronizeResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	//[Bindable]
	public class SynchronizeResponse extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.CardData[]</font> */
		[JavaType(name="com.fc.lami.Messages.CardData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var matrix :  Array;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.CardData[]</font> */
		[JavaType(name="com.fc.lami.Messages.CardData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var player_card :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var left_card :  int;

		/**
		 * @param matrix as <font color=#0000ff>com.fc.lami.Messages.CardData[]</font>
		 * @param player_card as <font color=#0000ff>com.fc.lami.Messages.CardData[]</font>
		 * @param left_card as <font color=#0000ff>int</font>		 */
		public function SynchronizeResponse(
			matrix :  Array = null,
			player_card :  Array = null,
			left_card :  int = 0) 
		{
			this.matrix = matrix;
			this.player_card = player_card;
			this.left_card = left_card;
		}
	}
}
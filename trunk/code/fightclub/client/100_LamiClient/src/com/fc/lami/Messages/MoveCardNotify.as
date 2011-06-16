package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [43] [com.fc.lami.Messages.MoveCardNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class MoveCardNotify extends Message
	{
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var cards :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var nx :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var ny :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;

		/**
		 * @param cards as <font color=#0000ff>int[]</font>
		 * @param nx as <font color=#0000ff>int</font>
		 * @param ny as <font color=#0000ff>int</font>
		 * @param player_id as <font color=#0000ff>int</font>		 */
		public function MoveCardNotify(
			cards :  Array = null,
			nx :  int = 0,
			ny :  int = 0,
			player_id :  int = 0) 
		{
			this.cards = cards;
			this.nx = nx;
			this.ny = ny;
			this.player_id = player_id;
		}
	}
}
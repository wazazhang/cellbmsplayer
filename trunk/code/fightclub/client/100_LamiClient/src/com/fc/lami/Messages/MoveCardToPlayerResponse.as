package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [49] [com.fc.lami.Messages.MoveCardToPlayerResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class MoveCardToPlayerResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const MOVE_CARD_TO_PLAYER_RESULT_SUCCESS :  int = 0;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const MOVE_CARD_TO_PLAYER_RESULT_FAIL_CANNT_TAKEBACK :  int = 1;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const MOVE_CARD_TO_PLAYER_RESULT_FAIL_CARD_NOEXIST :  int = 2;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var ids :  Array;

		/**
		 * @param result as <font color=#0000ff>int</font>
		 * @param ids as <font color=#0000ff>int[]</font>		 */
		public function MoveCardToPlayerResponse(
			result :  int = 0,
			ids :  Array = null) 
		{
			this.result = result;
			this.ids = ids;
		}
	}
}
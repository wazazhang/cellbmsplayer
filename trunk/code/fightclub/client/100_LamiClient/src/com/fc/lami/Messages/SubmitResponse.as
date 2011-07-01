package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [75] [com.fc.lami.Messages.SubmitResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class SubmitResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SUBMIT_RESULT_SUCCESS :  int = 0;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SUBMIT_RESULT_FAIL_CARD_COMBI_NO_MATCH :  int = 1;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SUBMIT_RESULT_FAIL_CARD_NOT_OPEN_ICE :  int = 2;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SUBMIT_RESULT_FAIL_CARD_NO_SEND :  int = 3;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SUBMIT_RESULT_FAIL_GAME_NOT_START :  int = 4;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var fail_cards :  Array;

		/**
		 * @param result as <font color=#0000ff>int</font>
		 * @param fail_cards as <font color=#0000ff>int[]</font>		 */
		public function SubmitResponse(
			result :  int = 0,
			fail_cards :  Array = null) 
		{
			this.result = result;
			this.fail_cards = fail_cards;
		}
	}
}
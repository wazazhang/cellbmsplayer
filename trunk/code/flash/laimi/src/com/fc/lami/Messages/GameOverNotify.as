package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [16] [com.fc.lami.Messages.GameOverNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class GameOverNotify extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const GAME_OVER_TYPE_CLEAR :  int = 0;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const GAME_OVER_TYPE_CARD_OVER :  int = 1;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const GAME_OVER_TYPE_ESCAPE :  int = 2;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var game_over_type :  int;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.ResultPak[]</font> */
		[JavaType(name="com.fc.lami.Messages.ResultPak[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var result_pak :  Array;

		/**
		 * @param game_over_type as <font color=#0000ff>int</font>
		 * @param result_pak as <font color=#0000ff>com.fc.lami.Messages.ResultPak[]</font>		 */
		public function GameOverNotify(
			game_over_type :  int = 0,
			result_pak :  Array = null) 
		{
			this.game_over_type = game_over_type;
			this.result_pak = result_pak;
		}
	}
}
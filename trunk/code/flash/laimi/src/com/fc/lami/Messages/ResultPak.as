package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [54] [com.fc.lami.Messages.ResultPak]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class ResultPak extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var point :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var is_win :  Boolean;

		/**
		 * @param point as <font color=#0000ff>int</font>
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param is_win as <font color=#0000ff>boolean</font>		 */
		public function ResultPak(
			point :  int = 0,
			player_id :  int = 0,
			is_win :  Boolean = false) 
		{
			this.point = point;
			this.player_id = player_id;
			this.is_win = is_win;
		}
	}
}
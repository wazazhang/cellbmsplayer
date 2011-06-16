package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [51] [com.fc.lami.Messages.ReadyNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class ReadyNotify extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var isReady :  Boolean;

		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param isReady as <font color=#0000ff>boolean</font>		 */
		public function ReadyNotify(
			player_id :  int = 0,
			isReady :  Boolean = false) 
		{
			this.player_id = player_id;
			this.isReady = isReady;
		}
	}
}
package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [53] [com.fc.lami.Messages.PlayerState]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class PlayerState extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var is_ready :  Boolean;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var is_openice :  Boolean;

		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param is_ready as <font color=#0000ff>boolean</font>
		 * @param is_openice as <font color=#0000ff>boolean</font>		 */
		public function PlayerState(
			player_id :  int = 0,
			is_ready :  Boolean = false,
			is_openice :  Boolean = false) 
		{
			this.player_id = player_id;
			this.is_ready = is_ready;
			this.is_openice = is_openice;
		}
	}
}
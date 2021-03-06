package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [80] [com.fc.lami.Messages.TurnStartNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class TurnStartNotify extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var stack_num :  int;

		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param stack_num as <font color=#0000ff>int</font>		 */
		public function TurnStartNotify(
			player_id :  int = 0,
			stack_num :  int = 0) 
		{
			this.player_id = player_id;
			this.stack_num = stack_num;
		}
	}
}
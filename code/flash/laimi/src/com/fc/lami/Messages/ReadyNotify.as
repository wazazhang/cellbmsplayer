package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [15] [com.fc.lami.Messages.ReadyNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class ReadyNotify extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		public var player_id :  int;

		/**
		 * @param player_id as <font color=#0000ff>int</font>		 */
		public function ReadyNotify(
			player_id :  int = 0) 
		{
			this.player_id = player_id;
		}
	}
}
package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [12] [com.fc.lami.Messages.ExitRoomNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class ExitRoomNotify extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData</font> */
		public var player :  com.fc.lami.Messages.PlayerData;

		/**
		 * @param player as <font color=#0000ff>com.fc.lami.Messages.PlayerData</font>		 */
		public function ExitRoomNotify(
			player :  com.fc.lami.Messages.PlayerData = null) 
		{
			this.player = player;
		}
	}
}
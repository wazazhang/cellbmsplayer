package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [7] [com.fc.lami.Messages.EnterDeskNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class EnterDeskNotify extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData</font> */
		public var player :  com.fc.lami.Messages.PlayerData;

		/**
		 * @param player as <font color=#0000ff>com.fc.lami.Messages.PlayerData</font>		 */
		public function EnterDeskNotify(
			player :  com.fc.lami.Messages.PlayerData = null) 
		{
			this.player = player;
		}
	}
}
package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [14] [com.fc.lami.Messages.Player]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class Player extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		public var name :  String;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.Player</font> */
		public var nextPlayer :  com.fc.lami.Messages.Player;

		/**
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param nextPlayer as <font color=#0000ff>com.fc.lami.Messages.Player</font>		 */
		public function Player(
			name :  String = null,
			nextPlayer :  com.fc.lami.Messages.Player = null) 
		{
			this.name = name;
			this.nextPlayer = nextPlayer;
		}
	}
}
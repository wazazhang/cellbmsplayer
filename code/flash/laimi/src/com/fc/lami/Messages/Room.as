package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [23] [com.fc.lami.Messages.Room]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class Room extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		public var RoomId :  String;
		/** Java type is : <font color=#0000ff>boolean</font> */
		public var Started :  Boolean;

		/**
		 * @param RoomId as <font color=#0000ff>java.lang.String</font>
		 * @param Started as <font color=#0000ff>boolean</font>		 */
		public function Room(
			RoomId :  String = null,
			Started :  Boolean = false) 
		{
			this.RoomId = RoomId;
			this.Started = Started;
		}
	}
}
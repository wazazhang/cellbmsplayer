package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [39] [com.fc.lami.Messages.RoomData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class RoomData extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		public var room_id :  int;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.DeskData[]</font> */
		public var desks :  Array;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData[]</font> */
		public var players :  Array;

		/**
		 * @param room_id as <font color=#0000ff>int</font>
		 * @param desks as <font color=#0000ff>com.fc.lami.Messages.DeskData[]</font>
		 * @param players as <font color=#0000ff>com.fc.lami.Messages.PlayerData[]</font>		 */
		public function RoomData(
			room_id :  int = 0,
			desks :  Array = null,
			players :  Array = null) 
		{
			this.room_id = room_id;
			this.desks = desks;
			this.players = players;
		}
	}
}
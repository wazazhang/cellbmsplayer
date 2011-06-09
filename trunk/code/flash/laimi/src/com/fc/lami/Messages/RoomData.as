package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [56] [com.fc.lami.Messages.RoomData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class RoomData extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var room_id :  int;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.DeskData[]</font> */
		[JavaType(name="com.fc.lami.Messages.DeskData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var desks :  Array;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData[]</font> */
		[JavaType(name="com.fc.lami.Messages.PlayerData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
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
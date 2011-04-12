package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [13] [com.fc.lami.Messages.ExitRoomNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class ExitRoomNotify extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData</font> */
		[JavaType(name="com.fc.lami.Messages.PlayerData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var player :  com.fc.lami.Messages.PlayerData;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.RoomData</font> */
		[JavaType(name="com.fc.lami.Messages.RoomData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var curRoom :  com.fc.lami.Messages.RoomData;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.DeskData</font> */
		[JavaType(name="com.fc.lami.Messages.DeskData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var curDesk :  com.fc.lami.Messages.DeskData;

		/**
		 * @param player as <font color=#0000ff>com.fc.lami.Messages.PlayerData</font>
		 * @param curRoom as <font color=#0000ff>com.fc.lami.Messages.RoomData</font>
		 * @param curDesk as <font color=#0000ff>com.fc.lami.Messages.DeskData</font>		 */
		public function ExitRoomNotify(
			player :  com.fc.lami.Messages.PlayerData = null,
			curRoom :  com.fc.lami.Messages.RoomData = null,
			curDesk :  com.fc.lami.Messages.DeskData = null) 
		{
			this.player = player;
			this.curRoom = curRoom;
			this.curDesk = curDesk;
		}
	}
}
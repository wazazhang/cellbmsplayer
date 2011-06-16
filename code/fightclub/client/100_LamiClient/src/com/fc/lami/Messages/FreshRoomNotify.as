package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [17] [com.fc.lami.Messages.FreshRoomNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class FreshRoomNotify extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.RoomSnapShot</font> */
		[JavaType(name="com.fc.lami.Messages.RoomSnapShot", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var room :  com.fc.lami.Messages.RoomSnapShot;

		/**
		 * @param room as <font color=#0000ff>com.fc.lami.Messages.RoomSnapShot</font>		 */
		public function FreshRoomNotify(
			room :  com.fc.lami.Messages.RoomSnapShot = null) 
		{
			this.room = room;
		}
	}
}
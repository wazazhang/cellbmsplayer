package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [66] [com.fc.lami.Messages.RoomSnapShot]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class RoomSnapShot extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var room_id :  int;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var room_name :  String;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_number_max :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_number :  int;

		/**
		 * @param room_id as <font color=#0000ff>int</font>
		 * @param room_name as <font color=#0000ff>java.lang.String</font>
		 * @param player_number_max as <font color=#0000ff>int</font>
		 * @param player_number as <font color=#0000ff>int</font>		 */
		public function RoomSnapShot(
			room_id :  int = 0,
			room_name :  String = null,
			player_number_max :  int = 0,
			player_number :  int = 0) 
		{
			this.room_id = room_id;
			this.room_name = room_name;
			this.player_number_max = player_number_max;
			this.player_number = player_number;
		}
	}
}
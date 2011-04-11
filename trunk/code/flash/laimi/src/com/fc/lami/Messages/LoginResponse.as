package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [26] [com.fc.lami.Messages.LoginResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class LoginResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData</font> */
		[JavaType(name="com.fc.lami.Messages.PlayerData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var player :  com.fc.lami.Messages.PlayerData;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.RoomData[]</font> */
		[JavaType(name="com.fc.lami.Messages.RoomData[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var rooms :  Array;

		/**
		 * @param result as <font color=#0000ff>int</font>
		 * @param player as <font color=#0000ff>com.fc.lami.Messages.PlayerData</font>
		 * @param rooms as <font color=#0000ff>com.fc.lami.Messages.RoomData[]</font>		 */
		public function LoginResponse(
			result :  int = 0,
			player :  com.fc.lami.Messages.PlayerData = null,
			rooms :  Array = null) 
		{
			this.result = result;
			this.player = player;
			this.rooms = rooms;
		}
	}
}
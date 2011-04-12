package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [24] [com.fc.lami.Messages.LeaveDeskRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class LeaveDeskRequest extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData</font> */
		[JavaType(name="com.fc.lami.Messages.PlayerData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var player :  com.fc.lami.Messages.PlayerData;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.DeskData</font> */
		[JavaType(name="com.fc.lami.Messages.DeskData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var desk :  com.fc.lami.Messages.DeskData;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var seatID :  int;

		/**
		 * @param player as <font color=#0000ff>com.fc.lami.Messages.PlayerData</font>
		 * @param desk as <font color=#0000ff>com.fc.lami.Messages.DeskData</font>
		 * @param seatID as <font color=#0000ff>int</font>		 */
		public function LeaveDeskRequest(
			player :  com.fc.lami.Messages.PlayerData = null,
			desk :  com.fc.lami.Messages.DeskData = null,
			seatID :  int = 0) 
		{
			this.player = player;
			this.desk = desk;
			this.seatID = seatID;
		}
	}
}
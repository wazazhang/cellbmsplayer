package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [2] [com.fc.lami.Messages.AutoEnterResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class AutoEnterResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const AUTO_ENETR_RESULT_SUCCESS :  int = 0;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const AUTO_ENTER_RESULT_FAIL_NO_IDLE_SEAT :  int = 1;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.RoomData</font> */
		[JavaType(name="com.fc.lami.Messages.RoomData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var room :  com.fc.lami.Messages.RoomData;

		/**
		 * @param result as <font color=#0000ff>int</font>
		 * @param room as <font color=#0000ff>com.fc.lami.Messages.RoomData</font>		 */
		public function AutoEnterResponse(
			result :  int = 0,
			room :  com.fc.lami.Messages.RoomData = null) 
		{
			this.result = result;
			this.room = room;
		}
	}
}
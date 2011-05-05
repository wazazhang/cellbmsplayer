package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [11] [com.fc.lami.Messages.EnterRoomRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	//[Bindable]
	public class EnterRoomRequest extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var room_no :  int;

		/**
		 * @param room_no as <font color=#0000ff>int</font>		 */
		public function EnterRoomRequest(
			room_no :  int = 0) 
		{
			this.room_no = room_no;
		}
	}
}
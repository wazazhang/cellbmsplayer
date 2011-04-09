package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [26] [com.fc.lami.Messages.LoginResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class LoginResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		public var result :  int;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.RoomData[]</font> */
		public var rooms :  Array;

		/**
		 * @param result as <font color=#0000ff>int</font>
		 * @param rooms as <font color=#0000ff>com.fc.lami.Messages.RoomData[]</font>		 */
		public function LoginResponse(
			result :  int = 0,
			rooms :  Array = null) 
		{
			this.result = result;
			this.rooms = rooms;
		}
	}
}
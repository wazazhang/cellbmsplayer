package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [9] [com.fc.lami.Messages.EnterDeskRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class EnterDeskRequest extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SEAT_EAST :  int = 0;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SEAT_WEST :  int = 1;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SEAT_SOUTH :  int = 2;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SEAT_NORTH :  int = 3;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var desk_No :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var seat :  int;

		/**
		 * @param desk_No as <font color=#0000ff>int</font>
		 * @param seat as <font color=#0000ff>int</font>		 */
		public function EnterDeskRequest(
			desk_No :  int = 0,
			seat :  int = 0) 
		{
			this.desk_No = desk_No;
			this.seat = seat;
		}
	}
}
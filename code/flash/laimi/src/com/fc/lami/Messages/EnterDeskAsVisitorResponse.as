package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [7] [com.fc.lami.Messages.EnterDeskAsVisitorResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class EnterDeskAsVisitorResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const ENTER_DESK_VISITOR_RESULT_SUCCESS :  int = 0;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const ENTER_DESK_VISITOR_RESULT_FAIL_ALREADY_IN_DESK :  int = 1;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const ENTER_DESK_VISITOR_RESULT_FAIL_NO_DESK :  int = 2;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const ENTER_DESK_VISITOR_RESULT_FAIL_NO_ROOM :  int = 3;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var desk_id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var turn_interval :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var operate_time :  int;

		/**
		 * @param result as <font color=#0000ff>int</font>
		 * @param desk_id as <font color=#0000ff>int</font>
		 * @param turn_interval as <font color=#0000ff>int</font>
		 * @param operate_time as <font color=#0000ff>int</font>		 */
		public function EnterDeskAsVisitorResponse(
			result :  int = 0,
			desk_id :  int = 0,
			turn_interval :  int = 0,
			operate_time :  int = 0) 
		{
			this.result = result;
			this.desk_id = desk_id;
			this.turn_interval = turn_interval;
			this.operate_time = operate_time;
		}
	}
}
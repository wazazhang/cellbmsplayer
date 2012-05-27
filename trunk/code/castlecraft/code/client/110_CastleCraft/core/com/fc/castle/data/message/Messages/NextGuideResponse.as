package com.fc.castle.data.message.Messages
{
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.NetDataTypes;
	import com.fc.castle.data.*;
	import com.fc.castle.data.message.*;
	import com.fc.castle.data.message.Messages.*;
	import com.fc.castle.data.template.*;
	import com.fc.castle.data.template.Enums.*;


	/**
	 * 下一关新手引导<br>
	 * Java Class [22] [com.fc.castle.data.message.Messages.NextGuideResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public class NextGuideResponse implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>byte</font>*/
		static public const RESULT_SUCCEED :  int = 1;
		/** Java type is : <font color=#0000ff>byte</font>*/
		static public const RESULT_FAILED_UNKNOW :  int = 2;
		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result :  int;

		/**
		 * @param result as <font color=#0000ff>byte</font>		 */
		public function NextGuideResponse(
			result :  int = 0) 
		{
			this.result = result;
		}
	}
}
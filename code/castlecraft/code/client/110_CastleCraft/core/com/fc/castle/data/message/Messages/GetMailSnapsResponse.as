package com.fc.castle.data.message.Messages
{
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.NetDataTypes;
	import com.cell.util.Map;
	import com.fc.castle.data.*;
	import com.fc.castle.data.message.*;
	import com.fc.castle.data.message.Messages.*;
	import com.fc.castle.data.template.*;
	import com.fc.castle.data.template.Enums.*;


	/**
	 * 获取邮件列表<br>
	 * Java Class [46] [com.fc.castle.data.message.Messages.GetMailSnapsResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetMailSnapsResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_UNKNOW : int = 2;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.PlayerMailData</font>*/
		public var mailSnaps : com.fc.castle.data.PlayerMailData;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param mailSnaps as <font color=#0000ff>com.fc.castle.data.PlayerMailData</font>		 */
		public function GetMailSnapsResponse(
			result : int = 0,
			mailSnaps : com.fc.castle.data.PlayerMailData = null) 
		{
			this.result = result;
			this.mailSnaps = mailSnaps;
		}
		


	}
}
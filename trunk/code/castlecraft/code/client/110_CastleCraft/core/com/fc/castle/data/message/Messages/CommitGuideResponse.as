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
	 * 提交新手引导数据<br>
	 * Java Class [32] [com.fc.castle.data.message.Messages.CommitGuideResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class CommitGuideResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_UNKNOW : int = 2;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.PlayerQuestData</font>*/
		public var data : com.fc.castle.data.PlayerQuestData;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param data as <font color=#0000ff>com.fc.castle.data.PlayerQuestData</font>		 */
		public function CommitGuideResponse(
			result : int = 0,
			data : com.fc.castle.data.PlayerQuestData = null) 
		{
			this.result = result;
			this.data = data;
		}
		


	}
}
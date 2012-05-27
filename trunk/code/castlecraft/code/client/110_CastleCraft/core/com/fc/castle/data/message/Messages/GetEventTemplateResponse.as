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
	 * 获取事件点详细数据<br>
	 * Java Class [36] [com.fc.castle.data.message.Messages.GetEventTemplateResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetEventTemplateResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_UNKNOW : int = 2;

		static public const RESULT_ST_IS_NULL : int = 3;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.template.EventTemplate[]</font>*/
		public var event_templates : Array;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param event_templates as <font color=#0000ff>com.fc.castle.data.template.EventTemplate[]</font>		 */
		public function GetEventTemplateResponse(
			result : int = 0,
			event_templates : Array = null) 
		{
			this.result = result;
			this.event_templates = event_templates;
		}
		


	}
}
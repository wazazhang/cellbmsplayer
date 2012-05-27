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
	 * 获取探索点<br>
	 * Java Class [40] [com.fc.castle.data.message.Messages.GetExploreStateResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetExploreStateResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED : int = 2;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.ExploreState</font>*/
		public var state : com.fc.castle.data.ExploreState;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.template.EventTemplate</font>*/
		public var event : com.fc.castle.data.template.EventTemplate;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param state as <font color=#0000ff>com.fc.castle.data.ExploreState</font>
		 * @param event as <font color=#0000ff>com.fc.castle.data.template.EventTemplate</font>		 */
		public function GetExploreStateResponse(
			result : int = 0,
			state : com.fc.castle.data.ExploreState = null,
			event : com.fc.castle.data.template.EventTemplate = null) 
		{
			this.result = result;
			this.state = state;
			this.event = event;
		}
		


	}
}
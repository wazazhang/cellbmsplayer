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
	 * 战斗结束时反馈结果<br>
	 * Java Class [30] [com.fc.castle.data.message.Messages.CommitBattleResultResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class CommitBattleResultResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_UNKNOW : int = 2;

		static public const RESULT_FAILED_VALIDATE : int = 3;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.ExploreState</font>*/
		public var explore_state : com.fc.castle.data.ExploreState;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param explore_state as <font color=#0000ff>com.fc.castle.data.ExploreState</font>		 */
		public function CommitBattleResultResponse(
			result : int = 0,
			explore_state : com.fc.castle.data.ExploreState = null) 
		{
			this.result = result;
			this.explore_state = explore_state;
		}
		


	}
}
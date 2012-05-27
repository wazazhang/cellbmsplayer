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
	 * 获取探索点列表<br>
	 * Java Class [38] [com.fc.castle.data.message.Messages.GetExploreDataListResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetExploreDataListResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED : int = 2;

		static public const RESULT_FAILED_PLAYER_NOT_FOUND : int = 3;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.PlayerFriend</font>*/
		public var targetPlayer : com.fc.castle.data.PlayerFriend;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var targetPlayerHomeScene : String;

		/** Java type is : <font color=#0000ff>java.util.HashMap</font>*/
		public var explores : com.cell.util.Map;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param targetPlayer as <font color=#0000ff>com.fc.castle.data.PlayerFriend</font>
		 * @param targetPlayerHomeScene as <font color=#0000ff>java.lang.String</font>
		 * @param explores as <font color=#0000ff>java.util.HashMap</font>		 */
		public function GetExploreDataListResponse(
			result : int = 0,
			targetPlayer : com.fc.castle.data.PlayerFriend = null,
			targetPlayerHomeScene : String = null,
			explores : com.cell.util.Map = null) 
		{
			this.result = result;
			this.targetPlayer = targetPlayer;
			this.targetPlayerHomeScene = targetPlayerHomeScene;
			this.explores = explores;
		}
		


	}
}
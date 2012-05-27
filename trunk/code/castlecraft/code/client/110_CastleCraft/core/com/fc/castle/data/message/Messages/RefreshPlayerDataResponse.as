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
	 * 刷新玩家数据返回<br>
	 * Java Class [62] [com.fc.castle.data.message.Messages.RefreshPlayerDataResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class RefreshPlayerDataResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_PLAYER_NOTEXIST : int = 2;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.PlayerData</font>*/
		public var player : com.fc.castle.data.PlayerData;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param player as <font color=#0000ff>com.fc.castle.data.PlayerData</font>		 */
		public function RefreshPlayerDataResponse(
			result : int = 0,
			player : com.fc.castle.data.PlayerData = null) 
		{
			this.result = result;
			this.player = player;
		}
		


	}
}
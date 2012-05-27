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
	 * 获取随机在线玩家<br>
	 * Java Class [50] [com.fc.castle.data.message.Messages.GetRandomOnlinePlayersResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetRandomOnlinePlayersResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED : int = 2;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>java.util.ArrayList</font>*/
		public var friends : Array;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param friends as <font color=#0000ff>java.util.ArrayList</font>		 */
		public function GetRandomOnlinePlayersResponse(
			result : int = 0,
			friends : Array = null) 
		{
			this.result = result;
			this.friends = friends;
		}
		


	}
}
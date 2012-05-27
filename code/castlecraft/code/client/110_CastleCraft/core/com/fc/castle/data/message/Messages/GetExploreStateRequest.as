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
	 * Java Class [39] [com.fc.castle.data.message.Messages.GetExploreStateRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetExploreStateRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var unitName : String;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var targetPlayerID : int;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param unitName as <font color=#0000ff>java.lang.String</font>
		 * @param targetPlayerID as <font color=#0000ff>int</font>		 */
		public function GetExploreStateRequest(
			player_id : int = 0,
			unitName : String = null,
			targetPlayerID : int = 0) 
		{
			this.player_id = player_id;
			this.unitName = unitName;
			this.targetPlayerID = targetPlayerID;
		}
		


	}
}
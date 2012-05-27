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
	 * 获取玩家任务数据<br>
	 * Java Class [47] [com.fc.castle.data.message.Messages.GetPlayerQuestRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetPlayerQuestRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{


		/**
		 * @param player_id as <font color=#0000ff>int</font>		 */
		public function GetPlayerQuestRequest(
			player_id : int = 0) 
		{
			this.player_id = player_id;
		}
		


	}
}
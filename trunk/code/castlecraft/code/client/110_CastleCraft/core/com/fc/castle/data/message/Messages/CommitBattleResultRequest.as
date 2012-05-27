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
	 * Java Class [29] [com.fc.castle.data.message.Messages.CommitBattleResultRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class CommitBattleResultRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>com.fc.castle.data.BattleLog</font>*/
		public var log : com.fc.castle.data.BattleLog;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var scene_unit_name : String;

		/** Java type is : <font color=#0000ff>boolean</font>*/
		public var is_win : Boolean;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param log as <font color=#0000ff>com.fc.castle.data.BattleLog</font>
		 * @param scene_unit_name as <font color=#0000ff>java.lang.String</font>
		 * @param is_win as <font color=#0000ff>boolean</font>		 */
		public function CommitBattleResultRequest(
			player_id : int = 0,
			log : com.fc.castle.data.BattleLog = null,
			scene_unit_name : String = null,
			is_win : Boolean = false) 
		{
			this.player_id = player_id;
			this.log = log;
			this.scene_unit_name = scene_unit_name;
			this.is_win = is_win;
		}
		


	}
}
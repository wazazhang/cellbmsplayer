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
	 * 布防，用于怪物来袭或者玩家来袭<br>
	 * Java Class [59] [com.fc.castle.data.message.Messages.OrganizeDefenseRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class OrganizeDefenseRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>*/
		public var soldiers : com.fc.castle.data.SoldierDatas;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.SkillDatas</font>*/
		public var skills : com.fc.castle.data.SkillDatas;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param soldiers as <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>
		 * @param skills as <font color=#0000ff>com.fc.castle.data.SkillDatas</font>		 */
		public function OrganizeDefenseRequest(
			player_id : int = 0,
			soldiers : com.fc.castle.data.SoldierDatas = null,
			skills : com.fc.castle.data.SkillDatas = null) 
		{
			this.player_id = player_id;
			this.soldiers = soldiers;
			this.skills = skills;
		}
		


	}
}
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
	 * 请求开始战斗<br>
	 * Java Class [25] [com.fc.castle.data.message.Messages.BattleStartRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class BattleStartRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		static public const TYPE_TEST : int = 0;

		static public const TYPE_PLAYER : int = 1;

		static public const TYPE_GUIDE : int = 2;

		static public const TYPE_EXPLORE : int = 3;

		static public const TYPE_EVENT : int = 4;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var battleType : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var targetPlayerID : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var scene_unit_name : String;

		/** SoldierDatas<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>*/
		public var soldiers : com.fc.castle.data.SoldierDatas;

		/** SoldierDatas<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SkillDatas</font>*/
		public var skills : com.fc.castle.data.SkillDatas;

		/** TestForceB-SoldierDatas<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>*/
		public var test_forceB_soldiers : com.fc.castle.data.SoldierDatas;

		/** TestForceB-SoldierDatas<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SkillDatas</font>*/
		public var test_forceB_skills : com.fc.castle.data.SkillDatas;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param battleType as <font color=#0000ff>int</font>
		 * @param targetPlayerID as <font color=#0000ff>int</font>
		 * @param scene_unit_name as <font color=#0000ff>java.lang.String</font>
		 * @param soldiers as <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>
		 * @param skills as <font color=#0000ff>com.fc.castle.data.SkillDatas</font>
		 * @param test_forceB_soldiers as <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>
		 * @param test_forceB_skills as <font color=#0000ff>com.fc.castle.data.SkillDatas</font>		 */
		public function BattleStartRequest(
			player_id : int = 0,
			battleType : int = 0,
			targetPlayerID : int = 0,
			scene_unit_name : String = null,
			soldiers : com.fc.castle.data.SoldierDatas = null,
			skills : com.fc.castle.data.SkillDatas = null,
			test_forceB_soldiers : com.fc.castle.data.SoldierDatas = null,
			test_forceB_skills : com.fc.castle.data.SkillDatas = null) 
		{
			this.player_id = player_id;
			this.battleType = battleType;
			this.targetPlayerID = targetPlayerID;
			this.scene_unit_name = scene_unit_name;
			this.soldiers = soldiers;
			this.skills = skills;
			this.test_forceB_soldiers = test_forceB_soldiers;
			this.test_forceB_skills = test_forceB_skills;
		}
		


	}
}
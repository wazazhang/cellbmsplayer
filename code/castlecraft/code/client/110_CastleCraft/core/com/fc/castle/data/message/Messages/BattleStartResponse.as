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
	 * Java Class [26] [com.fc.castle.data.message.Messages.BattleStartResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class BattleStartResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_UNKNOW : int = 2;

		static public const RESULT_FAILED_VALIDATE : int = 3;

		static public const RESULT_FAILED_EXPLORE_CD : int = 4;

		static public const RESULT_FAILED_TARGET_PLAYER_NOT_EXIST : int = 5;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var battleType : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var targetPlayerID : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var scene_unit_name : String;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var guideStep : int;

		/** 地图名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var cmap_id : String;

		/** 多长时间加一次ap<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var ap_upgrade_time : int;

		/** 加一次ap多少点<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var ap_upgrade_count : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.template.FormualMap</font>*/
		public var formual_map : com.fc.castle.data.template.FormualMap;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.BattlePlayer</font>*/
		public var forceA : com.fc.castle.data.BattlePlayer;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.BattlePlayer</font>*/
		public var forceB : com.fc.castle.data.BattlePlayer;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param battleType as <font color=#0000ff>int</font>
		 * @param targetPlayerID as <font color=#0000ff>int</font>
		 * @param scene_unit_name as <font color=#0000ff>java.lang.String</font>
		 * @param guideStep as <font color=#0000ff>int</font>
		 * @param cmap_id as <font color=#0000ff>java.lang.String</font>
		 * @param ap_upgrade_time as <font color=#0000ff>int</font>
		 * @param ap_upgrade_count as <font color=#0000ff>int</font>
		 * @param formual_map as <font color=#0000ff>com.fc.castle.data.template.FormualMap</font>
		 * @param forceA as <font color=#0000ff>com.fc.castle.data.BattlePlayer</font>
		 * @param forceB as <font color=#0000ff>com.fc.castle.data.BattlePlayer</font>		 */
		public function BattleStartResponse(
			result : int = 0,
			battleType : int = 0,
			targetPlayerID : int = 0,
			scene_unit_name : String = null,
			guideStep : int = 0,
			cmap_id : String = null,
			ap_upgrade_time : int = 0,
			ap_upgrade_count : int = 0,
			formual_map : com.fc.castle.data.template.FormualMap = null,
			forceA : com.fc.castle.data.BattlePlayer = null,
			forceB : com.fc.castle.data.BattlePlayer = null) 
		{
			this.result = result;
			this.battleType = battleType;
			this.targetPlayerID = targetPlayerID;
			this.scene_unit_name = scene_unit_name;
			this.guideStep = guideStep;
			this.cmap_id = cmap_id;
			this.ap_upgrade_time = ap_upgrade_time;
			this.ap_upgrade_count = ap_upgrade_count;
			this.formual_map = formual_map;
			this.forceA = forceA;
			this.forceB = forceB;
		}
		


	}
}
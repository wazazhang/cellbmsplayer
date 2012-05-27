package com.fc.castle.data.template
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
	 * 战斗引导数据<br>
	 * Java Class [79] [com.fc.castle.data.template.GuideData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GuideData extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** 引导步数<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var GUIDE_STEP : int;

		/** 新手引导基础ap<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var GUIDE_START_AP : int;

		/** 新手引导基础mp<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var GUIDE_START_MP : int;

		/** 新手引导基础hp<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var GUIDE_START_HP : int;

		/** 新手引导默认玩家士兵<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var GUIDE_PLAYER_SOLDIER : Array;

		/** 新手引导默认玩家技能<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var GUIDE_PLAYER_SKILL : Array;

		/** 新手引导ai名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var GUIDE_AI_NAME : String;

		/** 新手引导默认AI士兵<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var GUIDE_AI_SOLDIER : Array;

		/** 新手引导默认AI技能<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var GUIDE_AI_SKILL : Array;

		/** 新手引导地图<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var GUIDE_BATTLE_MAP : String;

		/** 事件完成的经验值<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var AWARD_EXP : int;

		/** 事件完成的游戏币<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var AWARD_GOLD : int;

		/** 事件完成掉落道具<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.ItemDatas</font>*/
		public var AWARD_ITEMS : com.fc.castle.data.ItemDatas;



		/**
		 * @param GUIDE_STEP as <font color=#0000ff>int</font>
		 * @param GUIDE_START_AP as <font color=#0000ff>int</font>
		 * @param GUIDE_START_MP as <font color=#0000ff>int</font>
		 * @param GUIDE_START_HP as <font color=#0000ff>int</font>
		 * @param GUIDE_PLAYER_SOLDIER as <font color=#0000ff>int[]</font>
		 * @param GUIDE_PLAYER_SKILL as <font color=#0000ff>int[]</font>
		 * @param GUIDE_AI_NAME as <font color=#0000ff>java.lang.String</font>
		 * @param GUIDE_AI_SOLDIER as <font color=#0000ff>int[]</font>
		 * @param GUIDE_AI_SKILL as <font color=#0000ff>int[]</font>
		 * @param GUIDE_BATTLE_MAP as <font color=#0000ff>java.lang.String</font>
		 * @param AWARD_EXP as <font color=#0000ff>int</font>
		 * @param AWARD_GOLD as <font color=#0000ff>int</font>
		 * @param AWARD_ITEMS as <font color=#0000ff>com.fc.castle.data.ItemDatas</font>		 */
		public function GuideData(
			GUIDE_STEP : int = 0,
			GUIDE_START_AP : int = 0,
			GUIDE_START_MP : int = 0,
			GUIDE_START_HP : int = 0,
			GUIDE_PLAYER_SOLDIER : Array = null,
			GUIDE_PLAYER_SKILL : Array = null,
			GUIDE_AI_NAME : String = null,
			GUIDE_AI_SOLDIER : Array = null,
			GUIDE_AI_SKILL : Array = null,
			GUIDE_BATTLE_MAP : String = null,
			AWARD_EXP : int = 0,
			AWARD_GOLD : int = 0,
			AWARD_ITEMS : com.fc.castle.data.ItemDatas = null) 
		{
			this.GUIDE_STEP = GUIDE_STEP;
			this.GUIDE_START_AP = GUIDE_START_AP;
			this.GUIDE_START_MP = GUIDE_START_MP;
			this.GUIDE_START_HP = GUIDE_START_HP;
			this.GUIDE_PLAYER_SOLDIER = GUIDE_PLAYER_SOLDIER;
			this.GUIDE_PLAYER_SKILL = GUIDE_PLAYER_SKILL;
			this.GUIDE_AI_NAME = GUIDE_AI_NAME;
			this.GUIDE_AI_SOLDIER = GUIDE_AI_SOLDIER;
			this.GUIDE_AI_SKILL = GUIDE_AI_SKILL;
			this.GUIDE_BATTLE_MAP = GUIDE_BATTLE_MAP;
			this.AWARD_EXP = AWARD_EXP;
			this.AWARD_GOLD = AWARD_GOLD;
			this.AWARD_ITEMS = AWARD_ITEMS;
		}
		


	}
}
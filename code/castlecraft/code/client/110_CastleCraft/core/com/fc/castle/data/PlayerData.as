package com.fc.castle.data
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
	 * 玩家数据<br>
	 * Java Class [13] [com.fc.castle.data.PlayerData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class PlayerData extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** PlayerID<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var player_id : int;

		/** 用户名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var player_name : String;

		/** 昵称<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var nick_name : String;

		/** AccountID<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var account_id : String;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var level : int;

		/** 战斗开始hp<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var hp : int;

		/** 战斗开始ap，造兵消耗<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var ap : int;

		/** 战斗开始mp，释放技能消耗<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var mp : int;

		/** 经验值<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var experience : int;

		/** 当前等级的经验<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var cur_exp : int;

		/** 到下一级所需的经验<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var next_exp : int;

		/** 用于游戏内的各类功能与物品消费<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var coin : int;

		/** 该玩家主城(中地图)<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var homeScene : String;

		/** 所有士兵数据<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>*/
		public var soldiers : com.fc.castle.data.SoldierDatas;

		/** 所有技能数据<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SkillDatas</font>*/
		public var skills : com.fc.castle.data.SkillDatas;

		/** 所有道具<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.ItemDatas</font>*/
		public var items : com.fc.castle.data.ItemDatas;

		/** 每场战斗基础携带多少个单位<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var battle_soldier_count : int;

		/** 每场战斗基础携带多少个技能<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var battle_skill_count : int;

		/** 最后一场战斗数据，用于验证战斗结束事件<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.message.Messages.BattleStartResponse</font>*/
		public var lastBattle : com.fc.castle.data.message.Messages.BattleStartResponse;

		/** 布防<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.message.Messages.OrganizeDefenseRequest</font>*/
		public var organizeDefense : com.fc.castle.data.message.Messages.OrganizeDefenseRequest;

		/** 所有探索点的记录<br>
		  * Java type is : <font color=#0000ff>java.util.HashMap</font>*/
		public var exploreStates : com.cell.util.Map;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param player_name as <font color=#0000ff>java.lang.String</font>
		 * @param nick_name as <font color=#0000ff>java.lang.String</font>
		 * @param account_id as <font color=#0000ff>java.lang.String</font>
		 * @param level as <font color=#0000ff>int</font>
		 * @param hp as <font color=#0000ff>int</font>
		 * @param ap as <font color=#0000ff>int</font>
		 * @param mp as <font color=#0000ff>int</font>
		 * @param experience as <font color=#0000ff>int</font>
		 * @param cur_exp as <font color=#0000ff>int</font>
		 * @param next_exp as <font color=#0000ff>int</font>
		 * @param coin as <font color=#0000ff>int</font>
		 * @param homeScene as <font color=#0000ff>java.lang.String</font>
		 * @param soldiers as <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>
		 * @param skills as <font color=#0000ff>com.fc.castle.data.SkillDatas</font>
		 * @param items as <font color=#0000ff>com.fc.castle.data.ItemDatas</font>
		 * @param battle_soldier_count as <font color=#0000ff>int</font>
		 * @param battle_skill_count as <font color=#0000ff>int</font>
		 * @param lastBattle as <font color=#0000ff>com.fc.castle.data.message.Messages.BattleStartResponse</font>
		 * @param organizeDefense as <font color=#0000ff>com.fc.castle.data.message.Messages.OrganizeDefenseRequest</font>
		 * @param exploreStates as <font color=#0000ff>java.util.HashMap</font>		 */
		public function PlayerData(
			player_id : int = 0,
			player_name : String = null,
			nick_name : String = null,
			account_id : String = null,
			level : int = 0,
			hp : int = 0,
			ap : int = 0,
			mp : int = 0,
			experience : int = 0,
			cur_exp : int = 0,
			next_exp : int = 0,
			coin : int = 0,
			homeScene : String = null,
			soldiers : com.fc.castle.data.SoldierDatas = null,
			skills : com.fc.castle.data.SkillDatas = null,
			items : com.fc.castle.data.ItemDatas = null,
			battle_soldier_count : int = 0,
			battle_skill_count : int = 0,
			lastBattle : com.fc.castle.data.message.Messages.BattleStartResponse = null,
			organizeDefense : com.fc.castle.data.message.Messages.OrganizeDefenseRequest = null,
			exploreStates : com.cell.util.Map = null) 
		{
			this.player_id = player_id;
			this.player_name = player_name;
			this.nick_name = nick_name;
			this.account_id = account_id;
			this.level = level;
			this.hp = hp;
			this.ap = ap;
			this.mp = mp;
			this.experience = experience;
			this.cur_exp = cur_exp;
			this.next_exp = next_exp;
			this.coin = coin;
			this.homeScene = homeScene;
			this.soldiers = soldiers;
			this.skills = skills;
			this.items = items;
			this.battle_soldier_count = battle_soldier_count;
			this.battle_skill_count = battle_skill_count;
			this.lastBattle = lastBattle;
			this.organizeDefense = organizeDefense;
			this.exploreStates = exploreStates;
		}
		


	}
}
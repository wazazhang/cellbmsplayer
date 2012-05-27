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
	 * 
	 * Java Class [4] [com.fc.castle.data.BattlePlayer]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class BattlePlayer extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		static public const TYPE_PLAYER : int = 0;

		static public const TYPE_MONSTER : int = 1;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var type : int;

		/** 如果是玩家，则有效<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var playerID : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var name : String;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var hp : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var mp : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var ap : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>*/
		public var soldiers : com.fc.castle.data.SoldierDatas;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.SkillDatas</font>*/
		public var skills : com.fc.castle.data.SkillDatas;



		/**
		 * @param type as <font color=#0000ff>int</font>
		 * @param playerID as <font color=#0000ff>int</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param hp as <font color=#0000ff>int</font>
		 * @param mp as <font color=#0000ff>int</font>
		 * @param ap as <font color=#0000ff>int</font>
		 * @param soldiers as <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>
		 * @param skills as <font color=#0000ff>com.fc.castle.data.SkillDatas</font>		 */
		public function BattlePlayer(
			type : int = 0,
			playerID : int = 0,
			name : String = null,
			hp : int = 0,
			mp : int = 0,
			ap : int = 0,
			soldiers : com.fc.castle.data.SoldierDatas = null,
			skills : com.fc.castle.data.SkillDatas = null) 
		{
			this.type = type;
			this.playerID = playerID;
			this.name = name;
			this.hp = hp;
			this.mp = mp;
			this.ap = ap;
			this.soldiers = soldiers;
			this.skills = skills;
		}
		


	}
}
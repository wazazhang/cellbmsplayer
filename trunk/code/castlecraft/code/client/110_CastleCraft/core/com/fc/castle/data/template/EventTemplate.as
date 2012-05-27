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
	 * 怪物模板数据，一般指地图上的怪物群和怪物进攻事件<br>
	 * Java Class [77] [com.fc.castle.data.template.EventTemplate]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class EventTemplate extends com.fc.castle.data.message.AbstractTemplate implements MutualMessage
	{


		/** 怪物群类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var type : int;

		/** 子类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var subtype : int;

		/** 战斗开始hp<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var hp : int;

		/** 战斗开始ap，造兵消耗<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var ap : int;

		/** 战斗开始mp，释放技能消耗<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var mp : int;

		/** 战斗地图<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var battleMap : String;

		/** 刷新时间(秒)，比如探索点刷新时间，小村庄占领时间<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var refreshTime : int;

		/** 所有士兵数据<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>*/
		public var soldiers : com.fc.castle.data.SoldierDatas;

		/** 所有技能数据<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SkillDatas</font>*/
		public var skills : com.fc.castle.data.SkillDatas;

		/** 事件完成的经验值<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var exp : int;

		/** 事件完成的游戏币<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var gold : int;

		/** 事件完成掉落道具<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.ItemDrops</font>*/
		public var itemDrops : com.fc.castle.data.ItemDrops;

		/** 怪物群名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var name : String;

		/** 描述<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var desc : String;



		/**
		 * @param type as <font color=#0000ff>int</font>
		 * @param subtype as <font color=#0000ff>int</font>
		 * @param hp as <font color=#0000ff>int</font>
		 * @param ap as <font color=#0000ff>int</font>
		 * @param mp as <font color=#0000ff>int</font>
		 * @param battleMap as <font color=#0000ff>java.lang.String</font>
		 * @param refreshTime as <font color=#0000ff>int</font>
		 * @param soldiers as <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>
		 * @param skills as <font color=#0000ff>com.fc.castle.data.SkillDatas</font>
		 * @param exp as <font color=#0000ff>int</font>
		 * @param gold as <font color=#0000ff>int</font>
		 * @param itemDrops as <font color=#0000ff>com.fc.castle.data.ItemDrops</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param desc as <font color=#0000ff>java.lang.String</font>		 */
		public function EventTemplate(
			type : int = 0,
			subtype : int = 0,
			hp : int = 0,
			ap : int = 0,
			mp : int = 0,
			battleMap : String = null,
			refreshTime : int = 0,
			soldiers : com.fc.castle.data.SoldierDatas = null,
			skills : com.fc.castle.data.SkillDatas = null,
			exp : int = 0,
			gold : int = 0,
			itemDrops : com.fc.castle.data.ItemDrops = null,
			name : String = null,
			desc : String = null) 
		{
			this.type = type;
			this.subtype = subtype;
			this.hp = hp;
			this.ap = ap;
			this.mp = mp;
			this.battleMap = battleMap;
			this.refreshTime = refreshTime;
			this.soldiers = soldiers;
			this.skills = skills;
			this.exp = exp;
			this.gold = gold;
			this.itemDrops = itemDrops;
			this.name = name;
			this.desc = desc;
		}
		
		override public function getName() : String {
			return name;
		}

		override public function getType() : int {
			return type;
		}



	}
}
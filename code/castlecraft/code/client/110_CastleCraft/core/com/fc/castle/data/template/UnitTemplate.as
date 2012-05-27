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
	 * 兵种模板数据<br>
	 * Java Class [82] [com.fc.castle.data.template.UnitTemplate]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class UnitTemplate extends com.fc.castle.data.message.AbstractTemplate implements MutualMessage
	{


		/** 兵种类型id<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var type : int;

		/** 对应编辑起精灵<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var csprite_id : String;

		/** 图标<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var icon : String;

		/** 移动类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var motionType : int;

		/** 攻击方式<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var fightType : int;

		/** 攻击力<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var attack : int;

		/** 攻击类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var attackType : int;

		/** 攻击间隔CD<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var attackCD : int;

		/** 攻击距离<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var attackRange : int;

		/** 攻击特效,近战是刀光id，远程是箭矢id<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var attackEffect : String;

		/** 警戒距离<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var guardRange : int;

		/** 防御力<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var defense : int;

		/** 防御类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var defenseType : int;

		/** 血量<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var hp : int;

		/** 魔法值<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var mp : int;

		/** 移动速度<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var moveSpeed : Number;

		/** 训练时间(帧)<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var trainingTime : int;

		/** 花费<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var cost : int;

		/** 兵种技能<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var skills : Array;

		/** 兵种名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var name : String;

		/** 兵种描述<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var description : String;



		/**
		 * @param type as <font color=#0000ff>int</font>
		 * @param csprite_id as <font color=#0000ff>java.lang.String</font>
		 * @param icon as <font color=#0000ff>java.lang.String</font>
		 * @param motionType as <font color=#0000ff>int</font>
		 * @param fightType as <font color=#0000ff>int</font>
		 * @param attack as <font color=#0000ff>int</font>
		 * @param attackType as <font color=#0000ff>int</font>
		 * @param attackCD as <font color=#0000ff>int</font>
		 * @param attackRange as <font color=#0000ff>int</font>
		 * @param attackEffect as <font color=#0000ff>java.lang.String</font>
		 * @param guardRange as <font color=#0000ff>int</font>
		 * @param defense as <font color=#0000ff>int</font>
		 * @param defenseType as <font color=#0000ff>int</font>
		 * @param hp as <font color=#0000ff>int</font>
		 * @param mp as <font color=#0000ff>int</font>
		 * @param moveSpeed as <font color=#0000ff>float</font>
		 * @param trainingTime as <font color=#0000ff>int</font>
		 * @param cost as <font color=#0000ff>int</font>
		 * @param skills as <font color=#0000ff>int[]</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param description as <font color=#0000ff>java.lang.String</font>		 */
		public function UnitTemplate(
			type : int = 0,
			csprite_id : String = null,
			icon : String = null,
			motionType : int = 0,
			fightType : int = 0,
			attack : int = 0,
			attackType : int = 0,
			attackCD : int = 0,
			attackRange : int = 0,
			attackEffect : String = null,
			guardRange : int = 0,
			defense : int = 0,
			defenseType : int = 0,
			hp : int = 0,
			mp : int = 0,
			moveSpeed : Number = 0,
			trainingTime : int = 0,
			cost : int = 0,
			skills : Array = null,
			name : String = null,
			description : String = null) 
		{
			this.type = type;
			this.csprite_id = csprite_id;
			this.icon = icon;
			this.motionType = motionType;
			this.fightType = fightType;
			this.attack = attack;
			this.attackType = attackType;
			this.attackCD = attackCD;
			this.attackRange = attackRange;
			this.attackEffect = attackEffect;
			this.guardRange = guardRange;
			this.defense = defense;
			this.defenseType = defenseType;
			this.hp = hp;
			this.mp = mp;
			this.moveSpeed = moveSpeed;
			this.trainingTime = trainingTime;
			this.cost = cost;
			this.skills = skills;
			this.name = name;
			this.description = description;
		}
		
		override public function getName() : String {
			return name;
		}

		override public function getType() : int {
			return type;
		}



	}
}
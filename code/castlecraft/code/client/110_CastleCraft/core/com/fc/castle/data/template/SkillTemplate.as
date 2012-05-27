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
	 * 玩家技能模板数据<br>
	 * Java Class [81] [com.fc.castle.data.template.SkillTemplate]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class SkillTemplate extends com.fc.castle.data.message.AbstractTemplate implements MutualMessage
	{


		/** 技能类型id<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var type : int;

		/** 攻击力<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var attack : int;

		/** MP消耗<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var costMP : int;

		/** HP消耗<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var costHP : int;

		/** 技能cd<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var coolDown : int;

		/** 技能表现,一般配合spriteEffect使用！<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var spriteBehavior : String;

		/** 精灵特效<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var spriteEffect : String;

		/** 图标<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var icon : String;

		/** 攻击范围,0表示选定一个目标<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var range : int;

		/** 可以攻击多少个附近目标,0表示选定一个目标，正整数表示具体数量，-1表示无上限<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var aoe : int;

		/** 目标类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var targetType : int;

		/** 产生BUFF列表<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var buffList : Array;

		/** 特殊能力列表<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var specialEffects : Array;

		/** 技能名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var name : String;

		/** 描述<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var desc : String;



		/**
		 * @param type as <font color=#0000ff>int</font>
		 * @param attack as <font color=#0000ff>int</font>
		 * @param costMP as <font color=#0000ff>int</font>
		 * @param costHP as <font color=#0000ff>int</font>
		 * @param coolDown as <font color=#0000ff>int</font>
		 * @param spriteBehavior as <font color=#0000ff>java.lang.String</font>
		 * @param spriteEffect as <font color=#0000ff>java.lang.String</font>
		 * @param icon as <font color=#0000ff>java.lang.String</font>
		 * @param range as <font color=#0000ff>int</font>
		 * @param aoe as <font color=#0000ff>int</font>
		 * @param targetType as <font color=#0000ff>int</font>
		 * @param buffList as <font color=#0000ff>int[]</font>
		 * @param specialEffects as <font color=#0000ff>int[]</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param desc as <font color=#0000ff>java.lang.String</font>		 */
		public function SkillTemplate(
			type : int = 0,
			attack : int = 0,
			costMP : int = 0,
			costHP : int = 0,
			coolDown : int = 0,
			spriteBehavior : String = null,
			spriteEffect : String = null,
			icon : String = null,
			range : int = 0,
			aoe : int = 0,
			targetType : int = 0,
			buffList : Array = null,
			specialEffects : Array = null,
			name : String = null,
			desc : String = null) 
		{
			this.type = type;
			this.attack = attack;
			this.costMP = costMP;
			this.costHP = costHP;
			this.coolDown = coolDown;
			this.spriteBehavior = spriteBehavior;
			this.spriteEffect = spriteEffect;
			this.icon = icon;
			this.range = range;
			this.aoe = aoe;
			this.targetType = targetType;
			this.buffList = buffList;
			this.specialEffects = specialEffects;
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
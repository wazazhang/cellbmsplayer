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
	 * BUFF模板数据<br>
	 * Java Class [69] [com.fc.castle.data.template.BuffTemplate]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class BuffTemplate extends com.fc.castle.data.message.AbstractTemplate implements MutualMessage
	{


		/** Buff类型id<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var type : int;

		/** 持续时间<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var time : int;

		/** 负面效果<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var debuff : int;

		/** Buff期间，持续产生的效果<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var effect : String;

		/** 攻击增加％,[百分比]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var enhanceDamage : Number;

		/** 攻击增加直接,[绝对值]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var addDamage : Number;

		/** 伤害减免%,[百分比]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var damageReduction : Number;

		/** 防御力增加％,[百分比]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var enhanceDefens : Number;

		/** 防御力增加,[绝对值]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var addDefense : Number;

		/** 急速等级，减少攻击cd％,[百分比]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var hasteRating : Number;

		/** 移动速度增加％,[百分比]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var moveRating : Number;

		/** 血量增加,[绝对值]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var addHP : Number;

		/** 血量增加％,[百分比]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var enhanceHP : Number;

		/** 持续减血,[伤害]<br>
		  * Java type is : <font color=#0000ff>float</font>*/
		public var burning : Number;

		/** 持续减血,[每隔多少帧触发一次]<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var burningInterval : int;

		/** icon<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var icon : String;

		/** Buff名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var name : String;



		/**
		 * @param type as <font color=#0000ff>int</font>
		 * @param time as <font color=#0000ff>int</font>
		 * @param debuff as <font color=#0000ff>int</font>
		 * @param effect as <font color=#0000ff>java.lang.String</font>
		 * @param enhanceDamage as <font color=#0000ff>float</font>
		 * @param addDamage as <font color=#0000ff>float</font>
		 * @param damageReduction as <font color=#0000ff>float</font>
		 * @param enhanceDefens as <font color=#0000ff>float</font>
		 * @param addDefense as <font color=#0000ff>float</font>
		 * @param hasteRating as <font color=#0000ff>float</font>
		 * @param moveRating as <font color=#0000ff>float</font>
		 * @param addHP as <font color=#0000ff>float</font>
		 * @param enhanceHP as <font color=#0000ff>float</font>
		 * @param burning as <font color=#0000ff>float</font>
		 * @param burningInterval as <font color=#0000ff>int</font>
		 * @param icon as <font color=#0000ff>java.lang.String</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>		 */
		public function BuffTemplate(
			type : int = 0,
			time : int = 0,
			debuff : int = 0,
			effect : String = null,
			enhanceDamage : Number = 0,
			addDamage : Number = 0,
			damageReduction : Number = 0,
			enhanceDefens : Number = 0,
			addDefense : Number = 0,
			hasteRating : Number = 0,
			moveRating : Number = 0,
			addHP : Number = 0,
			enhanceHP : Number = 0,
			burning : Number = 0,
			burningInterval : int = 0,
			icon : String = null,
			name : String = null) 
		{
			this.type = type;
			this.time = time;
			this.debuff = debuff;
			this.effect = effect;
			this.enhanceDamage = enhanceDamage;
			this.addDamage = addDamage;
			this.damageReduction = damageReduction;
			this.enhanceDefens = enhanceDefens;
			this.addDefense = addDefense;
			this.hasteRating = hasteRating;
			this.moveRating = moveRating;
			this.addHP = addHP;
			this.enhanceHP = enhanceHP;
			this.burning = burning;
			this.burningInterval = burningInterval;
			this.icon = icon;
			this.name = name;
		}
		
		override public function getName() : String {
			return name;
		}

		override public function getType() : int {
			return type;
		}



	}
}
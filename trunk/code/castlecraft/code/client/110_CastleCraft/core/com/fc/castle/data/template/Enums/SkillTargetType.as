package com.fc.castle.data.template.Enums
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
	 * 玩家技能目标类型，对应字段[SkillTemplate.targetType]<br>
	 * Java Class [76] [com.fc.castle.data.template.Enums.SkillTargetType]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class SkillTargetType extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{
		/** 敌人<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const ENEMY : int = 1;

		/** 友军<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const FIREND : int = 2;

		/** 敌方和友军<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const BOTH : int = 3;

		/** 随机敌人<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const RANDOM_ENEMY : int = 4;

		/** 随机友军<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const RANDOM_FIREND : int = 5;




		public function SkillTargetType() 
		{

		}
		


	}
}
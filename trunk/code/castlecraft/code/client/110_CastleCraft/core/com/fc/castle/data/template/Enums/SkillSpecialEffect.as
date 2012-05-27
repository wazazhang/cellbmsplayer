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
	 * 玩家技能特殊效果，对应字段[SkillTemplate.specialEffects]<br>
	 * Java Class [75] [com.fc.castle.data.template.Enums.SkillSpecialEffect]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class SkillSpecialEffect extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{
		/** 净化,清除友军负面BUFF效果<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const PURGE : int = 1;

		/** 诅咒,清除敌军正面BUFF效果<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const CURSE : int = 2;

		/** 叛变,敌军转换为友军<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const RENEGADE : int = 3;




		public function SkillSpecialEffect() 
		{

		}
		


	}
}
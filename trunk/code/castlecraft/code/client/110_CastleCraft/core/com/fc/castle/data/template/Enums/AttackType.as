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
	 * 攻击类型<br>
	 * Java Class [70] [com.fc.castle.data.template.Enums.AttackType]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class AttackType extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{
		/** 普通<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const NORMAL : int = 0;

		/** 穿刺<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const PIERCE : int = 1;

		/** 攻城<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const SIEGE : int = 2;

		/** 魔法<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const MAGIC : int = 3;

		/** 混乱<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const CHAOS : int = 4;

		/** 英雄<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const HERO : int = 5;

		/** 技能<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const SKILL : int = 6;




		public function AttackType() 
		{

		}
		


	}
}
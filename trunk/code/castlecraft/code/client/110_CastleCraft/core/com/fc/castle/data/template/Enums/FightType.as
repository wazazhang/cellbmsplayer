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
	 * 攻击方式<br>
	 * Java Class [72] [com.fc.castle.data.template.Enums.FightType]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class FightType extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{
		/** 近战<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const NORMAL : int = 0;

		/** 飞弹<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const MISSILE : int = 1;

		/** 火炮<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const ARTILLERY : int = 2;

		/** 直接<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const INSTANT : int = 3;




		public function FightType() 
		{

		}
		


	}
}
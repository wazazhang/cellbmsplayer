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
	 * 防御类型<br>
	 * Java Class [71] [com.fc.castle.data.template.Enums.DefenseType]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class DefenseType extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{
		/** 无甲<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const UNARMORED : int = 0;

		/** 轻甲<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const LIGHT : int = 1;

		/** 中甲<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const MEDIUM : int = 2;

		/** 重甲<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const HEAVY : int = 3;

		/** 英雄<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const HERO : int = 4;

		/** 城甲<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const FORT : int = 5;




		public function DefenseType() 
		{

		}
		


	}
}
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
	 * 移动类型<br>
	 * Java Class [73] [com.fc.castle.data.template.Enums.MotionType]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class MotionType extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{
		/** 陆地<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const LAND : int = 0;

		/** 飞行<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const FLY : int = 1;




		public function MotionType() 
		{

		}
		


	}
}
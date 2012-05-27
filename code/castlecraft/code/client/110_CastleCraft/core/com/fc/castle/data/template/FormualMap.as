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
	 * 
	 * Java Class [78] [com.fc.castle.data.template.FormualMap]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class FormualMap extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** [attackType, defenseType, 攻击加成],[attackType, defenseType, 攻击加成],<br>
		  * Java type is : <font color=#0000ff>float[][]</font>*/
		public var ammor_map : Array;



		/**
		 * @param ammor_map as <font color=#0000ff>float[][]</font>		 */
		public function FormualMap(
			ammor_map : Array = null) 
		{
			this.ammor_map = ammor_map;
		}
		


	}
}
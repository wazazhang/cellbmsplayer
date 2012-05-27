package com.fc.castle.data
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
	 * Java Class [19] [com.fc.castle.data.SkillData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class SkillData extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>int</font>*/
		public var skillType : int;



		/**
		 * @param skillType as <font color=#0000ff>int</font>		 */
		public function SkillData(
			skillType : int = 0) 
		{
			this.skillType = skillType;
		}
		


	}
}
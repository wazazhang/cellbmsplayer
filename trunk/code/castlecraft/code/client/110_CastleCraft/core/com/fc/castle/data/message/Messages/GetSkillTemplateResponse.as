package com.fc.castle.data.message.Messages
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
	 * 获取玩家技能详细数据<br>
	 * Java Class [54] [com.fc.castle.data.message.Messages.GetSkillTemplateResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetSkillTemplateResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_UNKNOW : int = 2;

		static public const RESULT_ST_IS_NULL : int = 3;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.template.SkillTemplate[]</font>*/
		public var skill_templates : Array;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param skill_templates as <font color=#0000ff>com.fc.castle.data.template.SkillTemplate[]</font>		 */
		public function GetSkillTemplateResponse(
			result : int = 0,
			skill_templates : Array = null) 
		{
			this.result = result;
			this.skill_templates = skill_templates;
		}
		


	}
}
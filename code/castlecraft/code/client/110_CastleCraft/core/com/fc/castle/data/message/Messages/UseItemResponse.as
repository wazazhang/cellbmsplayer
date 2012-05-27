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
	 * 使用道具<br>
	 * Java Class [66] [com.fc.castle.data.message.Messages.UseItemResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class UseItemResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED : int = 2;

		static public const RESULT_FAILED_NOT_ENOUGH : int = 3;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** 该道具栏位道具状态<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.ItemData</font>*/
		public var item_slot : com.fc.castle.data.ItemData;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param item_slot as <font color=#0000ff>com.fc.castle.data.ItemData</font>		 */
		public function UseItemResponse(
			result : int = 0,
			item_slot : com.fc.castle.data.ItemData = null) 
		{
			this.result = result;
			this.item_slot = item_slot;
		}
		


	}
}
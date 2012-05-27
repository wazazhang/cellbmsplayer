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
	 * 获取道具模板数据<br>
	 * Java Class [41] [com.fc.castle.data.message.Messages.GetItemTemplateRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetItemTemplateRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>int[]</font>*/
		public var item_types : Array;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param item_types as <font color=#0000ff>int[]</font>		 */
		public function GetItemTemplateRequest(
			player_id : int = 0,
			item_types : Array = null) 
		{
			this.player_id = player_id;
			this.item_types = item_types;
		}
		


	}
}
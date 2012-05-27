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
	 * Java Class [65] [com.fc.castle.data.message.Messages.UseItemRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class UseItemRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>int</font>*/
		public var indexOfItems : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var count : int;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param indexOfItems as <font color=#0000ff>int</font>
		 * @param count as <font color=#0000ff>int</font>		 */
		public function UseItemRequest(
			player_id : int = 0,
			indexOfItems : int = 0,
			count : int = 0) 
		{
			this.player_id = player_id;
			this.indexOfItems = indexOfItems;
			this.count = count;
		}
		


	}
}
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
	 * 购买商店道具<br>
	 * Java Class [28] [com.fc.castle.data.message.Messages.BuyShopItemResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class BuyShopItemResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED : int = 2;

		static public const RESULT_NEED_MORE_COIN : int = 3;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var bag_index : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.ItemData</font>*/
		public var bag_item : com.fc.castle.data.ItemData;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var my_coin : int;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param bag_index as <font color=#0000ff>int</font>
		 * @param bag_item as <font color=#0000ff>com.fc.castle.data.ItemData</font>
		 * @param my_coin as <font color=#0000ff>int</font>		 */
		public function BuyShopItemResponse(
			result : int = 0,
			bag_index : int = 0,
			bag_item : com.fc.castle.data.ItemData = null,
			my_coin : int = 0) 
		{
			this.result = result;
			this.bag_index = bag_index;
			this.bag_item = bag_item;
			this.my_coin = my_coin;
		}
		


	}
}
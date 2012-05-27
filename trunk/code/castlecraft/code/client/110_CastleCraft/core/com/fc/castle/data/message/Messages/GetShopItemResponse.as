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
	 * 获取商店道具<br>
	 * Java Class [52] [com.fc.castle.data.message.Messages.GetShopItemResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class GetShopItemResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED : int = 2;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.ShopItems</font>*/
		public var items : com.fc.castle.data.ShopItems;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param items as <font color=#0000ff>com.fc.castle.data.ShopItems</font>		 */
		public function GetShopItemResponse(
			result : int = 0,
			items : com.fc.castle.data.ShopItems = null) 
		{
			this.result = result;
			this.items = items;
		}
		


	}
}
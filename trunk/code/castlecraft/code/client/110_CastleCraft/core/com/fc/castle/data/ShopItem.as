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
	 * Java Class [17] [com.fc.castle.data.ShopItem]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class ShopItem extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** 道具类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var itemType : int;

		/** 数量<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var count : int;



		/**
		 * @param itemType as <font color=#0000ff>int</font>
		 * @param count as <font color=#0000ff>int</font>		 */
		public function ShopItem(
			itemType : int = 0,
			count : int = 0) 
		{
			this.itemType = itemType;
			this.count = count;
		}
		


	}
}
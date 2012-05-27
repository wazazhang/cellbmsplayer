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
	 * Java Class [10] [com.fc.castle.data.ItemDrops]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class ItemDrops extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>java.util.ArrayList</font>*/
		public var datas : Array;



		/**
		 * @param datas as <font color=#0000ff>java.util.ArrayList</font>		 */
		public function ItemDrops(
			datas : Array = null) 
		{
			this.datas = datas;
		}
		


	}
}
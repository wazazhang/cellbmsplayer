package com.slg.entity
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;
	import com.slg.entity.*;

	/**
	 * Java Class [3] [com.slg.entity.Currency]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class Currency extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String[]</font> */
		[JavaType(name="java.lang.String[]", leaf_type=NetDataTypes.TYPE_STRING)]
		public var key :  Array;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var value :  Array;

		/**
		 * @param key as <font color=#0000ff>java.lang.String[]</font>
		 * @param value as <font color=#0000ff>int[]</font>		 */
		public function Currency(
			key :  Array = null,
			value :  Array = null) 
		{
			this.key = key;
			this.value = value;
		}
	}
}
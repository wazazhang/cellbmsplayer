package com.slg.entity
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;
	import com.slg.entity.*;

	/**
	 * Java Class [8] [com.slg.entity.Soldiers]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class Soldiers extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var type :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var count :  int;

		/**
		 * @param type as <font color=#0000ff>int</font>
		 * @param count as <font color=#0000ff>int</font>		 */
		public function Soldiers(
			type :  int = 0,
			count :  int = 0) 
		{
			this.type = type;
			this.count = count;
		}
	}
}
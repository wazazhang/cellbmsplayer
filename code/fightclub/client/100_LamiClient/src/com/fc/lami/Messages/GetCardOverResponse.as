package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [27] [com.fc.lami.Messages.GetCardOverResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class GetCardOverResponse extends Message
	{
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var is_can_reset :  Boolean;

		/**
		 * @param is_can_reset as <font color=#0000ff>boolean</font>		 */
		public function GetCardOverResponse(
			is_can_reset :  Boolean = false) 
		{
			this.is_can_reset = is_can_reset;
		}
	}
}
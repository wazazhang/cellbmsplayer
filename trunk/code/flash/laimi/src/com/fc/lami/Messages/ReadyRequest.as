package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [34] [com.fc.lami.Messages.ReadyRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class ReadyRequest extends Message
	{
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var isReady :  Boolean;

		/**
		 * @param isReady as <font color=#0000ff>boolean</font>		 */
		public function ReadyRequest(
			isReady :  Boolean = false) 
		{
			this.isReady = isReady;
		}
	}
}
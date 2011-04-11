package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [9] [com.fc.lami.Messages.EnterDeskResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class EnterDeskResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.DeskData</font> */
		[JavaType(name="com.fc.lami.Messages.DeskData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var desk :  com.fc.lami.Messages.DeskData;

		/**
		 * @param result as <font color=#0000ff>int</font>
		 * @param desk as <font color=#0000ff>com.fc.lami.Messages.DeskData</font>		 */
		public function EnterDeskResponse(
			result :  int = 0,
			desk :  com.fc.lami.Messages.DeskData = null) 
		{
			this.result = result;
			this.desk = desk;
		}
	}
}
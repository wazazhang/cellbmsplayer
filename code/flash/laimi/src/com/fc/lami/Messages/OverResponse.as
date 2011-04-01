package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [13] [com.fc.lami.Messages.OverResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class OverResponse extends Message
	{
		/** Java type is : <font color=#0000ff>short</font> */
		public var result :  int;

		/**
		 * @param result as <font color=#0000ff>short</font>		 */
		public function OverResponse(
			result :  int = 0) 
		{
			this.result = result;
		}
	}
}
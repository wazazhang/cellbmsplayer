package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [21] [com.fc.lami.Messages.GetTimeResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class GetTimeResponse extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		public var time :  String;

		/**
		 * @param time as <font color=#0000ff>java.lang.String</font>		 */
		public function GetTimeResponse(
			time :  String = null) 
		{
			this.time = time;
		}
	}
}
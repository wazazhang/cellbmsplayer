package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [7] [com.fc.lami.Messages.EnterDeskRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class EnterDeskRequest extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		public var seat :  int;

		/**
		 * @param seat as <font color=#0000ff>int</font>		 */
		public function EnterDeskRequest(
			seat :  int = 0) 
		{
			this.seat = seat;
		}
	}
}
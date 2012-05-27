package com.fc.castle.data.message.Messages
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
	 * 发送邮件<br>
	 * Java Class [63] [com.fc.castle.data.message.Messages.SendMailRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class SendMailRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>com.fc.castle.data.Mail</font>*/
		public var mail : com.fc.castle.data.Mail;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param mail as <font color=#0000ff>com.fc.castle.data.Mail</font>		 */
		public function SendMailRequest(
			player_id : int = 0,
			mail : com.fc.castle.data.Mail = null) 
		{
			this.player_id = player_id;
			this.mail = mail;
		}
		


	}
}
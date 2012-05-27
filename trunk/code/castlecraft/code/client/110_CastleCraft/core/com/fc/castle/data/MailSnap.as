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
	 * 邮件简介<br>
	 * Java Class [12] [com.fc.castle.data.MailSnap]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class MailSnap extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>int</font>*/
		public var id : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var title : String;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var senderPlayerName : String;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var senderPlayerID : int;

		/** Java type is : <font color=#0000ff>boolean</font>*/
		public var readed : Boolean;



		/**
		 * @param id as <font color=#0000ff>int</font>
		 * @param title as <font color=#0000ff>java.lang.String</font>
		 * @param senderPlayerName as <font color=#0000ff>java.lang.String</font>
		 * @param senderPlayerID as <font color=#0000ff>int</font>
		 * @param readed as <font color=#0000ff>boolean</font>		 */
		public function MailSnap(
			id : int = 0,
			title : String = null,
			senderPlayerName : String = null,
			senderPlayerID : int = 0,
			readed : Boolean = false) 
		{
			this.id = id;
			this.title = title;
			this.senderPlayerName = senderPlayerName;
			this.senderPlayerID = senderPlayerID;
			this.readed = readed;
		}
		


	}
}
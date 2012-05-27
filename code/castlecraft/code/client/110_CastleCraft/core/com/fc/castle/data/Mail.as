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
	 * mail<br>
	 * Java Class [11] [com.fc.castle.data.Mail]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class Mail extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>int</font>*/
		public var id : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var type : int;

		/** Java type is : <font color=#0000ff>java.sql.Date</font>*/
		public var sendTime : Date;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var cyccode : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var senderName : String;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var receiverName : String;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var senderPlayerID : int;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var receiverPlayerID : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var title : String;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var content : String;

		/** Java type is : <font color=#0000ff>boolean</font>*/
		public var readed : Boolean;



		/**
		 * @param id as <font color=#0000ff>int</font>
		 * @param type as <font color=#0000ff>int</font>
		 * @param sendTime as <font color=#0000ff>java.sql.Date</font>
		 * @param cyccode as <font color=#0000ff>int</font>
		 * @param senderName as <font color=#0000ff>java.lang.String</font>
		 * @param receiverName as <font color=#0000ff>java.lang.String</font>
		 * @param senderPlayerID as <font color=#0000ff>int</font>
		 * @param receiverPlayerID as <font color=#0000ff>int</font>
		 * @param title as <font color=#0000ff>java.lang.String</font>
		 * @param content as <font color=#0000ff>java.lang.String</font>
		 * @param readed as <font color=#0000ff>boolean</font>		 */
		public function Mail(
			id : int = 0,
			type : int = 0,
			sendTime : Date = null,
			cyccode : int = 0,
			senderName : String = null,
			receiverName : String = null,
			senderPlayerID : int = 0,
			receiverPlayerID : int = 0,
			title : String = null,
			content : String = null,
			readed : Boolean = false) 
		{
			this.id = id;
			this.type = type;
			this.sendTime = sendTime;
			this.cyccode = cyccode;
			this.senderName = senderName;
			this.receiverName = receiverName;
			this.senderPlayerID = senderPlayerID;
			this.receiverPlayerID = receiverPlayerID;
			this.title = title;
			this.content = content;
			this.readed = readed;
		}
		


	}
}
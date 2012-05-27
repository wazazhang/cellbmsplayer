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
	 * 登录请求<br>
	 * Java Class [57] [com.fc.castle.data.message.Messages.LoginRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class LoginRequest extends com.fc.castle.data.message.Request implements MutualMessage
	{
		/** 帐号id<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var account_id : String;

		/** 验证串<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var sign : String;

		/** 是否自动创建帐号，如果服务器支持<br>
		  * Java type is : <font color=#0000ff>boolean</font>*/
		public var create : Boolean;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var createNickName : String;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param account_id as <font color=#0000ff>java.lang.String</font>
		 * @param sign as <font color=#0000ff>java.lang.String</font>
		 * @param create as <font color=#0000ff>boolean</font>
		 * @param createNickName as <font color=#0000ff>java.lang.String</font>		 */
		public function LoginRequest(
			player_id : int = 0,
			account_id : String = null,
			sign : String = null,
			create : Boolean = false,
			createNickName : String = null) 
		{
			this.player_id = player_id;
			this.account_id = account_id;
			this.sign = sign;
			this.create = create;
			this.createNickName = createNickName;
		}
		


	}
}
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
	 * 登录回馈<br>
	 * Java Class [58] [com.fc.castle.data.message.Messages.LoginResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class LoginResponse extends com.fc.castle.data.message.Response implements MutualMessage
	{
		static public const RESULT_SUCCEED : int = 1;

		static public const RESULT_FAILED_UNKNOW : int = 2;

		static public const RESULT_FAILED_USER_NOT_EXIST : int = 3;

		static public const RESULT_FAILED_USER_SIGN : int = 4;

		/** 如何<br>
		  * Java type is : <font color=#0000ff>byte</font>*/
		public var result : int;

		/** 你的帐号id<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var login_account : String;

		/** 登录获取的钥匙，登录后获取唯一钥匙，游戏内操作需要此钥匙作为验证。<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var login_key : String;

		/** 玩家数据，如果一个帐号有多个玩家，则返回数组<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.PlayerData[]</font>*/
		public var player_data : Array;

		/** Java type is : <font color=#0000ff>com.fc.castle.data.PlayerQuestData[]</font>*/
		public var player_quests : Array;



		/**
		 * @param result as <font color=#0000ff>byte</font>
		 * @param login_account as <font color=#0000ff>java.lang.String</font>
		 * @param login_key as <font color=#0000ff>java.lang.String</font>
		 * @param player_data as <font color=#0000ff>com.fc.castle.data.PlayerData[]</font>
		 * @param player_quests as <font color=#0000ff>com.fc.castle.data.PlayerQuestData[]</font>		 */
		public function LoginResponse(
			result : int = 0,
			login_account : String = null,
			login_key : String = null,
			player_data : Array = null,
			player_quests : Array = null) 
		{
			this.result = result;
			this.login_account = login_account;
			this.login_key = login_key;
			this.player_data = player_data;
			this.player_quests = player_quests;
		}
		


	}
}
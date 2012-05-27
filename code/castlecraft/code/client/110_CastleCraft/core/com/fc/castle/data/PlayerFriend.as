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
	 * 玩家数据<br>
	 * Java Class [14] [com.fc.castle.data.PlayerFriend]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class PlayerFriend extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>int</font>*/
		public var playerID : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var playerName : String;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var playerNickName : String;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var headUrl : String;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var level : int;



		/**
		 * @param playerID as <font color=#0000ff>int</font>
		 * @param playerName as <font color=#0000ff>java.lang.String</font>
		 * @param playerNickName as <font color=#0000ff>java.lang.String</font>
		 * @param headUrl as <font color=#0000ff>java.lang.String</font>
		 * @param level as <font color=#0000ff>int</font>		 */
		public function PlayerFriend(
			playerID : int = 0,
			playerName : String = null,
			playerNickName : String = null,
			headUrl : String = null,
			level : int = 0) 
		{
			this.playerID = playerID;
			this.playerName = playerName;
			this.playerNickName = playerNickName;
			this.headUrl = headUrl;
			this.level = level;
		}
		


	}
}
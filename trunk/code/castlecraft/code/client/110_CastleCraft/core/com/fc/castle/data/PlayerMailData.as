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
	 * 玩家邮件数据<br>
	 * Java Class [15] [com.fc.castle.data.PlayerMailData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class PlayerMailData extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>int</font>*/
		public var player_id : int;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var player_name : String;

		/** Java type is : <font color=#0000ff>java.util.HashMap</font>*/
		public var mails : com.cell.util.Map;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var friendsMaxCount : int;

		/** Java type is : <font color=#0000ff>java.util.HashMap</font>*/
		public var firends : com.cell.util.Map;



		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param player_name as <font color=#0000ff>java.lang.String</font>
		 * @param mails as <font color=#0000ff>java.util.HashMap</font>
		 * @param friendsMaxCount as <font color=#0000ff>int</font>
		 * @param firends as <font color=#0000ff>java.util.HashMap</font>		 */
		public function PlayerMailData(
			player_id : int = 0,
			player_name : String = null,
			mails : com.cell.util.Map = null,
			friendsMaxCount : int = 0,
			firends : com.cell.util.Map = null) 
		{
			this.player_id = player_id;
			this.player_name = player_name;
			this.mails = mails;
			this.friendsMaxCount = friendsMaxCount;
			this.firends = firends;
		}
		


	}
}
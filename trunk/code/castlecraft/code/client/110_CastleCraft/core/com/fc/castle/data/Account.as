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
	 * 
	 * Java Class [1] [com.fc.castle.data.Account]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class Account extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** 用户唯一id<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var id : String;

		/** 用户验证串<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var sign : String;

		/** RMB<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var gold : int;

		/** 所有玩家数据<br>
		  * Java type is : <font color=#0000ff>int[]</font>*/
		public var players_id : Array;



		/**
		 * @param id as <font color=#0000ff>java.lang.String</font>
		 * @param sign as <font color=#0000ff>java.lang.String</font>
		 * @param gold as <font color=#0000ff>int</font>
		 * @param players_id as <font color=#0000ff>int[]</font>		 */
		public function Account(
			id : String = null,
			sign : String = null,
			gold : int = 0,
			players_id : Array = null) 
		{
			this.id = id;
			this.sign = sign;
			this.gold = gold;
			this.players_id = players_id;
		}
		


	}
}
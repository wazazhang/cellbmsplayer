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
	 * Java Class [5] [com.fc.castle.data.ExploreData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class ExploreData extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var UnitName : String;

		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var explore_name : String;

		/** SECONDS<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var refreshTime : int;

		/** SECONDS<br>
		  * Java type is : <font color=#0000ff>java.sql.Date</font>*/
		public var last_time : Date;

		/** 如果为空，则表示式本人探索过该点，否则是别的玩家探索了该点<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.PlayerFriend</font>*/
		public var last_explorer : com.fc.castle.data.PlayerFriend;



		/**
		 * @param UnitName as <font color=#0000ff>java.lang.String</font>
		 * @param explore_name as <font color=#0000ff>java.lang.String</font>
		 * @param refreshTime as <font color=#0000ff>int</font>
		 * @param last_time as <font color=#0000ff>java.sql.Date</font>
		 * @param last_explorer as <font color=#0000ff>com.fc.castle.data.PlayerFriend</font>		 */
		public function ExploreData(
			UnitName : String = null,
			explore_name : String = null,
			refreshTime : int = 0,
			last_time : Date = null,
			last_explorer : com.fc.castle.data.PlayerFriend = null) 
		{
			this.UnitName = UnitName;
			this.explore_name = explore_name;
			this.refreshTime = refreshTime;
			this.last_time = last_time;
			this.last_explorer = last_explorer;
		}
		


	}
}
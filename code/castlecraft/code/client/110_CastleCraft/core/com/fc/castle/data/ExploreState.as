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
	 * Java Class [6] [com.fc.castle.data.ExploreState]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class ExploreState extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var UnitName : String;

		/** SECONDS<br>
		  * Java type is : <font color=#0000ff>java.sql.Date</font>*/
		public var last_time : Date;

		/** 如果为空，则表示式自己探索过该点，否则是别的玩家探索了该点<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.PlayerFriend</font>*/
		public var last_explorer : com.fc.castle.data.PlayerFriend;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var explore_count : int;



		/**
		 * @param UnitName as <font color=#0000ff>java.lang.String</font>
		 * @param last_time as <font color=#0000ff>java.sql.Date</font>
		 * @param last_explorer as <font color=#0000ff>com.fc.castle.data.PlayerFriend</font>
		 * @param explore_count as <font color=#0000ff>int</font>		 */
		public function ExploreState(
			UnitName : String = null,
			last_time : Date = null,
			last_explorer : com.fc.castle.data.PlayerFriend = null,
			explore_count : int = 0) 
		{
			this.UnitName = UnitName;
			this.last_time = last_time;
			this.last_explorer = last_explorer;
			this.explore_count = explore_count;
		}
		


	}
}
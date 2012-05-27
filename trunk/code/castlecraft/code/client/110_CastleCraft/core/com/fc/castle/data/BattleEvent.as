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
	 * Java Class [2] [com.fc.castle.data.BattleEvent]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class BattleEvent extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{


		/** [uuid, unitType]<br>
		  * Java type is : <font color=#0000ff>byte</font>*/
		static public const EVENT_BUILD : int = 0;

		/** [uuid, unitType]<br>
		  * Java type is : <font color=#0000ff>byte</font>*/
		static public const EVENT_UNBUILD : int = 1;

		/** [uuid, skillType]<br>
		  * Java type is : <font color=#0000ff>byte</font>*/
		static public const EVENT_LAUNCH_SKILL : int = 2;

		/** [uuid, unitType, buildingUUID]<br>
		  * Java type is : <font color=#0000ff>byte</font>*/
		static public const EVENT_SOLDIER_SPAWN : int = 3;

		/** [uuid, unitType]<br>
		  * Java type is : <font color=#0000ff>byte</font>*/
		static public const EVENT_SOLDIER_DIED : int = 4;

		/** Java type is : <font color=#0000ff>int</font>*/
		public var time : int;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var event : int;

		/** Java type is : <font color=#0000ff>byte</font>*/
		public var force : int;

		/** Java type is : <font color=#0000ff>int[]</font>*/
		public var datas : Array;



		/**
		 * @param time as <font color=#0000ff>int</font>
		 * @param event as <font color=#0000ff>byte</font>
		 * @param force as <font color=#0000ff>byte</font>
		 * @param datas as <font color=#0000ff>int[]</font>		 */
		public function BattleEvent(
			time : int = 0,
			event : int = 0,
			force : int = 0,
			datas : Array = null) 
		{
			this.time = time;
			this.event = event;
			this.force = force;
			this.datas = datas;
		}
		


	}
}
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
	 * Java Class [7] [com.fc.castle.data.ExploreStates]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public class ExploreStates  implements MutualMessage
	{


		/** Java type is : <font color=#0000ff>com.fc.castle.data.ExploreState[]</font>*/
		public var states :  Array;



		/**
		 * @param states as <font color=#0000ff>com.fc.castle.data.ExploreState[]</font>		 */
		public function ExploreStates(
			states :  Array = null) 
		{
			this.states = states;
		}
	}
}
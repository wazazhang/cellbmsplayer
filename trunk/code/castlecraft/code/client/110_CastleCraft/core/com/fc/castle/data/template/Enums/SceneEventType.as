package com.fc.castle.data.template.Enums
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
	 * 中地图事件类型<br>
	 * Java Class [74] [com.fc.castle.data.template.Enums.SceneEventType]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class SceneEventType extends com.fc.castle.data.message.AbstractData implements MutualMessage
	{
		/** 小村庄<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const VILLAGE : int = 1;

		/** 探索点<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const EXPLORE : int = 2;

		/** 种族探索点<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const ES_RACE : int = 3;

		/** 随机探索点<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const ES_RANDOM : int = 4;

		/** 未知探索点<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const ES_UNKNOW : int = 5;

		/** 挑战探索点<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		static public const ES_CHALLANGE : int = 6;




		public function SceneEventType() 
		{

		}
		


	}
}
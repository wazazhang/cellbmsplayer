package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [1] [com.fc.lami.Messages.CardData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class CardData extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		public var point :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		public var type :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		public var x :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		public var y :  int;

		/**
		 * @param point as <font color=#0000ff>int</font>
		 * @param type as <font color=#0000ff>int</font>
		 * @param x as <font color=#0000ff>int</font>
		 * @param y as <font color=#0000ff>int</font>		 */
		public function CardData(
			point :  int = 0,
			type :  int = 0,
			x :  int = 0,
			y :  int = 0) 
		{
			this.point = point;
			this.type = type;
			this.x = x;
			this.y = y;
		}
	}
}
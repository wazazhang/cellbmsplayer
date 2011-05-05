package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [1] [com.fc.lami.Messages.CardData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	//[Bindable]
	public class CardData extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var point :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var type :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var x :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var y :  int;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var isSended :  Boolean;

		/**
		 * @param id as <font color=#0000ff>int</font>
		 * @param point as <font color=#0000ff>int</font>
		 * @param type as <font color=#0000ff>int</font>
		 * @param x as <font color=#0000ff>int</font>
		 * @param y as <font color=#0000ff>int</font>
		 * @param isSended as <font color=#0000ff>boolean</font>		 */
		public function CardData(
			id :  int = 0,
			point :  int = 0,
			type :  int = 0,
			x :  int = 0,
			y :  int = 0,
			isSended :  Boolean = false) 
		{
			this.id = id;
			this.point = point;
			this.type = type;
			this.x = x;
			this.y = y;
			this.isSended = isSended;
		}
	}
}
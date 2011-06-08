package com.slg.entity
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;
	import com.slg.entity.*;

	/**
	 * Java Class [2] [com.slg.entity.Building]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class Building extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var level :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var type :  int;
		/** Java type is : <font color=#0000ff>com.slg.entity.Position</font> */
		[JavaType(name="com.slg.entity.Position", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var pos :  com.slg.entity.Position;

		/**
		 * @param id as <font color=#0000ff>int</font>
		 * @param level as <font color=#0000ff>int</font>
		 * @param type as <font color=#0000ff>int</font>
		 * @param pos as <font color=#0000ff>com.slg.entity.Position</font>		 */
		public function Building(
			id :  int = 0,
			level :  int = 0,
			type :  int = 0,
			pos :  com.slg.entity.Position = null) 
		{
			this.id = id;
			this.level = level;
			this.type = type;
			this.pos = pos;
		}
	}
}
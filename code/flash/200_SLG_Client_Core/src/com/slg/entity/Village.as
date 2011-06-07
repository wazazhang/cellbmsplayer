package com.slg.entity
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;
	import com.slg.entity.*;

	/**
	 * Java Class [6] [com.slg.entity.Village]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class Village extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var id :  int;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var name :  String;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var food :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var city_id :  int;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var buildings :  Array;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var heros :  Array;
		/** Java type is : <font color=#0000ff>com.slg.entity.Arms[]</font> */
		[JavaType(name="com.slg.entity.Arms[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var arms_list :  Array;
		/** Java type is : <font color=#0000ff>com.slg.entity.Soldiers[]</font> */
		[JavaType(name="com.slg.entity.Soldiers[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var soldiers_list :  Array;

		/**
		 * @param id as <font color=#0000ff>int</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param food as <font color=#0000ff>int</font>
		 * @param city_id as <font color=#0000ff>int</font>
		 * @param buildings as <font color=#0000ff>int[]</font>
		 * @param heros as <font color=#0000ff>int[]</font>
		 * @param arms_list as <font color=#0000ff>com.slg.entity.Arms[]</font>
		 * @param soldiers_list as <font color=#0000ff>com.slg.entity.Soldiers[]</font>		 */
		public function Village(
			id :  int = 0,
			name :  String = null,
			food :  int = 0,
			city_id :  int = 0,
			buildings :  Array = null,
			heros :  Array = null,
			arms_list :  Array = null,
			soldiers_list :  Array = null) 
		{
			this.id = id;
			this.name = name;
			this.food = food;
			this.city_id = city_id;
			this.buildings = buildings;
			this.heros = heros;
			this.arms_list = arms_list;
			this.soldiers_list = soldiers_list;
		}
	}
}
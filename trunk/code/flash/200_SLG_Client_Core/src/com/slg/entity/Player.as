package com.slg.entity
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;
	import com.slg.entity.*;

	/**
	 * Java Class [6] [com.slg.entity.Player]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class Player extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var name :  String;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var office :  int;
		/** Java type is : <font color=#0000ff>com.slg.entity.GuageNumber</font> */
		[JavaType(name="com.slg.entity.GuageNumber", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var exp :  com.slg.entity.GuageNumber;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var level :  int;
		/** Java type is : <font color=#0000ff>com.slg.entity.GuageNumber</font> */
		[JavaType(name="com.slg.entity.GuageNumber", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var ap :  com.slg.entity.GuageNumber;
		/** Java type is : <font color=#0000ff>com.slg.entity.Currency</font> */
		[JavaType(name="com.slg.entity.Currency", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var currency :  com.slg.entity.Currency;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var village_list :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var cur_village_id :  int;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var hero_list :  Array;

		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param office as <font color=#0000ff>int</font>
		 * @param exp as <font color=#0000ff>com.slg.entity.GuageNumber</font>
		 * @param level as <font color=#0000ff>int</font>
		 * @param ap as <font color=#0000ff>com.slg.entity.GuageNumber</font>
		 * @param currency as <font color=#0000ff>com.slg.entity.Currency</font>
		 * @param village_list as <font color=#0000ff>int[]</font>
		 * @param cur_village_id as <font color=#0000ff>int</font>
		 * @param hero_list as <font color=#0000ff>int[]</font>		 */
		public function Player(
			player_id :  int = 0,
			name :  String = null,
			office :  int = 0,
			exp :  com.slg.entity.GuageNumber = null,
			level :  int = 0,
			ap :  com.slg.entity.GuageNumber = null,
			currency :  com.slg.entity.Currency = null,
			village_list :  Array = null,
			cur_village_id :  int = 0,
			hero_list :  Array = null) 
		{
			this.player_id = player_id;
			this.name = name;
			this.office = office;
			this.exp = exp;
			this.level = level;
			this.ap = ap;
			this.currency = currency;
			this.village_list = village_list;
			this.cur_village_id = cur_village_id;
			this.hero_list = hero_list;
		}
	}
}
package com.slg.entity
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;
	import com.slg.entity.*;

	/**
	 * Java Class [5] [com.slg.entity.Hero]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class Hero extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var id :  int;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var name :  String;
		/** Java type is : <font color=#0000ff>com.slg.entity.GuageNumber</font> */
		[JavaType(name="com.slg.entity.GuageNumber", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var exp :  com.slg.entity.GuageNumber;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var level :  int;
		/** Java type is : <font color=#0000ff>com.slg.entity.GuageNumber</font> */
		[JavaType(name="com.slg.entity.GuageNumber", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var hp :  com.slg.entity.GuageNumber;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var attack :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var politics :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var commander :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var sex :  int;
		/** Java type is : <font color=#0000ff>com.slg.entity.GuageNumber</font> */
		[JavaType(name="com.slg.entity.GuageNumber", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var loyalty :  com.slg.entity.GuageNumber;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var quality :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var max_skill_count :  int;
		/** Java type is : <font color=#0000ff>com.slg.entity.Skill[]</font> */
		[JavaType(name="com.slg.entity.Skill[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var skill_list :  Array;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var max_item_count :  int;
		/** Java type is : <font color=#0000ff>com.slg.entity.Item[]</font> */
		[JavaType(name="com.slg.entity.Item[]", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var item_list :  Array;

		/**
		 * @param id as <font color=#0000ff>int</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param exp as <font color=#0000ff>com.slg.entity.GuageNumber</font>
		 * @param level as <font color=#0000ff>int</font>
		 * @param hp as <font color=#0000ff>com.slg.entity.GuageNumber</font>
		 * @param attack as <font color=#0000ff>int</font>
		 * @param politics as <font color=#0000ff>int</font>
		 * @param commander as <font color=#0000ff>int</font>
		 * @param sex as <font color=#0000ff>int</font>
		 * @param loyalty as <font color=#0000ff>com.slg.entity.GuageNumber</font>
		 * @param quality as <font color=#0000ff>int</font>
		 * @param max_skill_count as <font color=#0000ff>int</font>
		 * @param skill_list as <font color=#0000ff>com.slg.entity.Skill[]</font>
		 * @param max_item_count as <font color=#0000ff>int</font>
		 * @param item_list as <font color=#0000ff>com.slg.entity.Item[]</font>		 */
		public function Hero(
			id :  int = 0,
			name :  String = null,
			exp :  com.slg.entity.GuageNumber = null,
			level :  int = 0,
			hp :  com.slg.entity.GuageNumber = null,
			attack :  int = 0,
			politics :  int = 0,
			commander :  int = 0,
			sex :  int = 0,
			loyalty :  com.slg.entity.GuageNumber = null,
			quality :  int = 0,
			max_skill_count :  int = 0,
			skill_list :  Array = null,
			max_item_count :  int = 0,
			item_list :  Array = null) 
		{
			this.id = id;
			this.name = name;
			this.exp = exp;
			this.level = level;
			this.hp = hp;
			this.attack = attack;
			this.politics = politics;
			this.commander = commander;
			this.sex = sex;
			this.loyalty = loyalty;
			this.quality = quality;
			this.max_skill_count = max_skill_count;
			this.skill_list = skill_list;
			this.max_item_count = max_item_count;
			this.item_list = item_list;
		}
	}
}
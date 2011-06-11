package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [47] [com.fc.lami.Messages.PlayerData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class PlayerData extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_id :  int;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var uid :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var player_name :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var player_head_url :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var sex :  String;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var score :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var win :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var lose :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var level :  int;

		/**
		 * @param player_id as <font color=#0000ff>int</font>
		 * @param uid as <font color=#0000ff>java.lang.String</font>
		 * @param player_name as <font color=#0000ff>java.lang.String</font>
		 * @param player_head_url as <font color=#0000ff>java.lang.String</font>
		 * @param sex as <font color=#0000ff>java.lang.String</font>
		 * @param score as <font color=#0000ff>int</font>
		 * @param win as <font color=#0000ff>int</font>
		 * @param lose as <font color=#0000ff>int</font>
		 * @param level as <font color=#0000ff>int</font>		 */
		public function PlayerData(
			player_id :  int = 0,
			uid :  String = null,
			player_name :  String = null,
			player_head_url :  String = null,
			sex :  String = null,
			score :  int = 0,
			win :  int = 0,
			lose :  int = 0,
			level :  int = 0) 
		{
			this.player_id = player_id;
			this.uid = uid;
			this.player_name = player_name;
			this.player_head_url = player_head_url;
			this.sex = sex;
			this.score = score;
			this.win = win;
			this.lose = lose;
			this.level = level;
		}
	}
}
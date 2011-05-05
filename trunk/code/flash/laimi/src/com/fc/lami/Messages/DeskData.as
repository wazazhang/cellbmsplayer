package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [3] [com.fc.lami.Messages.DeskData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	//[Bindable]
	public class DeskData extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var desk_id :  int;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var is_started :  Boolean;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_E_id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_W_id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_S_id :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var player_N_id :  int;

		/**
		 * @param desk_id as <font color=#0000ff>int</font>
		 * @param is_started as <font color=#0000ff>boolean</font>
		 * @param player_E_id as <font color=#0000ff>int</font>
		 * @param player_W_id as <font color=#0000ff>int</font>
		 * @param player_S_id as <font color=#0000ff>int</font>
		 * @param player_N_id as <font color=#0000ff>int</font>		 */
		public function DeskData(
			desk_id :  int = 0,
			is_started :  Boolean = false,
			player_E_id :  int = 0,
			player_W_id :  int = 0,
			player_S_id :  int = 0,
			player_N_id :  int = 0) 
		{
			this.desk_id = desk_id;
			this.is_started = is_started;
			this.player_E_id = player_E_id;
			this.player_W_id = player_W_id;
			this.player_S_id = player_S_id;
			this.player_N_id = player_N_id;
		}
	}
}
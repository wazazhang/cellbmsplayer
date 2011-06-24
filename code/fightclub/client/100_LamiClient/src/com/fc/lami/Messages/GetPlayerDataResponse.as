package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [31] [com.fc.lami.Messages.GetPlayerDataResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class GetPlayerDataResponse extends Message
	{
		/** Java type is : <font color=#0000ff>com.fc.lami.Messages.PlayerData</font> */
		[JavaType(name="com.fc.lami.Messages.PlayerData", leaf_type=NetDataTypes.TYPE_EXTERNALIZABLE)]
		public var player :  com.fc.lami.Messages.PlayerData;

		/**
		 * @param player as <font color=#0000ff>com.fc.lami.Messages.PlayerData</font>		 */
		public function GetPlayerDataResponse(
			player :  com.fc.lami.Messages.PlayerData = null) 
		{
			this.player = player;
		}
	}
}
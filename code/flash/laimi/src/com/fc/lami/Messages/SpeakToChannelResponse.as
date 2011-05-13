package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [59] [com.fc.lami.Messages.SpeakToChannelResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class SpeakToChannelResponse extends Message
	{
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SPEAK_TO_CHANNEL_RESULT_SUCCESS :  int = 0;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		static public const SPEAK_TO_CHANNEL_RESULT_FAIL_CHANNEL_NOEXIST :  int = 1;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var result :  int;

		/**
		 * @param result as <font color=#0000ff>int</font>		 */
		public function SpeakToChannelResponse(
			result :  int = 0) 
		{
			this.result = result;
		}
	}
}
package com.fc.lami.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [48] [com.fc.lami.Messages.PlatformUserData]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class PlatformUserData extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var platform_uid :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var user_uid :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var user_name :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var user_sex :  String;
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var user_image_url :  String;

		/**
		 * @param platform_uid as <font color=#0000ff>java.lang.String</font>
		 * @param user_uid as <font color=#0000ff>java.lang.String</font>
		 * @param user_name as <font color=#0000ff>java.lang.String</font>
		 * @param user_sex as <font color=#0000ff>java.lang.String</font>
		 * @param user_image_url as <font color=#0000ff>java.lang.String</font>		 */
		public function PlatformUserData(
			platform_uid :  String = null,
			user_uid :  String = null,
			user_name :  String = null,
			user_sex :  String = null,
			user_image_url :  String = null) 
		{
			this.platform_uid = platform_uid;
			this.user_uid = user_uid;
			this.user_name = user_name;
			this.user_sex = user_sex;
			this.user_image_url = user_image_url;
		}
	}
}
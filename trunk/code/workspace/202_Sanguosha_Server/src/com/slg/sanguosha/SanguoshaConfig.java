package com.slg.sanguosha;

import com.cell.util.Config;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigType;

/**
 * 三国杀服务器配置
 * @author yagami0079
 *
 */
@ConfigType("三国杀服务器配置")
public class SanguoshaConfig extends Config
{
	@ConfigField("端口")
	public static Integer	SERVER_PORT = 19830;
	@ConfigField("第三方接口")
	public static String	LOGIN_CLASS = com.slg.sanguosha.login.test.LoginDefault.class.getCanonicalName();
	
	public static void load(String config_file)
	{
		load(SanguoshaConfig.class, config_file);
	}
}

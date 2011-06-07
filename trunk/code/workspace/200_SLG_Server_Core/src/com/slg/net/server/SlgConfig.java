package com.slg.net.server;

import com.cell.util.Config;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigType;

/**
 * 三国杀服务器配置
 * @author yagami0079
 *
 */
@ConfigType("三国杀服务器配置")
public class SlgConfig extends Config
{
	@ConfigField("端口")
	public static Integer	SERVER_PORT = 19830;
	
	public static void load(String config_file)
	{
		load(SlgConfig.class, config_file);
	}
}

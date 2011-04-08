package com.fc.lami;

import com.cell.util.Config;
import com.cell.util.anno.ConfigField;

/**  
 * 拉密牌服务器配置
 */
public class LamiConfig extends Config{
	
	@ConfigField("主机名")
	public static String 	SERVER_HOST = "127.0.0.1";
	@ConfigField("端口")
	public static Integer	SERVER_PORT = 19821;
	@ConfigField("房间数量")
	public static Integer	ROOM_NUMBER = 2;
	@ConfigField("桌子数量")
	public static Integer	DESK_NUMBER = 10;
	@ConfigField("每个房间能容纳的玩家")
	public static Integer	PLAYER_NUMBER_MAX = 100;
	@ConfigField("线程循环间隔")
	public static Integer	THREAD_INTERVAL = 1000;
	
	
	public static void load(String config_file)
	{
		load(LamiConfig.class, config_file);
	}
}
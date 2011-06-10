package com.fc.lami;

import com.cell.util.Config;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigSeparator;
import com.cell.util.anno.ConfigType;

/**  
 * 拉密牌服务器配置
 */
@ConfigType("拉密牌服务器配置")
public class LamiConfig extends Config
{
//	---------------------------------------------------------------------
	@ConfigSeparator("网络")
//	---------------------------------------------------------------------
	
	@ConfigField("MINA端口")
	public static Integer	MINA_SERVER_PORT = 19821;
	
//	---------------------------------------------------------------------
	@ConfigSeparator("游戏数据")
//	---------------------------------------------------------------------
	
	@ConfigField("房间数量")
	public static Integer	ROOM_NUMBER = 2;
	@ConfigField("桌子数量")
	public static Integer	DESK_NUMBER = 10;
	@ConfigField("每个房间能容纳的玩家")
	public static Integer	PLAYER_NUMBER_MAX = 100;
	@ConfigField("线程循环间隔")
	public static Integer	THREAD_INTERVAL = 1000;
	
	@ConfigField("回合时间")
	public static Integer	TURN_INTERVAL = 60000;
	@ConfigField("操作时间")
	public static Integer	OPERATE_TIME = 30000;
	
	@ConfigField("桌面牌矩阵宽")
	public static Integer	MATRIX_WIDTH = 26;
	@ConfigField("桌面牌矩阵高")
	public static Integer	MATRIX_HEIGHT = 10;

//	---------------------------------------------------------------------
	@ConfigSeparator("第三方")
//	---------------------------------------------------------------------
	
	@ConfigField("第三方接口")
	public static String	LOGIN_CLASS = 
		com.fc.lami.login.xingcloud.LoginXingCloud.class.getCanonicalName();
	
	
	public static void load(String config_file) {
		load(LamiConfig.class, config_file);
	}
}

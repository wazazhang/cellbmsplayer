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
	public static Integer	ROOM_NUMBER = 10;
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
	@ConfigField("确认手牌时间")
	public static Integer	READY_TIME = 10000;
	
	@ConfigField("桌面牌矩阵宽")
	public static Integer	MATRIX_WIDTH = 26;
	@ConfigField("桌面牌矩阵高")
	public static Integer	MATRIX_HEIGHT = 10;
	
	@ConfigField("是否快速游戏模式")
	public static Integer	IS_FAST_GAME = 0;
	
	@ConfigField("游戏中退出扣分")
	public static Integer	PUNISH_SCORE = 300;
	@ConfigField("初始牌数")
	public static Integer	START_CARD_NUMBER = 14;
	
	@ConfigField("多少时间没有进行游戏桌子重置")
	public static Integer	DESK_RESET_TIME = 60000;
	
	@ConfigField("默认桌子名")
	public static String	DEFAULT_DESK_NAME = "一起来玩拉密牌吧";
	@ConfigField("房间列表设置文件")
	public static String	ROOM_SET_CONFIG_XLS = "./extension/config/lami_roomset.xls";
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

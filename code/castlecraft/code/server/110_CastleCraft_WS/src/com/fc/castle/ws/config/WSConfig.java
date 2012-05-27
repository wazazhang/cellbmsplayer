package com.fc.castle.ws.config;

import com.cell.util.Config;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigSeparator;
import com.cell.util.anno.ConfigType;

@ConfigType("WebService环境配置")
public class WSConfig extends Config
{
	public static boolean	DEBUG		 			= true;

//	---------------------------------------------------------------------
	@ConfigSeparator("网络")
//	---------------------------------------------------------------------

	public static int		NET_MINA_PORT 			= 0;
	public static int		NET_MINA_IO_PROCESSOR 	= 0;
	public static int		NET_MINA_IDLE_TIME_SEC	= 0;
	
//	---------------------------------------------------------------------
	@ConfigSeparator("资源")
//	---------------------------------------------------------------------

	public static String		DATA_XLS_CONFIG 	= "/xls/xls_config.xls";
	public static String		DATA_XLS_UNIT 		= "/xls/xls_unit_template.xls";
	public static String		DATA_XLS_SKILL 		= "/xls/xls_skill_template.xls";
	public static String		DATA_XLS_ITEM 		= "/xls/xls_item_template.xls";
	public static String		DATA_XLS_EVENT 		= "/xls/xls_event_template.xls";
	public static String		DATA_XLS_BUFF 		= "/xls/xls_buff_template.xls";
	public static String		DATA_XLS_EXP		= "/xls/xls_expset.xls";
	public static String		DATA_XLS_GUIDE		= "/xls/xls_guide.xls";

	public static String		DATA_XML_SCENE 		= "/xls/scene.xml";
	
}

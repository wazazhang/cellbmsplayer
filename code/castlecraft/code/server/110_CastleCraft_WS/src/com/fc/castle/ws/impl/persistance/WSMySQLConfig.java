package com.fc.castle.ws.impl.persistance;

import com.cell.util.Config;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigSeparator;
import com.cell.util.anno.ConfigType;


@ConfigType("MySQL配置")
public class WSMySQLConfig extends Config
{
	@ConfigField("JDBC驱动")
	public static String 	DB_DRIVER 		= "com.mysql.jdbc.Driver";
	
	public static String 	DB_URL 			= "jdbc:mysql://localhost:3306/castle_db?useUnicode=true&characterEncoding=UTF-8";
	
	public static String 	DB_USER 		= "root";
	public static String 	DB_PSWD 		= "";
	public static int 		DB_POOL_MIN 	= 10;
	public static int 		DB_POOL_MAX 	= 20;
	
}

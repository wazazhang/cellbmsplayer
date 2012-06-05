package com.fc.castle.ws.impl.persistance;

import java.io.InputStream;

import com.cell.security.Crypt;
import com.fc.castle.service.impl.mysql.MySQLPersistanceManager;

public class WSPersistanceManager extends MySQLPersistanceManager
{
	public static void init(InputStream mysqlConfig)  throws Exception 
	{		
		WSMySQLConfig.load(WSMySQLConfig.class, mysqlConfig);
		new WSPersistanceManager();
	}
	
	private WSPersistanceManager()  throws Exception {
		super(WSMySQLConfig.DB_DRIVER,
				WSMySQLConfig.DB_URL, 
				WSMySQLConfig.DB_USER, 
				WSMySQLConfig.DB_PSWD,
				WSMySQLConfig.DB_POOL_MIN,
				WSMySQLConfig.DB_POOL_MAX);
	}
	

}

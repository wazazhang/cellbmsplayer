package com.fc.castle.ws.servlet;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.mysql.MySQLDriver;
import com.cell.security.MD5;
import com.cell.sql.SQLDriverManager;
import com.fc.castle.service.DataManager;
import com.fc.castle.ws.WSAppBridge;
import com.fc.castle.ws.WSHttpRequest;
import com.fc.castle.ws.config.WSConfig;
import com.fc.castle.ws.impl.game.WSGameManager;
import com.fc.castle.ws.impl.persistance.WSPersistanceManager;

public class Init extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(Init.class);
	
	public void init() throws ServletException
	{
		log.info("init CASTLE CRAFT web service");
		try
		{
			System.out.println("===============================================");
			System.out.println("= init System");
			CObject.initSystem(
					new CStorage("castle_ws"),
					new WSAppBridge(getServletConfig()));
			SQLDriverManager.setDriver(new MySQLDriver());
			
			InputStream isconfig = getResource("WEB-INF/config/wsconfig.properties");
			WSConfig.load(WSConfig.class, isconfig);
			
			System.out.println("===============================================");
			System.out.println("= init DataManager");
			DataManager.init(
					getResource(WSConfig.DATA_XLS_CONFIG),
					getResource(WSConfig.DATA_XLS_UNIT), 
					getResource(WSConfig.DATA_XLS_SKILL), 
					getResource(WSConfig.DATA_XLS_ITEM), 
					getResource(WSConfig.DATA_XLS_EVENT),
					getResource(WSConfig.DATA_XLS_BUFF),
					getResource(WSConfig.DATA_XLS_EXP),
					getResource(WSConfig.DATA_XLS_GUIDE),
					getResource(WSConfig.DATA_XML_SCENE));

			System.out.println("===============================================");
			System.out.println("= init WSPersistanceManager");
			WSPersistanceManager.init(getResource("WEB-INF/config/mysql.properties"));
			
			System.out.println("===============================================");
			System.out.println("= init WSHttpRequest");
			WSHttpRequest.initStatic();

			System.out.println("===============================================");
			System.out.println("= init WSGameManager");
			WSGameManager.init();

			System.out.println("===============================================");
			System.out.println("= SERVER STARTED : at " + new Date());
			System.out.println("===============================================");
		}
		catch (Throwable e) {
			log.error("===============================================");
			log.error(e.getMessage(), e);
			log.error("===============================================");
		}
		log.info("init CASTLE CRAFT web service done");
	
	}
	
	private InputStream getResource(String url) throws Exception
	{
		InputStream ret = null;
		try {
			ret = getServletContext().getResourceAsStream(url);
		} catch (Exception e) {}
		if (ret == null) {
			ret = CIO.getInputStream(url);
		}
		return ret;
	}
	
	public static void main(String[] args) 
	{
		try {
			new Init().init();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
		
}

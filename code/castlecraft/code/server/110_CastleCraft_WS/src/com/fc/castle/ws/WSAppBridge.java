package com.fc.castle.ws;

import java.io.InputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.cell.io.DefaultIODispatcher;
import com.cell.io.IODispatcher;
import com.cell.j2se.CAppBridge;

public class WSAppBridge extends CAppBridge
{
	private ServletConfig config;
	
	public WSAppBridge(ServletConfig config) 
	{
		this.config = config;
	}
	
//	@Override
//	protected IODispatcher createIO() 
//	{
//		return new WSIODispatcher();
//	}
//	
//	protected class WSIODispatcher extends CIODispatcher
//	{
//		@Override
//		protected InputStream getJarResource(String file) {
//			System.out.println(file);
//			InputStream is = config.getServletContext().getResourceAsStream(file);
//			if (is != null) {
//				return is;
//			}
//			return super.getJarResource(file);
//		}
//	}
}

package com.fc.castle.ws.util;

import java.io.File;
import java.util.Date;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.util.StringReplacer;
import com.cell.util.StringReplacer.StringRegion;
import com.fc.castle.ws.WSHttpRequest;
import com.fc.castle.ws.servlet.WebService;

public class ServletWebXmlGenerator 
{
	public static void main(String[] args)
	{
		CAppBridge.initNullStorage();

		String template = CIO.readAllText("/com/fc/castle/ws/util/web-template.xml");
		
		StringRegion servlet_region = StringReplacer.getRegion(template, "#servlet_begin", "#servlet_end");
		
		if (servlet_region.finded())
		{
			StringBuilder servlets = new StringBuilder();
			
			for (Class<?> sclass : WebService.class.getClasses()) {
				if (WSHttpRequest.class.isAssignableFrom(sclass)) {
					String context = servlet_region.getContent();
					context = CUtil.replaceString(context, "#name", sclass.getSimpleName());
					context = CUtil.replaceString(context, "#class", sclass.getName());
					servlets.append(context);
				}
			}
			
			template = StringReplacer.replaceRegion(template, 
					servlet_region.getBeginIndex(), 
					servlet_region.getEndIndex(), 
					servlets.toString());
		}
		
		template = CUtil.replaceString(template, "#date", new Date().toString(), 1);
		
		System.out.println(template);
		
		if (args.length > 0) {
			File out_file = new File(args[0]);
			System.out.println(out_file);
			CFile.writeText(out_file, template);
		}
	}
	
	



}

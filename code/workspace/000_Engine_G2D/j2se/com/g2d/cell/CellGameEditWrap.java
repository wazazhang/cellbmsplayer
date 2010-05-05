package com.g2d.cell;

import java.io.File;


public class CellGameEditWrap 
{

	public static Process openCellGameEdit(String cmd, File cpj_file, String ... args) 
	{
		try {
			StringBuffer append = new StringBuffer();
			for (String arg : args) {
				append.append("\"" + arg + "\" ");
			}
			String call_cmd = cmd + " " + cpj_file.getPath() + " " + append;
//			System.out.println("call cmd : " + call_cmd);
			return Runtime.getRuntime().exec(call_cmd);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}

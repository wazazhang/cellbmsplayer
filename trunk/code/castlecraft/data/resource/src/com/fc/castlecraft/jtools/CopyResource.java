package com.fc.castlecraft.jtools;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import com.cell.io.CFile;

public class CopyResource
{
	public static void main(String[] args)
	{
		try {
			if (args.length >= 2) {
				File src = new File(args[0]);
				File dst = new File(args[1]);
				copy(src, dst);
			} else {
				System.out.println("useage : \n" +
						"CopyResource [src] [dst]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copy(File src, File dst)
	{
//		if (!src.isHidden()) 
		{
			if (src.isDirectory()) 
			{
				for (File sf : src.listFiles()) {
					File df = new File(dst, sf.getName());
					copy(sf, df);
				}
			}
			else
			{
				String name = src.getName();
				if (name.endsWith(".xml") ||
					name.endsWith(".png") ||
					name.endsWith(".properties")) {
					try {
						CFile.copyFile(src, dst);
						System.out.println("copy " + src.getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}		
	}
	
}

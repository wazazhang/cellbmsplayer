package com.g2d.studio.cell.gameedit;

import java.io.File;
import java.io.IOException;

import com.cell.CIO;
import com.cell.CUtil;

public class BuildProcess 
{
	final private File dir;
	
	public BuildProcess(File dir) throws IOException
	{
		this.dir = dir.getCanonicalFile();
	}

	public Object exec(String cmd) {
		try {	
			Process p = Runtime.getRuntime().exec(cmd, CUtil.getEnv(), dir);
			p.waitFor();
			byte[] out = CIO.readStream(p.getInputStream());
			return (new String(out));
		} catch (Exception err) {
			err.printStackTrace();
			return null;
		}
	}
	
}

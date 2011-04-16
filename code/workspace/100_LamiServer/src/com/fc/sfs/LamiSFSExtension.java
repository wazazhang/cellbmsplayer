package com.fc.sfs;

import com.smartfoxserver.v2.extensions.SFSExtension;

public class LamiSFSExtension extends SFSExtension
{
	@Override
	public void init() 
	{
		trace(new Object[] { "Lami SFSExtension started" });
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
		
		
	}
}

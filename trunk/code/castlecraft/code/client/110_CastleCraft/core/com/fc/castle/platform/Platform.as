package com.fc.castle.platform
{
	public interface Platform
	{
	
		function login(user:String, auth:String, complete:Function, error:Function) : void;
				
	}
}
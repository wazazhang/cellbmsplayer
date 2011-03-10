package com.gt.loader
{
	public interface ResourceLoaderListener
	{
		function response(url:String, content:Object, result:int) : void;
	}
}
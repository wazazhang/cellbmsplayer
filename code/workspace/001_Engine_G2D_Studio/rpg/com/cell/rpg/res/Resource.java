package com.cell.rpg.res;

import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

import com.g2d.cell.CellSetResource;


public abstract class Resource extends CellSetResource
{
	/**
	 * @param file
	 * @param async true 代表是异步获得图片
	 * @throws Exception
	 */
	public Resource(String file, ThreadPoolExecutor async) throws Exception
	{
		super(file, async);
	}
	
	public Resource(String file, String name, ThreadPoolExecutor stream_image) throws Exception
	{
		super(file, name, stream_image);
	}
	
	public Resource(File file, String name, ThreadPoolExecutor stream_image) throws Exception
	{
		super(file, name, stream_image);
	}
	
	
	
}

package com.cell.rpg.res;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import com.cell.j2se.CImage;
import com.cell.util.zip.ZipUtil;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResource.ImagesSet;
import com.g2d.cell.CellSetResource.StreamTiles;


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

	@Override
	protected StreamTiles getLocalImage(ImagesSet img) throws IOException {
		StreamTiles tiles = createStreamTiles(img);
		tiles.run();
		return tiles;
	}
	
	@Override
	protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
		StreamTiles tiles = createStreamTiles(img);
		return tiles;
	}
	
	abstract protected StreamTiles createStreamTiles(ImagesSet img);
	
	
	
}

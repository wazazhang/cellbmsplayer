package com.cell.rpg.res;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import com.cell.j2se.CImage;
import com.g2d.cell.CellSetResource;


public class Resource extends CellSetResource
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
		StreamTypeTiles tiles = new StreamTypeTiles(img);
		tiles.run();
		return tiles;
	}
	
	@Override
	protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
		StreamTypeTiles tiles = new StreamTypeTiles(img);
		return tiles;
	}
	
	/**
	 * 根据图片组名字确定读入jpg或png
	 * @author WAZA
	 */
	protected class StreamTypeTiles extends StreamTiles
	{
		public StreamTypeTiles(ImagesSet img) throws IOException {
			super(img);
		}
		
		@Override
		protected void initImages() 
		{
			try{
				// 根据tile的类型来判断读取何种图片
				if (img.Name.equals("png") || img.Name.equals("jpg"))
				{
					for (int i=0; i<images.length; i++){
						if (img.ClipsW[i]>0 && img.ClipsH[i]>0){
							byte[] idata = loadRes("set/"+img.Name+"/"+i+"."+img.Name);
							images[i] = new CImage(new ByteArrayInputStream(idata));
							//Thread.sleep(1000);
						}
					}
				}
				else
				{
					byte[] idata = loadRes(img.Name+".png");
					CImage src = new CImage(new ByteArrayInputStream(idata));
					for (int i=0; i<images.length; i++){
						if (img.ClipsW[i]>0 && img.ClipsH[i]>0){
							images[i] = src.subImage(img.ClipsX[i], img.ClipsY[i], img.ClipsW[i], img.ClipsH[i]);
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				super.initImages();
			}
		}
		
	}
	
}

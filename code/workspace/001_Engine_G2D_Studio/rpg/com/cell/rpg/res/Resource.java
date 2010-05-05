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
	public class StreamTypeTiles extends StreamTiles
	{
		public StreamTypeTiles(ImagesSet img) throws IOException {
			super(img);
		}
		
		@Override
		protected void initImages() 
		{
			try {
				// 根据tile的类型来判断读取何种图片
				if (img.Name.equals("png") || img.Name.equals("jpg")) {
					if (loadZipImages()) {
						return;
					}
					if (loadSetImages()) {
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				super.initImages();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		protected boolean loadSetImages() {
			try{
				for (int i=0; i<images.length; i++){
					if (img.ClipsW[i]>0 && img.ClipsH[i]>0){
						byte[] idata = loadRes("set/"+img.Name+"/"+i+"."+img.Name);
						images[i] = new CImage(new ByteArrayInputStream(idata));
					}
				}
				return true;
			} catch (Exception err) {
				err.printStackTrace();
				return false;
			}
		}
		
		protected boolean loadZipImages() {
			byte[] zipdata = loadRes(img.Name+".zip");
			if (zipdata != null) {
				Map<String, ByteArrayInputStream> files = ZipUtil.unPackFile(new ByteArrayInputStream(zipdata));
				for (int i = 0; i < images.length; i++) {
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						ByteArrayInputStream idata = files.get(i+"."+img.Name);
						images[i] = new CImage(idata);
					}
				}
				return true;
			}
			return false;
		}
	}
	
	
}

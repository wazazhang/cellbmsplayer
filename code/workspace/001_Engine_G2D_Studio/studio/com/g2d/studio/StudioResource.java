package com.g2d.studio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import com.cell.gfx.IImages;
import com.cell.j2se.CImage;
import com.g2d.cell.CellSetResource;



public class StudioResource extends CellSetResource
{
	boolean is_load_resource = false;
	
	public StudioResource(String setPath, String name)  throws Exception{
		super(setPath, name, null);
	}
	
	public StudioResource(File file, String name) throws Exception {
		super(file, name, null);
	}
	
	@Override
	protected IImages getLocalImage(ImagesSet img) throws IOException {
		StreamTypeTiles tiles = new StreamTypeTiles(img);
		return tiles;
	}
	
	synchronized public boolean isLoadImages()
	{
		return is_load_resource;
	}
	
	synchronized public void initAllStreamImages()
	{
		if (!is_load_resource) {
			is_load_resource = true;
			Enumeration<ImagesSet> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				ImagesSet ts = imgs.nextElement();
				IImages images = getImages(ts);
				if (images instanceof StreamTiles) {
					((StreamTiles) images).run();
				}
			}
		}
	}
	
	synchronized public void destoryAllStreamImages(){
		if (is_load_resource) {
			is_load_resource = false;
			if (ResourceManager!=null) {
				for (Object obj : ResourceManager.values()) {
					if (obj instanceof StreamTiles){
						StreamTiles stiles = (StreamTiles)obj;
						stiles.unloadAllImages();
					}
				}
			}
		}
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

package com.g2d.studio;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import com.cell.gfx.IImages;
import com.cell.rpg.res.Resource;



public class StudioResource  extends Resource
{
	boolean is_load_resource = false;
	
	public StudioResource(String setPath, String name)  throws Exception{
		super(setPath, name, false);
	}
	
	public StudioResource(File file, String name) throws Exception {
		super(file, name, false);
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
	
}

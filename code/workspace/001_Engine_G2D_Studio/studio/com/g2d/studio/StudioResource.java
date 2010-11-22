package com.g2d.studio;

import java.io.IOException;
import java.util.Enumeration;

import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gfx.IImages;
import com.cell.rpg.res.Resource;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.io.File;



public class StudioResource extends Resource
{
	boolean is_load_resource = false;
	
//	public StudioResource(String setPath, String name)  throws Exception{
//		super(setPath, name, null);
//	}
	
	public StudioResource(File file) throws Exception {
		super(file.getPath(), null);
//		System.out.println(file.getPath());
	}
	
	@Override
	final protected StreamTiles getLocalImage(ImagesSet img) throws IOException {
		StreamTiles tiles = Builder.getInstance().createResource(img, this);
		return tiles;
	}
	
	@Override
	final protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
		StreamTiles tiles = Builder.getInstance().createResource(img, this);
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
			if (resource_manager!=null) {
				for (Object obj : resource_manager.values()) {
					if (obj instanceof StreamTiles){
						StreamTiles stiles = (StreamTiles)obj;
						stiles.unloadAllImages();
					}
				}
			}
		}
	}
	

	

}

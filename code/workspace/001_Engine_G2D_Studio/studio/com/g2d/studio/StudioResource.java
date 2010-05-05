package com.g2d.studio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import com.cell.CIO;
import com.cell.gfx.IImages;
import com.cell.j2se.CImage;
import com.cell.rpg.res.Resource;
import com.cell.util.Pair;
import com.cell.util.zip.ZipUtil;
import com.g2d.cell.CellSetResource;



public class StudioResource extends Resource
{
	boolean is_load_resource = false;
	
//	public StudioResource(String setPath, String name)  throws Exception{
//		super(setPath, name, null);
//	}
	
	public StudioResource(File file, String name) throws Exception {
		super(file, name, null);
	}
	
	@Override
	protected StreamTiles getLocalImage(ImagesSet img) throws IOException {
		StreamTypeTiles tiles = new StreamTypeTiles(img);
		return tiles;
	}
	
	@Override
	protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
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

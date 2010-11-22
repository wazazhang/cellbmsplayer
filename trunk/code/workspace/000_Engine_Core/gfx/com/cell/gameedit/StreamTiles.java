package com.cell.gameedit;

import java.io.ByteArrayInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cell.gameedit.object.ImagesSet;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.j2se.CImage;


/**
 * @author WAZA
 * 支持网络缓冲的图片组
 */
public class StreamTiles implements IImages, Runnable
{
	final protected SetResource	set;
	final protected ImagesSet		img;
	final protected IImage[]		images;
	
	private AtomicBoolean			is_loaded	= new AtomicBoolean(false);
	private AtomicBoolean			is_loading	= new AtomicBoolean(false);
	
	public StreamTiles(ImagesSet img, SetResource res) {
		this.set	= res;
		this.images	= new CImage[img.getCount()];
		this.img	= img;
	}
	
	/**
	 * 子类可以覆盖为自己的加载图片方法，注意，该方法已获得
	 * CellSetResource， StreamTiles 的锁
	 */
	protected void initImages()
	{
		byte[] idata = set.output_adapter.loadRes(img.getName()+".png");
		CImage src = new CImage(new ByteArrayInputStream(idata));
		for (int i=0; i<images.length; i++){
			if (img.getClipW(i)>0 && img.getClipH(i)>0){
				images[i] = src.subImage(img.getClipX(i), img.getClipY(i), img.getClipW(i), img.getClipH(i));
			}
		}
	}
	
	final public boolean isLoaded() {
		return is_loaded.get();
	}
	
	final public void run() 
	{
		if (is_loading.getAndSet(true)) {
			return;
		}
		try {
			if (!is_loaded.get()) {
				synchronized (this) {
					initImages();
					is_loaded.set(true);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			is_loading.set(false);
		}
	}
	
	final public void unloadAllImages() {
		synchronized (this) {
			is_loaded.set(false);
			for (int i = 0; i < images.length; i++) {
				images[i] = null;
			}
		}
	}
	
	final public IImage getImage(int index){
		return images[index];
	}
	
	final public int getWidth(int Index) {
		return img.getClipW(Index);
	}

	final public int getHeight(int Index) {
		return img.getClipH(Index);
	}

	final public int getCount(){
		return images.length;
	}
	
	public void render(IGraphics g, int Index, int PosX, int PosY) {
		IImage buff = images[Index];
		if (buff != null) {
			g.drawImage(buff, PosX, PosY, 0, IGraphics.BLEND_MODE_NONE, 1.0f);
		}else{
			drawLoading(g, PosX, PosY, img.getClipW(Index), img.getClipH(Index));
		}
	}

	public void render(IGraphics g, int Index, int PosX, int PosY, int Style, int blend_mode, float blend_alpha) {
		IImage buff = images[Index];
		if (buff != null) {
			g.drawImage(buff, PosX, PosY, Style, blend_mode, blend_alpha);
		}else{
			drawLoading(g, PosX, PosY, img.getClipW(Index), img.getClipH(Index));
		}
	}

	protected void drawLoading(IGraphics g, int x, int y, int w, int h) 
	{
		g.setColor(0xff808080);
		g.fillRect(x, y, w, h);
		g.setColor(0xffffffff);
		g.drawString("loading...", x, y);
	}
	
	public int getPixel(int index, int x, int y){
		IImage buff = images[index];
		if (buff != null) {
			int[] rgb = new int[1];
			buff.getRGB(rgb, 0, 1, x, y, 1, 1);
			return rgb[0];
		}
		return 0;
	}
	
	public boolean	addTile() {return false;}
	public boolean	addTile(int TileX, int TileY, int TileWidth, int TileHeight) {return false;}
	public void		addTile(int ClipX, int ClipY, int ClipWidth, int ClipHeight, int TileWidth, int TileHeight) {}
	
	public void buildImages(IImage srcImage, int count) {}
	public int setMode(int mode){return 0;}
	
	
	
}

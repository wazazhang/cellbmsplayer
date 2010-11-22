package com.g2d.cell;


import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.gameedit.SetResource;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.output.OutputProperties;
import com.cell.gfx.game.CSprite;
import com.cell.util.concurrent.ThreadPoolService;

/**
 * 对应output.properties的资源文件
 * @author WAZA
 */
public class CellSetResource extends SetResource
{
//	-------------------------------------------------------------------------------------
	
	final public String				Path;
	final public String				PathDir;

	
//	-------------------------------------------------------------------------------------
	
	public CellSetResource(String file) throws Exception
	{
		this(file, null);
	}
	
	public CellSetResource(File file, ThreadPoolService loading_service) throws Exception
	{
		this(file.getPath(), loading_service);
	}
	
	public CellSetResource(String file, ThreadPoolService loading_service) throws Exception
	{
		super(new OutputProperties(file), loading_service);
		this.Path				= ((OutputProperties)getOutput()).path;
		this.PathDir 			= ((OutputProperties)getOutput()).root;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " : " + Path;
	}
	
	synchronized final public AtomicReference<CSprite> getSpriteAsync(String key, LoadSpriteListener listener)
	{
		SpriteSet spr = SprTable.get(key);
		if (spr != null) {
			AtomicReference<CSprite> ret = new AtomicReference<CSprite>();
			CSprite obj = resource_manager.get("SPR_"+key, CSprite.class);
			if (obj != null) {
				CSprite cspr = new CSprite(obj);
				listener.loaded(this, cspr, spr);
				ret.set(cspr);
			} else {
				if (loading_service != null) {
					loading_service.executeTask(new LoadSpriteTask(spr, listener, ret));
				} else {
					new Thread(new LoadSpriteTask(spr, listener, ret), "get-sprite-" + key).start();
				}
			}
			return ret;
		} else {
			throw new NullPointerException("sprite not found : " + key);
		}
	}
	
	public static interface LoadSpriteListener
	{
		public void loaded(CellSetResource set, CSprite cspr, SpriteSet spr);
	}
	
	protected class LoadSpriteTask implements Runnable
	{
		final LoadSpriteListener listener;
		
		final SpriteSet spr;
		
		final AtomicReference<CSprite> ref;
		
		public LoadSpriteTask(SpriteSet spr, LoadSpriteListener listener, AtomicReference<CSprite> ref) {
			this.spr		= spr;
			this.listener 	= listener;
			this.ref		= ref;
		}
		
		public void run() {
			synchronized (listener) {
				try {
//					System.out.println("start load spr : " + spr.SprID);
					CSprite cspr = getSprite(spr);
					ref.set(cspr);
					listener.loaded(CellSetResource.this, cspr, spr);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

package com.cell.sound.util;

import java.io.InputStream;
import java.util.Vector;

import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.IStreamPlayer;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;

public class StreamSoundPlayer
{
	private	SoundInfo			bgm_info;
	private	IStreamPlayer		bgm_player;
	private	Vector<ISound> 		buffers;
	
	public StreamSoundPlayer(IPlayer player, SoundInfo info) throws Exception
	{
		if (!(player instanceof IStreamPlayer)) {
			throw new Exception("player must support : " + IStreamPlayer.class);
		}
		
		this.bgm_player	= (IStreamPlayer)player;
		this.bgm_info 	= info;
		this.buffers 	= new Vector<ISound>(2);
		if (info instanceof Runnable) {
			new Thread((Runnable)bgm_info).start();
		}
	}
	
	public void play(boolean loop) {
		bgm_player.play(loop);
	}
	
	public void pause() {
		this.bgm_player.pause();
	}

	public void resume() {
		this.bgm_player.resume();
	}
	
	public boolean isPlaying() {
		return bgm_player.isPlaying();
	}
	
	public void dispose() {
		try{
			synchronized (this) {
				if (bgm_player != null) {
					bgm_player.dispose();
				}
				for (ISound buffer : buffers) {
					buffer.dispose();
				}
				bgm_info = null;
				System.gc();
			}
		} catch (Throwable tx) {
			tx.printStackTrace();
		}
	}
	
	/**
	 * 更新当前缓冲，将新的数据插入到缓冲区。
	 * 返回true的话，代表已经缓冲完毕。
	 * @return
	 */
	public boolean update() {
		synchronized (this) {
			try {
				if (bgm_info == null) {
					return true;
				}
				if (bgm_info.hasData()) {
					ISound buffer = SoundManager.getSoundManager().createSound(bgm_info);
					if (buffer != null) {
						buffers.add(buffer);
						bgm_player.queue(buffer);
					}
					return false;
				} else {		
					return true;
				}
			} catch (Throwable tx) {
				tx.printStackTrace();
				return false;
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}

}

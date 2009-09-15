package com.cell.bms;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.cell.bms.BMSFile.HeadInfo;
import com.cell.bms.BMSFile.Note;
import com.cell.bms.BMSFile.NoteValue;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public class BMSPlayer extends Sprite implements Runnable
{
//	-------------------------------------------------------------------------------------------------
	
	final BMSFile 	bms_file;
	

//	-------------------------------------------------------------------------------------------------
//	play refer
	
	ArrayList<Note>	play_tracks;
	
	double 			play_bpm;
	
	/** 创建Note单位的检查范围 */
	double			play_create_length;
	
	/** 丢弃Note单位的检查范围  */
	double			play_drop_length;
	
	boolean			play_exit = false;

	long			play_start_time;
	
	Thread 			play_thread;
	
//	-------------------------------------------------------------------------------------------------
//	game refer
	
	/** 基线 */
	Line2D			game_base_line;
	
	/** note 移动方向 */
	Point2D			geme_note_direct;
	
//	-------------------------------------------------------------------------------------------------
	
	public BMSPlayer(BMSFile bms) 
	{
		bms_file 			= bms;
	}

//	-------------------------------------------------------------------------------------------------
	
	public void start()
	{
		if (!play_exit) {
			play_exit = true;
		}
		if (play_thread!=null){
			try {
				play_thread.join(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		play_thread = new Thread(this);
		play_thread.start();
	}
	
	@Override
	public void run() 
	{
		try{
			play_tracks			= bms_file.getAllNoteList();
			play_bpm			= (Integer)bms_file.getHeadInfo(HeadInfo.BPM);
			play_create_length	= (Integer)bms_file.LINE_SPLIT_DIV * 10;
			play_drop_length	= (Integer)bms_file.LINE_SPLIT_DIV;
			play_start_time		= System.currentTimeMillis();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		while (!play_exit)
		{
			try
			{
				
				Thread.sleep(1);
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 得到歌曲毫秒播放位置
	 * @return
	 */
	public long getPlayPosition()
	{
		return System.currentTimeMillis() - play_start_time;
	}
	
	

//	-------------------------------------------------------------------------------------------------
	
	@Override
	public void added(DisplayObjectContainer parent) {
		setSize(parent.getWidth(), parent.getHeight());
	}
	@Override
	public void removed(DisplayObjectContainer parent) {}
	
	@Override
	public void render(Graphics2D g) 
	{

	}

	@Override
	public void update() 
	{

	}

//	-------------------------------------------------------------------------------------------------
	
	
	
	class EffectNote extends Sprite
	{
		final Note note;
		
		public EffectNote(Note note)
		{
			this.note = note;
			
			
		}
	}
	
	
//	class EffectNoteBGM extends Sprite
//	{
//		
//	}
	
//	-------------------------------------------------------------------------------------------------
	
	class EffectKeyHit extends Sprite
	{
		
	}
	
	class EffectKeyHold extends Sprite
	{
		
	}
}

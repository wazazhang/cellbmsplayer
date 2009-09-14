package com.cell.bms;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cell.bms.BMSFile.HeadInfo;
import com.cell.bms.BMSFile.Note;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public class BMSPlayer extends Sprite implements Runnable
{
//	-------------------------------------------------------------------------------------------------
	
	BMSFile 		bms_file;
	

//	-------------------------------------------------------------------------------------------------

	ArrayList<Note>	play_tracks;
	
	double 			play_bpm;
	
	/** 创建Note单位的检查范围 */
	double			play_create_length;
	
	/** 丢弃Note单位的检查范围  */
	double			play_drop_length;
	
	boolean			play_exit = false;
	
//	-------------------------------------------------------------------------------------------------
	
	public BMSPlayer(BMSFile bms) 
	{
		bms_file 			= bms;
	}

//	-------------------------------------------------------------------------------------------------
	
	public void start()
	{
		play_tracks			= bms_file.getAllNoteList();
		play_bpm			= bms_file.getHeadInfo(HeadInfo.BPM);
		play_create_length	= bms_file.LINE_SPLIT_DIV * 10;
		play_drop_length	= bms_file.LINE_SPLIT_DIV;
		
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}
	
	@Override
	public void run() 
	{
		while (!play_exit)
		{
			try
			{
				
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	

//	-------------------------------------------------------------------------------------------------
	
	@Override
	public void added(DisplayObjectContainer parent) {}
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
	
}

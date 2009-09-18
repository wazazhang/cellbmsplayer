package com.cell.bms;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.cell.bms.BMSFile.HeadInfo;
import com.cell.bms.BMSFile.Note;
import com.cell.bms.BMSFile.NoteValue;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public class BMSPlayer
{
//	-------------------------------------------------------------------------------------------------
	
	final BMSFile 	bms_file;
	
//	-------------------------------------------------------------------------------------------------
//	play refer
	
	ArrayList<Note>	play_tracks;
	
	/** 创建Note单位的检查范围 */
	double			play_create_length;
	
	/** 丢弃Note单位的检查范围  */
	double			play_drop_length;
	
	// dynamic
	double 			play_bpm;
	double			play_position;
	double			play_pre_beat_position;
	double 			play_pre_record_time;
	
//	-------------------------------------------------------------------------------------------------
//	game refer
	
	/** 基线 */
	Line2D			game_base_line;
	
	/** note 移动方向 */
	Point2D			geme_note_direct;
	
	double			game_speed		= 1;
	
//	-------------------------------------------------------------------------------------------------
	
	public BMSPlayer(BMSFile bms) 
	{
		bms_file 			= bms;
	}

//	-------------------------------------------------------------------------------------------------
	
	public void start()
	{

		try{
			play_tracks				= bms_file.getAllNoteList();
			for (Note note : play_tracks) {
				System.out.println(note);
			}
			play_bpm				= bms_file.getHeadInfo(HeadInfo.BPM);
			play_create_length		= bms_file.LINE_SPLIT_DIV * 10;
			play_drop_length		= bms_file.LINE_SPLIT_DIV;
			play_position			= 0;
			play_pre_beat_position	= 0;
			play_pre_record_time	= System.currentTimeMillis();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	
	boolean processSystemNote(Note note) 
	{
		if (note.track<=9)
		{
			switch(note.command)
			{
			case BPM_CHANGE:
				play_bpm = Integer.parseInt(note.value, 16);
				System.out.println(note.command + " " + play_bpm);
				break;
				
			case INDEX_BPM:
				play_bpm = Double.parseDouble(note.note_value.value);
				System.out.println(note.command + " " + play_bpm);
				break;
				
			default:
				System.out.println(note.command + " " + note.value);
			}

			
			return true;
		}
		return false;
	}
	
	void removeNote(Note note) {
		
	}
	
	public void update()
	{
		try
		{
			double	cur_time		= System.currentTimeMillis();
			double	deta_time		= cur_time - play_pre_record_time; 
			play_pre_record_time	= cur_time;
			
			// 已缓冲的音符
			{
				ArrayList<Note> removed = new ArrayList<Note>();
				for (Note note : play_tracks) {
					// 如果该音符过线
					if (note.getBeginPosition() <= play_position) {
						if (processSystemNote(note)) {}
						removed.add(note);
					}
//					// 如果该音符过丢弃线
//					if (note.getBeginPosition() <= play_position) {
//						removed.add(note);
//						removeNote(note);
//					}
				}
				play_tracks.removeAll(removed);
			}
			
			play_position += bms_file.timeToPosition(deta_time, play_bpm);
			
			if (play_position > play_pre_beat_position + bms_file.BEAT_DIV) {
				play_pre_beat_position = play_position;
				System.out.println("BEAT " + play_position);
			}
			
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public double getPlayPosition() {
		return play_position;
	}
	
	public ArrayList<Note> getPlayTracks()
	{
		return play_tracks;
	}
	
}

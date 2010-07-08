package com.cell.midi.track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.Track;

import com.g2d.display.Sprite;

public class TrackLayer implements Comparator<MidiEvent>
{
	Track			src_track;
	
	MidiEvent[] 	events;
	
	/**上次更新时的tick位置*/
	private long 	last_tick_pos 	= 0;
	
	/**由当前位置开始索引前后的事件*/
	private int 	current_index 	= 0;
	
	public TrackLayer(Track track) 
	{
		this.src_track	= track;
		this.events		= new MidiEvent[track.size()];
		for (int i = 0; i < track.size(); i++) {
			this.events[i] = track.get(i);
		}
//		Arrays.sort(events, this);
//		System.out.println("sort events :");
//		for (int i = 0; i < track.size(); i++) {
//			System.out.println("\t"+events[i].getTick());
//		}
	}
	
	@Override
	final public int compare(MidiEvent o1, MidiEvent o2) {
		return (int)(o1.getTick() - o2.getTick());
	}
	
	/**
	 * 跟新当前轨道
	 * @param current_tick_pos
	 */
	final synchronized public void update(long current_tick_pos) 
	{
		long tick_pos_offset = current_tick_pos - last_tick_pos;
		try {
			// 前进了多少位置
			if (tick_pos_offset > 0) {
				for (int i = current_index; i < events.length; i++) {
					MidiEvent event = events[i];
					if (event.getTick() <= current_tick_pos) {
						current_index = i;
					} else {
						break;
					}
				}
			}
			// 后退了多少位置
			else if (tick_pos_offset < 0) {
				for (int i = current_index; i >= 0; i--) {
					MidiEvent event = events[i];
					if (event.getTick() >= current_tick_pos) {
						current_index = i;
					} else {
						break;
					}
				}
			}
		} finally {
			last_tick_pos = current_tick_pos;
		}
	}
	
	
	
	/**获取播放位置前的事件*/
	final synchronized public void getPrevEvents(long ticks, ArrayList<MidiEvent> out) 
	{
		out.clear();
		long end = last_tick_pos - ticks;
		for (int i = current_index; i >= 0; i--) {
			MidiEvent event = events[i];
			if (event.getTick() >= end) {
				out.add(event);
			} else {
				break;
			}
		}
	}
	
	/**获取播放位置后的事件*/
	synchronized public void getNextEvents(long ticks, ArrayList<MidiEvent> out)
	{
		out.clear();
		long end = last_tick_pos + ticks;
		for (int i = current_index; i < events.length; i++) {
			MidiEvent event = events[i];
			if (event.getTick() <= end) {
				out.add(event);
			} else {
				break;
			}
		}
	}
	
	
	
	
	
	
}

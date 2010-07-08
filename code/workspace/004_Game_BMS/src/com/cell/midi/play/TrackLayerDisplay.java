package com.cell.midi.play;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.cell.midi.track.TrackLayer;
import com.g2d.display.Sprite;

public class TrackLayerDisplay extends Sprite
{
	final Player 		player;
	final TrackLayer 	layer;
	
	ArrayList<MidiEvent> next_events = new ArrayList<MidiEvent>();
	
	public TrackLayerDisplay(Player player, Track track) 
	{
		this.player	= player;
		this.layer	= new TrackLayer(track);
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		g.setColor(Color.WHITE);
		g.draw(local_bounds);
		g.setColor(Color.RED);
		g.drawLine(0, getHeight(), getWidth(), getHeight());
		
		long current_tick = player.sequencer.getTickPosition();
		
		this.layer.update(current_tick);
		
		this.layer.getNextEvents(1000, next_events);
		
		int sw = 4;
		int sh = 1;
		
		System.out.println("tick : " + current_tick + " notes="+next_events.size());
		
		g.setColor(Color.WHITE);
		for (MidiEvent me : next_events) {
			MidiMessage mm = me.getMessage();
			if (mm instanceof ShortMessage) {
				ShortMessage sm = (ShortMessage)mm;
				if ((sm.getCommand() & ShortMessage.NOTE_ON) != 0) {
					int note = sm.getData1();
					g.fillRect(
							(int)(sw*note), 
							(int)(getHeight() - sh * (me.getTick()-current_tick)), 
							(int)(sw), 
							(int)(sh));
				}
				
//				g.fillRect(
//				(int)(sw), 
//				(int)(getHeight() - sh * (me.getTick()-current_tick)), 
//				(int)(sw), 
//				(int)(sh));
			}			
		}
		
	}
	
	
}

package com.cell.midi.play;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import com.cell.CIO;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.util.Drawing;

public class Player extends DisplayObjectContainer
{
	Sequence	sequence;
	Sequencer	sequencer;
	
	ArrayList<TrackLayerDisplay> tracks_display = new ArrayList<TrackLayerDisplay>();
	
	public Player()
	{
		System.out.println("list midi divice");
		for (MidiDevice.Info di : MidiSystem.getMidiDeviceInfo()) {
			System.out.println("\tmidi divice : " + di.getName() + " (" + di.getDescription() + ")");
		}
		
		try {
			sequence	= MidiSystem.getSequence(CIO.loadStream("/com/cell/midi/skycity.mid"));
			sequencer	= MidiSystem.getSequencer(false);
			System.out.println("use divice : " + sequencer.getDeviceInfo().getName());
			sequencer.open();
			sequencer.setSequence(sequence);
			
			for (Track t : sequence.getTracks()) {
				TrackLayerDisplay td = new TrackLayerDisplay(this, t);
				this.addChild(td);
				tracks_display.add(td);
			}
			
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {
		sequencer.start();
	}

	@Override
	public void removed(DisplayObjectContainer parent) {
		try {
			sequencer.stop();
		} catch (Exception err) {}
		try {
			sequencer.close();
		} catch (Exception err) {}
	}

	@Override
	public void update() {
		for (TrackLayerDisplay td : tracks_display) {
			td.setSize(getWidth(), getHeight()-40);
		}
	}
	
	@Override
	public void render(Graphics2D g) 
	{
		drawPercent(g);
	}

	
	protected void drawPercent(Graphics2D g) {
		Rectangle percent = new Rectangle(10, getHeight() - 10 - 4, getWidth()-20, 4);
		g.setColor(Color.GRAY);
		g.fill(percent);
		g.setColor(Color.WHITE);
		g.fillRect(percent.x, percent.y, 
				(int)(percent.width * sequencer.getMicrosecondPosition() / sequencer.getMicrosecondLength()), 
				percent.height);
	}

	

}

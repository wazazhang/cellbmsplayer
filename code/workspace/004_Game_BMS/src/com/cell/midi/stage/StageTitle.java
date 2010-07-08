package com.cell.midi.stage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import com.cell.CIO;
import com.cell.midi.play.Player;
import com.cell.midi.track.TrackLayer;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;
import com.g2d.display.Stage;
import com.g2d.util.Drawing;

public class StageTitle extends Stage
{
	Player player = new Player();
	
	public StageTitle(){}
	
	@Override
	public void added(DisplayObjectContainer parent) {
		player = new Player();
		this.addChild(player);
	}

	@Override
	public void removed(DisplayObjectContainer parent) {}

	@Override
	public void update() {
		player.setSize(getWidth(), getHeight());
	}
	
	@Override
	public void render(Graphics2D g) 
	{
		g.setColor(Color.BLACK);
		g.fill(local_bounds);
	}

	@Override
	protected void renderAfter(Graphics2D g) {
		g.setColor(Color.WHITE);
		Drawing.drawString(g, "FPS="+getRoot().getFPS(), 1, 1);
	}
	
}

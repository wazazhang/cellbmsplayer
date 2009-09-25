package com.cell.bms.game;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cell.bms.BMSPlayer;
import com.cell.bms.BMSPlayerListener;
import com.cell.bms.IDefineImage;
import com.cell.bms.BMSFile.Note;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public class BMSLayer extends Sprite implements BMSPlayerListener
{

	BMSPlayer player;
	
	public BMSLayer(BMSPlayer player)
	{
		this.player = player;
		this.player.addListener(this);
	}
	
	
	@Override
	public void added(DisplayObjectContainer parent) {
		super.added(parent);
		setSize(parent.getWidth(), parent.getHeight());
		player.start();
	}
	
	@Override
	public void update()
	{
		super.update();
	}
	
	@Override
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		// paint notes
		player.update();
		{
			IDefineImage img_bg = player.getPlayBGImage();
			if (img_bg != null && img_bg.getImage() != null) {
				g.drawImage(img_bg.getImage(), 0, 0, getWidth(), getHeight(), this);
			} else {
				g.setColor(Color.BLACK);
				g.fill(local_bounds);
			}
			
			g.setColor(Color.WHITE);
			for (Note note : player.getPlayTracks()) {
				double x = (note.track % 100) * 5;
				double y = (player.getPlayPosition() - note.getBeginPosition()) + getHeight()/2;
				g.fillRect((int)x, (int)y, 4, 1);
			}
		}
		
		
		
	}
	
	@Override
	public void onBeat(BMSPlayer player, int beatCount) {
//		System.out.println("BEAT : " + beatCount + " : " + player.getPlayPosition());
	}
	
	@Override
	public void onDropNote(BMSPlayer player, Note note) {
//		System.out.println("Drop Note : " + note);
	}
	
}

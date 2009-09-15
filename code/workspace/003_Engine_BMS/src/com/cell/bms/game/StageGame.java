package com.cell.bms.game;

import java.awt.Graphics2D;

import com.cell.bms.BMSFile;
import com.cell.bms.BMSPlayer;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;

public class StageGame extends Stage
{
	
	
	public StageGame() 
	{
		setSize(Config.STAGE_WIDTH, Config.STAGE_HEIGHT);
		
	}
	
	public void added(DisplayObjectContainer parent) 
	{
		getRoot().setFPS(40);
		
		BMSFile file = new BMSFile("/library.bms");
		
		BMSPlayer player = new BMSPlayer(file);
		
		player.start();

		this.addChild(player);
	}

	public void removed(DisplayObjectContainer parent) {}
	
	public void update() {}
	
	public void render(Graphics2D g) {}
	
}

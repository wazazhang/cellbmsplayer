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
		
		BMSFile file = new BMSFile("D:/CellBMSPlayer/resource/data/song/btm_mario/mario-5.bms");

		BMSPlayer player = new BMSPlayer(file);
		
		this.addChild(new BMSLayer(player));
	}

	public void removed(DisplayObjectContainer parent) {}
	
	public void update() {}
	
	public void render(Graphics2D g) {}
	
}

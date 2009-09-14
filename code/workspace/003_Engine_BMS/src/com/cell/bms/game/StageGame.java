package com.cell.bms.game;

import java.awt.Graphics2D;

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
	}

	public void removed(DisplayObjectContainer parent) {}
	
	public void update() {}
	
	public void render(Graphics2D g) {}
	
}

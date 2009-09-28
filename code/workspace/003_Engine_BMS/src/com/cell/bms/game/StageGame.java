package com.cell.bms.game;

import java.awt.Graphics2D;

import com.cell.bms.BMSFile;
import com.cell.bms.BMSPlayer;
import com.cell.bms.oal.JALNoteFactory;

import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;

public class StageGame extends Stage
{
	JALNoteFactory note_factory;
	
	public StageGame() 
	{
		setSize(Config.STAGE_WIDTH, Config.STAGE_HEIGHT);
		
		try{
			note_factory = new JALNoteFactory();
		}catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	public void added(DisplayObjectContainer parent) 
	{
		getRoot().setFPS(40);
		getRoot().setUnactiveFPS(40);
		
		BMSFile file = new BMSFile(
				note_factory,
//				"D:/CellBMSPlayer/resource/data/song/btm_mario/mario-5.bms"
				"D:/CellBMSPlayer/resource/data/song/animusic/animusic-超级多功能竖琴-7K.bms"
				);

		BMSPlayer player = new BMSPlayer(file);
		
		this.addChild(new BMSLayer(player));
	}

	public void removed(DisplayObjectContainer parent) {}
	
	public void update() {}
	
	public void render(Graphics2D g) {}
	
}

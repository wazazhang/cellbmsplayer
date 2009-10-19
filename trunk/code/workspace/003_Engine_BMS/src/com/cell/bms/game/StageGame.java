package com.cell.bms.game;

import java.awt.Graphics2D;

import com.cell.bms.BMSFile;
import com.cell.bms.BMSPlayer;
import com.cell.bms.oal.JALNoteFactory;

import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.ui.Form;

public class StageGame extends Stage
{
	JALNoteFactory note_factory;
	
	SystemForm system_from;
	
	public StageGame() 
	{
		setSize(Config.STAGE_WIDTH, Config.STAGE_HEIGHT);
		
		try{
			note_factory = new JALNoteFactory();
		}catch(Exception err) {
			err.printStackTrace();
		}
		
		system_from = new SystemForm(this);
		this.addChild(system_from);
	}
	
	public void added(DisplayObjectContainer parent) 
	{
		getRoot().setFPS(40);
		getRoot().setUnactiveFPS(40);
	}

	public void removed(DisplayObjectContainer parent) {}
	
	public void update() {}
	
	public void render(Graphics2D g) {}
	
	
	void start()
	{
		BMSFile file = new BMSFile(
				note_factory,
				"D:/Projects/CellBMSPlayer/resource/data/song/btm_hama/hama_5.bms"
//				"D:/Projects/CellBMSPlayer/resource/data/song/btm_mario/mario-5.bms"
//				"D:/Projects/CellBMSPlayer/resource/data/song/animusic/animusic-7K.bms"
//				"D:/Projects/CellBMSPlayer/resource/data/song/animusic/animusic-8K.bms"
				);

		BMSPlayer player = new BMSPlayer(file);
		
		this.addChild(new BMSLayer(player));

	}
}

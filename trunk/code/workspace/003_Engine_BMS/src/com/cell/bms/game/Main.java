package com.cell.bms.game;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.g2d.SimpleCanvasNoInternal;
import com.g2d.SimpleFrame;

public class Main {

	public static void main(String[] args) throws Exception
	{
		CObject.initSystem(new CStorage("bms_player"), new CAppBridge(
				Main.class.getClassLoader(),
				Main.class));
		
		String config_file = "/game.properties";
		if (args.length > 0) {
			config_file = args[0];
		}
		Config.load(Config.class, config_file);
		
		final SimpleCanvasNoInternal canvas = new SimpleCanvasNoInternal(
				Config.STAGE_WIDTH, 
				Config.STAGE_HEIGHT);
		
		final SimpleFrame frame = new SimpleFrame(
				canvas.getCanvasAdapter(),
				StageGame.class.getName());
		
		frame.setTitle("BMSPlayer");
		
		frame.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				frame.fillCanvasSize();
				super.componentResized(e);
			}
		});
	}
}

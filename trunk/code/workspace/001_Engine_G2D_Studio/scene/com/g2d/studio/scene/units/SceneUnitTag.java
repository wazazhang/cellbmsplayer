package com.g2d.studio.scene.units;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import com.cell.rpg.scene.SceneUnit;
import com.g2d.display.ui.Menu;
import com.g2d.game.rpg.Unit;

public interface SceneUnitTag<T extends SceneUnit>
{
	public Unit			getGameUnit();
	
	public T			getUnit();
	
//	---------------------------------------------------------------------------------------
//	io

	public T 			onWrite();
	
	public void			onReadComplete(ArrayList<Unit> all);
	public void			onWriteComplete(ArrayList<Unit> all);
	
//	---------------------------------------------------------------------------------------
//	edit
	
	public Menu 		getEditMenu();
	
//	---------------------------------------------------------------------------------------
//	snapshot
	public Color 		getSnapColor();
	public Shape 		getSnapShape();

//	---------------------------------------------------------------------------------------
}

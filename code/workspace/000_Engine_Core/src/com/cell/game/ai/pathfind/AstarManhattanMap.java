package com.cell.game.ai.pathfind;

public interface AstarManhattanMap
{
	public int getXCount() ;

	public int getYCount() ;

	public int getCellW() ;

	public int getCellH() ;
	
	public int getFlag(int bx, int by);
}


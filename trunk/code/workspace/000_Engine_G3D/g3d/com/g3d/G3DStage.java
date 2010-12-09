package com.g3d;

public interface G3DStage 
{
	public void init	(G3DCanvas canvas);
	
	public void update	(G3DCanvas canvas, G3DGraphics g, int interval);
	
	public void removed	(G3DCanvas canvas);
}

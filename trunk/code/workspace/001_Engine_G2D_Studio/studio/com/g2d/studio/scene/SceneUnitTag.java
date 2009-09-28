package com.g2d.studio.scene;

import com.cell.rpg.entity.Unit;

public interface SceneUnitTag<T extends Unit>
{
	void onRead(T unit);
	
	T onWrite();
	
}

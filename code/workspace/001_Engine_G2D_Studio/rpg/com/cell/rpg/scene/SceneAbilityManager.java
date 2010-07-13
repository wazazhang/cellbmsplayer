package com.cell.rpg.scene;

import java.util.Set;

public abstract class SceneAbilityManager
{
//	-------------------------------------------------------------------------
	static private SceneAbilityManager manager;
	static public void setManager(SceneAbilityManager m){
		manager = m;
	}
	static public SceneAbilityManager getManager() {
		return manager;
	}
//	-------------------------------------------------------------------------
	
	abstract public Set<Class<?>> getAllTypes();
	
}

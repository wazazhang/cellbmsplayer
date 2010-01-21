package com.cell.rpg.scene.ability;

import com.g2d.annotation.Property;

@Property("[单位能力] NPC行动开始点")
public class ActorPathStart extends AbstractSceneAbility {

	private static final long serialVersionUID = 1L;
	
	/** 对应point对象名 */
	@Property("对应point名字")
	public	String		point_name;
	
	
	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + point_name;
	}
}

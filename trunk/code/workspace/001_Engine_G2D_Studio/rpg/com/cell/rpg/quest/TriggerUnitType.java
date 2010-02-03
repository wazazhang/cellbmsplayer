package com.cell.rpg.quest;

public enum TriggerUnitType {

	PLAYER			("玩家"),
	PET				("携带的宠物"),
	TRIGGERING_NPC	("触发的NPC"),
	
	PLAYER_GROUP	("玩家组"),
	PET_GROUP		("宠物组"),
	;	
	
	final private String text;
	private TriggerUnitType(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
	
	
}

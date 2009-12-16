package com.cell.rpg.quest;

public enum TriggerUnitType {

	PLAYER			("玩家"),
	PLAYER_GROUP	("玩家组"),
	PET				("宠物"),
	TRIGGERING_NPC	("触发的NPC"),
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

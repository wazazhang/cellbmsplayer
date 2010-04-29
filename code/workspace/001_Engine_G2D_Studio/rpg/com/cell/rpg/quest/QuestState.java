package com.cell.rpg.quest;

public enum QuestState {

	NA,
	
	ACCEPTED,
	COMPLETED,
	ACCEPTED_OR_COMPLETED,

	NOT_ACCEPTED,
	NOT_COMPLETED,
	NOT_ACCEPTED_OR_NOT_COMPLETED,
	;
	
	public String toString() {
		return super.toString();
	}
}

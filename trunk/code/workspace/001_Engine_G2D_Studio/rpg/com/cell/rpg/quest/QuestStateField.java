package com.cell.rpg.quest;

public enum QuestStateField 
{
	ACCEPT_TIME_MS			("接收时间"),
	COMMIT_TIME_MS			("完成时间"),
	COMPLETE_COUNT			("完成次数"),
	;

	final private String text;
	private QuestStateField(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
}

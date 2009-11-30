package com.cell.rpg.quest;

import com.cell.DObject;
import com.cell.ExtObject;
import com.cell.util.MarkedHashtable;

public class QuestDiscussion extends ExtObject
{
	public String text	= "";
	

	public QuestDiscussion() {}
	
	@Override
	protected void onRead(MarkedHashtable data) {
		text = data.getString("text", "");
	}
	
	@Override
	protected void onWrite(MarkedHashtable data) {
		data.put("text", text);
	}
}

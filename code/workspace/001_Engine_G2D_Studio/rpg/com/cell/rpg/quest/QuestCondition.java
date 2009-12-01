package com.cell.rpg.quest;

import java.util.ArrayList;
import java.util.Iterator;

import com.cell.ExtObject;
import com.cell.util.MarkedHashtable;

public class QuestCondition extends ExtObject implements Iterable<ArrayList<QuestItem>>
{
	ArrayList<ArrayList<QuestItem>> groups = new ArrayList<ArrayList<QuestItem>>(1);
	
	@Override
	protected void onRead(MarkedHashtable data) {
		groups = data.getObject("groups", groups);
	}
	@Override
	protected void onWrite(MarkedHashtable data) {
		data.put("groups", groups);
	}
	
	public ArrayList<QuestItem> getGroup(int i) {
		return groups.get(i);
	}
	
	public void setGroup(int i, ArrayList<QuestItem> group) {
		groups.set(i, group);
	}
	
	public int size() {
		return groups.size();
	}
	
	@Override
	public Iterator<ArrayList<QuestItem>> iterator() {
		return groups.iterator();
	}
}

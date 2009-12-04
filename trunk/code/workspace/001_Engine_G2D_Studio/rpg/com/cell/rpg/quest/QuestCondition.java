package com.cell.rpg.quest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.cell.ExtObject;
import com.cell.util.MarkedHashtable;

public class QuestCondition extends ExtObject
{
	public TreeSet<String> item_name_list	= new TreeSet<String>();
	
	@Override
	protected void onRead(MarkedHashtable data) {
		item_name_list = data.getObject("item_name_list", new TreeSet<String>());
	}
	
	@Override
	protected void onWrite(MarkedHashtable data) {
		data.put("item_name_list", item_name_list);
	}
}

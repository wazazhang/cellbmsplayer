package com.fc.castle.data;

import java.util.ArrayList;

import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.Messages.BattleStartResponse;

public class BattleLog  extends AbstractData 
{
	private static final long serialVersionUID = 1L;

	public BattleStartResponse battle;
	public ArrayList<BattleEvent> logs;
}

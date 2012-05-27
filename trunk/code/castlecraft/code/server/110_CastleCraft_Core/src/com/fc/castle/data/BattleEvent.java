package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class BattleEvent   extends AbstractData 
{
	private static final long serialVersionUID = 1L;

	@Comment("[uuid, unitType]")
	public static final byte EVENT_BUILD 			= 0;
	
	@Comment("[uuid, unitType]")
	public static final byte EVENT_UNBUILD 			= 1;
	
	@Comment("[uuid, skillType]")
	public static final byte EVENT_LAUNCH_SKILL 	= 2;

	@Comment("[uuid, unitType, buildingUUID]")
	public static final byte EVENT_SOLDIER_SPAWN	= 3;
	
	@Comment("[uuid, unitType]")
	public static final byte EVENT_SOLDIER_DIED 	= 4;
	
	
	public int time;
	public byte event;
	public byte force;
	public int[] datas;
}

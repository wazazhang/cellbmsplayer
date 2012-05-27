package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class BattlePlayer extends AbstractData 
{
	private static final long serialVersionUID = 1L;
	
	final public static int TYPE_PLAYER		= 0;
	final public static int TYPE_MONSTER	= 1;
	
	public int type;

	@Comment("如果是玩家，则有效")
	public int playerID;
	
	public String name;

	public int hp;
	public int mp;
	public int ap;
		
	public SoldierDatas 	soldiers;
	
	public SkillDatas 		skills;

}

package com.fc.castle.data;

import java.sql.Date;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class ExploreData extends AbstractData
{
	private static final long serialVersionUID = 1L;

//	public int id;
	/** SpriteObject (MAP UID)*/
	public String 	UnitName;
	
	public String 	explore_name;

	@Comment("SECONDS")
	public int	 	refreshTime;

	
	@Comment("SECONDS")
	public Date 	last_time;
	
	@Comment("如果为空，则表示式本人探索过该点，否则是别的玩家探索了该点")
	public PlayerFriend last_explorer;

	public ExploreData(){}
}

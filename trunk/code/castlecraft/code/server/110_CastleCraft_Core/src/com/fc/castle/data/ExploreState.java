package com.fc.castle.data;

import java.sql.Date;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class ExploreState extends AbstractData
{
	private static final long serialVersionUID = 1L;

	/** SpriteObject (MAP UID)*/
	public String 		UnitName;

	@Comment("SECONDS")
	public Date 		last_time;

	@Comment("如果为空，则表示式自己探索过该点，否则是别的玩家探索了该点")
	public PlayerFriend last_explorer;

	public int	 		explore_count;
	
	public ExploreState(){}
}

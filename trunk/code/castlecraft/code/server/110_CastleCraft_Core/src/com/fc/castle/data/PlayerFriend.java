package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.fc.castle.data.message.AbstractData;

@Comment("玩家数据")
@SQLTable(primary_key_name = "playerID", primary_key_type = SQLType.INTEGER)
public class PlayerFriend extends AbstractData implements SQLTableRow<Integer>
{
	private static final long serialVersionUID = 1L;

	@SQLField(type=SQLType.INTEGER)
	public int playerID;

	@SQLField(type=SQLType.STRING, size=128)
	public String playerName;

	@SQLField(type=SQLType.STRING, size=128)
	public String playerNickName;

	@SQLField(type=SQLType.STRING, size=128)
	public String headUrl;

	@SQLField(type=SQLType.INTEGER)
	public int level;
	
	public PlayerFriend() {}
	public PlayerFriend(PlayerData pd) 
	{
		this.playerID		= pd.player_id;
		this.playerName		= pd.player_name;
		this.playerNickName	= pd.nick_name;
		this.level 			= pd.level;
	}
	
	@Override
	public Integer getPrimaryKey() {
		return playerID;
	}

}

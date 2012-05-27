package com.fc.castle.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.cell.net.io.Comment;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.fc.castle.data.message.AbstractData;

@Comment("玩家邮件数据")
@SQLTable(
		primary_key_name = "player_id",
		primary_key_type = SQLType.INTEGER, 
		constraint ="UNIQUE KEY `player_name` (`player_name`)",
		comment = "玩家邮件数据")
public class PlayerMailData extends AbstractData implements SQLTableRow<Integer>
{
	private static final long serialVersionUID = 1L;

	@SQLField(type=SQLType.INTEGER)
	public int player_id;

	@SQLField(type=SQLType.STRING, size=128, not_null=true)
	public String player_name;
	
	@SQLField(type=SQLType.BIG_STRUCT)
	public HashMap<Integer, MailSnap> mails;

	@SQLField(type=SQLType.INTEGER)
	public int friendsMaxCount = 50;
	
	@SQLField(type=SQLType.BIG_STRUCT)
	public HashMap<Integer, PlayerFriend> firends;
	
	public PlayerMailData() {}
	public PlayerMailData(int player_id, String pname) {
		this.player_id = player_id;
		this.player_name = pname;
		this.mails = new HashMap<Integer, MailSnap>();
	}
	
	
	@Override
	public Integer getPrimaryKey() {
		return player_id;
	}

	
	
	
	
	
}

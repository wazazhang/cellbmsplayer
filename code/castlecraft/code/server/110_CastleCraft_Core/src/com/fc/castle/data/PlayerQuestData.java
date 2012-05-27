package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.fc.castle.data.message.AbstractData;

@Comment("玩家任务成就数据")
@SQLTable(
		primary_key_name = "player_id",
		primary_key_type = SQLType.INTEGER, 
		comment = "玩家任务成就数据")
public class PlayerQuestData extends AbstractData implements SQLTableRow<Integer> 
{
	private static final long serialVersionUID = 1L;

	@Comment("PlayerID")
	@SQLField(type=SQLType.INTEGER)
	public int player_id;
	
	@Comment("新手引导阶段")
	@SQLField(type=SQLType.INTEGER)
	public int guide_steps;
	
	
	public PlayerQuestData() {}
	public PlayerQuestData(int player_id) {
		this.player_id = player_id;
	}
	
	@Override
	public Integer getPrimaryKey() {
		return player_id;
	}
}

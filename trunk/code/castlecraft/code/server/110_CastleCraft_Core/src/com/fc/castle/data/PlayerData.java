package com.fc.castle.data;

import java.util.HashMap;

import com.cell.net.io.Comment;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.Messages;

@Comment("玩家数据")
@SQLTable(
		primary_key_name = "player_id",
		primary_key_type = SQLType.INTEGER, 
		constraint		="UNIQUE KEY `player_name` (`player_name`)",
		comment = "玩家数据")
public class PlayerData  extends AbstractData implements SQLTableRow<Integer>
{
	private static final long serialVersionUID = 1L;

	@Comment("PlayerID")
	@SQLField(type=SQLType.INTEGER, auto_increment=true)
	public int player_id;
	
	@Comment("用户名字")
	@SQLField(type=SQLType.STRING, size=128, not_null=true)
	public String player_name;

	@Comment("昵称")
	@SQLField(type=SQLType.STRING, size=128)
	public String nick_name;
	
	@Comment("AccountID")
	@SQLField(type=SQLType.STRING, size=128)
	public String account_id;

	@SQLField(type=SQLType.INTEGER)
	public int level;

	@Comment("战斗开始hp")
	@SQLField(type=SQLType.INTEGER)
	public int hp;

	@Comment("战斗开始ap，造兵消耗")
	@SQLField(type=SQLType.INTEGER)
	public int ap;
	
	@Comment("战斗开始mp，释放技能消耗")
	@SQLField(type=SQLType.INTEGER)
	public int mp;
	
	@Comment("经验值")
	@SQLField(type=SQLType.INTEGER)
	public int experience;
	
	@Comment("当前等级的经验")
	@SQLField(type=SQLType.INTEGER)
	public int cur_exp;
	@Comment("到下一级所需的经验")
	@SQLField(type=SQLType.INTEGER)
	public int next_exp;

	@Comment("用于游戏内的各类功能与物品消费")
	@SQLField(type=SQLType.INTEGER)
	public int coin;
	
	@Comment("该玩家主城(中地图)")
	@SQLField(type=SQLType.STRING, size=128)
	public String homeScene;

//	----------------------------------------------------------------------------
	
	@Comment("所有士兵数据")
	@SQLField(type=SQLType.BIG_STRUCT)
	public SoldierDatas soldiers;
	
	@Comment("所有技能数据")
	@SQLField(type=SQLType.BIG_STRUCT)
	public SkillDatas skills;

	@Comment("所有道具")
	@SQLField(type=SQLType.BIG_STRUCT)
	public ItemDatas items;
	
	
//	----------------------------------------------------------------------------
		
	
	@Comment("每场战斗基础携带多少个单位")
	@SQLField(type=SQLType.INTEGER)
	public int battle_soldier_count;

	@Comment("每场战斗基础携带多少个技能")
	@SQLField(type=SQLType.INTEGER)
	public int battle_skill_count;

	@Comment("最后一场战斗数据，用于验证战斗结束事件")
	@SQLField(type=SQLType.STRUCT)
	public Messages.BattleStartResponse lastBattle;

	@Comment("布防")
	@SQLField(type=SQLType.STRUCT)
	public Messages.OrganizeDefenseRequest organizeDefense;
	
//	----------------------------------------------------------------------------
	
	@Comment("所有探索点的记录")
	@SQLField(type=SQLType.BIG_STRUCT)
	public HashMap<String, ExploreState> exploreStates;
	
	@Override
	public Integer getPrimaryKey() {
		return player_id;
	}
}

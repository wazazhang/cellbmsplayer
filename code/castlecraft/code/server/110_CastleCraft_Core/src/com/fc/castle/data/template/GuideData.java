package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.ItemDatas;
import com.fc.castle.data.ItemDrops;
import com.fc.castle.data.message.AbstractData;

@Comment("战斗引导数据")
public class GuideData extends AbstractData implements SQLTableRow<Integer>
{
	private static final long serialVersionUID = 1L;

	@Comment("引导步数")
	@SQLField(type=SQLType.INTEGER)
	public int GUIDE_STEP;
	
	@Comment("新手引导基础ap")
	@SQLField(type=SQLType.INTEGER)
	public int GUIDE_START_AP;
	
	@Comment("新手引导基础mp")
	@SQLField(type=SQLType.INTEGER)
	public int GUIDE_START_MP;
	
	@Comment("新手引导基础hp")
	@SQLField(type=SQLType.INTEGER)
	public int GUIDE_START_HP;
	
//	-------------------------------------------------------
//	玩家
//	-------------------------------------------------------
	
	@Comment("新手引导默认玩家士兵")
	@SQLField(type=SQLType.TEXT_STRUCT)
	public int[] GUIDE_PLAYER_SOLDIER;
	
	@Comment("新手引导默认玩家技能")
	@SQLField(type=SQLType.TEXT_STRUCT)
	public int[] GUIDE_PLAYER_SKILL;
	
//	-------------------------------------------------------
//	敌人
//	-------------------------------------------------------

	@Comment("新手引导ai名字")
	@SQLField(type=SQLType.STRING)
	public String GUIDE_AI_NAME;
	
	@Comment("新手引导默认AI士兵")
	@SQLField(type=SQLType.TEXT_STRUCT)
	public int[] GUIDE_AI_SOLDIER;
	
	@Comment("新手引导默认AI技能")
	@SQLField(type=SQLType.TEXT_STRUCT)
	public int[] GUIDE_AI_SKILL;
	
	@Comment("新手引导地图")
	@SQLField(type=SQLType.STRING)
	public String GUIDE_BATTLE_MAP;

//	-------------------------------------------------------
//	奖励
//	-------------------------------------------------------

	@Comment("事件完成的经验值")
	@SQLField(type=SQLType.INTEGER)
	public int AWARD_EXP;
	
	@Comment("事件完成的游戏币")
	@SQLField(type=SQLType.INTEGER)
	public int AWARD_GOLD;
	
	@Comment("事件完成掉落道具")
	@SQLField(type=SQLType.XML_STRUCT)
	public ItemDatas AWARD_ITEMS;

//	-------------------------------------------------------
	
	@Override
	public Integer getPrimaryKey() {
		return GUIDE_STEP;
	}

}

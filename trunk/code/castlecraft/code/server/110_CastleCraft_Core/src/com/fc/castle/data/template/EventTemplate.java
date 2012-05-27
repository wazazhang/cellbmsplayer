package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.cell.net.io.flash.ASFunction;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.ItemDatas;
import com.fc.castle.data.ItemDrops;
import com.fc.castle.data.SkillDatas;
import com.fc.castle.data.SoldierDatas;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.AbstractTemplate;

@Comment("怪物模板数据，一般指地图上的怪物群和怪物进攻事件")
public class EventTemplate extends AbstractTemplate
{
	private static final long serialVersionUID = 1L;

	@Comment("怪物群类型")
	@SQLField(type=SQLType.INTEGER)
	public int type;

	@Comment("子类型")
	@SQLField(type=SQLType.INTEGER)
	public int subtype;

	@Comment("战斗开始hp")
	@SQLField(type=SQLType.INTEGER)
	public int hp;

	@Comment("战斗开始ap，造兵消耗")
	@SQLField(type=SQLType.INTEGER)
	public int ap;
	
	@Comment("战斗开始mp，释放技能消耗")
	@SQLField(type=SQLType.INTEGER)
	public int mp;
	
	@Comment("战斗地图")
	@SQLField(type=SQLType.STRING)
	public String battleMap;
	
	@Comment("刷新时间(秒)，比如探索点刷新时间，小村庄占领时间")
	@SQLField(type=SQLType.INTEGER)
	public int refreshTime;
	
	@Comment("所有士兵数据")
	@SQLField(type=SQLType.XML_STRUCT)
	public SoldierDatas soldiers;
	
	@Comment("所有技能数据")
	@SQLField(type=SQLType.XML_STRUCT)
	public SkillDatas skills;
	
//	----------------------------------------------------------------------------------

	@Comment("事件完成的经验值")
	@SQLField(type=SQLType.INTEGER)
	public int exp;
	
	@Comment("事件完成的游戏币")
	@SQLField(type=SQLType.INTEGER)
	public int gold;
	
	@Comment("事件完成掉落道具")
	@SQLField(type=SQLType.XML_STRUCT)
	public ItemDrops itemDrops;
	
//	----------------------------------------------------------------------------------

	
	@Comment("怪物群名字")
	@SQLField(type=SQLType.STRING)
	public String name;
	
	@Comment("描述")
	@SQLField(type=SQLType.STRING)
	public String desc;
	
	
//	----------------------------------------------------------------------------------


	
	@Override
	@ASFunction({
	"override public function getType() : int {",
	"	return type;",
	"}"})
	public Integer getPrimaryKey() {
		return type;
	}
	
	@Override
	@ASFunction({
	"override public function getName() : String {",
	"	return name;",
	"}"})
	public String getName() {
		return name;
	}
}

package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.cell.net.io.flash.ASFunction;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.AbstractTemplate;

@Comment("玩家技能模板数据")
public class SkillTemplate extends AbstractTemplate
{
	private static final long serialVersionUID = 1L;

	@Comment("技能类型id")
	@SQLField(type=SQLType.INTEGER)
	public int type;

	@Comment("攻击力")
	@SQLField(type=SQLType.INTEGER)
	public int attack;

	@Comment("MP消耗")
	@SQLField(type=SQLType.INTEGER)
	public int costMP;

	@Comment("HP消耗")
	@SQLField(type=SQLType.INTEGER)
	public int costHP;
	
	@Comment("技能cd")
	@SQLField(type=SQLType.INTEGER)
	public int coolDown;
	
//	----------------------------------------------------------------------------------
	
	@Comment({"技能表现", "一般配合spriteEffect使用！"})
	@SQLField(type=SQLType.INTEGER)
	public String spriteBehavior;
	
	@Comment("精灵特效")
	@SQLField(type=SQLType.STRING)
	public String spriteEffect;

	@Comment("图标")
	@SQLField(type=SQLType.STRING)
	public String icon;
	
//	----------------------------------------------------------------------------------
	
	@Comment({"攻击范围", "0表示选定一个目标"})
	@SQLField(type=SQLType.INTEGER)
	public int range;
	
	@Comment({"可以攻击多少个附近目标", "0表示选定一个目标，正整数表示具体数量，-1表示无上限"})
	@SQLField(type=SQLType.INTEGER)
	public int aoe;

	@Comment({"目标类型"})
	@SQLField(type=SQLType.INTEGER)
	public int targetType;
	
	@Comment("产生BUFF列表")
	@SQLField(type=SQLType.TEXT_STRUCT)
	public int[] buffList;
	
	@Comment("特殊能力列表")
	@SQLField(type=SQLType.TEXT_STRUCT)
	public int[] specialEffects;
	
//	----------------------------------------------------------------------------------

	@Comment("技能名字")
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

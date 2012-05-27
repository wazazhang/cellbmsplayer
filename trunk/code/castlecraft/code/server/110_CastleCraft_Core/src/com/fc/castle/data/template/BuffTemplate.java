package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.cell.net.io.flash.ASFunction;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.AbstractTemplate;


@Comment("BUFF模板数据")
public class BuffTemplate extends AbstractTemplate
{
	private static final long serialVersionUID = 1L;

	@Comment("Buff类型id")
	@SQLField(type=SQLType.INTEGER)
	public int type;

	@Comment("持续时间")
	@SQLField(type=SQLType.INTEGER)
	public int time;
	
	@Comment("负面效果")
	@SQLField(type=SQLType.INTEGER)
	public int debuff;

	@Comment("Buff期间，持续产生的效果")
	@SQLField(type=SQLType.STRING)
	public String effect;
	
//	---------------------------------------------------------------------------
	
	@Comment({"攻击增加％", "[百分比]"})
	@SQLField(type=SQLType.FLOAT)
	public float enhanceDamage;
	
	@Comment({"攻击增加直接", "[绝对值]"})
	@SQLField(type=SQLType.FLOAT)
	public float addDamage;

	@Comment({"伤害减免%", "[百分比]"})
	@SQLField(type=SQLType.FLOAT)
	public float damageReduction;
	
	@Comment({"防御力增加％", "[百分比]"})
	@SQLField(type=SQLType.FLOAT)
	public float enhanceDefens;

	@Comment({"防御力增加", "[绝对值]"})
	@SQLField(type=SQLType.FLOAT)
	public float addDefense;
	
	@Comment({"急速等级，减少攻击cd％", "[百分比]"})
	@SQLField(type=SQLType.FLOAT)
	public float hasteRating;
	
	@Comment({"移动速度增加％", "[百分比]"})
	@SQLField(type=SQLType.FLOAT)
	public float moveRating;
	
	@Comment({"血量增加", "[绝对值]"})
	@SQLField(type=SQLType.FLOAT)
	public float addHP;

	@Comment({"血量增加％", "[百分比]"})
	@SQLField(type=SQLType.FLOAT)
	public float enhanceHP;
	
	@Comment({"持续减血", "[伤害]"})
	@SQLField(type=SQLType.FLOAT)
	public float burning;
	
	@Comment({"持续减血", "[每隔多少帧触发一次]"})
	@SQLField(type=SQLType.INTEGER)
	public int burningInterval;

//	---------------------------------------------------------------------------
	@Comment("icon")
	@SQLField(type=SQLType.STRING)
	public String icon;

	@Comment("Buff名字")
	@SQLField(type=SQLType.STRING)
	public String name;
	
	
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
package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.cell.net.io.flash.ASFunction;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.AbstractTemplate;

@Comment("兵种模板数据")
public class UnitTemplate extends AbstractTemplate
{
	private static final long serialVersionUID = 1L;

	@Comment("兵种类型id")
	@SQLField(type=SQLType.INTEGER)
	public int type;

	@Comment("对应编辑起精灵")
	@SQLField(type=SQLType.STRING)
	public String csprite_id;

	@Comment("图标")
	@SQLField(type=SQLType.STRING)
	public String icon;
	

	@Comment("移动类型")
	@SQLField(type=SQLType.INTEGER)
	public int motionType;

	@Comment("攻击方式")
	@SQLField(type=SQLType.INTEGER)
	public int fightType;

	@Comment("攻击力")
	@SQLField(type=SQLType.INTEGER)
	public int attack;

	@Comment("攻击类型")
	@SQLField(type=SQLType.INTEGER)
	public int attackType;
	
	@Comment("攻击间隔CD")
	@SQLField(type=SQLType.INTEGER)
	public int attackCD;

	@Comment("攻击距离")
	@SQLField(type=SQLType.INTEGER)
	public int attackRange;

	@Comment({"攻击特效","近战是刀光id，远程是箭矢id"})
	@SQLField(type=SQLType.STRING)
	public String attackEffect;
	
	@Comment("警戒距离")
	@SQLField(type=SQLType.INTEGER)
	public int guardRange;
	
	@Comment("防御力")
	@SQLField(type=SQLType.INTEGER)
	public int defense;

	@Comment("防御类型")
	@SQLField(type=SQLType.INTEGER)
	public int defenseType;

	@Comment("血量")
	@SQLField(type=SQLType.INTEGER)
	public int hp;

	@Comment("魔法值")
	@SQLField(type=SQLType.INTEGER)
	public int mp;

	@Comment("移动速度")
	@SQLField(type=SQLType.FLOAT)
	public float moveSpeed;
	
	@Comment("训练时间(帧)")
	@SQLField(type=SQLType.INTEGER)
	public int trainingTime;
	
	@Comment("花费")
	@SQLField(type=SQLType.INTEGER)
	public int cost;

	@Comment("兵种技能")
	@SQLField(type=SQLType.TEXT_STRUCT)
	public int[] skills;
	
	@Comment("兵种名字")
	@SQLField(type=SQLType.STRING)
	public String name;

	@Comment("兵种描述")
	@SQLField(type=SQLType.STRING)
	public String description;

	
	
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

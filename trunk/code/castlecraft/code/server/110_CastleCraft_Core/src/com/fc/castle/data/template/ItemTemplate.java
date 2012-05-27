package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.cell.net.io.flash.ASFunction;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.SkillDatas;
import com.fc.castle.data.SoldierDatas;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.AbstractTemplate;

@Comment("玩家道具模板数据")
public class ItemTemplate extends AbstractTemplate
{
	private static final long serialVersionUID = 1L;

	@Comment("道具类型")
	@SQLField(type=SQLType.INTEGER)
	public int type;

	@Comment("图标")
	@SQLField(type=SQLType.STRING)
	public String icon;

	@Comment({"价格","对应PlayerData.coin"})
	@SQLField(type=SQLType.INTEGER)
	public int priceCoin;

//	---------------------------------------------------------------------------------
//	function
	
	@Comment("可以获取兵种")
	@SQLField(type=SQLType.XML_STRUCT)
	public SoldierDatas getUnits;

	@Comment("可以获取技能")
	@SQLField(type=SQLType.XML_STRUCT)
	public SkillDatas getSkills;

//	---------------------------------------------------------------------------------
	
	@Comment("道具名字")
	@SQLField(type=SQLType.STRING)
	public String name;

	@Comment("描述")
	@SQLField(type=SQLType.STRING)
	public String desc;
	
//	---------------------------------------------------------------------------------
	
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

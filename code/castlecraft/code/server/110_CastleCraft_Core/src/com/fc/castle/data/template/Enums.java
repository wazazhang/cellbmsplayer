package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.fc.castle.data.message.AbstractData;

@SuppressWarnings("serial")
public class Enums 
{
//	------------------------------------------------------------------------------------------------------------
	
	@Comment("移动类型")
	public static class MotionType extends AbstractData
	{
		@Comment("陆地")
		final public static int LAND 		= 0;
		
		@Comment("飞行")
		final public static int FLY 		= 1;	
	}

	@Comment("攻击方式")
	public static class FightType extends AbstractData
	{
		@Comment("近战")
		final public static int NORMAL 		= 0;
		
		@Comment("飞弹")
		final public static int MISSILE 	= 1;
		
		@Comment("火炮")
		final public static int ARTILLERY	 	= 2;

		@Comment("直接")
		final public static int INSTANT	 	= 3;
	}

	@Comment("攻击类型")
	public static class AttackType extends AbstractData
	{
		@Comment("普通")
		final public static int NORMAL 		= 0;
		
		@Comment("穿刺")
		final public static int PIERCE 		= 1;
		
		@Comment("攻城")
		final public static int SIEGE 		= 2;
		
		@Comment("魔法")
		final public static int MAGIC 		= 3;
		
		@Comment("混乱")
		final public static int CHAOS	 	= 4;

		@Comment("英雄")
		final public static int HERO 		= 5;
		
		@Comment("技能")
		final public static int SKILL 		= 6;
		
	}

	@Comment("防御类型")
	public static class DefenseType extends AbstractData
	{
		@Comment("无甲")
		final public static int UNARMORED = 0;
		
		@Comment("轻甲")
		final public static int LIGHT = 1;
		
		@Comment("中甲")
		final public static int MEDIUM = 2;
		
		@Comment("重甲")
		final public static int HEAVY = 3;
		
		@Comment("英雄")
		final public static int HERO = 4;
		
		@Comment("城甲")
		final public static int FORT = 5;
	}	

	
	@Comment("玩家技能目标类型，对应字段[SkillTemplate.targetType]")
	public static class SkillTargetType extends AbstractData
	{
		@Comment({"敌人"})
		final public static int ENEMY = 1;

		@Comment({"友军"})
		final public static int FIREND = 2;

		@Comment({"敌方和友军"})
		final public static int BOTH = 3;
		
		@Comment({"随机敌人"})
		final public static int RANDOM_ENEMY = 4;
		
		@Comment({"随机友军"})
		final public static int RANDOM_FIREND = 5;
	}

	@Comment("玩家技能特殊效果，对应字段[SkillTemplate.specialEffects]")
	public static class SkillSpecialEffect extends AbstractData
	{
		@Comment({"净化", "清除友军负面BUFF效果"})
		final public static int PURGE = 1;

		@Comment({"诅咒", "清除敌军正面BUFF效果"})
		final public static int CURSE = 2;

		@Comment({"叛变", "敌军转换为友军"})
		final public static int RENEGADE = 3;
	}

	
	@Comment("中地图事件类型")
	public static class SceneEventType extends AbstractData
	{
		@Comment("小村庄")
		final public static int VILLAGE = 1;

		@Comment("探索点")
		final public static int EXPLORE = 2;

		@Comment("种族探索点")
		final public static int ES_RACE = 3;
		
		@Comment("随机探索点")
		final public static int ES_RANDOM = 4;
		
		@Comment("未知探索点")
		final public static int ES_UNKNOW = 5;
		
		@Comment("挑战探索点")
		final public static int ES_CHALLANGE = 6;
	}
	
//	@Comment("随机探索点类型")
//	public static class RandomExploreType extends AbstractData
//	{
//		@Comment("")
//		public static final int RANDOM_TYPE_GOLD	= 1;
//		@Comment("")
//		public static final int RANDOM_TYPE_BOX		= 2;
//		@Comment("")
//		public static final int RANDOM_TYPE_MONSTER	= 3;
//	}
	

}

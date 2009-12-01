package com.cell.rpg.quest;

import java.lang.ref.Reference;

import com.cell.CIO;
import com.cell.rpg.RPGObject;
import com.g2d.annotation.Property;

/**
 * 用于任务的状态存储<br>
 * 例如：
 * 玩家完成一个任务后或触发一段剧情后，获得若干QuestItem，
 * QuestItem存储到当前玩家的状态，当玩家触发了另一个任务时，
 * 如果该任务的接受条件包含玩家身上的QuestItem，
 * 那么触发的新任务就被激活，激活的任务可能消耗掉玩家身上的道具，
 * 也可能给予玩家另外的QuestItem。<br>
 * QuestItem也可以理解为任务标志或者任务令牌。
 * @author WAZA
 */
public class QuestItem extends RPGObject implements Cloneable
{
	final private Integer	type;

	public String 			name;
	
//	--------------------------------------------------------------------------------------
	
	@Property("物品类型")
	public int				titem_index	= -1;
	@Property("物品数量")
	public int				titem_count	= 0;
	
	
	
//	--------------------------------------------------------------------------------------
	public QuestItem(Integer type, String name) {
		super(type.toString());
		this.type = type;
		this.name = name;
	}
	public int getType() {
		return type;
	}
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return null;
	}	
	public QuestItem clone() {
		return CIO.cloneObject(this);
	}
//	--------------------------------------------------------------------------------------
	

}

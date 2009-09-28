package com.cell.rpg.ability;

import com.cell.CUtil;
import com.g2d.annotation.Property;


@Property("[单位能力] NPC队伍中的一个单位")
public class AbilitySceneNPCTeamNode extends AbilityXLS 
{
	private static final long serialVersionUID = 1L;
	
	/** 对应actor对象名 */
	@Property("对应actor对象名")
	public	String		cpj_actor_name;
	
	/** 战斗时处在第几行 */
	@Property("战斗时处在第几行")
	public	int			battle_row;
	
	/** 战斗时处在第几列 */
	@Property("战斗时处在第几列")
	public	int			battle_column;
	
	
	@Override
	public boolean isMultiField() {
		return true;
	}
	
	/**
	 * 获得 ActorSet 名
	 * @return
	 */
	public String getActorCPJName() {
		return CUtil.splitString(cpj_actor_name, "/")[0];
	}
	
	/**
	 * 获得 ActorSet 里的 ActorID
	 * @return
	 */
	public String getActorCPJSpriteName() {
		return CUtil.splitString(cpj_actor_name, "/")[1];
	}
	
	
	@Override
	public String toString() {
		return super.toString() + " :(r" + battle_row + ",c" + battle_column + ")";
	}
}

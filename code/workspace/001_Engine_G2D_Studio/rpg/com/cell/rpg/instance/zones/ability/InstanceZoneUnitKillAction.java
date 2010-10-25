package com.cell.rpg.instance.zones.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.struct.ScriptCode;
import com.g2d.annotation.Property;


@Property("[副本单位能力] 单位死亡后，设置副本变量")
public class InstanceZoneUnitKillAction extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;

	/** 掉落道具列表ID */
	@Property("脚本")
	public ScriptCode		dst_value		= new ScriptCode();

	@Override
	final public boolean isMultiField() {
		return false;
	}
}

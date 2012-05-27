package com.fc.castle.data;

import com.fc.castle.data.message.AbstractData;

public class SkillData  extends AbstractData
{
	private static final long serialVersionUID = 1L;

	
	public int skillType;
	
	public SkillData() {}
	public SkillData(int skillType) 
	{
		this.skillType = skillType;
	}

	@Override
	public String toString() {
		return skillType+"";
	}
}

package com.fc.castle.data;

import java.util.ArrayList;
import java.util.Arrays;

import com.cell.CUtil;
import com.fc.castle.data.message.AbstractData;

public class SkillDatas extends AbstractData
{
	private static final long serialVersionUID = 1L;

	public ArrayList<SkillData> datas;
	
	public SkillDatas() {
		this.datas = new ArrayList<SkillData>();
	}
	public SkillDatas(SkillData ... datas) 
	{
		this.datas = new ArrayList<SkillData>(Arrays.asList(datas));
	}


	@Override
	public String toString() {
		return getClass().getSimpleName() + "["  + CUtil.arrayToString(datas) + "]";
	}

}

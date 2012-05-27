package com.fc.castle.data;

import java.util.ArrayList;
import java.util.Arrays;

import com.cell.CUtil;
import com.fc.castle.data.message.AbstractData;

public class SoldierDatas extends AbstractData
{
	private static final long serialVersionUID = 1L;

	public ArrayList<SoldierData> datas;
	
	public SoldierDatas() {
		this.datas = new ArrayList<SoldierData>();
	}
	public SoldierDatas(SoldierData ... datas) 
	{
		this.datas = new ArrayList<SoldierData>(Arrays.asList(datas));
	}
	

	@Override
	public String toString() {
		return getClass().getSimpleName() + "["  + CUtil.arrayToString(datas) + "]";
	}

}


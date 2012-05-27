package com.fc.castle.data;

import com.fc.castle.data.message.AbstractData;

public class SoldierData  extends AbstractData
{
	private static final long serialVersionUID = 1L;

	public int unitType;
	
	public SoldierData() {}
	public SoldierData(int unitType) 
	{
		this.unitType = unitType;
	}
	@Override
	public String toString() {
		return unitType+"";
	}
}

package com.fc.castle.data;

import java.util.ArrayList;
import java.util.Arrays;

import com.cell.CUtil;
import com.fc.castle.data.message.AbstractData;

public class ItemDatas extends AbstractData
{
	private static final long serialVersionUID = 1L;

	public ArrayList<ItemData> datas;

	public ItemDatas() {
		this.datas = new ArrayList<ItemData>();
	}

	public ItemDatas(ItemData... datas) {
		this.datas = new ArrayList<ItemData>(Arrays.asList(datas));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "["  + CUtil.arrayToString(datas) + "]";
	}

}

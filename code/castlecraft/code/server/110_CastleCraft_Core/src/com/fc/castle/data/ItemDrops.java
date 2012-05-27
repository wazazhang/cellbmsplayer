package com.fc.castle.data;

import java.util.ArrayList;
import java.util.Arrays;

import com.cell.CUtil;
import com.fc.castle.data.message.AbstractData;

public class ItemDrops extends AbstractData
{
	private static final long serialVersionUID = 1L;

	public ArrayList<ItemDrop> datas;

	public ItemDrops() {
		this.datas = new ArrayList<ItemDrop>();
	}

	public ItemDrops(ItemDrop... datas) {
		this.datas = new ArrayList<ItemDrop>(Arrays.asList(datas));
	}


	@Override
	public String toString() {
		return getClass().getSimpleName() + "["  + CUtil.arrayToString(datas) + "]";
	}


}

package com.fc.castle.data;

import java.util.ArrayList;
import java.util.Arrays;

import com.cell.CUtil;
import com.fc.castle.data.message.AbstractData;

public class ShopItems extends AbstractData
{
	private static final long serialVersionUID = 1L;

	public ArrayList<ShopItem> datas;

	public ShopItems() {
		this.datas = new ArrayList<ShopItem>();
	}

	public ShopItems(ShopItem... datas) {
		this.datas = new ArrayList<ShopItem>(Arrays.asList(datas));
	}


	@Override
	public String toString() {
		return getClass().getSimpleName() + "["  + CUtil.arrayToString(datas) + "]";
	}


}

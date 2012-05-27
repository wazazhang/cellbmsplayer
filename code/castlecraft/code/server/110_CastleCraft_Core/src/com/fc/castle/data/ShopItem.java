package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class ShopItem extends AbstractData
{
	private static final long serialVersionUID = 1L;

	@Comment("道具类型")
	public int itemType;

	@Comment("数量")
	public int count;
	
	
	public ShopItem() {}
	public ShopItem(int itemType, int count) 
	{
		this.itemType = itemType;
		this.count = count;
	}

	@Override
	public String toString() 
	{
		return itemType + " x" + count;
	}
}

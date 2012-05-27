package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class ItemData extends AbstractData
{
	private static final long serialVersionUID = 1L;

	@Comment("道具类型")
	public int itemType;

	@Comment("堆叠数量")
	public int count;
	
	public ItemData() {}
	public ItemData(int itemType, int count) 
	{
		this.itemType = itemType;
		this.count = count;
	}

	@Override
	public String toString() 
	{
		return itemType+" x" + count;
	}
}

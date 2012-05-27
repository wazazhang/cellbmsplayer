package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class ItemDrop extends AbstractData
{
	private static final long serialVersionUID = 1L;

	@Comment("道具类型")
	public int itemType;

	@Comment("数量")
	public int count;
	
	@Comment("掉落几率 (100.0%)")
	public float percent;
	
	public ItemDrop() {}
	public ItemDrop(int itemType, int count, float percent) 
	{
		this.itemType = itemType;
		this.count = count;
		this.percent = percent;
	}

	@Override
	public String toString() 
	{
		return itemType + " x" + count + " " + (percent) + "%";
	}
}


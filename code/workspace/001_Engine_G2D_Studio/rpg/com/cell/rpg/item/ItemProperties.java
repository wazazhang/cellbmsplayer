package com.cell.rpg.item;

import java.util.ArrayList;
import java.util.Random;

import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.item.ItemPropertyTemplate.ArgTemplate;
import com.g2d.annotation.Property;

public class ItemProperties extends RPGObject implements NamedObject
{
	final private int	tid;
	
	/** */
	public String				name;
	
	@Property({"获得的属性范围"})
	public ArgTemplate<Integer>	properties_range = new ArgTemplate<Integer>(1);

	public ItemProperties(int tid, String name) {
		super(tid+"");
		this.tid	= tid;
		this.name	= name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (properties_range==null) {
			properties_range = new ArgTemplate<Integer>(1);
		}
	}
	
	public int getIntID() {
		return tid;
	}

	@Override
	public Class<?>[] getSubAbilityTypes() {
		return ItemPropertyTypes.getItemPropertyTypes();
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	/***
	 * 生成该道具时，产生一组模板内的道具属性
	 * @return
	 */
	public ItemPropertyData[] createItemPropertiesData(Random random) throws Exception {
		
		ArrayList<ItemPropertyTemplate> properties = getAbilities(ItemPropertyTemplate.class);
		
		int count = properties_range.getValue();
		if (count > properties.size()) {
			count = properties.size();
		}
		if (count < 0) {
			count = 0;
		}
		
		ItemPropertyData[] ret = new ItemPropertyData[count];
		
		for (int i = 0; i < count; i++) {
			int pi = Math.abs(random.nextInt() % properties.size());
			ItemPropertyTemplate t = properties.remove(pi);
			ret[i] = t.createData();
		}
		
		return ret;
	}
//	-----------------------------------------------------------------------------------------------------------------------

}

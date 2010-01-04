package com.cell.rpg.item;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.item.anno.ItemType;

/**
 * 描述一个道具属性，子类必须标注签名{@link ItemType}，子类也必须有默认构造函数
 * @author WAZA
 *
 */
public abstract class ItemProperty extends AbstractAbility
{
	@Override
	public boolean isMultiField() {
		return false;
	}
	
	public static Integer getType(Class<?> cls) {
		ItemType type = cls.getAnnotation(ItemType.class);
		if (type != null) {
			return type.value();
		} else {
			return null;
		}
	}
}


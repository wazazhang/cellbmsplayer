package com.cell.rpg.ability;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.g2d.annotation.Property;

/**
 * 子类必须有默认构造函数，该对象将要放到ObjectPropertyPanel里编辑
 * @author WAZA
 *
 */
public abstract class AbstractAbility implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 是否允许多个字段在同一个单位中
	 * @return
	 */
	abstract public boolean isMultiField() ;
	
	@Override
	public String toString() {
		return getName(getClass());
	}
	
//	----------------------------------------------------------------------------------------------------------------
	
	protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	
	protected Object readResolve() throws ObjectStreamException {
		return this;
	}

//	----------------------------------------------------------------------------------------------------------------
	
	
	public static String getName(Class<?> cls) {
		Property property = cls.getAnnotation(Property.class);
		if (property != null) {
			return property.value()[0];
		} else {
			return cls.getSimpleName();
		}
	}
	
	public static <T extends AbstractAbility> T createAbility(Class<T> cls) {
		try {
			T ability = cls.newInstance();
			return ability;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}

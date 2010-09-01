package com.net;

import java.util.Map;

import com.cell.exception.NotImplementedException;
import com.net.anno.ExternalizableMessageType;
import com.sun.xml.internal.txw2.IllegalAnnotationException;

/**
 * 类型和 integer 的映射关系，用于 TransmissionType = TRANSMISSION_TYPE_EXTERNALIZABLE 类型的消息。
 */
public abstract class ExternalizableFactory 
{
	final Map<Integer, Class<?>> type_map;
	
	public ExternalizableFactory() {
		this.type_map = createMap();
	}

	/**
	 * 子类提供的表实现
	 * @return
	 */
	abstract protected Map<Integer, Class<?>> createMap();
	
	public int getType(MessageHeader message) {
		ExternalizableMessageType ext_type = message.getClass().getAnnotation(ExternalizableMessageType.class);
		return ext_type.value();
	}
	
	public MessageHeader createMessage(int type) throws InstantiationException, IllegalAccessException   {
		Class<?> ext_type = type_map.get(type);
		return (MessageHeader)ext_type.newInstance();
	}
	
	/**
	 * 将标注{@link ExternalizableMessageType}的类注册到系统
	 * @param clazz
	 */
	public void registClass(Class<?> clazz) throws Exception {
		clazz.asSubclass(ExternalizableMessage.class);
		ExternalizableMessageType ext_type = clazz.getAnnotation(ExternalizableMessageType.class);
		if (ext_type == null) {
			throw new NotImplementedException("not Annotation ExternalizableMessageType : " + clazz);
		} else {
			Class<?> sc = type_map.get(ext_type.value());
			if (sc == null) {
				type_map.put(ext_type.value(), clazz);
			} else {
				throw new IllegalAnnotationException("duplicate Annotation ExternalizableMessageType : " +
						"\"" + clazz + "\"(" + ext_type.value() + ") src=\""+sc.getName()+"\"");
			}
		}
	}
	
	
	/**
	 * 如果该类是ExternalizableMessage，则将标注{@link ExternalizableMessageType}的类注册到系统，否则查找在此类中定义的类种类。
	 * @param cls
	 */
	public void registClasses(Class<?> cls) {
		try {
			cls.asSubclass(ExternalizableMessage.class);
			ExternalizableMessageType ext_type = cls.getAnnotation(ExternalizableMessageType.class);
			if (ext_type != null) {
				registClass(cls);
			}
		} catch (Exception err) {}
		
		for (Class<?> sub : cls.getClasses()) {
			registClasses(sub);
		}
	}
}

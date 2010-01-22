package com.net;

import java.util.Map;

import com.net.anno.ExternalizableMessageType;
import com.sun.xml.internal.txw2.IllegalAnnotationException;

/**
 * 类型和 integer 的映射关系，用于 TransmissionType = TRANSMISSION_TYPE_EXTERNALIZABLE 类型的消息。
 */
public abstract class ExternalizableFactory 
{
	final Map<Integer, Class<? extends MessageHeader>> type_map;
	
	public ExternalizableFactory() {
		this.type_map = createMap();
	}
	
	final public int getType(MessageHeader message){
		ExternalizableMessageType ext_type = message.getClass().getAnnotation(ExternalizableMessageType.class);
		return ext_type.value();
	}
	
	final public MessageHeader getMessage(int type) throws InstantiationException, IllegalAccessException   {
		Class<? extends MessageHeader> ext_type = type_map.get(type);
		return ext_type.newInstance();
	}
	
	/**
	 * 将标注{@link ExternalizableMessageType}的类注册到系统
	 * @param clazz
	 */
	final public void registClass(Class<? extends MessageHeader> clazz) {
		ExternalizableMessageType ext_type = clazz.getAnnotation(ExternalizableMessageType.class);
		if (!type_map.containsKey(ext_type.value())) {
			type_map.put(ext_type.value(), clazz);
		} else {
			new IllegalAnnotationException(
					"duplicate ExternalizableMessageType : " + clazz + "(" + ext_type.value() + ")");
		}
	}
	
	abstract protected Map<Integer, Class<? extends MessageHeader>> createMap();
}

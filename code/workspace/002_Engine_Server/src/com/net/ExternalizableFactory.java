package com.net;

import java.util.HashMap;

import com.net.anno.ExternalizableMessageType;

/**
 * 类型和 integer 的映射关系，用于 TransmissionType = TRANSMISSION_TYPE_EXTERNALIZABLE 类型的消息。
 */
public abstract class ExternalizableFactory 
{
	HashMap<Integer, Class<? extends MessageHeader>> type_map = new HashMap<Integer, Class<? extends MessageHeader>>();
	
	public int getType(MessageHeader message){
		ExternalizableMessageType ext_type = message.getClass().getAnnotation(ExternalizableMessageType.class);
		return ext_type.value();
	}
	
	public MessageHeader getMessage(int type) throws InstantiationException, IllegalAccessException   {
		Class<? extends MessageHeader> ext_type = type_map.get(type);
		return ext_type.newInstance();
	}
	
	/**
	 * 将标注{@link ExternalizableMessageType}的类注册到系统
	 * @param clazz
	 */
	final public void registClass(Class<? extends MessageHeader> clazz) {
		ExternalizableMessageType ext_type = clazz.getAnnotation(ExternalizableMessageType.class);
		type_map.put(ext_type.value(), clazz);
	}
}

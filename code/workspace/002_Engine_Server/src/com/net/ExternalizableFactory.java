package com.net;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 类型和 integer 的映射关系，用于 TransmissionType = TRANSMISSION_TYPE_EXTERNALIZABLE 类型的消息。
 * 此实现中，通过类名的字符串自然排序顺序注册。
 */
public abstract class ExternalizableFactory implements Comparator<Class<?>>
{
	final private TreeSet<Class<?>>			all_types		= new TreeSet<Class<?>>(this);
	final private Map<Integer, Class<?>>	map_id_type		= new HashMap<Integer, Class<?>>();
	final private Map<Class<?>, Integer>	map_type_id		= new HashMap<Class<?>, Integer>();
	
	public ExternalizableFactory() {}	
	
	@Override
	public int compare(Class<?> o1, Class<?> o2) {
		return o1.getCanonicalName().compareTo(o2.getCanonicalName());
	}
	
	public int getType(MessageHeader message) {
		return map_type_id.get(message.getClass());
	}
	
	public MessageHeader createMessage(int type) throws InstantiationException, IllegalAccessException   {
		Class<?> ext_type = map_id_type.get(type);
		return (MessageHeader)ext_type.newInstance();
	}
	
	public Map<Integer, Class<?>> getRegistTypes() {
		return new TreeMap<Integer, Class<?>>(map_id_type);
	}
	
	/**
	 * 注册所有的类后，记得调用此句
	 */
	protected Map<Integer, Class<?>> syncAll() {
		synchronized (all_types) {
			int index = 1;
			map_id_type.clear();
			map_type_id.clear();
			for (Class<?> cls : all_types) {
				map_id_type.put(index, cls);
				map_type_id.put(cls, index);
				index ++;
			}
		}
		return getRegistTypes();
	}
	
//	/**
//	 * 将标注{@link ExternalizableMessageType}的类注册到系统
//	 * @param clazz
//	 */
//	public void registClass(Class<?> clazz) throws Exception {
//		clazz.asSubclass(ExternalizableMessage.class);
//		ExternalizableMessageType ext_type = clazz.getAnnotation(ExternalizableMessageType.class);
//		if (ext_type == null) {
//			throw new NotImplementedException("not Annotation ExternalizableMessageType : " + clazz);
//		} else {
//			Class<?> sc = type_map.get(ext_type.value());
//			if (sc == null) {
//				type_map.put(ext_type.value(), clazz);
//			} else {
//				throw new IllegalAnnotationException("duplicate Annotation ExternalizableMessageType : " +
//						"\"" + clazz + "\"(" + ext_type.value() + ") src=\""+sc.getName()+"\"");
//			}
//		}
//	}
	
	
	/**
	 * 如果该类是ExternalizableMessage，则将标注{@link ExternalizableMessageType}的类注册到系统，否则查找在此类中定义的类种类。
	 * @param cls
	 */
	public void registClasses(Class<?> cls) {
		synchronized (all_types) {
			if (ExternalizableMessage.class.isAssignableFrom(cls)) {
				all_types.add(cls);
			}
			for (Class<?> sub : cls.getClasses()) {
				registClasses(sub);
			}
		}
//		try {
//			cls.asSubclass(ExternalizableMessage.class);
//			ExternalizableMessageType ext_type = cls.getAnnotation(ExternalizableMessageType.class);
//			if (ext_type != null) {
//				registClass(cls);
//			}
//		} catch (Exception err) {}
//		for (Class<?> sub : cls.getClasses()) {
//			registClasses(sub);
//		}
	}
}

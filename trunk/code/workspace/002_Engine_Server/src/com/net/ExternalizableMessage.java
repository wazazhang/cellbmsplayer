package com.net;

import com.net.anno.ExternalizableMessageType;


/**
 * <b>标记纯手动序列化/反序列化的对象</b><br>
 * 实现类也必须标注签名{@link ExternalizableMessageType}<br>
 * 实现类也必须拥有无参数公共构造函数<br>
 */
public interface ExternalizableMessage 
{
	public void readExternal(NetDataInput in) throws Exception ;
	
	public void writeExternal(NetDataOutput out) throws Exception ;
}

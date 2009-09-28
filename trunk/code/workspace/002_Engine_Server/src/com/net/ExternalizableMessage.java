package com.net;


/**
 * 标记纯手动序列化/反序列化的对象
 */
public interface ExternalizableMessage 
{
	public void readExternal(NetDataInput in) throws Exception ;
	
	public void writeExternal(NetDataOutput out) throws Exception ;
}

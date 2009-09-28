package com.net;

/**
 * 类型和 integer 的映射关系，用于 TransmissionType = TRANSMISSION_TYPE_EXTERNALIZABLE 类型的消息。
 */
public interface ExternalizableFactory 
{
	int getType(MessageHeader message) ;
	
	MessageHeader getMessage(int type);
}

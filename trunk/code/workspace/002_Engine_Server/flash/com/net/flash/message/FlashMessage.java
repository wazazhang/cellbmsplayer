package com.net.flash.message;

import java.io.IOException;

import sun.security.jca.GetInstance;

import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;

public class FlashMessage extends MessageHeader implements ExternalizableMessage
{
	public FlashMessage() {}
	
	@Override
	public void readExternal(NetDataInput in) throws IOException {
		FlashMessageFactory.getInstance();
	}
	@Override
	public void writeExternal(NetDataOutput out) throws IOException {
		FlashMessageFactory.getInstance();
	}
	
}

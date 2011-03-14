package com.net.flash.message;

import java.io.IOException;

import com.net.ExternalizableFactory;
import com.net.NetDataInput;
import com.net.NetDataOutput;

public class FlashMessageFactory extends ExternalizableFactory
{
	static FlashMessageFactory getInstance() {
		return instance;
	}
	private static FlashMessageFactory instance;
	
	public FlashMessageFactory(Class<?>... classes) {
		super(classes);
		this.instance = this;
	}

	public void readExternal(FlashMessage msg, NetDataInput in) throws IOException {
		
	}

	public void writeExternal(FlashMessage msg, NetDataOutput out) throws IOException {

	}
	
	
}

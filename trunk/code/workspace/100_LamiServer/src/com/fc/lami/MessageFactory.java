package com.fc.lami;

import com.fc.lami.model.Desk;
import com.net.flash.message.FlashMessageFactory;

public class MessageFactory extends FlashMessageFactory
{
	public MessageFactory() {
		super(new MessageCodecJava(),
				Messages.class, 
				Desk.class);
	}
}

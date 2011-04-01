package com.fc.lami;

import com.net.flash.message.FlashMessageFactory;

public class MessageFactory extends FlashMessageFactory
{
	public MessageFactory() {
		super(new MessageCodecJava(),
				Messages.class, 
				CardData.class, 
				Desktop.class);
	}
}

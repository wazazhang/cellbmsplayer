package com.slg.sanguosha;

import com.net.flash.message.FlashMessageFactory;
import com.slg.entity.Currency;
import com.slg.entity.GuageNumber;
import com.slg.entity.Hero;
import com.slg.entity.Player;
import com.slg.entity.Village;

public class MessageFactory extends FlashMessageFactory
{
	public MessageFactory() {
		super(new MessageCodecJava(),
				GuageNumber.class,
				Currency.class,
				Player.class,
				Messages.class
//				Village.class
//				Hero.class
			);
	}
}

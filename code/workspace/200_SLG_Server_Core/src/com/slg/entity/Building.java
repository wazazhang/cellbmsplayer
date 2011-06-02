package com.slg.entity;

import com.net.flash.message.FlashMessage;

/**
 * 建筑
 * @author yagami0079
 *
 */
public class Building extends FlashMessage{
	public int level;
	
	public int type;
	public Position pos = new Position();
}

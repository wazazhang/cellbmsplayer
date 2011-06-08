package com.slg.entity;

import java.util.ArrayList;

import com.net.flash.message.FlashMessage;

/**
 * 玩家数据结构
 * @author yagami0079
 *
 */
public class Player extends FlashMessage{
	/**
	 * 玩家ID
	 */
	public int player_id;
	/**
	 * 玩家名字
	 */
	public String name;
	/**
	 * 官职
	 */
	public int office;
	/**
	 * 经验
	 */
	public GuageNumber exp;
	/**
	 * 等级
	 */
	public int level;
	
	/**
	 * 行动值
	 */
	public GuageNumber ap;
	/**
	 * 货币
	 */
	public Currency currency;
	
	/**
	 * 拥有的村寨列表
	 */
	public int[] village_list;
	/**
	 * 当前所在村寨ID
	 */
	public int cur_village_id;
	
	/** 
	 * 拥有的武将列表
	 */
	public int[] hero_list;
	
	public Player(){
		
	}
	
	public Player(String name){
		this.name = name;
		this.exp = new GuageNumber(0, 100);
		this.level = 0;
		this.ap = new GuageNumber(0, 100, 100);
		currency = new Currency();
	}
}

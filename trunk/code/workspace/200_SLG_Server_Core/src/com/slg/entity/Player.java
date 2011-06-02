package com.slg.entity;

/**
 * 玩家数据结构
 * @author yagami0079
 *
 */
public class Player {
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
	public Currency currency = new Currency();
	
}

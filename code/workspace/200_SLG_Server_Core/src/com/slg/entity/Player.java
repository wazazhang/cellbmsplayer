package com.slg.entity;

/**
 * 玩家数据结构
 * @author yagami0079
 *
 */
public class Player {
	/**
	 * 经验
	 */
	public int exp;
	/**
	 * 等级
	 */
	public int level;
	
	/**
	 * 行动值
	 */
	public int ap;
	
	/**
	 * 货币
	 */
	public Currency currency = new Currency();
	
}

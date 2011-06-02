package com.slg.entity;

import java.util.HashMap;

import com.net.flash.message.FlashMessage;

/**
 * 武将
 * @author yagami0079
 *
 */
public class Hero extends FlashMessage{
	/**
	 * 武将ID
	 */
	public int id;
	/**
	 * 武将名字
	 */
	public String name;
	/**
	 * 经验
	 */
	public GuageNumber exp;
	/**
	 * 等级
	 */
	public int level;
	/**
	 * 生命值
	 */
	public GuageNumber hp;
	/**
	 * 攻击力
	 */
	public int attack;
	/**
	 * 内政力
	 */
	public int politics;
	/**
	 * 统帅力
	 */
	public int commander;
	/**
	 * 性别
	 */
	public int sex;
	/**
	 * 忠诚度
	 */
	public GuageNumber loyalty;
	/**
	 * 品阶
	 */
	public int quality;
	
	/**
	 * 最大技能数量
	 */
	public int max_skill_count;
	/**
	 * 技能列表
	 */
	public HashMap<Integer, Skill> skill_list;
	
	/**
	 * 最大道具数量
	 */
	public int max_item_count;
	/**
	 * 道具和装备列表
	 */
	public HashMap<Integer, Item> item_list;
}

package com.slg.entity;

import java.util.ArrayList;

import com.net.flash.message.FlashMessage;

/**
 * 玩家寨子
 * @author yagami0079
 *
 */
public class Village extends FlashMessage{
	/**
	 * 村寨ID
	 */
	public int id;
	/**
	 * 村寨名字
	 */
	public String name;
	/**
	 * 粮食
	 */
	public int food;
	/**
	 * 所属城市ID
	 */
	public int city_id;
	/**
	 * 建筑id列表
	 */
	public ArrayList<Integer> buildings;
	/**
	 * 武将id列表
	 */
	public ArrayList<Integer> heros;
	
	/**
	 * 兵器库
	 */
	public ArrayList<Arms> arms_list;
	/**
	 * 士兵
	 */
	public ArrayList<Soldiers> soldiers_list;
}

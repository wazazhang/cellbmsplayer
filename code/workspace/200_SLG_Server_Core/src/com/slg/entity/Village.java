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
	 * 所属玩家ID
	 */
	public int player_id;
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
	public Building[] buildings;
	/**
	 * 武将id列表
	 */
	public int[] heros;
	
	/**
	 * 兵器库
	 */
	public Arms[] arms_list;
	/**
	 * 士兵
	 */
	public Soldiers[] soldiers_list;
	
	public Village(){
		
	}
	
	public Village(Player player){
		name = player.name+"的村子";
		player_id = player.player_id;
	}
}

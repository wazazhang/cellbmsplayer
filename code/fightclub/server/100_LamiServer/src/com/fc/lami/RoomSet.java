package com.fc.lami;

public class RoomSet {
	public int id;
	public String name;
	public int desk_count;
	public int turn_time;
	public int operate_time;
	public int fastgame;
	public int startcard;
	public String default_desk_name;
	
	
	public String toString(){
		String s = ""+id+",房间名："+name+", 桌子数："+desk_count+", 回合时间:"+turn_time+",处理时间:"+operate_time+ ", 是否快速游戏"+fastgame+", 初始牌数:"+startcard;
		return s;
	}
}

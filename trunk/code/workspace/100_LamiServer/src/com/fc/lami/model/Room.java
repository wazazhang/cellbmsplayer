package com.fc.lami.model;

import java.util.HashMap;

import com.fc.lami.Messages.*;


public class Room implements Runnable{
	final static int desk_number = 50;
	
	public int room_No;
	
	public Desk desks[];
	
	public HashMap<Integer, Player> player_list;
	
	public Room(int room_No)
	{
		this.room_No = room_No;
		player_list = new HashMap<Integer, Player>();
		desks = new Desk[desk_number];
		for (int i = 0; i<desk_number; i++){
			desks[i] = new Desk(i);
		}
	}

	public void onPlayerEnter(Player player){
		player_list.put(player.player_id, player);
		player.cur_room = this;
		for (Player p : player_list.values()){
			p.session.send(new EnterRoomNotify(player.getPlayerData()));
		}
	}
	
	public void onPlayerLeave(int pid){
		Player player = player_list.remove(pid);
		if (player!=null){
			player.cur_room = null;
			for (Player p : player_list.values()){
				p.session.send(new ExitRoomNotify(player.getPlayerData()));
			}
		}
	}
	
	public RoomData getRoomData(){
		RoomData rd = new RoomData();
		rd.desks = new DeskData[desk_number];
		for (int i = 0; i<desk_number; i++){
			if (desks[i]!=null){
				rd.desks[i] = desks[i].getDeskData();
			}
		}
		int pn = player_list.size();
		rd.players = new PlayerData[pn];
		for (int i = 0; i<pn; i++){
			Player p = player_list.get(i);
			if (p!=null){
				rd.players[i] = p.getPlayerData();
			}
		}
		return rd;
	}
	
	@Override
	public void run() {
		for (int i = 0; i<desk_number; i++){
			if (desks[i].getPlayerNumber()>0){
				desks[i].logic();
			}
		}
		System.out.println("room logic");
	}
	
}

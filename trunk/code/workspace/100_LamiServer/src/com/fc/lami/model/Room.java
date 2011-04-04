package com.fc.lami.model;

import java.util.ArrayList;

import com.fc.lami.Messages.DeskData;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.RoomData;


public class Room implements Runnable{
	final static int desk_number = 50;
	
	public Desk desks[];
	
	public ArrayList<Player> player_list;
	
	public Room()
	{
		player_list = new ArrayList<Player>();
		desks = new Desk[desk_number];
		for (int i = 0; i<desk_number; i++){
			desks[i] = new Desk(i);
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

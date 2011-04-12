package com.fc.lami.model;

import java.util.HashMap;

import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.LamiConfig;
import com.fc.lami.Messages.*;
import com.net.flash.message.FlashMessage;


public class Room implements Runnable{
	
	public int room_No;
	ThreadPool thread_pool;
	int update_interval;
	
	int desk_number;
	public Desk desks[];
	
	public HashMap<Integer, Player> player_list;
	
	public Room(int room_No, ThreadPool tp, int interval)
	{
		this.room_No = room_No;
		this.thread_pool = tp;
		this.update_interval = interval;
		player_list = new HashMap<Integer, Player>();
		desk_number = LamiConfig.DESK_NUMBER;
		desks = new Desk[desk_number];
		for (int i = 0; i<desk_number; i++){
			desks[i] = new Desk(i, tp, interval);
		}
		this.thread_pool.scheduleAtFixedRate(this, update_interval, update_interval);
	}

	public boolean onPlayerEnter(Player player){
		
		if (player_list.size()>=LamiConfig.PLAYER_NUMBER_MAX){
			return false;
		}
		
		player_list.put(player.player_id, player);
		player.cur_room = this;
		
		for (Player p : player_list.values()){
			p.session.send(new EnterRoomNotify(player.getPlayerData()));
		}
		
		return true;
	}
	
	public void notifyAll(FlashMessage msg)
	{
		for (Player p : player_list.values()){
			p.session.send(msg);
		}
	}
	
	
	public void onPlayerLeave(int pid){
		Player player = player_list.remove(pid);
		if (player!=null){
			if (player.cur_desk !=null){
				player.cur_desk.leavePlayer(player);
			}
			player.cur_room = null;
			for (Player p : player_list.values()){
				ExitRoomNotify ern = new ExitRoomNotify(player.getPlayerData());
				if(player.cur_room!=null)
					ern.curRoom = player.cur_room.getRoomData();
				if(player.cur_desk!=null)
					ern.curDesk = player.cur_desk.getDeskData();
				p.session.send(ern);
			}
		}
	}
	
	public RoomData getRoomData(){
		RoomData rd = new RoomData();
		rd.room_id = this.room_No;
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
	
	/** room线程主要监视各个桌子是否有游戏开始 */
	@Override
	public void run() {
		for (int i = 0; i<desk_number; i++){
			if (desks[i].getPlayerNumber()>0){
				desks[i].logic();
			}
		}
	}
	
}

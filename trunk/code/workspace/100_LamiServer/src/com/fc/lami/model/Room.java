package com.fc.lami.model;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.LamiConfig;
import com.fc.lami.Messages.*;
import com.net.flash.message.FlashMessage;


public class Room implements Runnable{
	
	private int 		room_id;
	
	private ThreadPool 	thread_pool;
	
	private int			update_interval;
	
	private Desk 		desks[];
	
	private ConcurrentHashMap<Integer, Player> player_list;
	
	public Room(int room_id, ThreadPool tp, int interval)
	{
		this.room_id = room_id;
		this.thread_pool = tp;
		this.update_interval = interval;
		this.player_list = new ConcurrentHashMap<Integer, Player>();
	
		this.desks = new Desk[LamiConfig.DESK_NUMBER];
		for (int i = 0; i<desks.length; i++){
			desks[i] = new Desk(i, tp, interval);
		}
		
		this.thread_pool.scheduleAtFixedRate(this, update_interval, update_interval);
	}

	public int getRoomID() {
		return room_id;
	} 
	
	public Desk[] getDesks() {
		return desks;
	}
	
	public Desk getDesk(int desk_i) {
		return desks[desk_i];
	}
	
	
	public boolean onPlayerEnter(Player player)
	{
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
				ExitRoomNotify ern = new ExitRoomNotify(player.player_id);
				p.session.send(ern);
			}
		}
	}
	
	public RoomData getRoomData()
	{
		RoomData rd = new RoomData();
		rd.room_id = this.room_id;
		rd.desks = new DeskData[desks.length];
		for (int i = 0; i<desks.length; i++){
			if (desks[i]!=null){
				rd.desks[i] = desks[i].getDeskData();
			}
		}
		int pn = player_list.size();
		rd.players = new PlayerData[pn];
		int i = 0;
		for (Player p : player_list.values()) {
			rd.players[i] = p.getPlayerData();
			i++;
		}
		return rd;
	}
	
	/** room线程主要监视各个桌子是否有游戏开始 */
	@Override
	public void run() {
		for (int i = 0; i < desks.length; i++) {
			if (desks[i].getPlayerNumber() > 0) {
				desks[i].logic();
			}
		}
	}
	
}

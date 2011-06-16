package com.fc.lami.model;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.LamiConfig;
import com.fc.lami.Messages.DeskData;
import com.fc.lami.Messages.EnterRoomNotify;
import com.fc.lami.Messages.ExitRoomNotify;
import com.fc.lami.Messages.FreshRoomNotify;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.RoomData;
import com.fc.lami.Messages.RoomSnapShot;
import com.fc.lami.LamiServerListener;
import com.net.MessageHeader;
import com.net.flash.message.FlashMessage;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;


public class Room implements ChannelListener
{
	static private Logger		log = LoggerFactory.getLogger(Room.class);
	
	final private int 			room_id;

	final private Channel		channel;
	
	final private ThreadPool 	thread_pool;
	
	final private Desk 			desks[];
	
	final private ConcurrentHashMap<Integer, Player> player_list;
	
	final private Hall		hall;
	
	
	public Room(LamiServerListener server, int room_id, ThreadPool tp, int interval)
	{
		this.room_id 		= room_id;
		this.channel 		= server.createChannel(this);
		this.thread_pool 	= tp;
		this.player_list 	= new ConcurrentHashMap<Integer, Player>();
		this.desks 			= new Desk[LamiConfig.DESK_NUMBER];
		
		for (int i = 0; i<desks.length; i++){
			desks[i] = new Desk(server, i, this, tp, interval);
		}
		
//		this.thread_pool.scheduleAtFixedRate(this, interval, interval);
		
		this.hall = server.getHall();
	}
	
	public int getRoomID() {
		return room_id;
	} 
	
	public Desk[] getDesks() {
		return desks;
	}
	
	public Desk getDesk(int desk_i) {
		if (desk_i >= 0 && desk_i < desks.length) {
			return desks[desk_i];
		}
		return null;
	}
	
	/** 得到空闲的桌子 */
	public Desk getIdleDesk(){
		for (Desk d:desks){
			if (d.getPlayerNumber()>0 && d.getPlayerNumber()<4 && d.getGame() == null){// 找一个有人且游戏没开始的桌子
				return d;
			}
		}
		
		for (Desk d:desks){
			if (d.getPlayerNumber()<4 && d.getGame() == null){//找一个没有开始游戏的桌子
				return d;
			}
		}
		
		return null; //没找到合适桌子
	}
	
	public PlayerData getPlayer(int pid){
		return player_list.get(pid).getPlayerData();
	}
	
	@Override
	public void receivedMessage(Channel channel, ClientSession sender, MessageHeader message) {
	}
	@Override
	public void sessionJoined(Channel channel, ClientSession session) {
	}
	@Override
	public void sessionLeaved(Channel channel, ClientSession session) {
	}
	
	public Channel getChannel(){
		return channel;
	}
	
	public boolean onPlayerEnter(Player player)
	{
		synchronized (player_list) {
			if (player_list.size()>=LamiConfig.PLAYER_NUMBER_MAX){
				return false;
			}
			if (player_list.contains(player.player_id)) {
				return false;
			}
			player_list.put(player.player_id, player);
			player.cur_room = this;
		}			
		channel.join(player.session);
		hall.broadcast(new FreshRoomNotify(getRoomSnapShot()));
		broadcast(new EnterRoomNotify(player.getPlayerData()));
		log.info("player [" + player + "] enter room [" + getRoomID() + "]");
		return true;
	}
	
	
	public void onPlayerLeave(int pid){
		Player player = player_list.remove(pid);
		if (player != null) {
			if (player.cur_desk != null) {
				player.cur_desk.leaveDesk(player);
			}
			player.cur_room = null;
			broadcast(new ExitRoomNotify(player.player_id));
			hall.broadcast(new FreshRoomNotify(getRoomSnapShot()));
			channel.leave(player.session);
//			for (Player p : player_list.values()){
//				ExitRoomNotify ern = new ExitRoomNotify(player.player_id);
//				p.session.send(ern);
//			}
			log.info("player [" + player + "] leave room [" + getRoomID() + "]");
		}
	}

	public void broadcast(FlashMessage msg)
	{
//		for (Player p : player_list.values()){
//			p.session.send(msg);
//		}
		channel.send(msg);
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
		synchronized (player_list) {
			int pn = player_list.size();
			rd.players = new PlayerData[pn];
			int i = 0;
			for (Player p : player_list.values()) {
				rd.players[i] = p.getPlayerData();
				i++;
			}
		}
		return rd;
	}
	
	public int getPlayerNumber(){
		return player_list.size();
	}
	
	public RoomSnapShot getRoomSnapShot(){
		RoomSnapShot rs = new RoomSnapShot();
		rs.room_id = this.room_id;
		rs.room_name = "房间 "+(this.room_id+1);
		rs.player_number = player_list.size();
		rs.player_number_max = LamiConfig.PLAYER_NUMBER_MAX;
		return rs;
	}
	
	/** room线程主要监视各个桌子是否有游戏开始 */
//	@Override
//	public void run() {
//		for (int i = 0; i < desks.length; i++) {
//			if (desks[i].getPlayerNumber() > 0||desks[i].getGame()!=null) {
//				desks[i].logic();
//			}
//		}
//	}
	
}

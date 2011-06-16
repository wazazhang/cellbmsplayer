package com.fc.lami.model;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Messages.DeskData;
import com.fc.lami.Messages.EnterDeskNotify;
import com.fc.lami.Messages.GameOverToRoomNotify;
import com.fc.lami.Messages.GameStartToRoomNotify;
import com.fc.lami.Messages.LeaveDeskNotify;
import com.fc.lami.Messages.ReadyNotify;
import com.fc.lami.LamiServerListener;
import com.net.MessageHeader;
import com.net.flash.message.FlashMessage;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;

/**
 * 桌子，桌子内的事件只在桌子范围内广播。没必要广播到ROOM。
 * 用channel限定广播范围。
 * @author yagami0079
 */
public class Desk implements ChannelListener
{
	static private Logger		log = LoggerFactory.getLogger(Desk.class);
	
	final public static int MAX_PLAYER_COUNT = 4;
	final public static int PLAYER_E = 3;
	final public static int PLAYER_W = 1;
	final public static int PLAYER_S = 2;
	final public static int PLAYER_N = 0;
	
	final public int 		desk_id;
	final private Channel	channel;

	final private Room 		room;
	
	/**桌子上所有人，玩家*/
	private HashMap<Integer, Player>
							desk_players = new HashMap<Integer, Player>(MAX_PLAYER_COUNT);
	
	/**所有人，包括玩家，围观者*/
	private ConcurrentHashMap<Integer, Player> 
							all_players = new ConcurrentHashMap<Integer, Player>();
	
	/** 游戏逻辑体 */
	public  Game 				game;
	private ThreadPool			thread_pool;
	private int 				update_interval;
	private ScheduledFuture<?>	future;
	private boolean				initing = false;
	
	public Desk(LamiServerListener server, int id, Room room, ThreadPool tp, int interval){
		this.room = room;
		this.desk_id = id;
		this.thread_pool = tp;
		this.update_interval = interval;
		this.channel = server.createChannel(this);
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

	/**
	 * 添加到围观者列表。
	 * @param player
	 * @return
	 */
	private boolean addPlayer(Player player) {
		synchronized (all_players) {
			if (all_players.contains(player.player_id)) {
				return false;
			}
			all_players.put(player.player_id, player);
		}		
		player.cur_desk = this;
		player.is_ready = false;
		player.card_list.clear();
		return true;
	}
	
	
	/**
	 * 如果此玩家正在游戏，不能退出。除非先离开桌子，调用leaveDesk。
	 * 如果是围观者，立即离开。
	 * @param pid
	 * @return
	 */
	private Player removePlayer(int pid) {	
		synchronized (all_players) {
			for (Player p : desk_players.values()) {
				if (p.player_id == pid) {
					return null;
				}
			}
			Player player = all_players.remove(pid);
			if (player != null) {
				player.cur_desk = null;
				player.is_ready = false;
//				player.card_list.clear();
			}
			return player;
		}
	}
	
	public boolean addVisitor(Player player) {
		log.info("visitor [" + player + "] join desk " + desk_id);
		if (addPlayer(player)) {
			onPlayerEnter(player);
			return true;
		}
		return false;
	}
	
	public Player removeVisitor(Player player) {	
		log.info("visitor [" + player + "] leave desk " + desk_id);
		player = removePlayer(player.player_id);
		if (player != null) {
			onPlayerLeave(player);
		}
		return player;
	}
	
	/**
	 * 加入桌子失败后，将在围观列表里。
	 * @param player
	 * @param seat
	 * @return
	 */
	public boolean joinDesk(Player player, int seat) {
		log.info("player [" + player + "] enter desk [" + desk_id + "] with [" + seat + "]");
		synchronized (all_players) {
			if (addPlayer(player)) {
				if (seat != PLAYER_W &&
					seat != PLAYER_E && 
					seat != PLAYER_S &&
					seat != PLAYER_N) {
					return false;
				}
				if (desk_players.size() >= MAX_PLAYER_COUNT) {
					return false;
				}
				if (desk_players.containsKey(seat)) {
					return false;
				}
				desk_players.put(seat, player);
			}
		}
		onPlayerEnter(player);
		EnterDeskNotify ntf = new EnterDeskNotify(player.getPlayerData().player_id, desk_id, seat);
		room.broadcast(ntf);
		return true;
	}
	
	public boolean leaveDesk(Player player)
	{
		log.info("player [" + player + "] leave desk [" + desk_id + "]");
		Integer seat = null;
		synchronized (all_players) {
			seat = getSeat(player);
			if (seat != null) {
				desk_players.remove(seat);
				removePlayer(player.player_id);
			} else {
				return false;
			}
		}
		if (game != null) {
			game.onPlayerLeave(player);
		}
		onPlayerLeave(player);
		LeaveDeskNotify ntf = new LeaveDeskNotify(player.player_id, desk_id);
		room.broadcast(ntf);
		return true;
	}

	public Integer getSeat(Player player) {
		synchronized (all_players) {
			for (Integer s : desk_players.keySet()) {
				if (desk_players.get(s) == player) {
					return s;
				}
			}
		}
		return null;
	}
	
	public int getIdleSeat(){
		if (desk_players.get(PLAYER_N)== null){
			return PLAYER_N;
		}
		if (desk_players.get(PLAYER_W)== null){
			return PLAYER_W;
		}
		if (desk_players.get(PLAYER_S)== null){
			return PLAYER_S;
		}
		if (desk_players.get(PLAYER_E)== null){
			return PLAYER_E;
		}
		return -1;
	}
	

	private void onPlayerEnter(Player player){
		getChannel().join(player.session);
	}
	
	private void onPlayerLeave(Player player){
		getChannel().leave(player.session);
	}

	public int getPlayerNumber(){
		return desk_players.size();
	}
	
	public boolean isAllPlayerReady(){
		synchronized (desk_players) {
			int count = 0;
			for (Player player : desk_players.values()) {
				if (player.is_ready) {
					count ++;
				}
			}
			return count>1 && count == desk_players.size();
		}
//		if ((player_E==null || player_E.is_ready) &&
//			(player_W==null || player_W.is_ready) &&
//			(player_S==null || player_S.is_ready) &&
//			(player_N==null || player_N.is_ready)){
//			return true;
//		}
//		return false;
	}
	
	public Player[] getPlayerList(){
		synchronized (desk_players) {
			return desk_players.values().toArray(new Player[desk_players.size()]);
		}
	}
	
	public void onPlayerReady(Player p, boolean isReady){
		p.is_ready = isReady;
		channel.send(new ReadyNotify(p.player_id, isReady));
		if (p.is_ready){
			if (isAllPlayerReady()){
				game = new Game(this, thread_pool);
				log.info("desk [" + desk_id + "] game start");
				initing = false;
				room.broadcast(new GameStartToRoomNotify(desk_id));
			}
		}
	}

	public Game getGame() {
		return game;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public Logger getLogger(){
		return log;
	}
	
//	public void logic()
//	{
//		if (!initing)
//		{
//			if (game != null) {
//				if (game.isGameOver()){
//					future.cancel(false);
//					log.info("desk ]" + desk_id + "] game over");
//					game = null;
//					room.broadcast(new GameOverToRoomNotify(desk_id));
//				}
//			} else if (isAllPlayerReady()) {
//				initing = true;
//				game = new Game(this, thread_pool);
//				future = thread_pool.scheduleAtFixedRate(game, update_interval, update_interval);
//				log.info("desk [" + desk_id + "] game start");
//				initing = false;
//				room.broadcast(new GameStartToRoomNotify(desk_id));
//			}
//		}
//	}
	
	public DeskData getDeskData(){
		DeskData dd = new DeskData();
		dd.desk_id = this.desk_id;
		dd.desk_name = "桌子"+(this.desk_id+1);
		dd.player_number = getPlayerNumber();
		dd.is_started = (this.game != null);
		synchronized (desk_players) {
			Player player_E = desk_players.get(PLAYER_E);
			Player player_N = desk_players.get(PLAYER_N);
			Player player_S = desk_players.get(PLAYER_S);
			Player player_W = desk_players.get(PLAYER_W);
			dd.player_E_id = player_E != null ? player_E.player_id : -1;
			dd.player_N_id = player_N != null ? player_N.player_id : -1;
			dd.player_S_id = player_S != null ? player_S.player_id : -1;
			dd.player_W_id = player_W != null ? player_W.player_id : -1;
		}
		return dd;
	}
	
	
	
	
	//通知桌子的人
	public  void broadcast(FlashMessage msg)
	{
		channel.send(msg);
	}
	
	
}

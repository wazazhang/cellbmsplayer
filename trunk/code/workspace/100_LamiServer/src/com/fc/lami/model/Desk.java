package com.fc.lami.model;

import java.rmi.dgc.Lease;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.util.Pair;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Server;
import com.fc.lami.Messages.*;
import com.net.MessageHeader;
import com.net.flash.message.FlashMessage;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ChannelManager;
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
	final public static int PLAYER_E = 0;
	final public static int PLAYER_W = 1;
	final public static int PLAYER_S = 2;
	final public static int PLAYER_N = 3;
	
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
	private Game 				game;
	private ThreadPool			thread_pool;
	private int 				update_interval;
	private ScheduledFuture<?>	future;
	private boolean				initing = false;
	
	public Desk(Server server, int id, Room room, ThreadPool tp, int interval){
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
		channel.join(player.session);
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
				channel.leave(player.session);
			}
			return player;
		}
	}
	
	public boolean addVisitor(Player player) {
		log.info("visitor [" + player.getName() + "] join desk " + desk_id);
		if (addPlayer(player)) {
			return true;
		}
		return false;
	}
	
	public Player removeVisitor(Player player) {	
		log.info("visitor [" + player.getName() + "] leave desk " + desk_id);
		player = removePlayer(player.player_id);
		if (player != null) {
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
		log.info("player [" + player.getName() + "] enter desk [" + desk_id + "] with [" + seat + "]");
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
				player.cur_desk = this;
				player.is_ready = false;
			}
		}
		EnterDeskNotify ntf = new EnterDeskNotify(player.getPlayerData().player_id, desk_id, seat);
		broadcast(ntf);
		room.broadcast(ntf);
		return true;
	}
	
	public boolean leaveDesk(Player player)
	{
		log.info("player [" + player.getName() + "] leave desk [" + desk_id + "]");
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
		LeaveDeskNotify ntf = new LeaveDeskNotify(player.player_id, desk_id);
		broadcast(ntf);
		room.broadcast(ntf);
//		player.session.send(ntf);
		return true;
//		player.cur_room.notifyAll(ntf);
		
		//NotifyAll(ntf);
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
	
//	public Boolean setPlayerE(Player player){
//		if (joinDesk(player, )){
//			return false;
//		}
//		player_E = player;
//		onPlayerEnter(player);
//		return true;
//	}
//	
//	public Boolean setPlayerW(Player player){
//		if (player_W!=null){
//			return false;
//		}
//		player_W = player;
//		onPlayerEnter(player);
//		return true;
//	}
//	
//	public Boolean setPlayerS(Player player){
//		if (player_S!=null){
//			return false;
//		}
//		player_S = player;
//		onPlayerEnter(player);
//		return true;
//	}
//	
//	public Boolean setPlayerN(Player player){
//		if (player_N!=null){
//			return false;
//		}
//		player_N = player;
//		onPlayerEnter(player);
//		return true;
//	}
//	
//	private void onPlayerEnter(Player player){
//		if (player_E!=null){
//			player_E.session.send(new EnterDeskNotify(player.player_id,getDeskData(),3));
//		}
//		if (player_W!=null){
//			player_W.session.send(new EnterDeskNotify(player.player_id,getDeskData(),1));
//		}
//		if (player_S!=null){
//			player_S.session.send(new EnterDeskNotify(player.player_id,getDeskData(),2));
//		}
//		if (player_N!=null){
//			player_N.session.send(new EnterDeskNotify(player.player_id,getDeskData(),0));
//		}
//	}
//	
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
			return count > 1;
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
//		int pn = getPlayerNumber();
//		Player playerlist[] = new Player[pn];
//		int i = 0;
//		/** 顺手针排序 */
//		if (player_E!=null){
//			playerlist[i] = player_E;
//			i++;
//		}
//		if (player_S!=null){
//			playerlist[i] = player_S;
//			i++;
//		}
//		if (player_W!=null){
//			playerlist[i] = player_W;
//			i++;
//		}
//		if (player_N!=null){
//			playerlist[i] = player_N;
//			i++;
//		}
//		return playerlist;
	}
	
	public void onPlayerReady(Player p, boolean isReady){
//		if (player_E!=null){
//			player_E.session.send(new ReadyNotify(p.player_id, isReady));
//		}
//		if (player_W!=null){
//			player_W.session.send(new ReadyNotify(p.player_id, isReady));
//		}
//		if (player_S!=null){
//			player_S.session.send(new ReadyNotify(p.player_id, isReady));
//		}
//		if (player_N!=null){
//			player_N.session.send(new ReadyNotify(p.player_id, isReady));
//		}
		channel.send(new ReadyNotify(p.player_id, isReady));
	}

	public Game getGame() {
		return game;
	}
	
	public void logic()
	{
		if (!initing)
		{
			if (game != null) {
				if (game.isGameOver()){
					future.cancel(false);
					log.info("desk ]" + desk_id + "] game over");
					game = null;
				}
			} else if (isAllPlayerReady()) {
				initing = true;
				game = new Game(this);
				future = thread_pool.scheduleAtFixedRate(game, update_interval, update_interval);
				log.info("desk [" + desk_id + "] game start");
				initing = false;
			}
		}
	}
	
	public DeskData getDeskData(){
		DeskData dd = new DeskData();
		dd.desk_id = this.desk_id;
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
//		if (player_E!=null){
//			player_E.session.send(msg);
//		}
//		if (player_W!=null){
//			player_W.session.send(msg);
//		}
//		if (player_S!=null){
//			player_S.session.send(msg);
//		}
//		if (player_N!=null){
//			player_N.session.send(msg);
//		}
		// TODO 此处要添加发送给体旁观者的信息
	}
	
//	//通知本桌子的出牌区的变化
//	public void NotifyMatrixChange(MainMatrixChangeRequest res)
//	{
//		MainMatrixChangeNotify ntf = new MainMatrixChangeNotify(res.cards);
//		NotifyAll(ntf);
//	}
	
}

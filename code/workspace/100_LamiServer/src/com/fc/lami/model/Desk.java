package com.fc.lami.model;

import java.util.concurrent.ScheduledFuture;

import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Messages.*;
import com.net.flash.message.FlashMessage;

/**
 * 桌子
 * @author yagami0079
 *
 */
public class Desk
{
	public int desk_id;
	
	public Player player_E;
	public Player player_W;
	public Player player_S;
	public Player player_N;
	
	//TODO 这里还要有围观者列表 
	
	/** 游戏逻辑体 */
	public Game game;
	ThreadPool thread_pool;
	int update_interval;
	
	public Desk(int id, ThreadPool tp, int interval){
		this.desk_id = id;
		this.thread_pool = tp;
		this.update_interval = interval;
	}
	
	public Boolean setPlayerE(Player player){
		if (player_E!=null){
			return false;
		}
		player_E = player;
		return true;
	}
	
	public Boolean setPlayerW(Player player){
		if (player_W!=null){
			return false;
		}
		player_W = player;
		return true;
	}
	
	public Boolean setPlayerS(Player player){
		if (player_S!=null){
			return false;
		}
		player_S = player;
		return true;
	}
	
	public Boolean setPlayerN(Player player){
		if (player_N!=null){
			return false;
		}
		player_N = player;
		return true;
	}
	
	public int getPlayerNumber(){
		int n = 0;
		if (player_E!=null){
			n ++;
		}
		if (player_W!=null){
			n++;
		}
		if (player_S!=null){
			n++;
		}
		if (player_N!=null){
			n++;
		}
		return n;
	}
	
	public boolean isAllPlayerReady(){
		if (getPlayerNumber()<=1){
			return false;
		}
		if ((player_E==null || player_E.is_ready) &&
			(player_W==null || player_W.is_ready) &&
			(player_S==null || player_S.is_ready) &&
			(player_N==null || player_N.is_ready)){
			return true;
		}
		return false;
	}
	
	public Player[] getPlayerList(){
		int pn = getPlayerNumber();
		Player playerlist[] = new Player[pn];
		int i = 0;
		/** 顺手针排序 */
		if (player_E!=null){
			playerlist[i] = player_E;
			i++;
		}
		if (player_S!=null){
			playerlist[i] = player_S;
			i++;
		}
		if (player_W!=null){
			playerlist[i] = player_W;
			i++;
		}
		if (player_N!=null){
			playerlist[i] = player_N;
			i++;
		}
		return playerlist;
	}
	public void onPlayerReady(Player p){
		if (player_E!=null){
			player_E.session.send(new ReadyNotify(p.player_id));
		}
		if (player_W!=null){
			player_W.session.send(new ReadyNotify(p.player_id));
		}
		if (player_S!=null){
			player_S.session.send(new ReadyNotify(p.player_id));
		}
		if (player_N!=null){
			player_N.session.send(new ReadyNotify(p.player_id));
		}
	}
	
	ScheduledFuture<?> future;
	
	public void logic(){
		if (game!=null){
			if (game.isGameOver()){
				future.cancel(false);
				System.out.println("desk "+desk_id +" game over");
			}
		}else if (isAllPlayerReady()){
			game = new Game(this);
			future = thread_pool.scheduleAtFixedRate(game, update_interval, update_interval);
			System.out.println("desk "+desk_id +" game start");
		}
	}
	public DeskData getDeskData(){
		DeskData dd = new DeskData();
		dd.desk_id = this.desk_id;
		dd.is_started = (this.game != null);
		dd.player_E_id = this.player_E.player_id;
		dd.player_N_id = this.player_N.player_id;
		dd.player_S_id = this.player_S.player_id;
		dd.player_W_id = this.player_W.player_id;
		
		return dd;
	}
}

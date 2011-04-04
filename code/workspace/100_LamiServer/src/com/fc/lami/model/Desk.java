package com.fc.lami.model;

import com.fc.lami.Messages.DeskData;
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
	
	public Desk(int id){
		this.desk_id = id;
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
	
	public void logic(){
		if (game!=null){
			game.logic();
		}else if (isAllPlayerReady()){
			game = new Game(this);
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

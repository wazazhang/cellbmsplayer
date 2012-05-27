package com.fc.castle.service;

import java.util.ArrayList;
import java.util.Map;

import com.cell.CUtil;
import com.fc.castle.data.Account;
import com.fc.castle.data.Mail;
import com.fc.castle.data.PlayerData;
import com.fc.castle.data.PlayerFriend;
import com.fc.castle.data.PlayerMailData;
import com.fc.castle.data.PlayerQuestData;
import com.fc.castle.data.SoldierData;
import com.fc.castle.data.SoldierDatas;
import com.fc.castle.gs.config.GameConfig;

public abstract class PersistanceManager 
{
	private static PersistanceManager instance;

	public static PersistanceManager getInstance() {
		return instance;
	}
	
	public PersistanceManager() {
		instance = this;
	}

//	------------------------------------------------------------------------------------
	


//	------------------------------------------------------------------------------------
	
	abstract public Account 			getAccount(String id) ;
	abstract public Account				registAccount(Account a);
	abstract public boolean 			saveAccount(Account a);
	
	
	/**
	 * 查询所有字段
	 * @param player_id
	 * @return
	 */
	abstract public PlayerData 			getPlayer(int player_id);
	/**
	 * 查询指定字段
	 * @param player_id
	 * @param fieldName
	 * @return
	 */
	abstract public PlayerData 			getPlayerFields(int player_id, String ... fieldName);
	/**
	 * 查询排除某些字段
	 * @param player_id
	 * @param fieldName
	 * @return
	 */
	abstract public PlayerData 			getPlayerExcludeFields(int player_id, String ... fieldName);
	
	abstract public PlayerData 			registPlayer(Account account, PlayerData p);
	abstract public PlayerData			findPlayer(String name);
	abstract public PlayerData 			findPlayerFields(String name, String ... fieldName) ;
	abstract public boolean 			savePlayer(PlayerData p);	
	abstract public boolean 			savePlayerFields(PlayerData p, String ... fieldsName);


	abstract public PlayerQuestData 	getPlayerQuest(int player_id);
	abstract public boolean				savePlayerQuest(PlayerQuestData data);

	abstract public PlayerMailData	 	getPlayerMail(int player_id);
	abstract public boolean				savePlayerMail(PlayerMailData data);
	
	abstract public Mail				postMail(Mail mail);
	
	abstract public Mail				getMail(int mailID);
	
	
//	------------------------------------------------------------------------------------
	
	abstract public ArrayList<PlayerData> getPlayers(Account account);

	abstract public Map<Integer, PlayerFriend> getLastLoginPlayers();
	
	abstract public void pushLoginPlayer(PlayerData pd);
}

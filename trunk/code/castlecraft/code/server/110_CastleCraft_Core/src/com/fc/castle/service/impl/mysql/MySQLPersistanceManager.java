package com.fc.castle.service.impl.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLTableManager;
import com.cell.sql.SQLDriverManager;
import com.cell.sql.pool.SQLPool;
import com.fc.castle.data.Account;
import com.fc.castle.data.Mail;
import com.fc.castle.data.MailSnap;
import com.fc.castle.data.PlayerData;
import com.fc.castle.data.PlayerFriend;
import com.fc.castle.data.PlayerMailData;
import com.fc.castle.data.PlayerQuestData;
import com.fc.castle.service.PersistanceManager;

public class MySQLPersistanceManager extends PersistanceManager
{
	final static protected Logger log = LoggerFactory.getLogger("mysql");
	
	final private SQLPool pool;
	
	final private MySQLTable<String, Account>			taccounts;
	final private MySQLTable<Integer, PlayerData>		tplayers;
	final private MySQLTable<Integer, PlayerQuestData>	tplayerquest;
	final private MySQLTable<Integer, PlayerMailData>	tplayermails;
	
	final private MySQLTable<Integer, PlayerFriend>		tlastlogin;
	
	final private MySQLTable<Integer, Mail>				tmails;
	
	final private int 									max_login_player = 10;
	final private LinkedHashMap<Integer, PlayerFriend>	last_login_players = new LinkedHashMap<Integer, PlayerFriend>();

	public MySQLPersistanceManager(String dirver, String dburl, String user, String pswd, int poolmin, int poolmax) throws Exception
	{
		this.pool = new SQLPool(
				dirver, 
				dburl,
				user, 
				pswd,
				"castle_db", 
				poolmin, 
				poolmax);
		
		Connection conn = pool.getConnection();

		if (conn == null) {
			log.error("can not get db connection !");
			throw new SQLException("can not get db connection !");
		}
		
		//读入表结构
		taccounts		= new MySQLTable<String, Account>(Account.class, "taccount");
		tplayers		= new MySQLTable<Integer, PlayerData>(PlayerData.class, "tplayer");
		tplayerquest	= new MySQLTable<Integer, PlayerQuestData>(PlayerQuestData.class, "tplayerquest");
		tplayermails	= new MySQLTable<Integer, PlayerMailData>(PlayerMailData.class, "tplayermails");
		tlastlogin		= new MySQLTable<Integer, PlayerFriend>(PlayerFriend.class, "tlastlogin");
		
		tmails			= new MySQLTable<Integer, Mail>(Mail.class, "tmails");
		
		try
		{
			MySQLTable<?, ?>[] tables = new MySQLTable[]{
					taccounts,
					tplayers,
					tplayerquest,
					tplayermails,
					tlastlogin,
					tmails,
			};
			
			// 验证表结构
			boolean validate = true;
			for (SQLTableManager<?, ?> table : tables) {
				validate &= SQLDriverManager.getDriver().validateTable(conn, table, true, true, true);
			}
			
			if (validate) 
			{
				try {
					String db_struct = ""; 
					for (SQLTableManager<?, ?> table : tables) {
						db_struct += SQLDriverManager.getDriver().getCreateTableSQL(table, true, true) + "\n";
					}
					log.trace(db_struct);
//					CFile.writeText(new java.io.File("db_struct.sql"), db_struct, "UTF-8");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			} else {
				throw new SQLException("validate table error !");
			}
			
			Runtime.getRuntime().addShutdownHook(new ShutdownService());
			
		} catch (Exception e) {
			log.error("validate table error !");
			throw e;
		} finally {
			conn.close();
		}
		
	}

//	---------------------------------------------------------------------------------------------------------------
	
	Connection getConnection() {
		return pool.getConnection();
	}
	
//	---------------------------------------------------------------------------------------------------------------
	
	@Override
	public Account getAccount(String id)
	{
		return taccounts.read(this, id);
	}

	@Override
	public Account registAccount(Account acc) 
	{
		if (taccounts.insert(this, acc)) {		
			log.info("account regist : id=" + acc.id);
			return acc;
		}
		return null;
	}

	@Override
	public boolean saveAccount(Account acc) 
	{
		return taccounts.write(this, acc);
	}

	@Override
	public PlayerData getPlayer(int player_id) 
	{
		return tplayers.read(this, player_id);
	}
	
	@Override
	public PlayerData getPlayerFields(int player_id, String ... fieldName) 
	{
		if (fieldName.length == 0) {
			return null;
		}
		Connection conn = getConnection();
		try {
			String sql = "SELECT " + CUtil.arrayToString(fieldName, ",", "") + " FROM " + tplayers.table_name + " WHERE player_id='" + player_id + "';";
			ArrayList<PlayerData> result = tplayers.selectRows(conn, sql);
			if (!result.isEmpty()) {
				PlayerData pd = result.get(0);
				pd.player_id = player_id;
				return pd;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public PlayerData getPlayerExcludeFields(int player_id, String... fieldName)
	{
		ArrayList<String> list = new ArrayList<String>(tplayers.table_columns.length);
		
		
		
		return null;
	}
	
	@Override
	public PlayerData findPlayer(String name) 
	{
		return tplayers.find(this, "player_name", name);
	}

	@Override
	public PlayerData findPlayerFields(String name, String... fieldName) 
	{
		if (fieldName.length == 0) {
			return null;
		}
		Connection conn = getConnection();
		try {
			String sql = "SELECT " + CUtil.arrayToString(fieldName, ",", "") + " FROM " + tplayers.table_name + " WHERE player_name='" + name + "';";
			ArrayList<PlayerData> result = tplayers.selectRows(conn, sql);
			if (!result.isEmpty()) {
				PlayerData pd = result.get(0);
				pd.player_name = name;
				return pd;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
	@Override
	public ArrayList<PlayerData> getPlayers(Account account) 
	{
		if (account.players_id != null) {
			ArrayList<PlayerData> rlist = new ArrayList<PlayerData>(account.players_id.length);
			for (int pid : account.players_id) {
				PlayerData pd = getPlayer(pid);
				if (pd != null) {
					rlist.add(pd);
				}
			}
			return rlist;
		}
		return new ArrayList<PlayerData>(0);
	}
	
	@Override
	public PlayerData registPlayer(Account account, PlayerData player) 
	{
		if (tplayers.insert(this, player)) {
			PlayerData saved = findPlayerFields(player.player_name, "player_id");
			if (saved != null) {
				player.player_id = saved.player_id;
				account.players_id = CUtil.arrayAddElement(account.players_id, player.player_id);
				taccounts.write(this, account);
				log.info("player regist : id=" + player.player_id + ", name=" + player.player_name);
				tplayerquest.insert(this, new PlayerQuestData(player.player_id));
				tplayermails.insert(this, new PlayerMailData(player.player_id, player.player_name));
				return player;
			}
		}
		return null;
	}
	
	@Override
	public boolean savePlayer(PlayerData p)
	{
		return tplayers.write(this, p);
	}

	@Override
	public boolean savePlayerFields(PlayerData p, String ... fieldsName)
	{
		return tplayers.writeFields(this, p, fieldsName);
	}

//	--------------------------------------------------------------------------------------------------------
	

	@Override
	public PlayerQuestData getPlayerQuest(int player_id) 
	{
		return tplayerquest.read(this, player_id);
	}
	
	@Override
	public boolean savePlayerQuest(PlayerQuestData data)
	{
		return tplayerquest.write(this, data);
	}

//	--------------------------------------------------------------------------------------------------------

	@Override
	public PlayerMailData getPlayerMail(int player_id) {
		return tplayermails.read(this, player_id);
	}
	
	@Override
	public boolean savePlayerMail(PlayerMailData data) {
		return tplayermails.write(this, data);
	}

	public Mail postMail(Mail mail) 
	{
		PlayerMailData receiver = tplayermails.find(this, "player_name", mail.receiverName);
		if (receiver != null) 
		{
			mail.receiverPlayerID 	= receiver.player_id;
			mail.sendTime 			= new Date(System.currentTimeMillis());
			mail.cyccode 			= CUtil.getRandom().nextInt();
			if (tmails.insert(this, mail))
			{
				String sql = "SELECT id FROM " + tmails.table_name + " WHERE " +
						"cyccode='" + mail.cyccode + "' AND " +
						"senderPlayerID='"+mail.senderPlayerID+"' AND " +
						"receiverPlayerID='"+mail.receiverPlayerID+"';";
				Connection conn = getConnection();
				try {
					ArrayList<Mail> result = tmails.selectRows(conn, sql);
					if (!result.isEmpty()) {
						mail.id = result.get(0).id;
						receiver.mails.put(mail.id, new MailSnap(mail));
						if (tplayermails.write(this, receiver)) {
							return mail;
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				} finally {
					try {
						conn.close();
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public Mail getMail(int mailID)
	{
		return tmails.read(this, mailID);
	}
	
	
	@Override
	public Map<Integer, PlayerFriend> getLastLoginPlayers() 
	{
		return last_login_players;
	}
	

//	---------------------------------------------------------------------------------------------------------------
//	saved
//	---------------------------------------------------------------------------------------------------------------
	public void pushLoginPlayer(PlayerData pd)
	{
		if (!last_login_players.containsKey(pd.player_id)) {
			try {
				if (last_login_players.size() >= max_login_player) {
					while (last_login_players.size() >= max_login_player) {
						Integer pid = last_login_players.keySet().iterator().next();
						last_login_players.remove(pid);
					}
				}
				last_login_players.put(pd.player_id, new PlayerFriend(pd));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	class ShutdownService extends Thread
	{
		public void run()
		{
			log.info("shutdown service running...");
			
			
		}
	}
	
	
}

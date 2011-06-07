package com.slg.sanguosha.login.xingcloud;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.service.user.UserProfile;

import com.cell.CIO;
import com.net.server.ClientSession;
import com.slg.entity.Player;
import com.slg.login.Login;
import com.slg.login.User;
import com.smartfoxserver.bitswarm.sessions.ISession;
import com.xingcloud.framework.orm.session.PersistenceSession;
import com.xingcloud.framework.orm.session.SessionFactory;
import com.xingcloud.service.user.UserFactory;

public class LoginXingCloud implements Login
{
	private static Logger log = LoggerFactory.getLogger(LoginXingCloud.class);
	
	private PersistenceSession persistenceSession;
	
	@Override
	public User login(ClientSession session, String uid, String validate) 
	{
		//init PersistenceSession
		synchronized (this) {
			try {
				if (persistenceSession == null) {
					persistenceSession = SessionFactory.openSession();
				}
				if (persistenceSession == null) {
					log.error("there is no persistenceSession!");
					return null;
				}
				//get UserProfile
				UserProfile userProfile = (UserProfile) UserFactory.getInstance().get(
						persistenceSession, uid);
				if (userProfile == null) {
					log.error("there is no userProfile with uid(" + uid + ")!");
					return null;
				}
				return new XingCloudUser(userProfile);
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	private class XingCloudUser implements User
	{
		final private String		uid;
		final private String		name;
		final private UserProfile	userProfile;
		
		private ArrayList<Player> player_list;
				
		public XingCloudUser(UserProfile userProfile) {
			this.userProfile = userProfile;
			this.uid = userProfile.getUid();
			this.name = userProfile.getUserNameFull();
		}
		
		@Override
		public String getUID() {
			return uid;
		}
		
//		@Override
//		public String getName() {
//			return name;
//		}

		private int getValueAsInt(String key) {
			try {
				Object value = userProfile.get(key);
				if (value instanceof Integer) {
					return (Integer) value;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		private int addValueAsInt(String key, int add) {
			try {
				Object value = userProfile.get(key);
				if (value instanceof Integer) {
					int v = (Integer)value;
					v += add;
					userProfile.set(key, new Integer(v));
					return v;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
		

		@Override
		synchronized public void save() {
			try {
				persistenceSession.put(userProfile);
				persistenceSession.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public Player getDefaultPlayer() {
			if (player_list.size()>0){
				return player_list.get(0);
			}else{
				return null;
			}
		}

//		@Override
//		public Player getPlayer(int index) {
//			if (player_list.size()>index){
//				return player_list.get(index);
//			}else{
//				return null;
//			}
//		}
		
	}
}

package com.fc.lami.login.xingcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.service.user.UserProfile;

import com.cell.CIO;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.login.Login;
import com.fc.lami.login.LoginInfo;
import com.fc.lami.login.User;
import com.net.server.ClientSession;
import com.smartfoxserver.bitswarm.sessions.ISession;
import com.xingcloud.framework.orm.session.PersistenceSession;
import com.xingcloud.framework.orm.session.SessionFactory;
import com.xingcloud.service.user.UserFactory;

public class LoginXingCloud implements Login
{
	private static Logger log = LoggerFactory.getLogger("Extensions");
	
	private PersistenceSession persistenceSession;
	
	@Override
	public LoginInfo login(ClientSession session, LoginRequest login) 
	{
		String platformAddress = login.platform_user_uid + "_" + login.platform_uid;
		//init PersistenceSession
		synchronized (this) {
			try {
				if (persistenceSession == null) {
					persistenceSession = SessionFactory.openSession();
				}
				if (persistenceSession == null) {
					String reason = "there is no persistenceSession!";
					log.error(reason);
					return new LoginInfo(null, reason);	
				}
				//get UserProfile
				UserProfile userProfile = (UserProfile) 
				UserFactory.getInstance().register(
						persistenceSession, 
						platformAddress);
//				UserFactory.getInstance().get(persistenceSession, uid);
				if (userProfile == null) {
					String reason = "there is no userProfile with (" + platformAddress + ")!";
					log.error(reason);
					return new LoginInfo(null, reason);	
				} else {
					return new LoginInfo(new XingCloudUser(userProfile), "");
				}
			}
			catch (Throwable e) 
			{
				log.error(e.getMessage(), e);
				String reason = e.getClass() + " : " + e.getMessage();
				if (login.platform_uid == null || login.platform_uid.isEmpty()) {
					return new LoginInfo(new DefaultUser(platformAddress), reason);
				} else {
					return new LoginInfo(null, reason);
				}
			}
		}
	}
	
	private class XingCloudUser implements User
	{
		final private String		uid;
		final private String		name;
		final private UserProfile	userProfile;
				
		public XingCloudUser(UserProfile userProfile) {
			this.userProfile = userProfile;
			this.uid = userProfile.getUid();
			this.name = userProfile.getUserNameFull();
		}
		
		@Override
		public String getUID() {
			return uid;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getSex() {
			return 0;
		}

		@Override
		public String getHeadURL() {
			return userProfile.getImageUrl();
		}
		
		@Override
		public int getLevel() {
			return userProfile.getLevel();
		}
		
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
		synchronized public int getLose() {
			return getValueAsInt("lose");
		}

		@Override
		synchronized public int getPoint() {
			return getValueAsInt("point");
		}

		@Override
		synchronized public int getScore() {
			return getValueAsInt("score");
		}

		@Override
		synchronized public int getWin() {
			return getValueAsInt("win");
		}
		
		@Override
		synchronized public int addLose(int value) {
			return addValueAsInt("lose", value);
		}

		@Override
		synchronized public int addPoint(int value) {
			return addValueAsInt("point", value);
		}

		@Override
		synchronized public int addScore(int value) {
			return addValueAsInt("score", value);
		}

		@Override
		synchronized public int addWin(int value) {
			return addValueAsInt("win", value);
		}

		@Override
		synchronized public void save() {
			try {
				persistenceSession.put(userProfile);
				persistenceSession.flush();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
	}

	private class DefaultUser implements User
	{
		private String name;
		private int win;
		private int lose;
		private int point;
		private int score;
		private int sex;
		
		public DefaultUser(String name) {
			this.name = name;
		}
		@Override
		public String getHeadURL() {
			return null;
		}
		@Override
		public int getLevel() {
			return 0;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getUID() {
			return name;
		}
		
		@Override
		public int getSex() {
			return sex;
		}

		@Override
		synchronized public int getLose() {
			return lose;
		}

		@Override
		synchronized public int getPoint() {
			return point;
		}

		@Override
		synchronized public int getScore() {
			return score;
		}

		@Override
		synchronized public int getWin() {
			return win;
		}
		
		@Override
		public void save() {}
		
		@Override
		synchronized public int addLose(int value) {
			return lose += value;
		}
		@Override
		synchronized public int addPoint(int value) {
			return point += value;
		}
		@Override
		synchronized public int addScore(int value) {
			return score += value;
		}
		@Override
		synchronized public int addWin(int value) {
			return win += value;
		}

		
	}
	public static void main(String args[])
	{
		PersistenceSession persistenceSession = null;
		//init PersistenceSession
			try {
				if (persistenceSession == null) {
					persistenceSession = SessionFactory.openSession();
				}
				if (persistenceSession == null) {
					log.error("there is no persistenceSession!");
				}
				//get UserProfile
				UserProfile userProfile = (UserProfile) UserFactory.getInstance().get(
						persistenceSession, "1234");
				if (userProfile == null) {
					log.error("there is no userProfile with uid(" + "1234" + ")!");
				}
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			}
	
	}
}

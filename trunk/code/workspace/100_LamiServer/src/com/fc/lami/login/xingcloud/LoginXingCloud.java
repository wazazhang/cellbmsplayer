package com.fc.lami.login.xingcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.service.user.UserProfile;

import com.cell.CIO;
import com.fc.lami.login.Login;
import com.fc.lami.login.User;
import com.net.server.ClientSession;
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
}

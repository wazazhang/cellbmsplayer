package com.fc.lami.login.xingcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.service.user.UserProfile;

import com.cell.CIO;
import com.cell.CUtil;
import com.fc.lami.Messages;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.Messages.PlatformUserData;
import com.fc.lami.login.Login;
import com.fc.lami.login.LoginInfo;
import com.fc.lami.login.User;
import com.net.server.ClientSession;
import com.smartfoxserver.bitswarm.sessions.ISession;
import com.xingcloud.framework.orm.session.PersistenceSession;
import com.xingcloud.framework.orm.session.SessionFactory;
import com.xingcloud.service.user.AbstractUserProfile;
import com.xingcloud.service.user.UserFactory;

public class LoginXingCloud implements Login
{
	private static Logger log = LoggerFactory.getLogger("Extensions");
	
	private PersistenceSession persistenceSession;
	
	@Override
	public LoginInfo login(ClientSession session, LoginRequest login) 
	{
		String platformAddress = login.platform_user_data.getPlatformAddress();
		synchronized (this) 
		{
			try {
				if (persistenceSession == null) {
					persistenceSession = SessionFactory.openSession();
				}
				if (persistenceSession == null) {
					String reason = "there is no persistenceSession!";
					log.error(reason);
					return new LoginInfo(new DefaultUser(login.platform_user_data), reason);	
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
					return new LoginInfo(new DefaultUser(login.platform_user_data), reason);	
				} else {
					log.info("--> get user profile : ");
					StringBuilder sb = new StringBuilder("\n");
					CUtil.toStatusLine("getClassName", 
							userProfile.getClassName(), sb);
					CUtil.toStatusLine("getUid", 
							userProfile.getUid(), sb);
					CUtil.toStatusLine("getPlatformAddress",  
							userProfile.getPlatformAddress(), sb);
					CUtil.toStatusLine("getLose",  
							userProfile.getLose(), sb);
					CUtil.toStatusLine("getPoint",  
							userProfile.getPoint(), sb);	
					CUtil.toStatusLine("getScore",  
							userProfile.getScore(), sb);
					CUtil.toStatusLine("getWin",  
							userProfile.getWin(), sb);
					log.info(sb.toString());
					XingCloudUser user = new XingCloudUser(userProfile, login.platform_user_data);
					user.addScore(1);
					user.save();
					return new LoginInfo(user, "");
				}
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
				String reason = e.getClass() + " : " + e.getMessage();
				return new LoginInfo(new DefaultUser(login.platform_user_data), reason);
			}
		}
	}
	
	private class XingCloudUser extends User
	{
		final private UserProfile userProfile;
		
		public XingCloudUser(
				UserProfile userProfile, 
				PlatformUserData login_data)
		{
			super(login_data);
			this.userProfile = userProfile;
		}
		
		@Override
		synchronized public User save() {
			try {
				persistenceSession.put(userProfile);
				persistenceSession.flush();
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			}
			return this;
		}

		@Override
		public int getLevel() {
			return 0;
		}
		
		synchronized public int getLose() {
			return userProfile.getLose();
		}
		synchronized public int getPoint() {
			return userProfile.getPoint();
		}
		synchronized public int getScore() {
			return userProfile.getScore();
		}
		synchronized public int getWin() {
			return userProfile.getWin();
		}
		synchronized public User addLose(int value) {
			userProfile.setLose(userProfile.getLose() + value);
			return this;
		}
		synchronized public User addPoint(int value) {
			userProfile.setPoint(userProfile.getPoint() + value);
			return this;
		}
		synchronized public User addScore(int value) {
			userProfile.setScore(userProfile.getScore() + value);
			return this;
		}
		synchronized public User addWin(int value) {
			userProfile.setWin(userProfile.getWin() + value);
			return this;
		}

		
	}

	private class DefaultUser extends User
	{
		private int win;
		private int lose;
		private int point;
		private int score;
		
		public DefaultUser(PlatformUserData data) {
			super(data);
		}
		
		@Override
		public int getLevel() {
			return 0;
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
		public User save() {
			return this;
		}

		@Override
		synchronized public User addLose(int value) {
			lose += value;
			return this;
		}
		@Override
		synchronized public User addPoint(int value) {
			point += value;
			return this;
		}
		@Override
		synchronized public User addScore(int value) {
			score += value;
			return this;
		}
		@Override
		synchronized public User addWin(int value) {
			win += value;
			return this;
		}
	}
}

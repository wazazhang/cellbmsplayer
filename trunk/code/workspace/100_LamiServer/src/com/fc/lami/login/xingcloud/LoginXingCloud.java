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
	
	private byte[] default_head = CIO.loadData("/com/fc/lami/login/test/default_head.jpg");
	
	private PersistenceSession persistenceSession;
	
	@Override
	public User login(ClientSession session, String uid, String validate) 
	{
		/*
		//init PersistenceSession
		PersistenceSession persistenceSession = (PersistenceSession) session.getAttribute("persistenceSession");
		if(persistenceSession == null){
			persistenceSession = SessionFactory.openSession();
			session.setAttribute("persistenceSession", persistenceSession);
		}
		//check uid
		if(!sfsObject.containsKey("uid")){
			//TODO throw exception or return error
		}
		//update UserProfile
		try {
			//get UserProfile
			UserProfile userProfile = (UserProfile) UserFactory.getInstance().get(
					persistenceSession, sfsObject.getUtfString("uid"));
			if(userProfile == null){
				//TODO throw exception or return error
			}
			userProfile.setLevel(userProfile.getLevel() + 1);
			persistenceSession.put(userProfile);
			persistenceSession.flush();
			session.setProperty("persistenceSession", persistenceSession);
		} catch (Exception e) {
			//TODO throw exception or return error
		}*/
		return null;
	}
//	
//	private class DefaultUser implements User
//	{
//		private UserProfile userProfile;
//				
//		public DefaultUser(UserProfile userProfile) {
//			this.userProfile = userProfile;
//		}
//		
//		@Override
//		public String getUID() {
//			return uid;
//		}
//		
//		@Override
//		public String getName() {
//			return name;
//		}
//
//		@Override
//		synchronized public byte[] getHeadImageData() {
//			return default_head;
//		}
//
//		@Override
//		synchronized public int getLose() {
//			return lose;
//		}
//
//		@Override
//		synchronized public int getPoint() {
//			return point;
//		}
//
//		@Override
//		synchronized public int getScore() {
//			return score;
//		}
//
//		@Override
//		synchronized public byte getSex() {
//			return 1;
//		}
//
//		@Override
//		synchronized public int getWin() {
//			return win;
//		}
//		
//		
//		
//		@Override
//		synchronized public int addLose(int value) {
//			return lose += value;
//		}
//		@Override
//		synchronized public int addPoint(int value) {
//			return point += value;
//		}
//		@Override
//		synchronized public int addScore(int value) {
//			return score += value;
//		}
//		@Override
//		synchronized public int addWin(int value) {
//			return win += value;
//		}
//
//		
//	}
}

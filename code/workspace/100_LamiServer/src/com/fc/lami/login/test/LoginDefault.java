package com.fc.lami.login.test;

import com.cell.CIO;
import com.fc.lami.login.Login;
import com.fc.lami.login.User;
import com.net.server.ClientSession;

public class LoginDefault implements Login
{
	private byte[] default_head = CIO.loadData("/com/fc/lami/login/test/default_head.jpg");
	
	@Override
	public User login(ClientSession session, String uid, String validate) {
		return new DefaultUser(uid);
	}
	
	private class DefaultUser implements User
	{
		private String name;
		private int win;
		private int lose;
		private int point;
		private int score;
		
		public DefaultUser(String name) {
			this.name = name;
		}
		
		@Override
		public String getUID() {
			return name;
		}
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		synchronized public byte[] getHeadImageData() {
			return default_head;
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
		synchronized public byte getSex() {
			return 1;
		}

		@Override
		synchronized public int getWin() {
			return win;
		}
		
		
		
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
}

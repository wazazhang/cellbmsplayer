package com.fc.lami.login.test;

import com.cell.CIO;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.Messages.PlatformUserData;
import com.fc.lami.login.Login;
import com.fc.lami.login.LoginInfo;
import com.fc.lami.login.User;
import com.net.server.ClientSession;

public class LoginDefault implements Login
{
	@Override
	public LoginInfo login(ClientSession session, LoginRequest login) {
		return new LoginInfo(new DefaultUser(login.platform_user_data), "");
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
}

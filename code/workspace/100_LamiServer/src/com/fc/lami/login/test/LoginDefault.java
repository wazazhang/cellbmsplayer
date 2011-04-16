package com.fc.lami.login.test;

import com.fc.lami.login.Login;
import com.fc.lami.login.User;

public class LoginDefault implements Login
{
	@Override
	public User login(String user, String validate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	static class DefaultUser implements User
	{

		@Override
		public int addLose(int value) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int addPoint(int value) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int addScore(int value) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int addWin(int value) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public byte[] getHeadImageData() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLose() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getPoint() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getScore() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public byte getSex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getWin() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}

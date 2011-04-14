package com.fc.lami.login.test;

import com.fc.lami.login.Login;
import com.fc.lami.login.User;

public class LoginDefault implements Login
{
	@Override
	public User login(String user, String pswdMd5, String validateKey) {
		// TODO Auto-generated method stub
		return null;
	}
	
	static class DefaultUser implements User
	{
		public DefaultUser() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public int addPoint(int value) throws Exception {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public byte[] getHeadImageData() {
			// TODO Auto-generated method stub
			return null;
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
		public byte getSex() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}

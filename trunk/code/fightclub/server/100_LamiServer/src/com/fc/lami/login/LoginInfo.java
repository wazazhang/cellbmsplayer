package com.fc.lami.login;

import com.fc.lami.Messages.LoginResponse;

public class LoginInfo 
{
	final private String reason;

	final private User user;
	
	public LoginInfo(User user, String reason)
	{
		this.user = user;
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public User getUser() {
		return user;
	}
	
	public boolean isSuccess() {
		return user != null;
	}
}

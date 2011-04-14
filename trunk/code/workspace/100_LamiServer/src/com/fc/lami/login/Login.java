package com.fc.lami.login;

public interface Login {

	public User login(String user, String pswd_md5, String validate_key);

}

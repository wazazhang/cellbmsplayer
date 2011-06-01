package com.fc.lami.login;

import com.net.server.ClientSession;

public interface Login {

	public User login(ClientSession session, String uid, String validate);

}

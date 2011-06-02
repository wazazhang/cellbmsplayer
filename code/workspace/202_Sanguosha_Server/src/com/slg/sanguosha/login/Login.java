package com.slg.sanguosha.login;

import com.net.server.ClientSession;

public interface Login {

	public User login(ClientSession session, String uid, String validate);

}

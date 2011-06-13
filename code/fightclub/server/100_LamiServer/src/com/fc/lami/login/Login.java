package com.fc.lami.login;

import java.util.concurrent.atomic.AtomicReference;

import com.fc.lami.Messages.LoginRequest;
import com.net.server.ClientSession;

public interface Login {

	public LoginInfo login(ClientSession session, LoginRequest login);

}

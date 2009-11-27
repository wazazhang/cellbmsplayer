package com.cell.appengine.wga;

public enum Result 
{	
	UNKNOW_ERROR						(-1),
	
	REGIST_SUCCESS						(+1001),
	REGIST_ACCOUNT_NAME_ALREADY_EXIST	(-1002),
	
	LOGIN_SUCCESS						(+2001),
	LOGIN_USER_NOT_EXIST				(-2002),	
	LOGIN_SIGN_ERROR					(-2003),
	;
	
	public final int code;
	Result(int code){
		this.code = code;
	}
}

package com.fc.lami.login;

import com.fc.lami.Messages.PlatformUserData;

public abstract class User 
{
	final protected PlatformUserData data;
	final protected String uid;
	
	public User(PlatformUserData data) {
		this.data = data;
		this.uid  = this.data.getPlatformAddress();
	}
	
	final public String	getUID() {
		return uid;
	}
	
	public String	getName() {
		return data.user_name;
	}
	
	public String	getSex() {
		return data.user_sex;
	}
	
	public String	getHeadURL(){
		return data.user_image_url;
	}
	
	
	abstract public int		getLevel();
	
	abstract public int		getScore();
//	abstract public int		getPoint();
	abstract public int		getWin();
	abstract public int		getLose();

	abstract public User	addScore(int value);
//	abstract public User	addPoint(int value);
	abstract public User	addWin(int value);
	abstract public User	addLose(int value);
	
	abstract public User	save();
}

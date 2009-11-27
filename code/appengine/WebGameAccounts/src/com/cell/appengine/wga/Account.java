package com.cell.appengine.wga;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cell.security.MD5;
import com.google.appengine.api.datastore.Email;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Account 
{
	@PrimaryKey
	@Persistent
	private String 	name;
	
	@Persistent
	private String	sign;

	@Persistent
	private Email	email;
	
	@Persistent
	private Date	create_time		= new Date(System.currentTimeMillis());
	
	@Persistent
	private Boolean	activated		= false;
	
	@Persistent
	private Date	last_login_time;
	

	
	public Account(String name, String sign, Email email) 
	{
		this.name 	= name;
		this.sign	= MD5.getMD5(sign, "UTF-8");
		this.email	= email;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @return the email
	 */
	public Email getEmail() {
		return email;
	}

	/**
	 * @return the create_time
	 */
	public Date getCreateTime() {
		return create_time;
	}

	/**
	 * @return the last_login_time
	 */
	public Date getLastLoginTime() {
		return last_login_time;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign	= MD5.getMD5(sign, "UTF-8");
	}

	/**
	 * @param createTime the create_time to set
	 */
	public void setCreateTime(Date createTime) {
		create_time = createTime;
	}

	/**
	 * @param lastLoginTime the last_login_time to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		last_login_time = lastLoginTime;
	}

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated the activated to set
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
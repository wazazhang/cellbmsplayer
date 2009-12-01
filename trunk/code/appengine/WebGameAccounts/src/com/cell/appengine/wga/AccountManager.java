package com.cell.appengine.wga;


import java.lang.ref.Reference;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOException;

import com.cell.security.MD5;

final public class AccountManager
{
	private static final Logger log = Logger.getLogger(AccountManager.class.getName());

	private static final AccountManager instance = new AccountManager();
	
	public static AccountManager get() {
		return instance;
	}
	
//	---------------------------------------------------------------------------------------------------------------
	
	/**
	 * 注册新帐号
	 * @param account
	 * @return
	 */
	synchronized public Result regist(Account account)
	{
		javax.jdo.PersistenceManager pm = PMF.get().getPersistenceManager();
		try 
		{
			Account old = null;
			try {
				old = pm.getObjectById(Account.class, account.getName());
			} catch (Exception err) {}

			if (old == null) {
				pm.makePersistent(account);
				log.info("save account : " + account.getName());
				return Result.REGIST_SUCCESS;
			} else {
				return Result.REGIST_ACCOUNT_NAME_ALREADY_EXIST;
			}
		} 
		catch(Exception err){
			log.warning(err.getMessage());
		}
		finally {
			pm.close();
		}
		return Result.UNKNOW_ERROR;
	}
	
//	---------------------------------------------------------------------------------------------------------------

	/**
	 * 登陆
	 * @param name
	 * @param sign
	 * @return
	 */
	synchronized public Result login(String name, String sign, AtomicReference<Account> out_ref)
	{
		javax.jdo.PersistenceManager pm = PMF.get().getPersistenceManager();
		try 
		{
			Account old = null;
			try {
				old = pm.getObjectById(Account.class, name);
			} catch (Exception err) {}

			if (old != null) {
				if (old.getSign().equals(sign)) {
					out_ref.set(old);
					return Result.LOGIN_SUCCESS;
				} else {
					return Result.LOGIN_SIGN_ERROR;
				}
			} else {
				return Result.LOGIN_USER_NOT_EXIST;
			}
		}
		catch(Exception err){
			log.warning(err.getMessage());
		}
		finally {
			pm.close();
		}
		return Result.UNKNOW_ERROR;
		
	}
	
//	---------------------------------------------------------------------------------------------------------------

}

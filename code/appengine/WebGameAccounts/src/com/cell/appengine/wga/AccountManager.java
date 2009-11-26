package com.cell.appengine.wga;


import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOException;

final public class AccountManager
{
	private static final Logger log = Logger.getLogger(AccountManager.class.getName());

	synchronized public static void saveAccount(Account account)
	{
		javax.jdo.PersistenceManager pm = PMF.get().getPersistenceManager();
		try 
		{
			Account old = null;
			try {
				old = pm.getObjectById(Account.class, account.getName());
			} catch (Exception err) {
			}

			if (old == null) {
				pm.makePersistent(account);
				log.info("save account : " + account.getName());
			} else {
				throw new JDOException("\"" + account.getName() + "\" alerady exist !");
			}
		} finally {
			pm.close();
		}
	}
	
}

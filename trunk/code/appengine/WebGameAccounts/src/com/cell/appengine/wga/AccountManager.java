package com.cell.appengine.wga;


import java.util.logging.Logger;

import javax.jdo.JDOException;

final public class AccountManager
{
	private static final Logger log = Logger.getLogger(AccountManager.class.getName());

	synchronized public static void saveAccount(Account account)
	{
		javax.jdo.PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (pm.getObjectById(Account.class, account.getName())==null) {
				pm.makePersistent(account);
				System.out.println("save account : " + account.getName());
				log.info("save account : " + account.getName());
			} else {
				throw new JDOException("\"" + account.getName() + "\" alerady exist !");
			}
		} finally {
			pm.close();
			System.out.println("pm close");
		}
	}
	
}

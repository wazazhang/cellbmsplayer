package com.cell.appengine.wga;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Email;

@SuppressWarnings("serial")
public class WebGameAccountsServlet extends HttpServlet
{
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hello, world");
		
		Account acc1 = new Account("abc", "abc", new Email("1@2.c"));
		AccountManager.saveAccount(acc1);
		Account acc2 = new Account("abc", "abc", new Email("1@2.c"));
		AccountManager.saveAccount(acc2);
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + Account.class.getName();
		List<Account> greetings = (List<Account>) pm.newQuery(query).execute();
		for (Account acc : greetings) {
			System.out.println(acc.getName());
		}
		pm.close();
	}
}

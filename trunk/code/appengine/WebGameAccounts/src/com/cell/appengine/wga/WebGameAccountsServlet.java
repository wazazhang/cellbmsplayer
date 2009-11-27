package com.cell.appengine.wga;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.cell.security.MD5;
import com.google.appengine.api.datastore.Email;

@SuppressWarnings("serial")
public class WebGameAccountsServlet extends HttpServlet
{
//	@SuppressWarnings("unchecked")
//	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
//	{
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hello, world");
//		Account acc1 = new Account("abc", "abc", new Email("1@2.c"));
//		AccountManager.regist(acc1);
//		Account acc2 = new Account("abc", "abc", new Email("1@2.c"));
//		AccountManager.regist(acc2);
//		PersistenceManager pm = PMF.get().getPersistenceManager();
//		String query = "select from " + Account.class.getName();
//		List<Account> greetings = (List<Account>) pm.newQuery(query).execute();
//		for (Account acc : greetings) {
//			System.out.println(acc.getName());
//		}
//		pm.close();
//	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		String method = req.getParameter("method");
		
		if (method.equals("login")) 
		{
			String name	= req.getParameter("name");
			String sign	= req.getParameter("sign");
			Result result = AccountManager.get().login(name, MD5.getMD5(sign, "UTF-8"), new AtomicReference<Account>());
			System.out.println(req.getRemoteAddr() + " : " + result);
			resp.sendRedirect("/query/result.jsp?result="+result);
		}
		else if (method.equals("regist")) 
		{
			String name		= req.getParameter("name");
			String sign		= req.getParameter("sign");			
			String email	= req.getParameter("email");
			Account	account	= new Account(name, sign, new Email(email));
			Result	result	= AccountManager.get().regist(account);
			System.out.println(req.getRemoteAddr() + " : " + result);
			resp.sendRedirect("/query/result.jsp?result="+result);
		}
	}
}

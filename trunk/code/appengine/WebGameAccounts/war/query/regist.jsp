
<%@page import="com.cell.security.MD5"%>
<%@ page import="com.google.appengine.api.datastore.Email"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.concurrent.atomic.AtomicReference"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.cell.appengine.wga.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String name	= request.getParameter("name");
	String sign	= request.getParameter("sign");
	String mail	= request.getParameter("mail");

	Account account = new Account(name, sign, new Email(mail));
		
	Result result = AccountManager.get().regist(account);

	out.println(result);
%>
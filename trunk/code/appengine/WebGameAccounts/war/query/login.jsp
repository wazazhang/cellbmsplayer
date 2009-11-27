
<%@ page import="com.cell.security.MD5"%>
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
	
	Result result = AccountManager.get().login(name, sign, new AtomicReference<Account>());

	out.println(result);
%>
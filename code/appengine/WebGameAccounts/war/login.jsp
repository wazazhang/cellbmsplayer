
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.cell.appengine.wga.*"%>
<%@page import="com.google.appengine.api.datastore.Email"%>

<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
	</head>
	
	<body>
		<form action=/webgameaccounts method=POST>
			<input type=hidden 		name=method value=login><br>
			帐号:<br>
			<input type=text 		name=name><br>
			密码:<br>
			<input type=password 	name=sign><br>
			<br>
			<input type=submit value="登陆">
		</form>
		
	</body>
</html>
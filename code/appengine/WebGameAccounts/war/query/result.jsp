<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.cell.appengine.wga.*"%>
<%
	String result = request.getParameter("result");
	out.println(Result.valueOf(result));
%>
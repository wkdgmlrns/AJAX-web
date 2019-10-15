<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ page import="javax.sql.*, java.sql.*,java.io.*,javax.naming.InitialContext, javax.naming.Context" %>
</head>
<body>
	<%
		InitialContext iniCtx = new InitialContext();
		Context envContext = (Context)iniCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/UserChat");
		Connection con = ds.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT userID FROM userchat");
		while(rs.next()){
			out.println(rs.getString(1));
		}
		rs.close();
		stmt.close();
		con.close();
		iniCtx.close();
	%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>个人Home主页</title>
</head>
<body>
	<p> 我的个人Home主页 </p>
	 <center>
		 Now time is: <%=new java.util.Date()%> 
	 </center>
	<br>
	<input type="button" name="Get请求" value="Get请求" onclick="Get请求">
</body>
</html>
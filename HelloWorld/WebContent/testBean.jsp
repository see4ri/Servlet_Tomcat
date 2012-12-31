<%@ page import="com.web.app.TestBean" %>
<html>
<head>
<title>
Test Bean
</title>
</head>
<body>
<center>
<%  TestBean testBean = new TestBean("Http://yexin218.cublog.cn"); %>
Java Bean Test:   
	The author's blog address is<%=testBean.getName()%>
</center>
</body>
</html>
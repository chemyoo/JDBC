<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问出错</title>
</head>
<body>
<p align="center">
	<font color="red" size=+2>
		服务访问出错:
		<code><%=response.getStatus()%></code>
	</font>
</p>
</body>
</html>
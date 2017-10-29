<%-- Created by IntelliJ IDEA. --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <%--<base href="<%=basePath%>">--%>
    <title>欢迎你到来</title>
  </head>
  <body>
    欢迎，这是我的第一个项目！中文输入法！
  </body>
</html>
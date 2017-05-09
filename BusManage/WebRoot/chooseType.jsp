<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'chooseType.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div style="float:left;height:50px;margin-top:10px;margin-left:5px">
      <a href="MainPage.jsp" target="wholepage">回到首页</a>
    </div>
    <div style="float:right;height:50px">
      <ul>
        <ui><a href="companyRegister.jsp" target="registerForm">公交公司注册</a></ui>|
        <ui><a href="userRegister.jsp" target="registerForm">普通用户注册</a></ui>
      </ul>
    </div>
  </body>
</html>

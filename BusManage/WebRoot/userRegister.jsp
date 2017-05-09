<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册</title>
    
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
  <div style="width:400px;margin-top:8%;margin-left:35%;text-align:center">
    <h1>BusInfo | 普通用户注册</h1>
    <form action="/BusManage/register/UserRegister" method="post">
        <div style="margin-top:12px;margin-bottom:12px;"><input style="width:320px;height:35px" type="text" placeholder="用户名" name="usernameRegister"/></div>
        <div style="margin-top:12px;margin-bottom:12px;"><input style="width:320px;height:35px" type="text" placeholder="邮箱" name="mail"/></div>
        <div style="margin-top:12px;margin-bottom:12px"><input style="width:320px;height:35px" type="password" placeholder="请设置密码 " name="passwordRegister"/></div>
         <div style="margin-top:12px;margin-bottom:12px"><input style="width:320px;height:35px" type="password" placeholder="请再次输入密码" name="passwordEnsured"/></div>
        <div style="margin-top:30px;margin-bottom:10px"><input style="width:120px" type="submit" value="确认"/></div>
      </form>
   </div>
  </body>
</html>

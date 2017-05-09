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
    <h1>BusInfo | 公交公司注册</h1>
    <form action="/BusManage/register/CompanyRegister" method="post" onsubmit="return varify()" id="registerForm">
        <div style="margin-top:12px;margin-bottom:12px;"><input id="cname" name="companyName" style="width:320px;height:35px" type="text" placeholder="请输入公交公司名称"/></div>
        <div style="margin-top:12px;margin-bottom:12px;"><input id="tel" name="telephone" style="width:320px;height:35px" type="text" placeholder="请输入公司联系电话"/></div>
        <div style="margin-top:12px;margin-bottom:12px;"><input id="usname" name="username" style="width:320px;height:35px" type="text" placeholder="请输入管理员昵称"/></div>
        <div style="margin-top:12px;margin-bottom:12px;"><input id="psword" name="password" style="width:320px;height:35px" type="text" placeholder="请设置密码"/></div>
        <div style="margin-top:12px;margin-bottom:12px"><input id="pswordConfirmed" name="passwordConfirmed" style="width:320px;height:35px" type="password" placeholder="请再次输入密码"/></div>
        <div style="margin-top:30px;margin-bottom:10px"><button style="width:180px;height:38px" type="submit">提交信息，等待审核</button></div>
      </form>
     </div>
     <script type="text/javascript">
     function varify(){
       var companyName = $("#cname").val();
       var telephone = $("#tel").val();
       var username = $("#uname").val();
       var password = $("#psword").val();
       var passwordConfirmed = $("#pswordConfirmed").val();
      
       if(companyName == null||companyName ==""){
          window.alert("公司名不能为空！");
          return false;
       }else if(telephone == null||telephone==""){
          window.alert("联系电话不能为空！");
          return false;
       }else if(username == null||username==""){
          window.alert("用户名不能为空！");
          return false;
       }else if(username == null||username==""){
          window.alert("公司名不能为空！");
          return false;
       }else if(password == null||password==""){
          window.alert("请设置密码！");
          return false;
       }else if(passwordConfirmed == null||passwordConfirmed==""){
          window.alert("请再次输入密码以确认！");
          return false;
       }
     }
    </script>

  </body>
</html>

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

    <link rel="stylesheet" href="./css/bootstrap.min.css"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
  </head>
  
  
  <body>
   <div style="margin-top:10px;margin-left:10px"><a href="MainPage.jsp">返回首页</a></div>
      <div style="width:400px;margin-top:10%;margin-left:35%;text-align:center">
      <h1>BusInfo</h1>
      <h3>注册</h3>
      <form action="/BusManage/register/CompanyRegister" method="post" onsubmit="return varifyRegister()" id="registerForm">
        <div style="margin-top:10px;margin-bottom:10px;"><input  id="cname"  style="width:320px" type="text" placeholder="请输入公交公司名称"  name="companyName"/></div>
        <div style="margin-top:10px;margin-bottom:10px"><input id="tel" name="telephone" style="width:320px" type="text" placeholder="请输入公司联系电话"/></div>
        <div style="margin-top:10px;margin-bottom:10px"><input id="uname" name="username" style="width:320px" type="text" placeholder="请输入管理员名称"/></div>
        <div style="margin-top:10px;margin-bottom:10px"><input id="psword" name="password" style="width:320px" type="password" placeholder="请设置密码"/></div>
        <div style="margin-top:10px;margin-bottom:10px"><input id="pswordConfirmed" name="passwordConfirmed" style="width:320px" type="password" placeholder="请再次输入密码"/></div>
    
        <div style="margin-top:80px;margin-bottom:10px"><button style="width:200px" type="submit">提交信息，等待审核</button></div>
      </form>
      </div>

    
     <script type="text/javascript">
     function varifyRegister(){
       var companyName = $("#cname").val();
       var telephone = $("#tel").val();
       var username = $("#uname").val();
       var password = $("#psword").val();
       var passwordConfirmed = $("#pswordConfirmed").val();
      
       if(companyName == null||companyName ==""){
          window.alert("公司名不能为空！");
          return false;
       }
            else if(telephone == null||telephone==""){
          window.alert("联系电话不能为空！");
          return false;
       }else if(username == null||username ==""){
          window.alert("用户名不能为空！");
          return false;
       }
       else if(password == null||password==""){
          window.alert("请设置密码！");
          return false;
       }else if(passwordConfirmed == null||passwordConfirmed==""){
          window.alert("请再次输入密码以确认！");
          return false;
       }
     }
   </script>
  
<!--   <frameset name="wholepage" rows="60,*" style="border:0px"> -->
<!--     <frame src="chooseType.jsp" frameborder=0 noresize="noresize"> -->
<!--     <frame src="companyRegister.jsp" frameborder=0> -->
<!--   </frameset> -->

   
  </body>
</html>

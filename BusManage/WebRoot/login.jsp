<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录</title>
    
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
<!--       <nav class="navbar navbar" role="navigation"> -->
<!--         <div class="container-fluid"> -->
<!--        <div class="navbar-header"> -->
<!--          <a class="navbar-brand">BusInfo</a> -->
<!--        </div> -->
<!--      </div> -->
<!--     </nav> -->
<!--   <div class="container"> -->
<!--      <h1 class="page-header">鐧诲綍</h1> -->
<!--      <form class="form-horizontal" action="${basePath}/submit" method="post"> -->
<!--        <div class="form-group"> -->
<!--          <div class="col-md-5"> -->
<!--             <input type="text" class="form-control" placeholder="鐢ㄦ埛鍚�閭"/> -->
<!--          </div> -->
<!--        </div> -->
<!--        <div class="form-group"> -->
<!--          <div class="col-md-5"> -->
<!--             <input type="password" class="form-control" placeholder="瀵嗙爜"/> -->
<!--          </div> -->
<!--        </div> -->
<!--        <div class="form-group"> -->
<!--          <div class="col-md-5"> -->
<!--             <button class="btn btn-primary">鐧诲綍</button> -->
<!--          </div> -->
<!--        </div> -->
<!--       </form> -->
<!--   </div> -->
      <div style="margin-top:10px;margin-left:10px"><a href="MainPage.jsp">返回首页</a></div>
      <div style="width:400px;margin-top:15%;margin-left:35%;text-align:center">
      <h1>BusInfo</h1>
      <h3>登录</h3>
      <form action="/BusManage/login/submit" method="post" onsubmit="return varifyLogin()" id="loginForm">
        <div style="margin-top:10px;margin-bottom:10px;"><input id="username" style="width:320px" type="text" placeholder="用户名" name="usernameLogin"/></div>
        <div style="margin-top:10px;margin-bottom:10px"><input id="password" style="width:320px" type="password" placeholder="密码" name="passwordLogin"/></div>
        <div style="margin-top:20px;margin-left:10%;width:80%">
          <div style="float:left">
            <a href="register.jsp">注册新用户</a>
          </div>
        </div>
        <div style="margin-top:80px;margin-bottom:10px"><button style="width:120px" type="submit">登录</button></div>
      </form>
      </div>
    <script type="text/javascript">
     function varifyLogin(){
       var username = $("#username").val();
       if(username == null||username==""||username.equals("0")){
          window.alert("用户名不能为空！");
          return false;
       }
     }
    </script>
  </body>
</html>

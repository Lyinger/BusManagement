<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公交管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" href="./css/bootstrap.min.css"/>
    <script type="text/javascript" src="js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>

  </head>
  
  <body>
      <div style="float:right;margin-top:20px;margin-right:30px"><a href="MainPage.jsp">返回</a></div>
      <div class="container"> 
      <div class="page-header">
       <h1>公交管理 <small>BusInfo</small></h1>
      </div>
     
     <iframe frameborder="0" src="/BusManage/manage/judge" style="width:100%;height:100%"></iframe>
     
     
   </div>
  </body>
</html>

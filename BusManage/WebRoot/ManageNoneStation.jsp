<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ManageNoneStation.jsp' starting page</title>
    
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
    <div class="span6" style="width:20%;float:left;margin-top:20px"> 
        <ul class="nav nav-list"> 
            <li class="active"><a href="/BusManage/manage/manageRoute"><i class="icon-white icon-home"></i>线路管理</a></li> 
            <li><a href="/BusManage/manage/manageStation"><i class="icon-book"></i>站点管理</a></li> 
            <li class="nav-header"><a href="/BusManage/manage/manageInfo">公司信息管理</a></li> 
        </ul> 
    </div> 
      <form action="/BusManage/manage/addStation">
         <div style="float:right;margin-bottom:10px"><button class="btn btn-default" type="submit">添加新站点</button></div>
      </form>
      <div style="float:left;margin-top:40px"><h4>没有相关数据</h4></div>
  </body>
</html>

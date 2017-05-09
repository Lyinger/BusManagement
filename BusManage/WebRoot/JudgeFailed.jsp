<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'JudgeFailed.jsp' starting page</title>
    
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
    <div class="span6" style="width:20%;float:left;margin-top:20px"> 
        <ul class="nav nav-list"> 
            <li class="active"><a href="/BusManage/manage/manageRoute"><i class="icon-white icon-home"></i>线路管理</a></li> 
            <li><a href="/BusManage/manage/manageStation"><i class="icon-book"></i>站点管理</a></li> 
            <li class="nav-header"><a href="/BusManage/manage/manageInfo">公司信息管理</a></li> 
        </ul> 
    </div> 
    <div>
    <h4>审核未通过！</h4>
    </div>
  </body>
</html>

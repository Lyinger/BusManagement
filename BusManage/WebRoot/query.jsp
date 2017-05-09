<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'query.jsp' starting page</title>
    
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
  <div id="classification" style="width:80%;float:left;margin-left:190px;margin-top:50px">
    <div style="float:left;width:33%">
       <a href="/BusManage/query/queryRoute"><img src="img/routeQuery.png" style="height: 200px; width: 200px; "/></a>
      </div>
      <div style="float:left;width:34%">
       <a href="/BusManage/query/queryStation"><img src="img/stationQuery.png" style="height: 200px; width: 200px; "/></a>
      </div>
      <div style="float:left;width:33%">
       <a href="/BusManage/query/queryCompany"><img src="img/companyQuery.png" style="height: 200px; width: 200px; "/></a>
      </div>
     </div>
  </body>
</html>

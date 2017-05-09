<%@ page language="java" import="java.util.*" import="com.model.company" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公司查询</title>
    
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
  
   <div style="float:left;width:90%;margin-left:60px;margin-top:40px">
   <ul class="list-group">
    <%  
      List<company> list =(List<company>) request.getAttribute("company");   
    %>
    <li class="list-group-item"><b>公交公司名称：</b><%= list.get(0).getCname() %></li>
    <li class="list-group-item"><b>联系电话：</b><%=list.get(0).getTel() %></li>
    <li class="list-group-item"><b>地址：</b><%=list.get(0).getAddress() %></li>
    <li class="list-group-item"><b>拥有车辆数：</b><%=list.get(0).getCarNum() %></li>
    <li class="list-group-item"><b>拥有线路数：</b><%=list.get(0).getRouteNum() %></li>
    </ul>
    </div>
  </body>
</html>

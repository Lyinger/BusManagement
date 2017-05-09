<%@ page language="java" import="java.util.*" import="com.model.station" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>站点查询</title>
    
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
 <div>
  <div id="classification" style="width:80%;float:left;margin-left:190px;margin-top:50px;margin-bottom:20px">
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
  
  <div class="panel panel-default" style="float:left;margin-left:50px;margin-right:75px;margin-top:40px;width:90%">
  <!-- Default panel contents -->
   <table class="table">
    <tr>
      <td width="200px" align="center">站点名称</td>
      <td width="800px" align="center">经过该站点的线路</td>
   </tr>
   <%  
  List<station> list =(List<station>) request.getAttribute("station");   
  for(int i=0;i<list.size();i++){ 
     String sname=list.get(i).getSname();
  %>
       <tr>
           <td width="200px" align="center"><%=sname%></td>
           <td width="800px" align="center">
             <%
             String routes[]=list.get(i).getRoutes().split(";");
             %>
             <a href="/BusManage/connect/routeHttp" name="stationName"> <%=routes[0] %></a>
              <% for(int j=1;j<routes.length;j++){%>
                  ;<a href="/BusManage/connect/routeHttp" name="stationName"><%=routes[j] %></a>
              <%} 
             %>
           </td>
       </tr>
   <%} %>
   </table>
  </div>
  </div>
  </body>
</html>

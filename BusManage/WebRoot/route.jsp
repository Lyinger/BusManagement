<%@ page language="java" import="java.util.*" import="com.model.route" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>线路查询</title>
    
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
  
  <div class="panel panel-default" style="float:left;margin-left:40px;margin-top:40px">
  <!-- Default panel contents -->

  <!-- Table -->
    <table class="table">
      <tr>
       <td width="80px" align="center">线路编号</td>
       <td width="70px" align="center">票价(元)</td>
       <td width="100px" align="center">首班车时间</td>
       <td width="100px" align="center">末班车时间</td>
       <td width="80px" align="center">发车间隔(min)</td>
       <td width="260px" align="center">所属公司</td>
       <td width="450px" align="center">所经站点</td> 
       <td width="80px" align="center">路线类型</td> 
     </tr>
      <%  
      List<route> list =(List<route>) request.getAttribute("route");   
      for(int i=0;i<list.size();i++){ 
      %>
      <tr>
        <td width="70px" align="center"><%=list.get(i).getRnum() %></td>
        <td width="70px" align="center"><%=list.get(i).getPrice() %></td>
        <td width="100px" align="center"><%=list.get(i).getStartTime() %></td>
        <td width="100px" align="center"><%=list.get(i).getFinishTime() %></td>
        <td width="140px" align="center"><%=list.get(i).getInterval() %></td>
        <td width="260px" align="center"><%=list.get(i).getCompany() %></td>
       <td width="400px" align="center">
         <%
           String stations[]=list.get(i).getStations().split(";");
           %>
           <a href="/BusManage/connect/routeHttp" name="stationName"> <%=stations[0] %></a>
          <% for(int j=1;j<stations.length;j++){%>
             →<a href="/BusManage/connect/routeHttp" name="stationName"><%=stations[j] %></a>
          <%} 
          %>
       </td> 
       <td width="80px" align="center"><%=list.get(i).getType() %></td>
      </tr>
     <%} %>
     </table>
   </div>
  </body>
</html>

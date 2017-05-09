<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'TranseferResult.jsp' starting page</title>
    
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
  <nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
      <a class="navbar-brand" href="#"><b>BusInfo</b></a>
    </div>
  </nav>
  
    <%String routeStations=(String)session.getAttribute("routeStations");
      String rnum=(String)session.getAttribute("routes");
      String[] routes=routeStations.split("%");
      String[] rnums=rnum.split("%");
      String[] everyRoutes;
      String[] everyRnums;
      for(int i=0;i<routes.length;i++){
         everyRoutes=routes[i].split("\\*");
         System.out.println(everyRoutes[0]);
         everyRnums=rnums[i].split("\\*");
         for(int j=0;j<everyRoutes.length;j++){
      %>
       <div class="panel panel-default">
       <div class="panel-heading">
          <%String[] everyIncludedRoutes=everyRnums[j].split("@");%>
          <%=everyIncludedRoutes[0] %>
          <%for(int k=1;k<everyIncludedRoutes.length;k++){%>
           <%=" -> "+ everyIncludedRoutes[k]%>   
          <% }%>
       </div>
     <div class="panel-body">
        <%String[] everyIncludedStations=everyRoutes[j].split("@");%>
        <%for(int m=0;m<everyIncludedStations.length;m++){ %>
        <%String stations[]=everyIncludedStations[m].split(";");%>
        <%=stations[0] %>
        <%for(int k=1;k<stations.length;k++){%>
        <%="->"+stations[k]%>
        <%}%>
        </br>
        <%}%>
     </div>
     </div>
     <%}} %>
</div>
    
         
  </body>
</html>

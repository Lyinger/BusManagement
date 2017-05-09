<%@ page language="java" import="java.util.*" import="com.model.station" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ManageStation.jsp' starting page</title>
    
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
   <jsp:useBean id="manager" class="cn.mysystem.controller.ManagerController"></jsp:useBean>
  
  
    <div class="span6" style="width:20%;float:left;margin-top:20px"> 
        <ul class="nav nav-list"> 
            <li class="active"><a href="/BusManage/manage/manageRoute"><i class="icon-white icon-home"></i>线路管理</a></li> 
            <li><a href="/BusManage/manage/manageStation"><i class="icon-book"></i>站点管理</a></li> 
            <li class="nav-header"><a href="/BusManage/manage/manageInfo">公司信息管理</a></li> 
        </ul> 
    </div> 
       <div style="float:left">
       <form action="/BusManage/manage/addStation" method="post">
      <div style="float:right;margin-bottom:10px"><button class="btn btn-default" type="submit">添加新站点</button></div>
      </form>
      <table class="table">
         <tr>
           <td width="600px" align="center"><b>站点名称</b></td>
           <td width="100px" align="center"></td>
           <td width="100px" align="center"></td>
         </tr>
       <%  
       List<station> list =(List<station>) request.getAttribute("station"); 
      for(int i=0;i<list.size();i++){ 
       %> 
       <form method="post">
     <tr>
       <td width="600px" align="center"><input name="stationName" readonly="readonly" type="text" value="<%=list.get(i).getSname() %>" style="border:0"/></td> 
       <td width="100px" align="center"><button class="btn btn-primary" formaction="/BusManage/manage/modifyStation/">修改</button></td> 
       <td width="100px" align="center"><button class="btn btn-primary" formaction="/BusManage/manage/deleteStation/">删除</button></td>
     </tr>
      </form>
      <%} %> 
     <tr>
       <td width="400px" align="center"></td> 
       <td width="100px" align="center"></td> 
       <td width="100px" align="center"></td>
     </tr>
      </table>
    </div>
  </body>
</html>

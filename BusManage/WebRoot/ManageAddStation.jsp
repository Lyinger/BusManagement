<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addStation.jsp' starting page</title>
    
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
    <form action="/BusManage/manage/addStationSQL" method="post" onsubmit="return varify()">
    <div>
        <div style="float:right;margin-top:40px"><button class="btn btn-default" type="submit">确认添加</button></div>
        <div class="input-group input-group" style="float:left;width:60%;margin-left:100px;margin-top:40px">
        <span class="input-group-addon"><b>站点名称</b></span><input id="sname" name="stationName" type="text" class="form-control">
        </div>
    </div>
    </form>
     <script type="text/javascript">
     function varify(){
       var sname = $("#sname").val();
       if(sname == null||sname==""){
          window.alert("站点名称不能为空！");
          return false;
       }
     }
    </script>
  </body>
</html>

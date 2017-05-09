<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>BusInfo</title>
    
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
  <div class="container-fluid" >
    <div class="navbar-header">
      <a class="navbar-brand" href="#"><b>BusInfo</b></a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <form class="navbar-form navbar-left" role="search" action="/BusManage/transefer/transefer" method="post" onsubmit="return varify()">
        <div class="form-group" style="margin-left:120px">
          <input id="strstation" name="startStation" type="text" class="form-control" placeholder="起点站" style="width:300px;margin-right:20px">
          <input id="fistation" name="finishStation" type="text" class="form-control" placeholder="终点站" style="width:300px">
        </div>
        <button type="submit" class="btn btn-default">查询</button>
      </form>
      
     <form action="/BusManage/query/city" method="post" onsubmit="return city()">
       <div style="margin-top:8px;margin-right:10px">
         <div class="form-group col-sm-2" style="float:left">
           <select id="cityChoosed" name="city" class="selectpicker show-tick form-control" data-live-search="false">
              <option value="济南">济南</option>
              <option value="青岛">青岛</option>
           </select>
         </div>
         <div style="float:left"><button type="submit" class="btn btn-default">确认</button></div>
       </div>
     </form>

     <ul class="nav navbar-nav">
       <li><a href="login.jsp">登录</a></li>
       <li><a href="register.jsp">注册</a></li>
     </ul>
      
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
  
  <iframe frameborder=0 src="query.jsp" style="width:100%;height:100%">
  </iframe>
         
   <script type="text/javascript">
     function varify(){
       var startstation = $("#strstation").val();
       var finishstation = $("#fistation").val();
       if(startstation == null||startstation==""){
          window.alert("请输入起点站！");
          return false;
       }else if(finishstation == null||finishstation==""){
          window.alert("请输入终点站！");
          return false;
       }
     }
     function city(){
       var city = $("#cityChoosed").val();
       if(city == null||city==""||city.equals("0")){
          window.alert("请选择城市！");
          return false;
     }
     }
   </script>
         
  </body>
</html>

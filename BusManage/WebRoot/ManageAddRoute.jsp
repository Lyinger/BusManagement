<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ManageAddRoute.jsp' starting page</title>
    
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
    <form action="/BusManage/manage/addRouteSQL" method="post" onsubmit="return varify()">
    <div style="float:left;width:70%;margin-top:10px;margin-left:60px">
      <div style="float:right;margin-bottom:10px"><button class="btn btn-default" type="submit">确认添加</button></div>
      <div style="float:left">
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>线路编号</b></span><input id="rnum" name="routeNum" type="text" class="form-control">
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>线路类型</b></span>
           <select id="rtype" name="routeType" class="selectpicker show-tick form-control" data-live-search="false">
              <option value="单线路">单线路</option>
              <option value="双线路">双线路</option>
           </select>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>车类型</b></span>
           <select id="ctype" name="carType" class="selectpicker show-tick form-control" data-live-search="false">
              <option value="空调/普通车">空调/普通车</option>
              <option value="普通车">普通车</option>
              <option value="空调车">空调车</option>
           </select>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>票价</b></span><input id="pri" name="price" type="text" class="form-control">
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>始发时间</b></span><input id="stime" name="startTime" type="text" class="form-control" placeholder="格式为：hh:mm:ss"/>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>末班时间</b></span><input id="ftime" name="finishTime" type="text" class="form-control" placeholder="格式为：hh:mm:ss"/>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>发车间隔</b></span><input id="intv" name="interval" type="text" class="form-control" placeholder="以分钟为单位，仅输入数字即可" />
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>所经站点</b></span><textarea id="st" name="stations" class="form-control" style="height:80px" placeholder="请将站点与站点之间用“；”隔开"></textarea>
        </div>
      </div>
     </div>
    </form>
     <script type="text/javascript">
     function varify(){
       var rnum = $("#rnum").val();
       var rtype = $("#rtype").val();
       var ctype = $("#ctype").val();
       var price = $("#pri").val();
       var stime = $("#stime").val();
       var ftime = $("#ftime").val();
       var interval = $("#intv").val();
       var station = $("#st").val();
      
       if(rnum == null||rnum ==""){
          window.alert("线路名不能为空！");
          return false;
       }
       else if(rtype == null||rtype==""){
          window.alert("线路类型不能为空！");
          return false;
       }
       else if(ctype == null||ctype ==""){
          window.alert("车辆类型不能为空！");
          return false;
       }
       else if(price == null||price==""){
          window.alert("票价不能为空！");
          return false;
       }else if(stime == null||stime==""){
          window.alert("始发时间不能为空！");
          return false;
       }else if(ftime== null||ftime==""){
          window.alert("末班时间不能为空！");
          return false;
       }else if(interval == null||interval ==""){
          window.alert("发车间隔不能为空！");
          return false;
       }else if(station == null||station==""){
          window.alert("所经车站不能为空！");
          return false;
       }
     }
   </script>
  </body>
</html>

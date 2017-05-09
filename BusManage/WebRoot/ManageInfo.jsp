<%@ page language="java" import="java.util.*" import="com.model.company" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ManageInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
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
    <form action="/BusManage/manage/modifyInfo" method="post" onsubmit="return varify()">
    <%company com = (company)request.getAttribute("company");
//     request.setCharacterEncoding("UTF-8");  
       %>
    <div style="float:left;width:70%;margin-top:10px;margin-left:60px">
      <div style="float:right;margin-bottom:10px"><button class="btn btn-default" type="submit">确认修改</button></div>
      <div style="float:left">
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>公司名称</b></span><input id="cname" name="companyName" type="text" class="form-control" value="<%=com.getCname()%>">
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>联系电话</b></span><input id="tel" name="telephone" type="text" class="form-control" value="<%=com.getTel()%>">
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>地址</b></span><input id="addr" name="address" type="text" class="form-control" value="<%=com.getAddress()%>">
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>拥有车辆数</b></span><input id="cnum" name="carNum" type="text" class="form-control" value="<%=com.getCarNum()%>">
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>拥有线路数</b></span><input id="rnum" name="routeNum" type="text" class="form-control" value="<%=com.getRouteNum()%>">
        </div>
      </div>
     </div>
    </form>
    
    <script type="text/javascript">
     function varify(){
       var cname = $("#cname").val();
       var tel = $("#tel").val();
       var addr = $("#addr").val();
       var cnum = $("#cnum").val();
       var rnum = $("#rnum").val();
      
       if(cname == null||cname ==""){
          window.alert("公司名不能为空！");
          return false;
       }else if(tel == null||tel ==""){
          window.alert("联系电话不能为空！");
          return false;
       }
       else if(addr == null||addr==""){
          window.alert("地址不能为空！");
          return false;
       }
       else if(cnum == null||cnum ==""){
          window.alert("车辆数目不能为空！");
          return false;
       }
       else if(rnum == null||rnum==""){
          window.alert("线路数目不能为空！");
          return false;
       }
     }
   </script>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>完善公司</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	 <link rel="stylesheet" href="./css/bootstrap.min.css"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
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
    <form action="/BusManage/manage/CompleteCompanyInfo">
    <div style="float:left;width:70%;margin-top:10px;margin-left:60px">
      <div>
        <h4>请先完善信息！</h4>
      </div>
      <div style="float:right;margin-bottom:10px"><button class="btn btn-default" type="submit">确认</button></div>
      <div style="float:left">
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>所在城市</b></span><input name="cityName" type="text" class="form-control" placeholder="请填写公司所在城市" name="city"/>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>城市编码</b></span><input name="cityId" type="text" class="form-control" placeholder="请为该城市设置一个两位字母编码" name="cityId"/>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>地址</b></span><input name="address" type="text" class="form-control" placeholder="地址" name="address"/>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>拥有车辆数</b></span><input name="carNum" type="text" class="form-control" placeholder="拥有车辆数" name="carNum"/>
        </div>
        <div class="input-group input-group" style="margin-top:10px;margin-bottom:10px">
        <span class="input-group-addon"><b>拥有线路数</b></span><input name="routeNum" type="text" class="form-control" placeholder="拥有路线数" name="routeNum"/>
        </div>
      </div>
     </div>
    </form>
  </body>
  
</html>

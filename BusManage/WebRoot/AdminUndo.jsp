<%@ page language="java" import="java.util.*" import="com.model.apply" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'AdminUndo.jsp' starting page</title>
    
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
            <li class="nav-header">公交公司申请</li> 
            <li class="active"><a href="/BusManage/admin/searchApply"><i class="icon-white icon-home"></i>全部申请</a></li> 
            <li><a href="/BusManage/admin/undoApply"><i class="icon-book"></i>未处理申请</a></li> 
            <li><a href="/BusManage/admin/doneApply"><i class="icon-pencil"></i>已处理申请</a></li> 
        </ul> 
    </div> 
    <div style="float:left">
    <div class="panel panel-default">
     <div class="panel-heading">未处理申请</div>

     <!-- Table -->
     <table class="table">
       <tr>
         <td align="center"><b>公司名称</b></td>
         <td align="center"><b>联系电话</b></td>
         <td align="center"><b>审核</b></td>
       </tr>
       <%  
       List<apply> list =(List<apply>) request.getAttribute("apply"); 
      for(int i=0;i<list.size();i++){ 
       %> 
      <form action="/BusManage/admin/process">
     <tr>
       <td width="400px" align="center"><input name="companyName" readonly="readonly" type="text" value="<%=list.get(i).getCompanyName() %>" style="border:0"/></td> 
       <td width="200px" align="center"><%=list.get(i).getTelephone() %></td> 
       <td width="300px" align="center">
       <input style="width:120px" type="submit" value="审核通过" name="result"/>
       <input style="width:120px" type="submit" value="审核不通过" name="result"/>
       </td>
     </tr>
     </form>
      <%} %> 
     </table>
  </div>
    </div>
   
  </body>
</html>

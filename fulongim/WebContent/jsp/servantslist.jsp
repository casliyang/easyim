<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="../js/jquery-1.10.1.js" type="text/javascript"></script>
<script src="../js/imscommon.js" type="text/javascript"></script>
<script src="../js/servantlist.js" type="text/javascript"></script>
</head>
<body>
<h3>FulongMessager 客服列表</h3>
注意：在完成客服账号管理功能后，以下客服列表自动生成，并且需要生成一个servantslist对象。
<hr>
<%String realpath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);%>
<!-- 默认都是offline，由js读取在线状态来改变 -->

<table>
<tr>
	<td id="name_leon">
	Leon
	</td>
	<td id="status_leon">
	offline
	</td>
</tr>
<tr>
	<td id="name_lion">
	Lion
	</td>
	<td id="status_lion">
	offline
	</td>
</tr>
<tr>
	<td id="name_steven">
	Steven
	</td>
	<td id="status_steven">
	offline
	</td>
</tr>
</table>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="../js/jquery-1.10.1.js" type="text/javascript"></script>
<script src="../js/imscommon.js" type="text/javascript"></script>
<script type="text/javascript">
//当DOM载入就绪可以查询及操纵时执行
$().ready(function(){
	//从url获取发送者和接收者
	var sender = getQueryString("sender");
	var receiver =  getQueryString("receiver");
	
	
	//设置在线访客
	function addOnlineClient(){
		var currenttime = getNow();
		$.post("fulongim/addClient.action",{"client":sender,"sendTime":currenttime},function(data){});
	}
	addOnlineClient();
	
	//访客显式退出（点击退出按钮）
	$("#logoutbtn").click(function(){
		if (confirm("您确定要结束与客服的沟通？")){
			var currenttime = getNow();
			$.post("/fulongim/delClient.action",{"client":sender,"sendTime":currenttime},function(data){});	 
			window.opener=null;
			window.open('','_self');
			window.close();
		}
	});
	
	//发送消息
	$("#sendbtn").click(function(){
		var currenttime = getNow();
		var msg = $("#inputarea").val();
		msg = encodeURI(encodeURI(msg));
		$("#inputarea").val("");
		var htmlnow = $("#chatarea").html();
		$("#chatarea").html(htmlnow + "[From me] <b>" + sender+ "</b>" + "  " + currenttime + "<br>" + msg + "<br>");
		$.post("/fulongim/sendmsg.action",
				{"mm.sender":sender,"mm.receiver":receiver,"mm.content":msg,"mm.conversationID":"","mm.sendTime":currenttime},
				function(data){});
	});
	
	//长连接接受消息
	function fetchmsg()
	{
		var currenttime = getNow();
		$.post("/fulongim/receivemsg.action",{"sender":receiver,"receiver":sender,"sendTime":currenttime},function(data){
			//todo....是否需要把消息做成json格式
			var msg = data;
		    var msggroup = msg.split("|");
		    for (var i=0 ; i< msggroup.length ; i++){
		    	//每一次内循环是一条消息
	        	var onemsg = msggroup[i].split("&");
		        var tcontent = onemsg[0];
		        var tsender = onemsg[1];
		        var treceiver = onemsg[2];
		        var tconversationID = onemsg[3];
		        var timestamp = onemsg[4];
		        var htmlnow = $("#chatarea").html();
		        $("#chatarea").html(htmlnow + "<b>" + tsender+ "</b>" + "  " + timestamp + "<br>" + tcontent + "<br>");
		    }
		    fetchmsg();
		});
	}
	//加载页面就开始监听消息
	fetchmsg();

});
</script>
</head>
<body>
<h3>FulongMessager 访客端</h3>
<hr>
聊天内容：
<br>
<div id="chatarea"></div>
<hr>
输入框：<br>
<textarea id="inputarea" rows="8" cols="80">
</textarea>
<br>
<button id="sendbtn">发送</button>
<br>
<button id="logoutbtn">退出</button>
</body>
</html>
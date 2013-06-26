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
$().ready(function(){
	var clientsList = new Array(); //创建访客数组

	//从url获取发送者和接收者
	var sender = getQueryString("sender");
	var receiver = getQueryString("receiver");
	
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
		    //alert(msg);
		    var msggroup = msg.split("|");
		    //有可能一次返回多条消息，用|划分，下面分别处理每一条消息
		    for (var i=0 ; i< msggroup.length ; i++){
		    	//每一次内循环是一条消息，onemsg是一条消息，消息的各字段用&划分
		        var onemsg = msggroup[i].split("&");
		        var tcontent = onemsg[0];
		        var tsender = onemsg[1];
		        var treceiver = onemsg[2];
		        var tconversationID = onemsg[3];
		        var timestamp = onemsg[4];
		        //控制父页面serverframe的访客tab变色
		        if(window.parent != null && $("#focustab_field",window.parent.document).text() != "tab_"+tsender)
		        	if($("#tab_"+tsender,window.parent.document) != null)
		        		$("#tab_"+tsender,window.parent.document).css({"background-color":"#00FF00","width":"99px","height":"20px"});
		        
		        //将消息显示到聊天区域
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
<h3>FulongMessager 客服端</h3>
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

</body>
</html>
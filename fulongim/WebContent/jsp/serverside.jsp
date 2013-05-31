<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<h3>FulongMessager 客服端</h3>
<hr>
聊天内容：
<br>
<div id="chatarea"></div>
<hr>
输入框：<br>
<textarea id="inputarea" rows="8" cols="160">
</textarea>
<br>
<button onclick="send()">发送</button>

<script>
//设置sender，receiver，优先从url中取（开始）
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }

var urlsender = "";
urlsender =	getQueryString("sender");
var urlreceiver = "";
urlreceiver = getQueryString("receiver");

var sender = "lion";
var receiver = "servantA";

if(urlsender != "" && urlreceiver != ""){
	sender = urlsender;
	receiver = urlreceiver;
}
//设置sender，receiver，优先从url中取（结束）

//发送消息
function send(){
  var currenttime = getNow();
  var msg = document.getElementById("inputarea").value;
  document.getElementById("inputarea").value = "";
  document.getElementById("chatarea").innerHTML += "[From me] <b>" + sender+ "</b>" + "  " + currenttime + "<br>" + msg + "<br>";
  msg = encodeURI(encodeURI(msg));
  var req = null;
  try{
    req = new XMLHttpRequest();
  }catch(error){
    try{
      req = new ActiveXObject("Microsoft.XMLHTTP");
    }catch(error){return false;}
  }
  req.onreadystatechange = function ajaxExcute(){
    if((req.readyState==4)&&(req.status==200))
    {
    }
  }     //设置回调处理函数
 var url = "fulongim/sendmsg.action?mm.sender="+sender+"&mm.receiver="+receiver+"&mm.content="+msg+"&mm.sendTime="+currenttime;                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}

function getNow(){
 var myDate = new Date();
 var year = myDate.getFullYear();
 var month = myDate.getMonth() + 1;
 var day = myDate.getDate();
 var time = myDate.getHours()+":"+myDate.getMinutes()+myDate.getSeconds();
 return year + "-" + month + "-" + day + " " + time;
}

//长连接接受消息
function fetchmsg()
{
  var req = null;
  try{
    req = new XMLHttpRequest();
  }catch(error){
    try{
      req = new ActiveXObject("Microsoft.XMLHTTP");
    }catch(error){return false;}
  }
  req.onreadystatechange = function ajaxExcute(){
    if((req.readyState==4)&&(req.status==200))
    {
    var msg = req.responseText;
    //alert(msg);
    var msggroup = msg.split("|");
    for (var i=0 ; i< msggroup.length ; i++){
    	//每一次内循环是一条消息
        var onemsg = msggroup[i].split("&");
        var tcontent = onemsg[0];
        var tsender = onemsg[1];
        var treceiver = onemsg[2];
        var tconversationID = onemsg[3];
        var timestamp = onemsg[4];
        document.getElementById("chatarea").innerHTML += "<b>" + tsender+ "</b>" + "  " + timestamp + "<br>" + tcontent + "<br>";
    }
    fetchmsg();
    }
  }     //设置回调处理函数
 var timestamp=new Date().getTime();
 var url = "fulongim/receivemsg.action?receiver="+sender+"&timestamp="+timestamp;                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}
//加载页面就开始获取消息
fetchmsg();

</script>
</body>
</html>
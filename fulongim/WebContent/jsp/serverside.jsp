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
<textarea id="inputarea" rows="8" cols="80">
</textarea>
<br>
<button onclick="send()">发送</button>

<script>
var clientsList = new Array(); //创建访客数组

//查找某元素是否在数组中，如果在，返回下标，否则返回-1
function usual_search(array,key)
{
	var len=array.length;
	var i = 0;
	for(;i<len;i++){
		if(array[i]==key)
		return i;
	}
	return -1;
}

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
  };     //设置回调处理函数
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
        if(window.parent != null && window.parent.focustab != "tab_"+tsender)
        	window.parent.document.getElementById("tab_"+tsender).setAttribute("style", "background-color:#00FF00;width:99px;height:20px");

        
        //将消息显示到聊天区域
        document.getElementById("chatarea").innerHTML += "<b>" + tsender+ "</b>" + "  " + timestamp + "<br>" + tcontent + "<br>";
    }
    fetchmsg();
    }
  };     //设置回调处理函数
 var timestamp=new Date().getTime();
 var url = "fulongim/receivemsg.action?sender="+receiver+"&receiver="+sender+"&timestamp="+timestamp;                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}
//加载页面就开始获取消息
fetchmsg();

</script>
</body>
</html>
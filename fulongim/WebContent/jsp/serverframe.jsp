<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<!-- 
本页面功能：
1.监听消息缓冲区
2.如果有发送给当前客服的消息，并且发送者不在当前页面的访客列表中
   （如果在列表中就不用管了，对话页面、访客标签均已创建好，并且对话页面会通过parent控制父页面的访客标签提示收到信息）
	2.1 创建一个访客标签，
	2.2创建一个隐藏iframe，
	2.3 iframe请求一个客服对话页面，带上参数sender和receiver
3.当客服点击访客标签时，显示对应的隐藏iframe，其余iframe隐藏
-->

<table width="100%" height="100%">
<tr>
<td id="tabtd" width="100px"></td>
<td id="chattd" height="100%"></td>
</tr>
</table>

<script>
var clientsList = new Array(); //创建访客数组，用于存储沟通中的所有访客id
var focustab = "";   //用于标示当前聊天访客

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

var urlreceiver = "";
urlreceiver = getQueryString("receiver");

var receiver = "leon";   //接受者

if(urlreceiver != ""){
	//利用url接收到的receiver
	receiver = urlreceiver;
}
//设置sender，receiver，优先从url中取（结束）

function getNow(){
 var myDate = new Date();
 var year = myDate.getFullYear();
 var month = myDate.getMonth() + 1;
 var day = myDate.getDate();
 var time = myDate.getHours()+":"+myDate.getMinutes()+myDate.getSeconds();
 return year + "-" + month + "-" + day + " " + time;
}

//长连接接受消息
function listeningClients()
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
    var currenttime = getNow();
    //alert(msg);
    var clientsgroup = msg.split("|");
    //有可能一次返回多条消息，用|划分，下面分别处理每一条消息
    for (var i=0 ; i< clientsgroup.length ; i++){
    	//每一次内循环是一个发送者
    	var tempclient = clientsgroup[i];
    	tempclient = tempclient.trim();
        //判断是否是新访客,如果是新访客，将访客id添加进访客列表，创建一个新对话标签及对话窗口
        //如果访客已在页面访客列表中，就不做任何处理了
        //采用DOM方式添加子节点，比innerHTML效率要高
        if(usual_search(clientsList,tempclient) == -1){
        	clientsList.push(tempclient);
        	
        	//document.getElementById("tabtd").innerHTML += "<div onclick='gochat(this.id);' id='tab_"+tempclient+"' style='background-color:yellow;width:99px;height:20px'>"+tempclient+"</div><br>";        	
        	//document.getElementById("chattd").innerHTML += "<iframe id='chat_"+tempclient+"' src='serverside.jsp?sender="+receiver+"&receiver="+tempclient+"&sendTime="+currenttime+"' style='display:block;background-color:#99FFFF;width:100%;height:600px'></iframe>";
        	var tabnode = document.createElement("div");
        	tabnode.setAttribute("id", "tab_"+tempclient);
        	tabnode.setAttribute("onclick", "gochat(this.id);");
        	if(clientsList.length <= 1){
        		//注意：tab有三种状态，选中状态（黄色  yellow），闲置状态（蓝色  #00FFFF），非选中收到消息状态（绿色 #00FF00）
        		//当时第一个访客时，tabl为黄色，当前访客tab记录该tab的id
	        	tabnode.setAttribute("style", "background-color:yellow;width:99px;height:20px");
	        	focustab = "tab_"+tempclient;
        	}else{
        		tabnode.setAttribute("style", "background-color:#00FF00;width:99px;height:20px");
        	}
        	tabnode.appendChild(document.createTextNode(tempclient));
        	document.getElementById("tabtd").appendChild(tabnode);     //添加访客tab
        	var brnode = document.createElement("br");
        	document.getElementById("tabtd").appendChild(brnode);      //换行
        	//动态添加访客tab完成
        	
        	//添加聊天iframe
        	var framenode = document.createElement("iframe");
        	framenode.setAttribute("id", "chat_"+tempclient);
        	framenode.setAttribute("src", "serverside.jsp?sender="+receiver+"&receiver="+tempclient+"&sendTime="+currenttime);
			//如果是第一个访客就显示对话窗口，否则隐藏，等待选择访客标签时再显示
        	if(clientsList.length <= 1){
        		framenode.setAttribute("style", "display:block;background-color:#99FFFF;width:100%;height:600px");
        	}else{
        		framenode.setAttribute("style", "display:none;background-color:#99FFFF;width:100%;height:600px");
        	}
        	document.getElementById("chattd").appendChild(framenode);
        	

        }
    }
    listeningClients();
    }
  };     //设置回调处理函数
 var timestamp=new Date().getTime();
 var url = "fulongim/getActiveClients.action?receiver="+receiver+"&timestamp="+timestamp;                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}
//加载页面就开始获取消息
listeningClients();

function gochat(thisid){
	//将之前选中的tab设置为闲置状态（蓝色  #00FFFF），之前tab对应的iframe隐藏
	document.getElementById(focustab).setAttribute("style", "background-color:#00FFFF;width:99px;height:20px");
	document.getElementById("chat_"+(focustab.split("_"))[1]).style.display = "none";
	
	//将当前tab设置为选中状态，当前tab对应的iframe显示
	focustab = thisid;
	document.getElementById(focustab).setAttribute("style", "background-color:yellow;width:99px;height:20px");
	document.getElementById("chat_"+(focustab.split("_"))[1]).style.display = "block";
}
</script>

</body>
</html>
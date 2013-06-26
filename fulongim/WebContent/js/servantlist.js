var servantsList = new Array();
servantsList.push("leon");
servantsList.push("lion");
servantsList.push("steven");

//当前访客用户名，最终需要从url/session中取得
var currentclient = "lucy";

var urlclient = "";
urlclient =	getQueryString("client");

if(urlclient != "" && urlclient != ""){
	currentclient = urlclient;
}
//设置currentclient优先从url中取（结束）


//移除已经下线的访客tab和对话iframe
function listeningOnlineServants(){
var currenttime = getNow();
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
  	//首先遍历所有客服，都设置为offline，然后再遍历返回的在线客服，置为online
  	for ( var j = 0; j < servantsList.length; j++) {
  		document.getElementById("name_"+servantsList[j]).innerHTML = "<font color='black'>"+servantsList[j]+"</font>";
			document.getElementById("status_"+servantsList[j]).innerHTML = "<font color='black'>offline</font>";
		}
  	
  	var msg = req.responseText;	
  	var servants = msg.split("|");
      for (var i=0 ; i< servants.length ; i++){
      	if (servants[i] != "") {
      		var temp = Trim(servants[i]);
				document.getElementById("name_"+temp).innerHTML = "<a target='_TOP' href='<%=realpath%>clientside.jsp?sender="+currentclient+"&receiver="+temp+"'><font color='blue'><b>"+temp+"</b></font></a>";
				document.getElementById("status_"+temp).innerHTML = "<font color='blue'><b>online</b></font>";
      	}
      }
      
      setTimeout("listeningOnlineServants()",20000);
  }
};
var url = "fulongim/getAllOnlineServants.action?sendTime="+currenttime;                 //发送请求的路径
req.open("GET",url);             //打开连接
req.send(null);          //发送请求
}
listeningOnlineServants();
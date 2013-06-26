var clientsList = new Array(); //****活跃访客数组，用于存储沟通中的所有访客id****
var focustab = "";   //用于标示当前聊天访客

var urlreceiver = "";
urlreceiver = getQueryString("receiver");

var receiver = "leon";   //接受者

if(urlreceiver != ""){
	//利用url接收到的receiver
	receiver = urlreceiver;
}
//设置sender，receiver，优先从url中取（结束）


//工具：删除Array中指定下标的元素，下标从0开始
Array.prototype.remove=function(dx){
	if(isNaN(dx)||dx>this.length){return false;}
	for(var i=0,n=0;i<this.length;i++){
		if(this[i]!=this[dx]){
			this[n++]=this[i];
		}
	}
	this.length-=1;
};

//长连接监听是否有访客发消息给当前客服
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
    	tempclient = Trim(tempclient);
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
        	
        	//监听该访客什么时候下线，下线后删除tab和iframe
        	delOfflineClients(tempclient);
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
//加载页面就开始监听
listeningClients();


//用户点击访客tab页
function gochat(thisid){
	//将之前选中的tab设置为闲置状态（蓝色  #00FFFF），之前tab对应的iframe隐藏
	document.getElementById(focustab).setAttribute("style", "background-color:#00FFFF;width:99px;height:20px");
	document.getElementById("chat_"+(focustab.split("_"))[1]).style.display = "none";
	
	//将当前tab设置为选中状态，当前tab对应的iframe显示
	focustab = thisid;
	document.getElementById(focustab).setAttribute("style", "background-color:yellow;width:99px;height:20px");
	document.getElementById("chat_"+(focustab.split("_"))[1]).style.display = "block";
}

//移除已经下线的访客tab和对话iframe
function delOfflineClients(paramclient){
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
		//有返回值时说明当前访客已经下线了
		//删掉访客tab和聊天iframe，从clientsList中去除当前下线的访客
		var offtab = document.getElementById("tab_"+paramclient);
		var offiframe = document.getElementById("chat_"+paramclient);
		offtab.parentNode.removeChild(offtab); 
		offiframe.parentNode.removeChild(offiframe); 
		for ( var i = 0; i < clientsList.length; i++) {
			if (clientsList[i] == paramclient) {
				clientsList.remove(i);
				break;
			}
		}
		
		//如果当前关掉的tab是focustab,则clientsList[0]选中clientsList中的第一个tab
		if ((focustab.split("_"))[1] == paramclient) {
			focustab = "tab_"+clientsList[0];
			gochat("tab_"+clientsList[0]);
		}
    }
  };
 var url = "fulongim/isClientOffline.action?client="+paramclient+"&sendTime="+currenttime;                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}

//设置客服上线
function addOnlineServant(){
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
    }
  };
 var url = "fulongim/addServant.action?servant="+receiver+"&sendTime="+currenttime;                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}
addOnlineServant();

//客服显式退出（比如点击退出按钮）
function logoutServant(){ 
 if (confirm("您确定要退出当前页面？")){
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
	    }
	  };
	 var url = "fulongim/delServant.action?servant="+receiver+"&sendTime="+currenttime;                 //发送请求的路径
	 req.open("GET",url);             //打开连接
	 req.send(null);          //发送请求
	 
	 window.opener=null;
	 window.open('','_self');
	 window.close();
	}
}
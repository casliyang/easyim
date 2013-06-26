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
	var clientsList = new Array(); //****活跃访客数组，用于存储沟通中的所有访客id****
	var focustab = "";   //用于标示当前聊天访客
	setFocusTab("");
	
	//使用隐藏域来保存当前选中标签，供子页面调用
	$("#focustab_field").hide();
	function setFocusTab(focus){
		$("#focustab_field").text(focus);
	}
	function getFocusTab(){
		return $("#focustab_field").text();
	}

	//当前客服ID从url中获取
	var receiver = getQueryString("receiver");

	//长连接监听是否有访客发消息给当前客服
	function listeningClients()
	{
		var timestamp=new Date().getTime();
		$.post("/fulongim/getActiveClients.action",{"receiver":receiver,"timestamp":timestamp},function(data){
		    var msg = data;
		    var currenttime = getNow();
		    var clientsgroup = msg.split("|");
		    //有可能一次返回多条消息，用|划分，下面分别处理每一条消息
		    for (var i=0 ; i< clientsgroup.length ; i++){
		    	//每一次内循环是一个发送者
		    	var tempclient = clientsgroup[i];
		    	tempclient = $.trim(tempclient);
		        //判断是否是新访客,如果是新访客，将访客id添加进访客列表，创建一个新对话标签及对话窗口
		        //如果访客已在页面访客列表中，就不做任何处理，新到消息时聊天页面会改天父页面对应tab的颜色
		        //采用DOM方式添加子节点，比innerHTML效率要高----》改为jquery方式
		        if(usual_search(clientsList,tempclient) == -1){
		        	clientsList.push(tempclient);
		        	
		        	var tabtdnow = $("#tabtd").html();
		        	$("#tabtd").html(tabtdnow + "<div id='tab_"+tempclient+"'>"+tempclient+"</div><br/>");
		        	$("#tabtd div").click(function(){
	        			//将之前选中的tab设置为闲置状态（蓝色  #00FFFF），之前tab对应的iframe隐藏
	        			$("#"+focustab).css({"background-color":"#00FFFF","width":"99px","height":"20px"});
	        			$("#chat_"+(focustab.split("_"))[1]).hide();
	        			
	        			//将当前tab设置为选中状态，当前tab对应的iframe显示
	        			focustab = this.id;
	        			setFocusTab(this.id);
	        			$("#"+focustab).css({"background-color":"yellow","width":"99px","height":"20px"});
	        			$("#chat_"+(focustab.split("_"))[1]).show();
	        			
	        		});
	        		
		        	//注意：tab有三种状态，选中状态（黄色  yellow），闲置状态（蓝色  #00FFFF），非选中收到消息状态（绿色 #00FF00）
	        		//当是第一个访客时，tabl为黄色，当前访客tab记录该tab的id
	        		if(clientsList.length <= 1){
		        		$("#tab_"+tempclient).css({"background-color":"yellow","width":"99px","height":"20px"});
		        		focustab = "tab_"+tempclient;
		        		setFocusTab("tab_"+tempclient);
		        	}else{
		        		$("#tab_"+tempclient).css({"background-color":"#00FF00","width":"99px","height":"20px"});
		        	}
		        	
		        	//动态添加访客tab完成
		        	
		        	//添加聊天iframe
		        	var chattdnow = $("#chattd").html();
		        	$("#chattd").html(chattdnow + "<iframe id='chat_"+tempclient+"'/>");
		        	$("#chat_"+tempclient).prop("src","serverside.jsp?sender="+receiver+"&receiver="+tempclient+"&sendTime="+currenttime);
		        	$("#chat_"+tempclient).css({"background-color":"#99FFFF","width":"100%","height":"600px"});
		        	//如果是第一个访客就显示对话窗口，否则隐藏，等待选择访客标签时再显示
		        	if(clientsList.length <= 1){
		        		$("#chat_"+tempclient).show();
		        	}else{
		        		$("#chat_"+tempclient).hide();
		        	}
		        	
		        	//监听该访客什么时候下线，下线后删除tab和iframe
		        	delOfflineClients(tempclient);
		        }
		    }
		    listeningClients();
		});
	}
	//加载页面就开始监听
	listeningClients();

	//移除已经下线的访客tab和对话iframe
	function delOfflineClients(paramclient){
		var currenttime = getNow();
		$.post("/fulongim/isClientOffline.action",{"client":paramclient,"sendTime":currenttime},function(data){
			//有返回值时说明当前访客已经下线了
			//删掉访客tab和聊天iframe
			($("#tab_"+paramclient).next()).remove();   //先把后面的<BR>删掉
			$("#tab_"+paramclient).remove();
			
			$("#chat_"+paramclient).remove();
			
			//从clientsList中去除当前下线的访客
			for ( var i = 0; i < clientsList.length; i++) {
				if (clientsList[i] == paramclient) {
					clientsList.remove(i);
					break;
				}
			}
			
			//如果当前关掉的tab是focustab,则focustab设置为clientsList中的第一个访客对应的tab
			if ((focustab.split("_"))[1] == paramclient) {
				focustab = "tab_"+clientsList[0];
				setFocusTab("tab_"+clientsList[0]);
				$("#tab_"+clientsList[0]).click();
				//gochat("tab_"+clientsList[0]);
			}
		});
	}

	//设置客服上线
	function addOnlineServant(){
		var currenttime = getNow();	 
		$.post("/fulongim/addServant.action",{"servant":receiver,"sendTime":currenttime},function(data){});
	}
	addOnlineServant();

	//客服显式退出（比如点击退出按钮）
	$("#logoutbtn").click(function(){
		 if (confirm("您确定要退出当前页面？")){
				var currenttime = getNow();
				$.post("/fulongim/delServant.action",{"servant":receiver,"sendTime":currenttime},function(data){});
				window.opener=null;
				window.open('','_self');
				window.close();
			}
	});
	
});




</script>
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
<button id="logoutbtn">退出</button>
<br/>
<table width="100%" height="100%">
<tr>
<!-- tabtd是容纳访客tab的容器 -->
<td id="tabtd" width="100px"></td>
<!-- chattd是容纳聊天iframe的容器 -->
<td id="chattd" height="100%"></td>
</tr>
</table>
<div id="focustab_field"></div>
</body>
</html>
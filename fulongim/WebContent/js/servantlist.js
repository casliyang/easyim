var servantsList = new Array();
servantsList.push("leon");
servantsList.push("lion");
servantsList.push("steven");

//��ǰ�ÿ��û�����������Ҫ��url/session��ȡ��
var currentclient = "lucy";

var urlclient = "";
urlclient =	getQueryString("client");

if(urlclient != "" && urlclient != ""){
	currentclient = urlclient;
}
//����currentclient���ȴ�url��ȡ��������


//�Ƴ��Ѿ����ߵķÿ�tab�ͶԻ�iframe
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
  	//���ȱ������пͷ���������Ϊoffline��Ȼ���ٱ������ص����߿ͷ�����Ϊonline
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
var url = "fulongim/getAllOnlineServants.action?sendTime="+currenttime;                 //���������·��
req.open("GET",url);             //������
req.send(null);          //��������
}
listeningOnlineServants();
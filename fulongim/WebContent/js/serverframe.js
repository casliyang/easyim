var clientsList = new Array(); //****��Ծ�ÿ����飬���ڴ洢��ͨ�е����зÿ�id****
var focustab = "";   //���ڱ�ʾ��ǰ����ÿ�

var urlreceiver = "";
urlreceiver = getQueryString("receiver");

var receiver = "leon";   //������

if(urlreceiver != ""){
	//����url���յ���receiver
	receiver = urlreceiver;
}
//����sender��receiver�����ȴ�url��ȡ��������


//���ߣ�ɾ��Array��ָ���±��Ԫ�أ��±��0��ʼ
Array.prototype.remove=function(dx){
	if(isNaN(dx)||dx>this.length){return false;}
	for(var i=0,n=0;i<this.length;i++){
		if(this[i]!=this[dx]){
			this[n++]=this[i];
		}
	}
	this.length-=1;
};

//�����Ӽ����Ƿ��зÿͷ���Ϣ����ǰ�ͷ�
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
    //�п���һ�η��ض�����Ϣ����|���֣�����ֱ���ÿһ����Ϣ
    for (var i=0 ; i< clientsgroup.length ; i++){
    	//ÿһ����ѭ����һ��������
    	var tempclient = clientsgroup[i];
    	tempclient = Trim(tempclient);
        //�ж��Ƿ����·ÿ�,������·ÿͣ����ÿ�id��ӽ��ÿ��б�����һ���¶Ի���ǩ���Ի�����
        //����ÿ�����ҳ��ÿ��б��У��Ͳ����κδ�����
        //����DOM��ʽ����ӽڵ㣬��innerHTMLЧ��Ҫ��
        if(usual_search(clientsList,tempclient) == -1){
        	clientsList.push(tempclient);
        	
        	//document.getElementById("tabtd").innerHTML += "<div onclick='gochat(this.id);' id='tab_"+tempclient+"' style='background-color:yellow;width:99px;height:20px'>"+tempclient+"</div><br>";        	
        	//document.getElementById("chattd").innerHTML += "<iframe id='chat_"+tempclient+"' src='serverside.jsp?sender="+receiver+"&receiver="+tempclient+"&sendTime="+currenttime+"' style='display:block;background-color:#99FFFF;width:100%;height:600px'></iframe>";
        	var tabnode = document.createElement("div");
        	tabnode.setAttribute("id", "tab_"+tempclient);
        	tabnode.setAttribute("onclick", "gochat(this.id);");
        	if(clientsList.length <= 1){
        		//ע�⣺tab������״̬��ѡ��״̬����ɫ  yellow��������״̬����ɫ  #00FFFF������ѡ���յ���Ϣ״̬����ɫ #00FF00��
        		//��ʱ��һ���ÿ�ʱ��tablΪ��ɫ����ǰ�ÿ�tab��¼��tab��id
	        	tabnode.setAttribute("style", "background-color:yellow;width:99px;height:20px");
	        	focustab = "tab_"+tempclient;
        	}else{
        		tabnode.setAttribute("style", "background-color:#00FF00;width:99px;height:20px");
        	}
        	tabnode.appendChild(document.createTextNode(tempclient));
        	document.getElementById("tabtd").appendChild(tabnode);     //��ӷÿ�tab
        	var brnode = document.createElement("br");
        	document.getElementById("tabtd").appendChild(brnode);      //����
        	//��̬��ӷÿ�tab���
        	
        	//�������iframe
        	var framenode = document.createElement("iframe");
        	framenode.setAttribute("id", "chat_"+tempclient);
        	framenode.setAttribute("src", "serverside.jsp?sender="+receiver+"&receiver="+tempclient+"&sendTime="+currenttime);
			//����ǵ�һ���ÿ;���ʾ�Ի����ڣ��������أ��ȴ�ѡ��ÿͱ�ǩʱ����ʾ
        	if(clientsList.length <= 1){
        		framenode.setAttribute("style", "display:block;background-color:#99FFFF;width:100%;height:600px");
        	}else{
        		framenode.setAttribute("style", "display:none;background-color:#99FFFF;width:100%;height:600px");
        	}
        	document.getElementById("chattd").appendChild(framenode);
        	
        	//�����÷ÿ�ʲôʱ�����ߣ����ߺ�ɾ��tab��iframe
        	delOfflineClients(tempclient);
        }
    }
    listeningClients();
    }
  };     //���ûص�������
 var timestamp=new Date().getTime();
 var url = "fulongim/getActiveClients.action?receiver="+receiver+"&timestamp="+timestamp;                 //���������·��
 req.open("GET",url);             //������
 req.send(null);          //��������
}
//����ҳ��Ϳ�ʼ����
listeningClients();


//�û�����ÿ�tabҳ
function gochat(thisid){
	//��֮ǰѡ�е�tab����Ϊ����״̬����ɫ  #00FFFF����֮ǰtab��Ӧ��iframe����
	document.getElementById(focustab).setAttribute("style", "background-color:#00FFFF;width:99px;height:20px");
	document.getElementById("chat_"+(focustab.split("_"))[1]).style.display = "none";
	
	//����ǰtab����Ϊѡ��״̬����ǰtab��Ӧ��iframe��ʾ
	focustab = thisid;
	document.getElementById(focustab).setAttribute("style", "background-color:yellow;width:99px;height:20px");
	document.getElementById("chat_"+(focustab.split("_"))[1]).style.display = "block";
}

//�Ƴ��Ѿ����ߵķÿ�tab�ͶԻ�iframe
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
		//�з���ֵʱ˵����ǰ�ÿ��Ѿ�������
		//ɾ���ÿ�tab������iframe����clientsList��ȥ����ǰ���ߵķÿ�
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
		
		//�����ǰ�ص���tab��focustab,��clientsList[0]ѡ��clientsList�еĵ�һ��tab
		if ((focustab.split("_"))[1] == paramclient) {
			focustab = "tab_"+clientsList[0];
			gochat("tab_"+clientsList[0]);
		}
    }
  };
 var url = "fulongim/isClientOffline.action?client="+paramclient+"&sendTime="+currenttime;                 //���������·��
 req.open("GET",url);             //������
 req.send(null);          //��������
}

//���ÿͷ�����
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
 var url = "fulongim/addServant.action?servant="+receiver+"&sendTime="+currenttime;                 //���������·��
 req.open("GET",url);             //������
 req.send(null);          //��������
}
addOnlineServant();

//�ͷ���ʽ�˳����������˳���ť��
function logoutServant(){ 
 if (confirm("��ȷ��Ҫ�˳���ǰҳ�棿")){
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
	 var url = "fulongim/delServant.action?servant="+receiver+"&sendTime="+currenttime;                 //���������·��
	 req.open("GET",url);             //������
	 req.send(null);          //��������
	 
	 window.opener=null;
	 window.open('','_self');
	 window.close();
	}
}
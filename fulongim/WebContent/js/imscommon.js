//���߷��������ڻ�ȡ��ǰʱ��
function getNow(){
 var myDate = new Date();
 var year =myDate.getFullYear();
 var month = myDate.getMonth() + 1;
 var day = myDate.getDate();
 var time = myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
 return year + "-" + month + "-" + day + " " + time;
}

//��url�л�ȡ����
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return "";
    }

//����ĳԪ���Ƿ��������У�����ڣ������±꣬���򷵻�-1
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

//ȥ�ո񹤾ߣ�����֧��IE8
function LTrim(str)  
{  
    var i;  
    for(i=0;i<str.length;i++)  
    {  
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;  
    }  
    str=str.substring(i,str.length);  
    return str;  
}  
function RTrim(str)  
{  
    var i;  
    for(i=str.length-1;i>=0;i--)  
    {  
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;  
    }  
    str=str.substring(0,i+1);  
    return str;  
}  
function Trim(str)  
{  
    return LTrim(RTrim(str));  
} 

//Ψһ��¼У��,************�÷����ݲ����ã�****************
var unique = true;
function unique_login_check(){
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
    	var res = req.responseText;
    	alert(res);
    	if(res == "online")
    		alert("�����ڱ𴦵�¼!!");
		//to do .............
    }
  };
 var url = "fulongim/isClientOnline.action?client="+sender+"&sendTime="+currenttime;                 //���������·��
 req.open("GET",url);             //������
 req.send(null);          //��������
}

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
//工具方法，用于获取当前时间
function getNow(){
 var myDate = new Date();
 var year =myDate.getFullYear();
 var month = myDate.getMonth() + 1;
 var day = myDate.getDate();
 var time = myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
 return year + "-" + month + "-" + day + " " + time;
}

//从url中获取参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return "";
    }

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

//去空格工具，用于支持IE8
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

//唯一登录校验,************该方法暂不启用！****************
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
    		alert("您已在别处登录!!");
		//to do .............
    }
  };
 var url = "fulongim/isClientOnline.action?client="+sender+"&sendTime="+currenttime;                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}

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
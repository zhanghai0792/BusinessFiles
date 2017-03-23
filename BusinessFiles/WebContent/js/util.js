
///学期
function initTeamMyDate(startYear,combo){
	var curDate=new Date();
	 var nowYear =curDate.getFullYear();
	 var c=nowYear-startYear;
	 for(var i = 0; i <= c; i++){
		 combo.append("<option value='"+getTvalue(startYear+i)+"' "+(i == c ? "selected" : "")+">"+getTvalue(startYear+i)+"</option>");
	 }
}
function getTvalue(year){
	return year+"-"+(year+1);
}
////单文件选择框验证文件类型
//http://www.111cn.net/wy/js-ajax/57996.htm
//http://wenku.baidu.com/link?url=4OHTV6a0I9lKntyUDDgGY5Xw92tmUZ8JU3ZOVzdiVcTM02yp2vNu45y5bCZFDfE6uPsyA23pxTQQ-w1X8jWWkEdUTYkhfSzeuLmsAIlxCPi
function getfileExt(file){
	var filename=file.replace(/.*(\/|\\)/, "");
	var fileExt=(/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '';
	return fileExt;
}

//循环得到相应的值
function getCookie(cname)
{
    var ss = document.cookie;
    var name = cname + "=";
    var ca = ss.split(';');
    for(var i=0; i<ca.length; i++)
    {
        var c = ca[i].trim();
        if (c.indexOf(name)==0)
            return c.substring(name.length,c.length);
    }
    return "";
}

//退出
function destroySession()
{

/*$.post("/BusinessFiles/logout",{},function(data){
window.location.reload();
},"JSON");*/
	/*$.ajax({
		url : "/BusinessFiles/Jsps/logout",
		type : "post",
		contentType : false,
		processData : false,
		success:function(data){
			//window.location.reload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});*/
	window.location.href="/BusinessFiles/logout";
}

//判断是否为空或null
function isNull(str){
	if(str==null||str==""||str=="null"||str==undefined||str=="undefined")return true;
	else false;
}
function iconFormatter(val,row){
    if(val == '1')
		return "<img src='images/up.png'";
	else 
		return "<img src='images/down.png'";
}
function closeWindow(){
	window.close();
	//window.history.back(-1);
}


//http://www.jb51.net/article/42562.htm
//http://www.cnblogs.com/wanggd/p/3164536.html
//禁用了浏览器的退格键
function forbidBackSpace(e) { 
	var ev = e || window.event; //获取event对象 
	var obj = ev.target || ev.srcElement; //获取事件源 
	var t = obj.type || obj.getAttribute('type'); //获取事件源类型 
	//获取作为判断条件的事件类型 
	var vReadOnly = obj.readOnly; 
	var vDisabled = obj.disabled; 
	//处理undefined值情况 
	vReadOnly = (vReadOnly == undefined) ? false : vReadOnly; 
	vDisabled = (vDisabled == undefined) ? true : vDisabled; 
	//当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
	//并且readOnly属性为true或disabled属性为true的，则退格键失效 
	var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true); 
	//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
	var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea"; 
	//判断 
	if (flag2 || flag1) return false; 
} 
//禁止后退键 作用于Firefox、Opera 
document.onkeypress = forbidBackSpace; 
//禁止后退键 作用于IE、Chrome 
document.onkeydown = forbidBackSpace;

/*修改密码*/

function serverLogin() {
    var newpass = $('#newPwd').val();
    var rePass = $('#cfmPwd').val();
    var curPass = $('#nowPwd').val();
		if (curPass == '') {
        $.messager.alert('系统提示', '请输入当前密码！', 'warning');
        return false;
    }
    if (newpass == '') {
        $.messager.alert('系统提示', '请输入新密码！', 'warning');
        return false;
    }
    if (rePass == '') {
        $.messager.alert('系统提示', '请再一次输入密码！', 'warning');
        return false;
    }

    if (newpass!= rePass) {
        $.messager.alert('系统提示', '新密码与确认密码不一至！请重新输入', 'warning');
        return false;
    }

	$.ajax({
		url:'/BusinessFiles/editPwd',
		data:{
			newPwd:newpass,//$.md5(newpass),
			oldPwd:curPass//$.md5(curPass)
			},
		dataType : 'json',
		type:'post',
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		error : function(x) {
			$.messager.alert('提示', "密码修改失败！", 'error');
		},
		success:function(data){
			console.info(data);
			if(data.success){
				$.messager.alert('提示', "密码修改成功！", 'info');
				$('#newPwd').val('');
			     $('#cfmPwd').val();
			    $('#nowPwd').val('');
        		closePwd();
			}else{
				$.messager.alert('提示', "密码修改失败！"+data.msg, 'error');
			}
			
		}
	});
}
 //关闭登录窗口
function closePwd() {
    $('#w').window('close');
}

function authorFormatter(ss){
	if(ss==null ||ss ==""){
		return "";
	}
	var s=new Array();
	var result="";
	s=ss.split(",");
	console.info(s);
	for(var i=0;i<s.length;i++){
		var start=s[i].indexOf('[');
		var name=s[i].substring(0,start);
		result+=name+","
	}
	return result.substring(0, result.length-1);
	
}
function authorMTFormatter(ss){
	if(ss==null ||ss ==""){
		return "";
	}
	
	var s=new Array();var result="";
	s=ss.split(",");
	for(var i=0;i<s.length;i++){
		var str=s[i].split("|");
		var name=str[0];
		var type_author=str[2];
		result+=name+"|"+type_author+","
	}
	return result.substring(0, result.length-1);	
}

function namepyFormatter(value,row,index){
	return row.name+"-"+row.py;
}

function isphone(s){
return 	/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(s);
}
/**/














/*学期暂未使用的方法*/
function getTermString(year,n){
	 return year+"-"+(year+1)+"第"+n+"学期";
}
function getTermValue(year,n){
	 return year+"-"+(year+1)+"-"+n;
}

function initTeamDate(combo){
	 var initYear = 2016;
	 var curDate=new Date();
	 var nowYear =curDate.getFullYear();
	 var c=nowYear-2016;
	var nowMonth=curDate.getMonth()+1;
		if(nowMonth>=3&&nowMonth<=8){//第二学期的月份
			//var f = "2016-2017第2学期";
			combo.append("<option value='"+getTermValue(initYear, 2)+"' "+(c == 0 ? "selected":"")+">"+getTermString(initYear, 2)+"</option>");
			if(c > 0){
				for(var i = 1; i <= c-1; i++){
					combo.append("<option value='"+getTermValue(initYear+i, 1)+"'>"+getTermString(initYear+i, 1)+"</option>");
					combo.append("<option value='"+getTermValue(initYear+i, 2)+"' "+(i == c-1 ? "selected":"")+">"+getTermString(initYear+i, 2)+"</option>");
				}alert(combo.html());
			}
		}else{
			if(initYear != nowYear){
				var y = 1;
				var m = 0;
				if(nowMonth ==1 || nowMonth == 2){
					y = 0;
					m = 1;
				}
				if(c >= (2-y)){
					combo.append("<option value='"+getTermValue(initYear, 2)+"'>"+getTermString(initYear, 2)+"</option>");
					for(var i = 1; i < c+y-1; i++){
						combo.append("<option value='"+getTermValue(initYear+i, 1)+"'>"+getTermString(initYear+i, 1)+"</option>");
						combo.append("<option value='"+getTermValue(initYear+i, 2)+"'>"+getTermString(initYear+i, 2)+"</option>");
					}
					combo.append("<option value='"+getTermValue(nowYear-m, 1)+"' selected>"+getTermString(nowYear-m, 1)+"</option>");
				}
				
			}
			
		}
}
/*end*/
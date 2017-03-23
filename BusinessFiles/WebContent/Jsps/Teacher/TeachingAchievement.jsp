<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>普通教师-教学成果</title>
<link rel="Shortcut Icon" href="images/favicon.ico">
<link rel="stylesheet" href="js/jquery-easyui-1.4.3/themes/default/easyui.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.3/themes/icon.css"  />
<script type="text/javascript" src="js/jquery-easyui-1.4.3/jquery.min.js"></script>  
<script type="text/javascript" src="js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/jquery-easyui-datagridview/datagrid-detailview.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/mytool.js"></script>
<script type="text/javascript" src="js/uploadPreview.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/jquery.md5.js"></script>

<script type="text/javascript" charset="utf-8">
$(function(){
	$('#table_teaching_achievement').datagrid({
		url:'/BusinessFiles/getTeachingAchievementlist?tid=${session.loginteacher.id}',
		iconCls:'icon-save',
		rownumbers:true,
		toolbar:'#tb_Teachingachievement',
		fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'id',//翻页操作有效
		onSelect:function(){
	  		$('#updateTTA').linkbutton('enable');
	  		$('#deleteTTA').linkbutton('enable');
	  },
		columns:[[
      { field:'ID', checkbox:true,},
      {title:'id',field:'id',width:100,hidden:true},
      {title:'作者',field:'author',width:100,align:'center',formatter:authorFormatter},
      {title:'奖项',field:'prize',width:100,align:'center'},
  	 {title:'奖励级别',field:'level',width:100,align:'center',sortable:true},
  	 {title:'等次',field:'torder',width:100,align:'center',sortable:true},
  	 {title:'时间',field:'time',width:100,align:'center',sortable:true},
      {title:'分值',field:'score',width:100,align:'center',sortable:true},
	{title:'状态',field:'status',width:50,align:'center',sortable:true,formatter:iconFormatter},
		           {title:'附件',field:'detail',width:100,align:'center',formatter:format_detail}
			]]
		});
});
function format_detail(value,row){
	var names = "附件";
	var files = row.fileName;
	var str = "<a onclick='getDetail(\""+row.id+"\",\""+names+"\",\""+files+"\")'  href='javascript:void(0);'>查看</a>";
	return str;
}
function getDetail(id,names,files){
	window.open("Jsps/viewImg.jsp?dir=TeachingAchievement/"+id+"&names="+names+"&files="+files);
}
function addTTA(){
	closeTTA();
	$('#teacherIdTTA').val($('#tid').val());
	$('#dlg_TTA').dialog('open');
}
function updateTTA(){
	var rows= $('#table_teaching_achievement').datagrid('getSelections');
	if(rows.length==1){
	$('#form_TTA').form('load',rows[0]);
	$('#dlg_TTA').dialog('open');
	}
}
function deleteTTA(){
	var rows = $('#table_teaching_achievement').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
				$.ajax({
					url : "/BusinessFiles/deleteTeachingAchievement",
					type : "post",
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					data : {
						ids:param
						},
					success : function(data) {
						if(data.success){
							for(var i = rows.length - 1; i >= 0; i--){
								var index = $('#table_teaching_achievement').datagrid('getRowIndex',rows[i]);
								$('#table_teaching_achievement').datagrid('deleteRow',index);
							}
							$.messager.show({
								title:'提示',
								timeout:3000,
								msg:data.msg,
								showType:'slide'
							});
							
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						 $.messager.alert("提 示", "操作失败");
					}
				});		
			}	
			});
	}
}
function saveTTA(){
	var Id=$('#idTTA').val();
	var data= new FormData($("#form_TTA")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateTeachingAchievement",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_teaching_achievement').datagrid('reload');
					closeTTA();
					$.messager.show({
						title:'提示',
						timeout:3000,
						msg:"保存成功",
						showType:'slide'
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 $.messager.alert("提 示", "操作失败");
			}
		});
		return ;
	}
	$.ajax({
		url : "/BusinessFiles/addTeachingAchievement",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_teaching_achievement').datagrid('reload');
				closeTTA();
				$.messager.show({
					title:'提示',
					timeout:3000,
					msg:"保存成功",
					showType:'slide'
				});
				
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 $.messager.alert("提 示", "操作失败");
		}
	});
}
function closeTTA(){
	$('#form_TTA').form('clear');
	$('#dlg_TTA').dialog('close');
}
function searchTTA(){
	$("#table_teaching_achievement").datagrid('load',{
		DateStart:$('#DateStartTTA').datebox('getValue'),
		DateEnd:$('#DateEndTTA').datebox('getValue'),
		cond:$('#condTTA').val(),level:$('#levelTTA').combobox('getText'),
	});
}
function searchenterTTA(event) {
    event = event || window.event;
    if (event.keyCode == 13) {
    	searchTTA();
    }
}

function editAuthor(){
	$('#dlg_author').dialog('open');
	
	var ss=$('#author_TTA').val();
	if(ss!=""){
		var s=new Array();
		s=ss.split(",");
		console.info(s);
		for(var i=0;i<s.length;i++){
			var start=s[i].indexOf('['); var end=s[i].indexOf(']');
			console.info(start+","+end);
			var name=s[i].substring(0,start);
			console.info(name+","+id);
			$("#table_author").datagrid('appendRow',{id:id,name:name});
		}
	}
}

//清空参与者列表
function clearAuthor(){
	var rows = $("#table_author").datagrid('getRows');
	for(var i=rows.length-1; i>=0; i--){
		$("#table_author").datagrid('deleteRow',i);
	}
}

function saveAuthor(){
	var rows=$("#table_author").datagrid('getRows');
	if(rows.length<1){
		return;
	}
	var ss="";
		for(var i=0;i<=rows.length-1;i++){
			ss+=rows[i].name+"["+rows[i].id+"]"+",";
		}
		ss=ss.substring(0,ss.length-1);
	$('#author_TTA').textbox('setValue',ss);
	$('#dlg_author').dialog('close');
	clearAuthor();
}
function exportRecords(){
	var rows = $('#table_teaching_achievement').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的记录");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportTeachingAchievement?"+cond);

}
</script> 
</head>
<body class="easyui-layout">
	<div data-options="region:'north',title:'',split:true," style="height:15%;overflow: hidden;">
 		<%@include file="../../inc/header.inc" %> 	
	</div> 
   
    <div data-options="region:'west',title:'功能导航',iconCls:'icon-reload',split:true,border:false" 
    	style="width:12%;background:#B3DFDA;padding-left:5px;">
 		<%@include file="../../inc/menu/teacher.inc" %> 	
    </div> 
    <div  data-options="region:'east',title:'帮助',collapsible:true,width:250">
		<%@include file="../../inc/help/teacher/help_TeachingAchievement.inc" %>
	</div>
	<div data-options="region:'center',title:'教学成果',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_teaching_achievement"></table>
<div id="tb_Teachingachievement" style="padding:5px;height:auto">
    
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTTA()" >新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTTA" disabled=true onclick="updateTTA();">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTTA" disabled=true onclick="deleteTTA()">删除</a>
  <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
    </div>
    
 </div>
 
 <div id="dlg_TTA" closed="true" class="easyui-dialog" title="教学成果" style="width:450px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
       <form  id="form_TTA" method="post" enctype="multipart/form-data" >
			<table cellspacing="6" cellpadding="3" >
				<tr hidden="true">
					<td><label for="idTTA"></label><input type="text" id="idTTA" name="id"/><br/></td>
				</tr>
				<tr hidden="true">
						<td><input type="text"  name="status"/></td>
				</tr>
				<tr>
					<td>获奖时间:<input  class="easyui-datebox" style="width:100px;" type="text"  name="time"  /><br /></td>
				</tr>
				<tr>
					<td>参与成员:<input  style="width:240px;" class="easyui-textbox" data-options="prompt:'单击右边的编辑按钮编辑参与者'" id="author_TTA" name="author"  /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="editAuthor()" >编辑</a></td>
				</tr>
				<tr>
					<td>获奖奖项:<input  style="width:240px;" class="easyui-textbox" id="prize" name="prize"  /><br/></td>
				</tr>
				<tr>
					<td >奖励级别:<input class="easyui-combobox" style="width:130px;"  type="text" name="level" data-options="editable:false,panelHeight:'auto',valueField: 'id',textField: 'name',url:'jsondata/TeachingAchievement.json'"/></td>
				</tr>
				<tr>
					<td>奖励等次:<input  style="width:130px;" class="easyui-combobox" type="text"  name="torder" data-options="editable:false,panelHeight:'auto',valueField: 'name',textField: 'name',url:'jsondata/TeachingAchievement_torder.json'" /></td>
				</tr>
				<tr>
					<td>折算分值:<input class="easyui-numberbox" style="width:130px;" type="text" id="score" name="score"  /></td>
				</tr>
				<tr>
					<td>上传附件:<input id="imgFile_TTA" name="file"  multiple style="width:240px;" class="easyui-filebox" accept="image/*"><input  type="hidden" name="fileName" /></td>
				</tr>
			</table>
			<div align="center">
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTTA();">保 存</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTTA();">取 消</a>
			</div>
         </form>
  </div>
  
  <%@include file="../../inc/dialog/dialog_author.inc" %>
	</div>
	
</body>
</html>


  
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

<title>普通教师-教师业务管理系统</title>
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
	var year = new Date().getYear()+1900;
	$('#date_start').datebox('setValue', (year-3)+'-1-1');
	$('#date_end').datebox('setValue', year+'-12-31');
	
	$('#table_learning_meeting').datagrid({
		 url:'/BusinessFiles/getLearningMeetinglist?tid=${session.loginteacher.id}',
		 queryParams: {
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		  },
			iconCls:'icon-save',
			rownumbers:true,
			singleSelect : true,
			toolbar:'#tb_LearningMeeting',
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTLM').linkbutton('enable');
		  		$('#deleteTLM').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'学术会议名称',field:'name',width:300,align:'center'},
	{title:'专业方向',field:'major',width:100,align:'center',sortable:true},
	{title:'组委会',field:'organization',align:'center',sortable:true},
	{title:'会议时间',field:'time',width:100,align:'center',sortable:true},
	{title:'会议地点',field:'address',width:100,align:'center',sortable:true},
	]],
	onAfterEdit: function (rowIndex,rowData,changes) 
	{
		rowData.teacherId=$('#tid').val();
		var inserted =$(this).datagrid('getChanges', "inserted");
		var updated = $(this).datagrid('getChanges', "updated");
		if(inserted.length<1 && updated.length<1){
			editRow_LearningMeeting = undefined;
			$(this).datagrid('unselectAll');
				return;
			}
			if(inserted.length>0){
				toolAction.baseAjax("addLearningMeeting",$(this),rowData,"增加成功");
			}else if(updated.length>0){ 
				toolAction.baseAjax("updateLearningMeeting",$(this),rowData,"修改成功");
			}
	},
		
			});
});

function addTLM(){
	$('#dlg_TLM').dialog('open');
}
function updateTLM(){
	var row = $('#table_learning_meeting').datagrid('getSelected');
	$('#form_TLM').form('load',row);
	$('#dlg_TLM').dialog('open');
}
function saveTLM(){
	var Id=$('#idTLM').val();
	var data= new FormData($("#form_TLM")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateLearningMeeting",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_learning_meeting').datagrid('reload');
					closeTLM();
					$.messager.show({
						title:'提示',
						timeout:3000,
						msg:"保存成功",
						showType:'slide',
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
		url : "/BusinessFiles/addLearningMeeting",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_learning_meeting').datagrid('reload');
				closeTLM();
				$.messager.show({
					title:'提示',
					timeout:3000,
					msg:"保存成功",
					showType:'slide',
				});
				
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 $.messager.alert("提 示", "操作失败");
		}
	});
}
function closeTLM(){
	$('#form_TLM').form('clear');
	$('#dlg_TLM').dialog('close');
}
function deleteTLM(){
		var row = $('#table_learning_meeting').datagrid('getSelected');
		if (row) {
		$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
				if (res) {	
					var rowIndex = $('#table_learning_meeting').datagrid('getRowIndex', row);
					$('#table_learning_meeting').datagrid('deleteRow', rowIndex);
					toolAction.baseAjax("deleteLearningMeeting",$('#table_learning_meeting'),row,"删除成功");
						}	
				});
		}
}

function searchTLM(){
	$('#table_learning_meeting').datagrid('load',{
		DateStart:$('#date_start').datebox('getValue'),
		DateEnd:$('#date_end').datebox('getValue')
	});
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
		<%@include file="../../inc/help/teacher/help_meeting.inc" %>
	</div>
	<div data-options="region:'center',title:'学术会议',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_learning_meeting"></table>
	
 
<div id="tb_LearningMeeting" style="padding:5px;height:auto">
	会议时间:<input class="easyui-datebox" style="width:100px;" id="date_start" name="date_start" data-options="editable:false,"/>至
	<input class="easyui-datebox" style="width:100px;" id="date_end" name="date_end" data-options="editable:false,"/>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTLM()" >查找</a>
   	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTLM()" >新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTLM" disabled=true onclick="updateTLM()">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTLM" disabled=true onclick="deleteTLM()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save"   onclick="saveTLM()">保存</a>
</div>
 
  <div id="dlg_TLM" closed="true" class="easyui-dialog" title="学术会议" style="width:510px;height:auto;padding:5px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
	data-options="iconCls: 'icon-save',closable:true">
       <form  id="form_TLM" method="post"  > 
			<div style="overflow: hidden;margin:0px;padding:0px;" >
				<div  style="width:490px;height:150px;float:left;margin-top:5px;">
					<table cellspacing="6" cellpadding="3" >
						<tr hidden="true">
							<td><label for="idTLM"></label><input type="text" id="idTLM" name="id"/><br/></td>
							<td><input  type="text"  value="${session.loginteacher.id}"   name="teacherId" /></td>
						</tr>
						<tr>
							<td><label  style="float:left;width:65px;">会议名称:</label><input  style="width:130px;" type="text"  name="name"  /></td>
							<td><label  style="float:left;width:65px;">专业方向:</label><input  style="width:130px;" type="text"  name="major"  /></td>
						</tr>
						<tr>
							<td><label  style="float:left;width:65px;">组委会:</label><input  style="width:130px;" type="text"  name="organization"  /></td>
							<td><label  style="float:left;width:65px;">会议时间:</label><input  style="width:130px;" class="easyui-datebox" data-options="editable:false," type="text"  name="time"  /></td>
						</tr>
						<tr>
							<td><label  style="float:left;width:65px;">会议地点:</label><input  style="width:130px;" type="text"  name="address"  /></td>
						</tr>
					</table>
				</div>
			</div>
			<div align="center">
				<input type="button"  value="保 存" onclick="saveTLM();"  style="width:80px; height:30px; border:0px; font-size:14px; font-weight:700;cursor: pointer;"/>
				<input type="button"  value="取 消" onClick="closeTLM();"   style="width:80px; height:30px; border:0px; font-size:14px; font-weight:700;cursor: pointer;"/>
			</div>
         </form>
  </div>
	</div>
	
</body>
</html>
 

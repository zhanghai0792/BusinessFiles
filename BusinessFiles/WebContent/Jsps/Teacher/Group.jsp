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

<title>普通教师-学术团体</title>
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
	$('#table_learning_group').datagrid({
		 url:'/BusinessFiles/getLearningGrouplist?tid=${session.loginteacher.id}',
			iconCls:'icon-save',
			rownumbers:true,
			singleSelect : true,
			toolbar:'#tb_LearningGroup',
			fitColumns:true,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTLG').linkbutton('enable');
		  		$('#deleteTLG').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'学术团体名称',field:'name',width:100,align:'center'},
	{title:'团体级别',field:'level',width:128,align:'center',sortable:true},
	{title:'专业方向',field:'major',width:128,align:'center',sortable:true},
	{title:'担任职务',field:'job',width:100,align:'center',sortable:true},
	]],
	onAfterEdit: function (rowIndex,rowData,changes) 
	{
		
	},
		 
			});
});
function addTLG(){
	$('#dlg_TLG').dialog('open');
}
function updateTLG(){
	var row = $('#table_learning_group').datagrid('getSelected');
	$('#form_TLG').form('load',row);
	$('#dlg_TLG').dialog('open');
}
function saveTLG(){
	var Id=$('#idTLG').val();
	var data= new FormData($("#form_TLG")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateLearningGroup",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_learning_group').datagrid('reload');
					closeTLG();
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
		url : "/BusinessFiles/addLearningGroup",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_learning_group').datagrid('reload');
				closeTLG();
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
function closeTLG(){
	$('#form_TLG').form('clear');
	$('#dlg_TLG').dialog('close');
}
function deleteTLG(){
		var row = $('#table_learning_group').datagrid('getSelected');
		if (row) {
		$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
				if (res) {	
					var rowIndex = $('#table_learning_group').datagrid('getRowIndex', row);
					$('#table_learning_group').datagrid('deleteRow', rowIndex);
					toolAction.baseAjax("deleteLearningGroup",$('#table_learning_group'),row,"删除成功");
						}	
				});
		}
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
		<%@include file="../../inc/help/teacher/help_group.inc" %>
	</div>
	<div data-options="region:'center',title:'学术团体',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_learning_group"></table>
 <div id="tb_LearningGroup" style="padding:5px;height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTLG()" >新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTLG" disabled=true onclick="updateTLG();">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTLG" disabled=true onclick="deleteTLG()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save"   onclick="saveTLG()">保存</a>
 
 </div>
 
 <div id="dlg_TLG" closed="true" class="easyui-dialog" title="学术团体" style="width:510px;height:auto;padding:5px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
	data-options="iconCls: 'icon-save',closable:true">
       <form  id="form_TLG" method="post"  > 
			<div style="overflow: hidden;margin:0px;padding:0px;" >
				<div  style="width:490px;height:150px;float:left;margin-top:5px;">
					<table cellspacing="6" cellpadding="3" >
						<tr hidden="true">
							<td><label for="idTLG"></label><input type="text" id="idTLG" name="id"/><br/></td>
							<td><input  type="text"  value="${session.loginteacher.id}"   name="teacherId" /></td>
						</tr>
						<tr>
							<td><label  style="float:left;width:65px;">团体名称:</label><input  style="width:130px;" type="text"  name="name"  /></td>
							<td><label  style="float:left;width:65px;">团体级别:</label><input  style="width:130px;" type="text"  name="level"  /></td>
						</tr>
						<tr>
							<td><label  style="float:left;width:65px;">专业方向:</label><input  style="width:130px;" type="text"  name="major"  /></td>
							<td><label  style="float:left;width:65px;">担任职务:</label><input  style="width:130px;" type="text"  name="job"  /></td>
						</tr>
					</table>
				</div>
			</div>
			<div align="center">
				<input type="button"  value="保 存" onclick="saveTLG();"  style="width:80px; height:30px; border:0px; font-size:14px; font-weight:700;cursor: pointer;"/>
				<input type="button"  value="取 消" onClick="closeTLG();"   style="width:80px; height:30px; border:0px; font-size:14px; font-weight:700;cursor: pointer;"/>
			</div>
         </form>
  </div>
	</div>
	
</body>
</html>
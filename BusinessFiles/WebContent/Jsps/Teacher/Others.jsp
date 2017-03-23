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

<title>普通教师-其它工作</title>
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
var editRow_OtherWorks = undefined;
$(function(){
	var year = new Date().getYear()+1900;
	$('#date_start').datebox('setValue', (year-3)+'-1-1');
	$('#date_end').datebox('setValue', year+'-12-31');
	$('#table_other_works').datagrid({
		 url:'/BusinessFiles/getOtherWorkslist',
		 queryParams: {
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		  },
			iconCls:'icon-save',
			//collapsible : true,
			rownumbers:true,
			singleSelect : true,
			//pagination:true,
			//pageSize:MypSize,
			//pageList:MypList,
			fit:true,
			toolbar:'#tb_OtherWorks',
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTOW').linkbutton('enable');
		  		$('#deleteTOW').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'center',hidden:true},
	{title:'工作性质',field:'work',width:200,align:'center',editor :{ type : 'combotree',options : { required : true,valueField: 'id',textField: 'text',url:'jsondata/OtherWorks_work.json' }}},
	{title:'工作描述',field:'description',width:300,align:'center',editor :{ type : 'textbox',options : { required : true}}},
	{title:'时间',field:'time',width:100,align:'center',editor :{ type : 'datebox',options : { required : true }},sortable:true},
	]],
	onAfterEdit: function (rowIndex,rowData,changes) 
	{
		rowData.teacherId=$('#tid').val();
		var inserted =$(this).datagrid('getChanges', "inserted");
		var updated = $(this).datagrid('getChanges', "updated");
		if(inserted.length<1 && updated.length<1){
			editRow_OtherWorks = undefined;
			$(this).datagrid('unselectAll');
				return;
			}
			if(inserted.length>0){
				toolAction.baseAjax("addOtherWorks",$(this),rowData,"增加成功");
			}else if(updated.length>0){ 
				toolAction.baseAjax("updateOtherWorks",$(this),rowData,"修改成功");
			}
	},
			});
});
function addTOW(){
	if (editRow_OtherWorks == undefined) {
		$('#table_other_works').datagrid('unselectAll');
			$('#table_other_works').datagrid('insertRow', {index :0,row : {}});
			$('#table_other_works').datagrid('beginEdit', 0);
			editRow_OtherWorks = 0;			
		}
}
function updateTOW(){
	if(editRow_OtherWorks == undefined){
		var row = $('#table_other_works').datagrid('getSelected');
			if(row){
			var index = $('#table_other_works').datagrid('getRowIndex',row);
			editRow_OtherWorks = index;
				$('#table_other_works').datagrid('beginEdit', index);
		}
			}
}
function deleteTOW(){
	if(editRow_OtherWorks == undefined){
		var row = $('#table_other_works').datagrid('getSelected');
		if (row) {
		$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
				if (res) {	
					var rowIndex = $('#table_other_works').datagrid('getRowIndex', row);
					$('#table_other_works').datagrid('deleteRow', rowIndex);
					toolAction.baseAjax("deleteOtherWorks",$('#table_other_works'),row,"删除成功");
						}	
				});
		}
	}
}
function redoTOW(){
	$('#table_other_works').datagrid('rejectChanges');
	editRow_OtherWorks = undefined;
}
function saveTOW(){
	if(editRow_OtherWorks != undefined){
		$('#table_other_works').datagrid('endEdit', editRow_OtherWorks);
		editRow_OtherWorks = undefined;
	}
}


function searchRecord(){
	$("#table_other_works").datagrid('load',{
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
		<%@include file="../../inc/help/teacher/help_other_work.inc" %>
	</div>
	<div data-options="region:'center',title:'其它',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_other_works"></table>
 
 		<div id="tb_OtherWorks" style="padding:5px;height:auto">
 			<label >时间:</label> 
			<input class="easyui-datebox" style="width:100px;" id="date_start" name="date_start" data-options="editable:false,"/>至
			<input class="easyui-datebox" style="width:100px;" id="date_end" name="date_end" data-options="editable:false,"/>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchRecord()" >查找</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTOW()" >新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTOW" disabled=true onclick="updateTOW();">修改</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="redoTOW"  onclick="redoTOW();">撤销</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTOW" disabled=true onclick="deleteTOW()">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save"  onclick="saveTOW()">保存</a>
  		</div>
	</div>	
</body>
</html>



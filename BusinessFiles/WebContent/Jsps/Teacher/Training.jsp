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

<title>普通教师-进修培训</title>
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
var editRow_TrainStudy = undefined;
$(function(){
	var year = new Date().getYear()+1900;
	$('#date_start').datebox('setValue', (year-3)+'-1-1');
	$('#date_end').datebox('setValue', year+'-12-31');
	
	
	initTeamMyDate(2016,$('#termTTS'));
	 $('#table_training_study').datagrid({
		 url:'/BusinessFiles/getTrainingStudylist?tid=${session.loginteacher.id}',
		 queryParams: {
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		  },
			iconCls:'icon-save',
			rownumbers:true,
			singleSelect : true,
			toolbar:'#tb_TrainingStudy',
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTTS').linkbutton('enable');
		  		$('#deleteTTS').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'类型',field:'type',width:100,align:'center',editor :{ type : 'textbox',options : { required : true }}},
	{title:'学习内容',field:'direction',width:100,align:'center',editor :{ type : 'textbox',options : { required : true }},sortable:true},
	{title:'开始时间',field:'startDate',width:100,align:'center',editor :{ type : 'datebox',options : { required : true,editable:false }},sortable:true},
	{title:'总天数',field:'totalDays',width:100,align:'center',editor :{ type : 'textbox',options : { required : true }},sortable:true},
	{title:'地点',field:'address',width:250,align:'center',editor :{ type : 'textbox',options : { required : true }},sortable:true},
	]],
	onAfterEdit: function (rowIndex,rowData,changes) 
	{
		rowData.teacherId=$('#tid').val();
		var inserted =$(this).datagrid('getChanges', "inserted");
		var updated = $(this).datagrid('getChanges', "updated");
		if(inserted.length<1 && updated.length<1){
			editRow_TrainStudy = undefined;
			$(this).datagrid('unselectAll');
				return;
			}
			if(inserted.length>0){
				toolAction.baseAjax("addTrainingStudy",$(this),rowData,"增加成功");
			}else if(updated.length>0){ 
				toolAction.baseAjax("updateTrainingStudy",$(this),rowData,"修改成功");
			}
	},
		
			});
});

function addTTS(){
	if (editRow_TrainStudy == undefined) {
		$('#table_training_study').datagrid('unselectAll');
			$('#table_training_study').datagrid('insertRow', {index :0,row : {}});
			$('#table_training_study').datagrid('beginEdit', 0);
			editRow_TrainStudy = 0;			
		}
}
function updateTTS(){
	if(editRow_TrainStudy == undefined){
		var row = $('#table_training_study').datagrid('getSelected');
			if(row){
			var index = $('#table_training_study').datagrid('getRowIndex',row);
			editRow_TrainStudy = index;
				$('#table_training_study').datagrid('beginEdit', index);
		}
			}
}
function redoTTS(){
	$('#table_training_study').datagrid('rejectChanges');
	editRow_TrainStudy = undefined;
}
function deleteTTS(){
	if(editRow_TrainStudy == undefined){
		var row = $('#table_training_study').datagrid('getSelected');
		if (row) {
		$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
				if (res) {	
					var rowIndex = $('#table_training_study').datagrid('getRowIndex', row);
					$('#table_training_study').datagrid('deleteRow', rowIndex);
					toolAction.baseAjax("deleteTrainingStudy",$('#table_training_study'),row,"删除成功");
						}	
				});
		}
	}
}
function saveTTS(){
	if(editRow_TrainStudy != undefined){
		$('#table_training_study').datagrid('endEdit', editRow_TrainStudy);
		editRow_TrainStudy = undefined;
	}
}
function searchTTS(){
	$('#table_training_study').datagrid('load',{
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
		<%@include file="../../inc/help/teacher/help_train.inc" %>
	</div>
	<div data-options="region:'center',title:'进修培训',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_training_study"></table>
<div id="tb_TrainingStudy" style="padding:5px;height:auto">
    <input class="easyui-datebox" style="width:100px;" id="date_start" name="date_start" data-options="editable:false,"/>至
	<input class="easyui-datebox" style="width:100px;" id="date_end" name="date_end" data-options="editable:false,"/>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTTS()" >查找</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTTS()" >新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTTS" disabled=true onclick="updateTTS();">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTTS" disabled=true onclick="deleteTTS()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save"  onclick="saveTTS()">保存</a>
    
 </div>
	</div>	
</body>
</html>

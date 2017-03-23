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


<title>普通教师-学习经历</title>
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
	var editRow_LearningExperience = undefined;
	var levels = [{ "value": "本科学士", "text": "本科学士" }, { "value": "本科硕士", "text": "本科硕士" }, { "value": "硕士研究生", "text": "硕士研究生" },{ "bsyjs": "博士研究生", "text": "博士研究生" }];
				$('#table_larning_experience').datagrid({
					url:'/BusinessFiles/getLearningExperiencelist',
					iconCls:'icon-save',
					//collapsible : true,
					rownumbers:true,
					singleSelect : true,
					pagination:true,
					pageSize:10,
					pageList:[10,20,30],
					fit:true,
					fitColumns:false,
					nowarp:true,
					border:false,
					idField:'id',//翻页操作有效
					columns:[[
					{ field:'ID', checkbox:true,},
			{title:'id',field:'id',width:100,hidden:true},
			{title:'层次',field:'level',width:100,align:'center',editor :{ type : 'combobox',options : { required : true,editable:false,valueField:'text',
				textField:'text',data: levels}}},
			{title:'专业',field:'major',width:128,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}},
			{title:'学校',field:'school',width:128,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}},
			{title:'毕业时间',field:'graduatDate',width:100,align:'center',sortable:true,editor :{ type : 'datebox',options : { required : true,editable:false }}}
			]],
			
			onAfterEdit: function (rowIndex,rowData,changes) 
			{
				rowData.teacherId=$('#id').val();
				var inserted =$(this).datagrid('getChanges', "inserted");
				var updated = $(this).datagrid('getChanges', "updated");
				if(inserted.length<1 && updated.length<1){
					editRow_LearningExperience = undefined;
					$(this).datagrid('unselectAll');
						return;
					}
					if(inserted.length>0){
						toolAction.baseAjax("addLE",$(this),rowData,"增加成功");
					}else if(updated.length>0){ 
						toolAction.baseAjax("updateLE",$(this),rowData,"修改成功");
					}
					//editRow_LearningExperience = undefined;
					//updated.length=0;
			},
				 toolbar: [{
							text: '增加',
							iconCls: 'icon-add',
							handler: function () {
								if (editRow_LearningExperience == undefined) {
									$('#table_larning_experience').datagrid('unselectAll');
										$('#table_larning_experience').datagrid('insertRow', {index :0,row : {}});
										$('#table_larning_experience').datagrid('beginEdit', 0);
										editRow_LearningExperience = 0;			
									}
								}
							}, "-",{
								text: '修改',
								iconCls: 'icon-edit',
								handler: function () {
									if(editRow_LearningExperience == undefined){
										var row = $('#table_larning_experience').datagrid('getSelected');
											if(row){
											var index = $('#table_larning_experience').datagrid('getRowIndex',row);
												editRow_LearningExperience = index;
												$('#table_larning_experience').datagrid('beginEdit', index);	
										}
											}
								}
							},"-",{
								text: '撤销',
								iconCls: 'icon-redo',
								handler:function(){
									$('#table_larning_experience').datagrid('rejectChanges');
									editRow_LearningExperience = undefined;
									}
								},"-",{
								text: '删除',
								iconCls: 'icon-remove',
								handler: function () {
									if(editRow_LearningExperience == undefined){
										var row = $('#table_larning_experience').datagrid('getSelected');
										if (row) {
										$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
												if (res) {	
													var rowIndex = $('#table_larning_experience').datagrid('getRowIndex', row);
													$('#table_larning_experience').datagrid('deleteRow', rowIndex);
													toolAction.baseAjax("deleteLE",$('#table_larning_experience'),row,"删除成功");
														}	
												});
										}
									}
								}
							},"-",{
								text: '保存',
								iconCls: 'icon-save',
								handler: function () {
								if(editRow_LearningExperience != undefined){
										$('#table_larning_experience').datagrid('endEdit', editRow_LearningExperience);
										editRow_LearningExperience = undefined;
										//$('#table_larning_experience').datagrid('reload');
									}
								}
							}],
					});
});
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
		<%@include file="../../inc/help/teacher/help_LearningExperience.inc" %>
	</div>
	<div data-options="region:'center',title:'学习经历',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_larning_experience"></table>
	</div>
</body>
</html>



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


<title>普通教师-工作经历</title>
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
var editRow_WE = undefined;
$(function(){
	$('#table_working_experience').datagrid({
		url:'/BusinessFiles/getWorkingExperiencelist',
		iconCls:'icon-save',
		//collapsible : true,
		rownumbers:true,
		singleSelect : true,
		//pagination:true,
		//pageSize:1,
		//pageList:[1,2,3],
		//height:'80%',
		fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'id',//翻页操作有效
		//
		columns:[[
		{ field:'ID', checkbox:true,},
{title:'id',field:'id',width:100,align:'left',hidden:true},
{title:'工作单位',field:'company',width:100,align:'center',editor :{ type : 'textbox',options : { required : true }}},
{title:'入职时间',field:'startDate',width:100,align:'center',sortable:true,editor :{ type : 'datebox',options : { required : true,editable:false }}},
{title:'离职时间',field:'endDate',width:100,align:'center',sortable:true,editor :{ type : 'datebox',options : { required : false,editable:false }}},
{title:'职务',field:'role',width:100,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}}
]],
onAfterEdit: function (rowIndex,rowData,changes) 
{
	rowData.teacherId=$('#id').val();
	var inserted =$(this).datagrid('getChanges', "inserted");
	var updated = $(this).datagrid('getChanges', "updated");
	if(inserted.length<1 && updated.length<1){
		editRow_WE = undefined;
		$(this).datagrid('unselectAll');
			return;
		}
		if(inserted.length>0){
			toolAction.baseAjax("addWE",$(this),rowData,"增加成功");
		}else if(updated.length>0){ 
			toolAction.baseAjax("updateWE",$(this),rowData,"修改成功");
		}
		//editRow_LE = undefined;
		//updated.length=0;
},	
	 toolbar: [{
					text: '增加',
					iconCls: 'icon-add',
					handler: function () {
						if (editRow_WE == undefined) {
							$('#table_working_experience').datagrid('unselectAll');
								$('#table_working_experience').datagrid('insertRow', {index :0,row : {}});
								$('#table_working_experience').datagrid('beginEdit', 0);
								editRow_WE = 0;			
							}
					}
				}, "-",{
					text: '修改',
					iconCls: 'icon-edit',
					handler: function () {
						if(editRow_WE == undefined){
							var row = $('#table_working_experience').datagrid('getSelected');
								if(row){
								var index = $('#table_working_experience').datagrid('getRowIndex',row);
									editRow_WE = index;
									$('#table_working_experience').datagrid('beginEdit', index);	
							}
								}
					}
				},"-",{
					text: '撤销',
					iconCls: 'icon-redo',
					handler:function(){
						$('#table_working_experience').datagrid('rejectChanges');
						editRow_WE = undefined;
						}
					},"-",{
					text: '删除',
					iconCls: 'icon-remove',
					handler: function () {
						if(editRow_WE == undefined){
							var row = $('#table_working_experience').datagrid('getSelected');
							if (row) {
							$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
									if (res) {	
										var rowIndex = $('#table_working_experience').datagrid('getRowIndex', row);
										$('#table_working_experience').datagrid('deleteRow', rowIndex);
										toolAction.baseAjax("deleteWE",$('#table_working_experience'),row,"删除成功");
											}	
									});
							}
						}
					}
				},"-",{
					text: '保存',
					iconCls: 'icon-save',
					handler: function () {
						if(editRow_WE != undefined){
							$('#table_working_experience').datagrid('endEdit', editRow_WE);
							editRow_WE = undefined;
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
		<%@include file="../../inc/help/teacher/help_workingExperience.inc" %>
	</div>
	<div data-options="region:'center',title:'工作经历',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_working_experience"></table>
	</div>
	
</body>
</html>
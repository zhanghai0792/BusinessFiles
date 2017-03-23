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

<title>教务管理员-教师业务管理系统</title>
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
	$('#year').numberspinner('setValue', year);  
	var term = $('#year').val()+"-"+$('#term').val();
	$('#table_paper_teaching').datagrid({
		url:'/BusinessFiles/getPaperTeachinglist',
		queryParams: {
			termDate: term
		},
		rownumbers:true,
		pagination:true,
		pageSize:20,
		pageList:[20,40,60],
		toolbar:'#toolbar',
		fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'id',//翻页操作有效
		onSelect:function(){
	  		$('#btn_update').linkbutton('enable');
	  		$('#btn_delete').linkbutton('enable');
	  	},
		columns:[[
			{ field:'ID', checkbox:true,},
			{title:'id',field:'id',width:100,hidden:true,align:'left',},
			{title:'工号',field:'teacherId',width:50,align:'center',},
			{title:'指导教师',field:'teacherName',width:50,align:'center',},
			{title:'职称',field:'professTitle',width:80,align:'center'},
			{title:'学生数',field:'tclassNum',width:100,align:'center',sortable:true},
			{title:'折算课时',field:'discountClassNum',width:100,align:'center',sortable:true}
		]],
	});
	
	//加载教师列表
	$.ajax({
		url : '/BusinessFiles/getTeacherlist',
		type : "post",
		success : function(data) {
			$("#teacherId").combobox({
			'valueField': 'id',
			'textField': 'name',
			'data':data.rows});
		}
	});
	
});


function searchRecord(){
	var term = $('#year').val()+"-"+$('#term').combobox('getValue');
	$("#table_paper_teaching").datagrid('load',{
		termDate: term,
		cond: $('#cond').val()
	});
}

function deleteRecord(){
	var rows = $('#table_paper_teaching').datagrid('getChecked');
	if (rows.length <= 0) {
		alert("请选择要删除的记录");
		return;
	}
	var param={};
	for(var i=0;i<rows.length;i++){
		param[i] =rows[i].id;
	}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
		if (res) {	
			$.ajax({
				url : '/BusinessFiles/deletePaperTeaching',
				type : "post",
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				data : {
					ids:param
					},
				success : function(data) {
					if(data.msg){
					for(var i=rows.length-1; i>=0; i--){
						var index = $('#table_paper_teaching').datagrid('getRowIndex',rows[i]);
						$('#table_paper_teaching').datagrid('deleteRow',index);
					}
						$.messager.show({
							title:'提示',
							timeout:3000,
							msg:data.msg,
							showType:'slide',
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

function saveRecord(){
	var data= new FormData($("#form_edit")[0]);
	var Id=$('#id').val();
	//数据校验
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updatePaperTeaching",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_paper_teaching').datagrid('reload');
					closeDlg();
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
		url : "/BusinessFiles/addPaperTeaching",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_paper_teaching').datagrid('reload');
				closeDlg();
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

 function exportData(){
 	var rows = $('#table_paper_teaching').datagrid('getSelections');
 	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	 window.open("/BusinessFiles/exportPaperTeaching?"+cond); 
 }
 function ImportData(){
 	var file=$('#file_xls').val();
	var fileExt=getfileExt(file);
	var tp ="xls,csv,xlsx,et";
	var rs=tp.indexOf(fileExt);
	if(rs<0){
		$.messager.alert('提示', "请选择excel文件！", 'info');
		return;
	}
	
	var data= new FormData($("#form_import")[0]);
	$.ajax({
		url : "/BusinessFiles/importPaperTeaching",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			$('#dlg_import').dialog('close');
			if(data.success){
				$('#table_paper_teaching').datagrid('reload');
				$.messager.show({
					title:'提示',
					timeout:3000,
					msg:data.msg,
					showType:'slide',
				});				
			}else
				$.messager.show({
					title:'提示',
					timeout:3000,
					msg:data.msg,
					showType:'slide',
				});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 $.messager.alert("提 示", "操作失败");
		}
	});
	
 }
	
	function showImportDlg(){
		$('#year_import').numberspinner('setValue', $('#year').numberspinner('getValue'));  
		$('#term_import').combobox('setValue', $('#term').numberspinner('getValue'));  
		setYearTermImport();
		$('#dlg_import').dialog('open');
	}
	
	function showEditDlg(flag){
		$('#year_edit').numberspinner('setValue', $('#year').numberspinner('getValue'));  
		$('#term_edit').combobox('setValue', $('#term').numberspinner('getValue'));  
		if(flag){
			var row= $('#table_paper_teaching').datagrid('getSelected');
			$('#form_edit').form('load',row);
		}
		setYearTermEdit();
		$('#dlg_edit').dialog('open');
	}
	
 	function setYearTermImport(){
 		var year = $('#year_import').numberspinner('getValue');
 		var term =  $('#term_import').combobox('getValue')
 		$('#year_term_import').val(year+"-"+term);
 	}
	
 	function setYearTermEdit(){
 		var year = $('#year_edit').numberspinner('getValue');
 		var term =  $('#term_edit').combobox('getValue')
 		$('#year_term_edit').val(year+"-"+term);
 	}
 	
	function closeDlg(){
		$('#form_edit').form('clear');
		$('#dlg_edit').dialog('close');
	}
</script> 
</head>
<body class="easyui-layout">
	<div data-options="region:'north',title:'',split:true," style="height:15%;overflow: hidden;">
 		<%@include file="../../inc/header.inc" %> 	
	</div> 
   
    <div data-options="region:'west',title:'功能导航',iconCls:'icon-reload',split:true,border:false" 
    	style="width:10%;background:#B3DFDA;padding-left:5px;">
 		<%@include file="../../inc/menu/TeachingSecretary.inc" %> 	
    </div> 
    <div  data-options="region:'east',title:'帮助',collapsible:true,width:250">
		<%@include file="../../inc/help/TeachingSecretary/help_theory_teaching.inc" %>
	</div>
	<div data-options="region:'center',title:'论文指导',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_paper_teaching">
		
		</table>

		<div id="toolbar" style="padding:5px;height:auto">
		    
		     <input class="easyui-numberspinner" id="year" data-options="min:2010,max:2100,required:true" style="width:60px;"></input>年
			<select class="easyui-combobox" id="term" style="width:70px;">
				<option value="1">上学期</option>
				<option value="2">下学期</option>
			</select>
			<input class="easyui-textbox" style="width:240px;margin-top:5px;" id="cond"  data-options="prompt:'请输入工号/姓名'" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchRecord()" >查找</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-xls" onclick="showImportDlg()">导入</a>
			<a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'," onclick="exportData()">导出</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="showEditDlg(false)" >新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" id="btn_update" iconCls="icon-edit" disabled=true onclick="showEditDlg(true)">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" id="btn_delete" iconCls="icon-remove" disabled=true onclick="deleteRecord()">删除</a>
		</div>
 
		<div id="dlg_import" closed="true" class="easyui-dialog" title="导入论文指导" style="width:300px;padding:10px">
			<form id="form_import" method="post" enctype="multipart/form-data">
				<input type="hidden"  id="year_term_import" name="termDate"/>
				<table>
					<tr>
						<td>学期</td>
						<td>
							<input class="easyui-numberspinner" id="year_import" data-options="onSpinUp:setYearTermImport,onSpinDown:setYearTermImport,min:2010,max:2100,required:true" style="width:80px;">
							<select class="easyui-combobox" id="term_import" data-options="onSelect:setYearTermImport" style="width:70px;">
								<option value="1">上学期</option>
								<option value="2">下学期</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Excel文件</td>
						<td><input class="easyui-filebox" id="file_xls" name="file" style="width:200px" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /></td>
					</tr>
				</table>
				
				<div align="center">
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="ImportData();">导入</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-down'" href="template/PaperTeaching.xls">下载模板</a>				
				</div>	     
			</form>
		</div>
 
	   <div id="dlg_edit" closed="true" class="easyui-dialog" title="论文指导" style="width:300px;height:auto;padding:5px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"  data-options="iconCls: 'icon-save',closable:false">
	   		<form  id="form_edit" method="post"  >
				<input type="hidden"  id="id" name="id"/>
				<input type="hidden"  id="year_term_edit" name="termDate"/>
				<table cellpadding="3" >
					<tr>
						<td >学期</td>
						<td>
							<input class="easyui-numberspinner" id="year_edit" data-options="onSpinUp:setYearTermEdit,onSpinDown:setYearTermEdit,min:2010,max:2100,required:true" style="width:80px;">
							<select class="easyui-combobox" id="term_edit" data-options="onSelect:setYearTermEdit" style="width:70px;">
								<option value="1">上学期</option>
								<option value="2">下学期</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>指导教师:</td>
						<td ><select id="teacherId" name="teacherId" class="easyui-combobox" style="width:90px;"  />
					</tr>
					<tr>
						<td>学生人数:</td>
						<td ><input class="easyui-numberbox" data-options="min:0" style="width:143px;" type="text" id="tclassNum" name="tclassNum"  /></td>
					</tr>
					<tr>
						<td>折算学时</td>
						<td><input class="easyui-numberbox" data-options="precision:1,min:0" style="width:141px;" type="text" id="discountClassNum" name="discountClassNum"  /></td>
					</tr>
				</table>
				<div align="center">
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveRecord();">保 存</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeDlg();">取 消</a>
				</div>	
			</form>    
		</div>
	</div>
</body>
</html>

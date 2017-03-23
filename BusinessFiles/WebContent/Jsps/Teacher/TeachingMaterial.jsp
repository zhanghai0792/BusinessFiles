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

<title>普通教师-教材使用</title>
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
var editRow_TeachingMaterial = undefined;
$(function(){
	var year = new Date().getYear()+1900;
	$('#termYear').numberspinner('setValue', year);
	var term = $('#termYear').val()+"-"+$('#term').val();	
	
	$('#tb_teaching_material').datagrid({
		url:'/BusinessFiles/getTeachingMateriallist?tid=${session.loginteacher.id}',		
		queryParams: {
			termDate: term
		},
		iconCls:'icon-save',
		//collapsible : true,
		rownumbers:true,
		singleSelect : true,
		pagination:true,
		pageSize:20,
		pageList:[20,40,60],
		toolbar:'#tb_TeachingMaterial',
		fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'id',//翻页操作有效
		onSelect:function(){
	  		$('#updateTTM').linkbutton('enable');
	  		$('#deleteTTM').linkbutton('enable');
	  },
		columns:[[
		{ field:'ID', checkbox:true,},
{title:'id',field:'id',width:100,align:'left',hidden:true},
{title:'工号',field:'teacherId',width:50,align:'center',},
{title:'授课教师',field:'teacherName',width:100,align:'center',},
{title:'课程名称',field:'courseName',width:100,align:'center',},
{title:'教材名称',field:'guideBook',width:100,align:'center',},
{title:'作者',field:'author',width:100,align:'center',},
{title:'出版社(全名)',field:'publishing',width:100,align:'center',},
{title:'出版时间',field:'publishingDate',width:100,align:'center',sortable:true,},
{title:'版次',field:'editionNum',width:100,align:'center',sortable:true,},
{title:'书号(ISBN)',field:'bookNum',width:100,align:'center',sortable:true,},
{title:'教材获奖情况',field:'bookPrize',width:100,align:'center',sortable:true,},
{title:'专业/年级/班级',field:'tclass',width:150,align:'center',sortable:true,},
{title:'教研室',field:'dept',width:100,align:'center',sortable:true,}
]],


		});
});
function addTTM(){
	$('#teacherIdTTM').val($('#tid').val());
	$('#termYear_edit').numberspinner('setValue', $('#termYear').numberspinner('getValue'));  
	$('#term_edit').combobox('setValue', $('#term').combobox('getValue'));  
	changeTerm();
	
	$('#dlg_TTM').dialog('open');
}
function updateTTM(){
	var row= $('#tb_teaching_material').datagrid('getSelected');
	$('#form_TTM').form('load',row);
	
	var s = row.termDate.split("-");
	$('#termYear_edit').numberspinner('setValue',Number(s[0]));	
	$('#term_edit').combobox('setValue',s[1]);
	changeTerm();
	$('#dlg_TTM').dialog('open');
}

function deleteTTM(){
	if(editRow_TeachingMaterial == undefined){
		var row = $('#tb_teaching_material').datagrid('getSelected');
		if (row) {
		$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
				if (res) {	
					var rowIndex = $('#tb_teaching_material').datagrid('getRowIndex', row);
					$('#tb_teaching_material').datagrid('deleteRow', rowIndex);
					toolAction.baseAjax("deleteTeachingMaterial",$('#tb_teaching_material'),row,"删除成功");
						}	
				});
		}
	}
}

function saveTTM(){
	var data= new FormData($("#form_TTM")[0]);
	var Id=$('#idTTM').val();
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateTeachingMaterial",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#tb_teaching_material').datagrid('reload');
					closeTTM();
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
		url : "/BusinessFiles/addTeachingMaterial",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#tb_teaching_material').datagrid('reload');
				closeTTM();
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
function closeTTM(){
	$('#form_TTM').form('clear');
	$('#dlg_TTM').dialog('close');
}
function search(){
	var term = $('#termYear').val()+"-"+$('#term').combobox('getValue');
	$("#tb_teaching_material").datagrid('load',{
		termDate:term
	});
}

function changeTerm(){
	var term = $('#termYear_edit').val()+"-"+$('#term_edit').combobox('getValue');
	$("#fullTerm").textbox('setValue',term);
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
		<%@include file="../../inc/help/teacher/help_teaching_material.inc" %>
	</div>
	<div data-options="region:'center',title:'教材使用',collapsible:false,border:false" style="overflow:hidden;">
		
<table id="tb_teaching_material"></table>


<div id="tb_TeachingMaterial" style="padding:5px;height:auto">
    <input class="easyui-numberspinner" id="termYear" data-options="min:2010,max:2100,required:true,onSpinUp:search,onSpinDown:search" style="width:80px;"></input>年
	<select class="easyui-combobox" id="term" style="width:90px;" data-options="onSelect:search">
		<option value="1">上学期</option>
		<option value="2">下学期</option>
	</select>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTTM()" >新增</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTTM" disabled=true onclick="updateTTM()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTTM" disabled=true onclick="deleteTTM()">删除</a>
 
 </div>
<div id="dlg_TTM" closed="true" class="easyui-dialog" title="教材使用" style="width:450px;height:auto;padding:5px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">		
       <form  id="form_TTM" method="post" >
			<table cellspacing="6" cellpadding="3" >
				<tr hidden="true">
					<td><input class="easyui-textbox" id="fullTerm" name="termDate"/><input type="text" id="idTTM" name="id"/><input  type="text"  value="${session.loginteacher.id}" name="teacherId" id="teacherIdTTM" /></td>
				</tr>
				<tr>
					<td>年份:</td>
					<td><input class="easyui-numberspinner" id="termYear_edit" data-options="min:2010,max:2100,required:true,onSpinUp:changeTerm,onSpinDown:changeTerm" style="width:80px;"></input></td>
				</tr>
				<tr>
					<td>学期:</td>
					<td>
						<select id="term_edit" class="easyui-combobox" style="width:90px;"  data-options="onSelect:changeTerm">
							<option value="1">上学期</option>
							<option value="2">下学期</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>课程名称:</td>
					<td><input style="width:240px;" class="easyui-textbox" id="courseName" name="courseName" /></td>
				</tr>
				<tr>
					<td>教研室:</td>
					<td><input class="easyui-combobox" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_dept.json'" style="width:130px;" type="text" id="deptTTM" name="dept"  /></td>
				</tr>
				<tr>
					<td>专业/年级/班级:</td>
					<td><input style="width:240px;"  class="easyui-textbox" id="tclass" name="tclass" /></td>
				</tr>
				<tr>
					<td>教材名称:</td>
					<td><input  style="width:240px;" class="easyui-textbox" id="guideBook" name="guideBook" /></td>
				</tr>
				<tr>
					<td>作者:</td>
					<td><input style="width:130px;" class="easyui-textbox" id="author" name="author" /></td>
				</tr>
				<tr>
					<td>出版社:</td>
					<td><input style="width:130px;" class="easyui-textbox" id="publishing" name="publishing" /></td>
				</tr>
				<tr>
					<td>教材获奖情况:</td>
					<td><input style="width:240px;" class="easyui-textbox" id="bookPrize" name="bookPrize" /></td>
				</tr>
				<tr>
					<td>书号(ISBN):</td>
					<td><input  style="width:130px;" class="easyui-textbox" id="bookNum" name="bookNum"  /></td>
				</tr>
				<tr>
					<td>出版时间:</td>
					<td><input class="easyui-datebox" style="width:130px;" id="publishingDate" name="publishingDate" /></td>
				</tr>
				<tr>
					<td>版次:</td>
					<td><input style="width:130px;" class="easyui-textbox" id="editionNum" name="editionNum" /></td>
				</tr>						
			</table>
			<div align="center">
				<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTTM();">保 存</a>
				<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTTM();">取 消</a>
			</div>
         </form>
  </div>
	</div>
	
</body>
</html>
 
 

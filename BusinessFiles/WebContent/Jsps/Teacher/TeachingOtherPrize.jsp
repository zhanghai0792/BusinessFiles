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

<title>普通教师-其它获奖</title>
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
	$('#table_teaching_otherPrize').datagrid({
		url:'/BusinessFiles/getTeachingOtherPrizelist?tid=${session.loginteacher.id}',
		 queryParams: {
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		  },
		iconCls:'icon-save',
		rownumbers:true,
		toolbar:'#tb_TeachingOtherPrize',
		fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'id',//翻页操作有效
		onSelect:function(){
	  		$('#updateTTO').linkbutton('enable');
	  		$('#deleteTTO').linkbutton('enable');
	  },
		columns:[[
    { field:'ID', checkbox:true,},
    {title:'id',field:'id',width:100,hidden:true},
    {title:'奖项',field:'prize',width:100,align:'center',},
  	{title:'奖励级别',field:'level',width:100,align:'center',sortable:true},
  	{title:'等次',field:'torder',width:100,align:'center',sortable:true},
  	{title:'时间',field:'time',width:100,align:'center',sortable:true},
  	{title:'分值',field:'score',width:100,align:'center',sortable:true},
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
	window.open("Jsps/viewImg.jsp?dir=TeachingOtherPrize/"+id+"&names="+names+"&files="+files);
}

function addTTO(){
	closeTTO();
	$('#teacherIdTTO').val($('#tid').val());
	$('#dlg_TTO').dialog('open');
}
function updateTTO(){
	var rows= $('#table_teaching_otherPrize').datagrid('getSelections');
	if(rows.length==1){
	$('#form_TTO').form('load',rows[0]);
	$('#dlg_TTO').dialog('open');
	}
}
function deleteTTO(){
	var rows = $('#table_teaching_otherPrize').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
				$.ajax({
					url : "/BusinessFiles/deleteTeachingOtherPrize",
					type : "post",
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					data : {
						ids:param
						},
					success : function(data) {
						if(data.success){
							for(var i = rows.length - 1; i >= 0; i--){
								var index = $('#table_teaching_otherPrize').datagrid('getRowIndex',rows[i]);
								$('#table_teaching_otherPrize').datagrid('deleteRow',index);
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
}
function saveTTO(){
	var Id=$('#idTTO').val();
	var data= new FormData($("#form_TTO")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateTeachingOtherPrize",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_teaching_otherPrize').datagrid('reload');
					closeTTO();
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
		url : "/BusinessFiles/addTeachingOtherPrize",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_teaching_otherPrize').datagrid('reload');
				closeTTO();
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
function closeTTO(){
	$('#form_TTO').form('clear');
	$('#dlg_TTO').dialog('close');
}
function searchTTO(){
	$("#table_teaching_otherPrize").datagrid('load',{
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
		<%@include file="../../inc/help/teacher/help_OtherPrize.inc" %>
	</div>
	<div data-options="region:'center',title:'其它获奖',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_teaching_otherPrize"></table>
		<div id="tb_TeachingOtherPrize" style="padding:5px;height:auto">
	    	<label >时间:</label> 
	    	<input class="easyui-datebox" style="width:100px;" id="date_start" name="date_start" data-options="editable:false,"/>至
			<input class="easyui-datebox" style="width:100px;" id="date_end" name="date_end" data-options="editable:false,"/>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTTO()" >查找</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTTO()" >新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTTO" disabled=true onclick="updateTTO();">修改</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTTO" disabled=true onclick="deleteTTO()">删除</a>
		</div>
 
 		<div id="dlg_TTO" closed="true" class="easyui-dialog" title="其他获奖" style="width:450px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
	       <form  id="form_TTO" method="post" enctype="multipart/form-data" > 
				<table cellspacing="6" cellpadding="3" >
					<tr hidden="true">
						<td><label for="idTTO"></label><input type="text" id="idTTO" name="id"/></td>
						<td><input  type="text"  value="${session.loginteacher.id}" name="teacherId" id="teacherIdTTO" /></td>
					</tr>
					<tr hidden="true">
							<td><input type="text"  name="status"/></td>
					</tr>
					<tr>
						<td>获奖时间:<input class="easyui-datebox" style="width:150px;" id="timeTTO" name="time" /></td>
					</tr>
					<tr>
						<td>获奖奖项:<input style="width:240px;" class="easyui-textbox" id="prize" name="prize" /></td>
					</tr>
					<tr>
						<td >奖励级别:<input class="easyui-combobox" style="width:150px;" name="level" data-options="editable:false,panelHeight:'auto',valueField: 'name',textField: 'name',url:'jsondata/OtherAchievementLevel.json'"/></td>
					</tr>
					<tr>
						<td>奖励等次:<input class="easyui-combobox" style="width:150px;" name="torder" data-options="editable:false,panelHeight:'auto',valueField: 'name',textField: 'name',url:'jsondata/OtherAchievementOrder.json'" /></td>
					</tr>
					<tr>
						<td>折算分值:<input class="easyui-numberbox" style="width:150px;" id="score" name="score"  /></td>
					</tr>
					<tr>
						<td>上传附件:<input id="imgFile_TTO" name="file"  multiple style="width:300px;" class="easyui-filebox" accept="image/*"></td>
					</tr>
				</table>
				
				<div align="center">
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTTO();">保 存</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTTO();">取 消</a>
				</div>
	         </form>
  		</div>
	</div>
</body>
</html>


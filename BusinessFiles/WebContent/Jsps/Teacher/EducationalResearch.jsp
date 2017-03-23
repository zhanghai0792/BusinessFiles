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


<title>普通教师-教改课题</title>
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
	
	$('#table_educational_research').datagrid({
		 url:'/BusinessFiles/getEducationalResearchlist?tid=${session.loginteacher.id}',
		 queryParams: {
			condDate:$('#condDateTER').combobox('getValue'),
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		  },
			iconCls:'icon-save',
			rownumbers:true,
			toolbar:'#tb_EducationalResearch',
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTER').linkbutton('enable');
		  		$('#deleteTER').linkbutton('enable');
		  },
			
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,hidden:true},
	{title:'课题名称',field:'topic',width:240},
	{title:'参与者(包括负责人)',field:'author',width:180,formatter:authorFormatter},
	{title:'立项编号',field:'topicNum',width:100,align:'center',sortable:true},
	{title:'立项时间',field:'startDate',width:70,align:'center',sortable:true},
	{title:'结题时间',field:'endDate',width:70,align:'center',sortable:true},
	{title:'项目类别',field:'approver',width:100,align:'center',sortable:true},
	{title:'课题经费（元）',field:'funds',width:100,align:'center',sortable:true},
	{title:'分值',field:'score',width:50,align:'center',sortable:true},
	{title:'状态',field:'status',width:50,align:'center',sortable:true,formatter:iconFormatter,},
	{title:'附件',field:'detail',width:60,align:'center',formatter:format_detail}
			]]
		});
});
function format_detail(value,row){
	var names = "申报书,立项证书,结题证书,结题材料";
	var files = row.sfbook+","+row.projectCertificat+","+row.knotCertificat+","+row.fileName;
	var str = "<a onclick='getDetail(\""+row.id+"\",\""+names+"\",\""+files+"\")'  href='javascript:void(0);'>查看</a>";
	return str;
}

function getDetail(id,names,files){
	window.open("Jsps/viewImg.jsp?dir=EducationalResearch/"+id+"&names="+names+"&files="+files);
}

function authorFormatter(ss){
	if(ss!=""){
		var s=new Array();
		var str="";
		s=ss.split(",");
		console.info(s);
		for(var i=0;i<s.length;i++){
			var start=s[i].indexOf('[');
			var name=s[i].substring(0,start);
			str+=name+","
		}
		return str.substring(0, str.length-1);
	}
}

function updateTER(){
	var rows= $('#table_educational_research').datagrid('getSelections');
	if(rows.length<1){
		alert("请选择待修改的记录");
		return;
	}
	if(rows.length>1){
		alert("只能修改一条记录");
		return;
	}
	if(rows[0].status == "1"){
		alert("已经审核通过的不能再修改了");
		return;
	}
	$('#form_TER').form('load',rows[0]);
	$('#dlg_TER').dialog('open');
}
function addTER(){
	closeTER();
	$('#dlg_TER').dialog('open');
}
function closeTER(){
	$('#form_TER').form('clear');
	$('#dlg_TER').dialog('close');
}
function saveTER(){
	var Id=$('#idTER').val();
	var data= new FormData($("#form_TER")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateEducationalResearch",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_educational_research').datagrid('reload');
					closeTER();
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
		url : "/BusinessFiles/addEducationalResearch",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_educational_research').datagrid('reload');
				closeTER();
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

function searchTER(){
	$("#table_educational_research").datagrid('load',{
		condDate:$('#condDateTER').combobox('getValue'),
		DateStart:$('#date_start').datebox('getValue'),
		DateEnd:$('#date_end').datebox('getValue')
	});
}
function deleteTER(){
	var rows = $('#table_educational_research').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
				$.ajax({
					url : "/BusinessFiles/deleteEducationalResearch",
					type : "post",
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					data : {
						ids:param
						},
					success : function(data) {
						if(data.success){
							for(var i = rows.length - 1; i >= 0; i--){
								var index = $('#table_educational_research').datagrid('getRowIndex',rows[i]);
								$('#table_educational_research').datagrid('deleteRow',index);
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


function exportRecords(){
	var rows = $('#table_educational_research').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的记录");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportEducationalResearch?"+cond);

}

</script> 
</head>
<body class="easyui-layout">
	<div data-options="region:'north',title:'',split:true," style="height:15%;overflow: hidden;">
 		<%@include file="../../inc/header.inc" %> 	
	</div> 
   
    <div data-options="region:'west',title:'功能导航',iconCls:'icon-reload',split:true,border:false" 
    	style="width:10%;background:#B3DFDA;padding-left:5px;">
 		<%@include file="../../inc/menu/teacher.inc" %> 	
    </div> 
    <div  data-options="region:'east',title:'帮助',collapsible:true,width:250">
		<%@include file="../../inc/help/teacher/help_EducationalResearch.inc" %>
	</div>
	<div data-options="region:'center',title:'教改课题',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_educational_research"></table>
		<div id="tb_EducationalResearch" style="padding:5px;height:auto">
		    <select id="condDateTER" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'," style="width:80px;">
				<option value="startDate">立项时间</option>
				<option value="endDate">结题时间</option>
			</select>
			<input class="easyui-datebox" style="width:100px;" id="date_start" data-options="editable:false,"/>至
			<input class="easyui-datebox" style="width:100px;" id="date_end" data-options="editable:false,"/>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTER()" >查找</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTER()" >新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTER" disabled=true onclick="updateTER()">修改</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTER" disabled=true onclick="deleteTER()">删除</a>
	   		<a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
	 	</div>
 
 		<div id="dlg_TER" closed="true" class="easyui-dialog" title="教改课题" style="width:450px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
	       <form  id="form_TER" method="post" enctype="multipart/form-data" >
				<table cellpadding="3" >
					<tr hidden="true">
						<td><input type="text" id="idTER" name="id"/><input type="text"  name="status"/></td>
					</tr>
					<tr>
						<td>课题类型:</td>
						<td><input class="easyui-combobox" name="approver" data-options="valueField: 'name',textField: 'name',editable:false,url:'jsondata/EducationResearch_approver.json'"style="width:200px;" /></td>
					</tr>
					<tr>
						<td>课题名称:</td>
						<td><input  class="easyui-textbox" style="width:240px;" name="topic" /></td>
					</tr>
					<tr>
						<td >立项编号:</td>
						<td><input  style="width:130px;" class="easyui-textbox"  name="topicNum" /></td>
					</tr>
					<tr>
						<td>课题经费:</td>
						<td><input class="easyui-numberbox" style="width:130px;" name="funds" />元</td>
					</tr>
					<tr>
						<td >立项时间:</td>
						<td><input class="easyui-datebox" style="width:130px;" data-options="editable:false" name="startDate" /></td>
					</tr>
					<tr>
						<td>结题时间:</td>
						<td><input class="easyui-datebox" style="width:130px;" data-options="editable:false" name="endDate" /></td>
					</tr>
					<tr>
						<td>折算分值:</td>
						<td><input class="easyui-numberbox" style="width:130px;"  name="score" /></td>
					</tr>
					<tr>
						<td>参与者:</td>
						<td><input  class="easyui-textbox" style="width:240px;" data-options="prompt:'单击右边的编辑按钮编辑参与者'" id="author" name="author"  /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="showAuthorDlg()" >编辑</a></td>
					</tr>
					<tr>
						<td>申报书:</td>
						<td><input  name="sf"  multiple   style="width:300px;" class="easyui-filebox" ></td>
					</tr>
					<tr>
						<td>立项证书:</td>
						<td><input   name="pc"  multiple  style="width:300px;" class="easyui-filebox" accept="image/*"></td>
					</tr>
					<tr>
						<td>结题证书:</td>
						<td><input  name="kc"  multiple  style="width:300px;" class="easyui-filebox" accept="image/*"></td>
					</tr>
					<tr>
						<td>结题材料:</td>
						<td><input  name="file"  multiple  style="width:300px;" class="easyui-filebox" ></td>
					</tr>
				</table>
				<div align="center">
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTER();">保 存</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTER();">取 消</a>
				</div>
	         </form>
  		</div>  
  		<%@include file="../../inc/dialog/dialog_author.inc" %>
	</div>	
</body>
</html>
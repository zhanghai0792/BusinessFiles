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


<title>普通教师-科研课题</title>
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
	
	$('#table_scientific_research').datagrid({
		 url:'/BusinessFiles/getScientificResearchlist?tid=${session.loginteacher.id}',
		 queryParams: {
			condDate:$('#condDateTSR').combobox('getValue'),
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		  },
			iconCls:'icon-save',
			rownumbers:true,
			singleSelect : true,
			pagination:true,
			pageSize:10,
			pageList:[10,20,30],
			toolbar:'#tb_ScientificResearch',
			pagination:true,
			fit:true,
			fitColumns:false,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTSR').linkbutton('enable');
		  		$('#deleteTSR').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
			{title:'id',field:'id',width:10,align:'left',hidden:true},
			{title:'参与者(包括负责人)',field:'author',width:250,formatter:authorFormatter},
			{title:'课题名称',field:'topic',width:200,},
			{title:'立项编号',field:'topicNum',width:100,align:'left',sortable:true},
			{title:'立项时间',field:'startDate',width:100,align:'left',sortable:true},
			{title:'结题时间',field:'endDate',width:100,align:'left',sortable:true},
			{title:'项目类型',field:'approver',width:100,align:'left',sortable:true},
			{title:'项目性质',field:'projectType',width:100,align:'center',sortable:true},
			{title:'课题经费（元）',field:'funds',width:80,align:'left',sortable:true},
			{title:'分值',field:'score',width:50,align:'left',sortable:true},
			{title:'状态',field:'status',width:50,align:'center',sortable:true,formatter:iconFormatter},
			{title:'附件',field:'detail',width:100,align:'center',formatter:format_detail}
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
	window.open("Jsps/viewImg.jsp?dir=ScientificResearch/"+id+"&names="+names+"&files="+files);
}
function addTSR(){
	closeTSR();
	$('#form_TSR').form('clear');
	$('#dlg_TSR').dialog('open');
}
function updateTSR(){
	var rows= $('#table_scientific_research').datagrid('getSelections');
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
	$('#form_TSR').form('load',rows[0]);
	$('#dlg_TSR').dialog('open');
}
function closeTSR(){
	$('#dlg_TSR').dialog('close');
}

function saveTSR(){
	var Id=$('#idTSR').val();
	var data= new FormData($("#form_TSR")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateScientificResearch",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_scientific_research').datagrid('reload');
					closeTSR();
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
		url : "/BusinessFiles/addScientificResearch",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_scientific_research').datagrid('reload');
				closeTSR();
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
function deleteTSR(){
	var rows = $('#table_scientific_research').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
				$.ajax({
					url : "/BusinessFiles/deleteScientificResearch",
					type : "post",
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					data : {
						ids:param
						},
					success : function(data) {
						if(data.success){
							for(var i = rows.length - 1; i >= 0; i--){
								var index = $('#table_scientific_research').datagrid('getRowIndex',rows[i]);
								$('#table_scientific_research').datagrid('deleteRow',index);
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
function searchTSR(){
	$('#table_scientific_research').datagrid('load',{
		condDate:$('#condDateTSR').combobox('getValue'),
		DateStart:$('#date_start').datebox('getValue'),
		DateEnd:$('#date_end').datebox('getValue')
	});
}

function addTSRauthor(){
	$('#dlg_author').dialog('open');
	
	var ss=$('#author_TSR').val();
	if(ss!=""){
		var s=new Array();
		s=ss.split(",");
		console.info(s);
		for(var i=0;i<s.length;i++){
			var start=s[i].indexOf('['); var end=s[i].indexOf(']');
			console.info(start+","+end);
			var name=s[i].substring(0,start);
			var id=s[i].substring(start+1,end); 
			console.info(name+","+id);
			$("#table_author").datagrid('appendRow',{id:id,name:name});
		}
	}
}
function saveAuthor(){
	var rows=$("#table_author").datagrid('getRows');
	if(rows.length<1){
		return;
	}
	var ss="";
		for(var i=0;i<=rows.length-1;i++){
			ss+=rows[i].name+"["+rows[i].id+"]"+",";
		}
		ss=ss.substring(0,ss.length-1);
	$('#author_TSR').textbox('setValue',ss);
	$('#dlg_author').dialog('close');clearTSRauthor();
}
function clearTSRauthor(){
	var item = $('#table_author').datagrid('getRows');    
    for (var i = item.length - 1; i >= 0; i--) {    
        var index = $('#table_author').datagrid('getRowIndex', item[i]);    
        $('#table_author').datagrid('deleteRow', index);    
    }
}

function changeType(rec){
	var type=rec.id;
	var url = 'jsondata/ScientificResearch0.json';    
			
	if(type=="1"){
		url = 'jsondata/ScientificResearch1.json';
	}
	$('#approver_SR').combobox('reload',url);  
	
}
function exportRecords(){
	var rows = $('#table_scientific_research').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的证书");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportScientificResearch?"+cond);

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
		<%@include file="../../inc/help/teacher/help_ScientificResearch.inc" %>
	</div>
	<div data-options="region:'center',title:'科研课题',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_scientific_research"></table>
<div id="tb_ScientificResearch" style="padding:5px;height:auto">
    <form  method="post" >
   		<label style="margin-left:10px;">时间查询:</label> 
   		<select id="condDateTSR" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'," style="width:80px;">
			<option value="startDate">立项时间</option>
			<option value="endDate">结题时间</option>
		</select>
		<input class="easyui-datebox" style="width:90px;" id="date_start" data-options="editable:false,"/>至
		<input class="easyui-datebox" style="width:90px;" id="date_end" data-options="editable:false,"/>
					
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTSR()" >查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTSR()" >新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTSR" disabled=true onclick="updateTSR()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTSR" disabled=true onclick="deleteTSR()">删除</a>
  <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
   	</form>
 </div>
 
 <div id="dlg_TSR" closed="true" class="easyui-dialog" title="科研课题" style="width:450px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
       <form  id="form_TSR" method="post" enctype="multipart/form-data" > 
			<table cellpadding="3" >
				<tr hidden="true">
					<td><input type="text" id="idTSR" name="id"/><input type="text"  name="status"/></td>
				</tr>
				<tr>
					<td>项目性质:</td>
					<td><input id="projectType_SR" name="projectType" class="easyui-combobox" style="width:140px;" 	data-options="valueField: 'name',textField: 'name',panelHeight:'auto',url:'jsondata/ScientificResearch_projectType.json',	onSelect: changeType"/></td>
				</tr>
				<tr>
					<td>项目类型:</td>
					<td><input class="easyui-combobox" name="approver" id="approver_SR" style="width:150px;" data-options="valueField: 'name',textField: 'name',editable:false,panelHeight:'auto',	url:'jsondata/ScientificResearch0.json'" /></td>
				</tr>
				<tr>
					<td>课题名称:</td>
					<td><input class="easyui-textbox" style="width:240px;" type="text"  name="topic" /></td>
				</tr>
				<tr>
					<td>立项编号:</td>
					<td><input class="easyui-textbox"  style="width:130px;" type="text"  name="topicNum" /><br/></td>
				</tr>
				<tr>
					<td>立项时间:</td>
					<td><input class="easyui-datebox" style="width:130px;" name="startDate" /></td>
				</tr>
				<tr>
					<td>结题时间:</td>
					<td><input class="easyui-datebox" style="width:130px;" name="endDate" /></td>
				</tr>
				<tr>
					<td>课题经费:</td>
					<td><input class="easyui-numberbox" style="width:130px;" name="funds" />元</td>
				</tr>
				<tr>
					<td>折算分值:</td>
					<td><input class="easyui-numberbox" style="width:130px;" name="score" /></td>
				</tr>
				<tr>
					<td>参与者:</td>
					<td><input class="easyui-textbox" style="width:240px;"  data-options="prompt:'单击右边的编辑按钮编辑参与者'" id="author_TSR" name="author"  /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="addTSRauthor()" >编辑</a></td>
				</tr>						
				<tr>
					<td>申报书:</td>
					<td><input name="sf" multiple class="easyui-filebox" style="width:300px;"></td>
				</tr>						
				<tr>
					<td>立项证书:</td>
					<td><input name="pc" multiple class="easyui-filebox" style="width:300px;" accept="image/*" ></td>
				</tr>						
				<tr>
					<td>结题证书:</td>
					<td><input name="kc" multiple class="easyui-filebox" style="width:300px;" accept="image/*" ></td>
				</tr>						
				<tr>
					<td>结题材料:</td>
					<td><input name="file" multiple class="easyui-filebox" style="width:300px;" ></td>
				</tr>
			</table>
			<div align="center">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTSR();">保 存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTSR();">取 消</a>
			</div>
         </form>
  	</div>
  
  	<%@include file="../../inc/dialog/dialog_author.inc" %>
	</div>
	
</body>
</html>

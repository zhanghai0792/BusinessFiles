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


<title>普通教师-专利</title>
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
	$('#table_patent').datagrid({
		 url:'/BusinessFiles/getPatentlist',
		 queryParams: {
			 dateStart:$('#date_start').datebox('getValue'),
			 dateEnd:$('#date_end').datebox('getValue'),
			status:$('#cmb_status').combobox('getValue')
		  },
			rownumbers:true,
			pagination:true,
			toolbar:'#tb_patent',
			pageSize:10,
			pageList:[10,20,30],
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTP').linkbutton('enable');
		  		$('#deleteTP').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'所有者',field:'author',width:100,align:'center',formatter:authorFormatter},
	{title:'专利号',field:'patentNum',width:100,align:'left'},
	{title:'授权时间',field:'time',width:100,align:'center',sortable:true},
	{title:'类型',field:'type',width:100,align:'center',sortable:true},
	{title:'转化情况',field:'transform',width:250,align:'center',sortable:true},
	{title:'状态',field:'status',width:50,align:'center',sortable:true,formatter:iconFormatter,},
	{title:'附件',field:'detail',width:100,align:'center',formatter:format_detail}
			]]
		});
});
function format_detail(value,row){
	var names = "证书复印件,转化鉴定";
	var files = row.certName+","+row.transName;
	var str = "<a onclick='getDetail(\""+row.id+"\",\""+names+"\",\""+files+"\")'  href='javascript:void(0);'>查看</a>";
	return str;
}

function getDetail(id,names,files){
	window.open("Jsps/viewImg.jsp?dir=Patent/"+id+"&names="+names+"&files="+files);
}

function DeleteRecords(){
	var rows = $('#table_patent').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
				$.ajax({
					url : "/BusinessFiles/deletePatent",
					type : "post",
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					data : {
						ids:param
						},
					success : function(data) {
						if(data.success){
							for(var i = rows.length - 1; i >= 0; i--){
								var index = $('#table_patent').datagrid('getRowIndex',rows[i]);
								$('#table_patent').datagrid('deleteRow',index);
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
function Search(){
	$('#table_patent').datagrid('load',{
		dateStart:$('#date_start').datebox('getValue'),
		dateEnd:$('#date_end').datebox('getValue'),
		status:$('#cmb_status').datebox('getValue'),
	});
}
function CheckRecords(){
	var rows= $('#table_patent').datagrid('getSelections');
	if(rows.length<=0){
		return;
	}
	var param={};
	for(var i=0;i<rows.length;i++){
		param[i] =rows[i].id;
	}
	$.ajax({
		url : "/BusinessFiles/reviewPatent",
		type : "post",
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		dataType : 'json',
		data :{ids:param},
		success : function(data) {
			if(data.success){
				$('#table_patent').datagrid('reload');
				$.messager.show({
					title:'提示',
					timeout:3000,
					msg:data.msg,
					showType:'slide',
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 $.messager.alert("提示", "操作错误");
		}
	});
}
function exportRecords(){
	var rows = $('#table_patent').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的记录");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportPatent?"+cond);

}
</script> 
</head>
<body class="easyui-layout">
	<div data-options="region:'north',title:'',split:true," style="height:15%;overflow: hidden;">
 		<%@include file="../../inc/header.inc" %> 	
	</div> 
   
    <div data-options="region:'west',title:'功能导航',iconCls:'icon-reload',split:true,border:false" 
    	style="width:12%;background:#B3DFDA;padding-left:5px;">
 		<%@include file="../../inc/menu/AcademicSecretary.inc" %> 	
    </div> 
    <div  data-options="region:'east',title:'帮助',collapsible:true,width:250">
		<%@include file="../../inc/help/AcademicSecretary/help_Patent.inc" %>
	</div>
	<div data-options="region:'center',title:'专利',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_patent"></table>
 		<div id="tb_patent" style="padding:5px;height:auto">   
	          授权时间   
			<input class="easyui-datebox" style="width:100px;" id="date_start" data-options="editable:false,"/>至
			<input class="easyui-datebox" style="width:100px;" id="date_end" data-options="editable:false,"/>
			&emsp;状态
		       <select class="easyui-combobox" id="cmb_status">
			       	<option value="-1">全部</option>
			       	<option value="0">待审核</option>
			       	<option value="1">审核通过</option>
		       </select>
		       <a class="easyui-linkbutton" iconCls="icon-search" onclick="Search()" >查找</a>
		       <a class="easyui-linkbutton" iconCls="icon-remove" onclick="DeleteRecords()">删除</a>
		       <a class="easyui-linkbutton" iconCls="icon-ok" onclick="CheckRecords()">审核通过</a>
  <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
 		</div>
 
 
	</div>
	
</body>
</html>



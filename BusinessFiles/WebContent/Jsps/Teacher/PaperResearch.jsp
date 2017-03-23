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


<title>普通教师-学术论文</title>
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
	$('#table_paper_research').datagrid({
		 url:'/BusinessFiles/getPaperResearchlist?tid=${session.loginteacher.id}',
		 queryParams: {
			date_start:$('#date_start').datebox('getValue'),
			date_end:$('#date_end').datebox('getValue')
		  },
			iconCls:'icon-save',
			rownumbers:true,
			toolbar:'#tb_PaperResearch',
			pagination:true,
			pageSize:10,
			pageList:[10,20,30],
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTPR').linkbutton('enable');
		  		$('#deleteTPR').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'作者',field:'author',width:250,align:'center',formatter:authorFormatter},
	{title:'论文篇名',field:'paperTitle',width:200,align:'center'},
	
	{title:'fmName',field:'fmName',width:100,align:'left',hidden:true},
	{title:'bqyName',field:'bqyName',width:100,align:'left',hidden:true},
	{title:'mlName',field:'mlName',width:100,align:'left',hidden:true},
	{title:'zwName',field:'zwName',width:100,align:'left',hidden:true},
	{title:'fdName',field:'fdName',width:100,align:'left',hidden:true},
	
	{title:'发表时间',field:'publishDate',width:100,align:'center',sortable:true},
	{title:'发表刊物',field:'publication',width:100,align:'center',sortable:true},
	{title:'刊号',field:'publishNum',width:100,align:'center',sortable:true},
	{title:'刊物等级',field:'level',width:100,align:'center',sortable:true},
	{title:'分值',field:'score',width:50,align:'center',sortable:true},
	{title:'状态',field:'status',width:50,align:'center',sortable:true,formatter:iconFormatter,},
	{title:'附件',field:'detail',width:100,align:'center',formatter:format_detail}
			]]
		});
});

function format_detail(value,row){
	var names = "封面,版权页,目录,正文,封底,检索页";
	var files = row.fmName+","+row.bqyName+","+row.mlName+","+row.zwName+","+row.fdName+","+row.jsName;
	var str = "<a onclick='getDetail(\""+row.id+"\",\""+names+"\",\""+files+"\")'  href='javascript:void(0);'>查看</a>";
	return str;
}

function getDetail(id,names,files){
	window.open("Jsps/viewImg.jsp?dir=PaperResearch/"+id+"&names="+names+"&files="+files);
}

function addTPR(){
	closeTPR();
	//$('#imgShow_PaperResearch').attr("src",'');
	$('#teacherIdTPR').val($('#tid').val());//${session.loginteacher.id}
	$('#form_TPR').form('clear');
	$('#dlg_TPR').dialog('open');
}
function updateTPR(){
	var rows= $('#table_paper_research').datagrid('getSelections');
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
	$('#form_TPR').form('load',rows[0]);
	$('#dlg_TPR').dialog('open');
}
function deleteTPR(){
	var rows = $('#table_paper_research').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
	$.ajax({
		url : '/BusinessFiles/deletePaperResearch',
		type : "post",
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		data : {
			ids:param
			},
		success : function(data) {
			if(data.success){
				for(var i = rows.length - 1; i >= 0; i--){
					var index = $('#table_paper_research').datagrid('getRowIndex',rows[i]);
					$('#table_paper_research').datagrid('deleteRow',index);
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
function saveTPR(){
	var Id=$('#idTPR').val();
	var data= new FormData($("#form_TPR")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updatePaperResearch",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_paper_research').datagrid('reload');
					closeTPR();clearTPRauthor();
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
		url : "/BusinessFiles/addPaperResearch",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_paper_research').datagrid('reload');
				closeTPR();clearTPRauthor();
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
function closeTPR(){
	$('#dlg_TPR').dialog('close');
}
function searchTPR(){
	$("#table_paper_research").datagrid('load',{
		publishDateStart:$('#date_start').datebox('getValue'),
		publishDateEnd:$('#date_end').datebox('getValue')
	});
}

function addTPRauthor(){
	$('#dlg_author').dialog('open');
	var ss=$('#author').val();
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
	$('#author').textbox('setValue',ss);
	$('#dlg_author').dialog('close');
	clearTPRauthor();
}
function clearTPRauthor(){
	var item = $('#table_author').datagrid('getRows');    
    for (var i = item.length - 1; i >= 0; i--) {    
        var index = $('#table_author').datagrid('getRowIndex', item[i]);    
        $('#table_author').datagrid('deleteRow', index);    
    }
}
function exportRecords(){
	var rows = $('#table_paper_research').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的记录");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportPaperResearch?"+cond);

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
		<%@include file="../../inc/help/teacher/help_PaperResearch.inc" %>
	</div>
	<div data-options="region:'center',title:'学术论文',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_paper_research"></table>
<div id="tb_PaperResearch" style="padding:5px;height:auto">
    <label >发表时间:</label> 
    <input class="easyui-datebox" style="width:100px;" id="date_start" name="publishDateStart" data-options="editable:false,"/>至
	<input class="easyui-datebox" style="width:100px;" id="date_end" name="publishDateEnd" data-options="editable:false,"/>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTPR()" >查找</a>
    <a href="javascript:void(0)" style="margin-left:10px;" class="easyui-linkbutton" iconCls="icon-add" onclick="addTPR()" >新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTPR" disabled=true onclick="updateTPR()">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTPR" disabled=true onclick="deleteTPR()">删除</a>
    <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
</div>
 
  <div id="dlg_TPR" closed="true" class="easyui-dialog" title="学术论文" style="width:720px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体" data-options="iconCls: 'icon-save',closable:true">
       <form  id="form_TPR" method="post" enctype="multipart/form-data" > 
			<table cellspacing="6" cellpadding="3" >
				<tr hidden="true">
					<td><label for="idTPR"></label><input type="text" id="idTPR" name="id"/><br/></td>
					<td><input  type="text"  value="${session.loginteacher.id}" id="teacherIdTPR"  name="teacherId" /></td>
				</tr>
				<tr hidden="true">
						<td><input type="text"  name="status"/></td>
				</tr>
				<tr>
					<td>发表时间:</td>
					<td><input class="easyui-datebox" style="width:130px;" id="publishDate" name="publishDate"/></td>
					<td>封面:</td>
					<td><input type="file"  id="imgFile_PaperResearch_fm" name="fm"  multiple style="width:200px;"  accept="image/*" >
				</tr>
				<tr>
					<td>参与者:</td>
					<td><input class="easyui-textbox" style="width:240px;" id="author" name="author"  data-options="prompt:'单击右边的编辑按钮编辑参与者'" /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="addTPRauthor()" >编辑</a></td>
					<td>版权页:</td>
					<td><input type="file"  id="imgFile_PaperResearch_bqy" name="bqy"  multiple style="width:200px;" accept="image/*">
				</tr>
				<tr>
					<td>论文篇名:</td>
					<td><input class="easyui-textbox" style="width:240px;" id="paperTitle" name="paperTitle" /></td>
					<td>目录:</td>
					<td><input type="file"  id="imgFile_PaperResearch_ml" name="ml"  multiple style="width:200px;" accept="image/*">
				</tr>
				<tr>
					<td >发表刊物:</td>
					<td><input class="easyui-textbox" style="width:130px;" id="publication" name="publication" /></td>
					<td>正文:</td>
					<td><input type="file" imgFile_PaperResearch_zw" name="zw"  multiple style="width:200px;" accept="application/pdf">
				</tr>
				<tr>
					<td>刊号:</td>
					<td><input class="easyui-textbox" style="width:130px;" id="publishNum" name="publishNum" /></td>
					<td>封底:</td>
					<td><input type="file" id="imgFile_PaperResearch_wd" name="fd"  multiple style="width:200px;" accept="image/*">
				</tr>
				<tr>
					<td>刊物等级:</td>
					<td><input class="easyui-combobox"  data-options="valueField: 'name',textField: 'name',editable:false,url:'jsondata/PaperResearch_level.json'"style="width:130px;" id="level" name="level" /></td>
					<td>检索页:</td>
					<td><input type="file"  id="imgFile_PaperResearch_js" name="js"  multiple="multiple"  style="width:200px;" accept="image/*">
				</tr>
				<tr>
					<td>分值:</td>
					<td><input class="easyui-numberbox" style="width:130px;" id="score65" name="score" /></td>
				</tr>
			</table>
			<div align="center">
				<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTPR();">保 存</a>
				<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTPR();">取 消</a>
			</div>
         </form>
  </div>
   <%@include file="../../inc/dialog/dialog_author.inc" %>
	</div>
	
</body>
</html>
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
	$('#table_patent').datagrid({
		 url:'/BusinessFiles/getPatentlist?tid=${session.loginteacher.id}',
			iconCls:'icon-save',
			//collapsible : true,
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
	{title:'状态',field:'status',width:50,align:'center',sortable:true,formatter:iconFormatter},
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

function addTP(){
	closeTP();
	$('#teacherIdTP').val($('#tid').val());
	$('#dlg_TP').dialog('open');	
}
function updateTP(){
	var rows= $('#table_patent').datagrid('getSelections');
	//$('#imgShow_Patent').attr("src",'/BusinessFiles/attachments_Img/Patent/'+row.attachment+'?&t='+Math.random());
	if(rows.length==1){
		$('#form_TP').form('load',rows[0]);
		$('#dlg_TP').dialog('open');
	}
	
}
function deleteTP(){
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
function saveTP(){
	var Id=$('#idTP').val();
	var data= new FormData($("#form_TP")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updatePatent",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_patent').datagrid('reload');
					closeTP();
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
		url : "/BusinessFiles/addPatent",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_patent').datagrid('reload');
				closeTP();
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
function closeTP(){
	$('#form_TP').form('clear');
	$('#dlg_TP').dialog('close');
}
function searchTP(){
	$('#table_patent').datagrid('load',{
		publishDateStart:$('#publishDateStartTP').datebox('getValue'),
		publishDateEnd:$('#publishDateEndTP').datebox('getValue'),
		cond:$('#condTP').val(),
	});
}
function searchenterTP(event) {
    event = event || window.event;
    if (event.keyCode == 13) {
    	searchTP();
    }
}
function addTPauthor(){
	
	$('#dlg_author').dialog('open');
	
	var ss=$('#author_TP').val();
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
	$('#author_TP').textbox('setValue',ss);
	$('#dlg_author').dialog('close');clearTPauthor();
}

function clearTPauthor(){
	var item = $('#table_author').datagrid('getRows');    
    for (var i = item.length - 1; i >= 0; i--) {    
        var index = $('#table_author').datagrid('getRowIndex', item[i]);    
        $('#table_author').datagrid('deleteRow', index);    
    }
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
		<%@include file="../../inc/help/teacher/help_Patent.inc" %>
	</div>
	<div data-options="region:'center',title:'专利',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_patent"></table>
 		<div id="tb_patent" style="padding:5px;height:auto">   
	        <a href="javascript:void(0)" style="margin-left:10px;" class="easyui-linkbutton" iconCls="icon-add" onclick="addTP()" >新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTP" disabled=true onclick="updateTP()">修改</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTP" disabled=true onclick="deleteTP()">删除</a>   
 		</div>
 
 <div id="dlg_TP" closed="true" class="easyui-dialog" title="专利" style="width:450px;height:auto;padding:5px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
       <form  id="form_TP" method="post" enctype="multipart/form-data" > 			
			<table cellspacing="6" cellpadding="3" >
				<tr hidden="true">
					<td><label for="idTP"></label><input type="text" id="idTP" name="id"/><br/></td>
				</tr>
				<tr hidden="true">
						<td><input type="text"  name="status"/></td>
				</tr>
				<tr>
					<td >专利编号:</td>
					<td><input style="width:130px;"  class="easyui-textbox" id="patentNum" name="patentNum"  /><br/></td>
				</tr>
				<tr>
					<td>专利类型:</td>
					<td><input class="easyui-combobox" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Patent_type.json' " style="width:130px;" type="text" id="type_TP" name="type"  /><br /></td>
				</tr>
				<tr>
					<td >所&nbsp;有&nbsp;者:</td>
					<td><input class="easyui-textbox" style="width:240px;" data-options="prompt:'单击右边的编辑按钮编辑参与者'" id="author_TP" name="author"  /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="addTPauthor()" >编辑</a></td>
				</tr>
				<tr>
					<td >授权时间:</td>
					<td><input  class="easyui-datebox" style="width:140px;" data-options="editable:false" type="text" id="time_TP" name="time"  /><br /></td>
				</tr>
				<tr>
					<td >转化情况:</td>
					<td><input  style="width:240px;height:100px;" id="transform_TP"  name="transform" class="easyui-textbox" data-options="multiline:true" /></td>
				</tr>
				<tr>
					<td >证书复印件:</td>
					<td><input id="imgFile_TP" name="cert"  multiple style="width:300px;" class="easyui-filebox" accept="image/*"><input  type="hidden" name="certName" /></td>
				</tr>
				<tr>
					<td >转化&nbsp;鉴定:</td>
					<td><input id="imgFile_TP" name="trans"  multiple style="width:300px;" class="easyui-filebox" accept="image/*"><input  type="hidden" name="transName" /></td>
				</tr>
			</table> 			
			<div align="center">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"	onclick="saveTP()">保存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"	onclick="closeTP()">取消</a>
			</div>
         </form>
  </div>
  
  <%@include file="../../inc/dialog/dialog_author.inc" %>
	</div>
	
</body>
</html>



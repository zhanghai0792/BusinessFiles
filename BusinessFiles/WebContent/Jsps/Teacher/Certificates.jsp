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


<title>普通教师-证书信息</title>
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

function onSelectCert(rowIndex, rowData){
	$('#updateTCM').linkbutton('enable');
	$('#deleteTCM').linkbutton('enable');
}

function format_file(value,row){
	var names = row.name;
	var files = row.fileName;
	var str = "<a onclick='getDetail(\""+row.id+"\",\""+names+"\",\""+files+"\")'  href='javascript:void(0);'>查看</a>";
	return str;
}

function getDetail(id,names,files){
	window.open("Jsps/viewImg.jsp?dir=Certificate/"+id+"&names="+names+"&files="+files);
}
function addTCM(){
	$('#teacherIdTCM').val($('#tid').val());
	$('#dlg_TCM').dialog('open');
}
function updateTCM(){
	var row= $('#table_certificate_manage').datagrid('getSelected');
	$('#form_TCM').form('load',row);
	$('#dlg_TCM').dialog('open');
}
function saveTCM(){
	var data= new FormData($("#form_TCM")[0]);
	var Id=$('#idTCM').val();
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateCM",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_certificate_manage').datagrid('reload');
					closeTCM();
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
		url : "/BusinessFiles/addCM",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_certificate_manage').datagrid('reload');
				closeTCM();
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
function closeTCM(){
	$('#dlg_TCM').dialog('close');
	$('#form_TCM').form('clear');
}
function deleteTCM(){
	var rows = $('#table_certificate_manage').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
			$.ajax({
				url : '/BusinessFiles/deleteCM',
				type : "post",
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				data : {
					ids:param
					},
				success : function(data) {
					if(data.success){
					for(var i=rows.length-1; i>=0; i--){
						var index = $('#table_certificate_manage').datagrid('getRowIndex',rows[i]);
						$('#table_certificate_manage').datagrid('deleteRow',index);
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
function exportRecords(){
	var rows = $('#table_certificate_manage').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的证书");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportCertificateManage?"+cond);

}

</script> 
</head>
<body class="easyui-layout">
	<div data-options="region:'north',title:'',split:true," style="height:15%;overflow: hidden;">
 		<%@include file="../../inc/header.inc" %> 	
	</div> 
   
    <div data-options="region:'west',title:'功能导航',iconCls:'icon-reload',split:true,border:false" 
    	style="width:12%;">
 		<%@include file="../../inc/menu/teacher.inc" %> 	
    </div> 
    <div  data-options="region:'east',title:'帮助',collapsible:true,width:250">
		<%@include file="../../inc/help/teacher/help_certificate.inc" %>
	</div>
	<div data-options="region:'center',title:'证书信息',collapsible:false,border:false" style="overflow:hidden;">
		
<table class="easyui-datagrid" id="table_certificate_manage" data-options="url:'/BusinessFiles/getCMlist',toolbar:'#tb_CertificateManage',idField:'id',onSelect:onSelectCert">
	<thead>
		<tr>
			<th data-options="field:'selector',checkbox:true"></th>
			<th data-options="field:'id',width:100,align:'left',hidden:true"></th>
			<th data-options="field:'type',width:100">证书类型</th>
			<th data-options="field:'name',width:150">证书名称</th>
			<th data-options="field:'fileName',width:90,formatter:format_file">复印件</th>
		</tr>
	</thead>
</table>
<div id="tb_CertificateManage" >
   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTCM()" >新增</a>
   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  id="updateTCM"  disabled=true onclick="updateTCM()">修改</a>
   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTCM" disabled=true onclick="deleteTCM()">删除</a>
   <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
</div>
 
  <div id="dlg_TCM" closed="true" class="easyui-dialog" title="证书信息" style="width:400px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="closable:true">
       <form  id="form_TCM" method="post" enctype="multipart/form-data" > 
       		<input type="text" id="idTCM" name="id" hidden=true />
			<table cellpadding="3" >	
				<tr>
					<td width="80">证书类型:</td>
					<td width="150"><input style="width:150px;" type="text" id="typeTCM" name="type" class="easyui-combobox" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/CertificateManage_type.json'" /></td>
				</tr>					
				<tr>
					<td>证书名称:</td>
					<td><input style="width:150px;" type="text" id="nameTCM" name="name"  /></td>
				</tr>
				<tr>
					<td>证书复印件:</td>
					<td><input id="imgFile_TCM" name="file"  multiple  class="easyui-filebox" accept="image/*"></td>
				</tr>
			</table>
			<br/>
			<div align="center">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"	onclick="saveTCM()">保存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"	onclick="closeTCM()">取消</a>
			</div>
         </form>
  </div>
	</div>
	
</body>
</html>

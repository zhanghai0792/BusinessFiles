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

<title>普通教师-科研成果</title>
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
	$('#table_scientific_achievement').datagrid({
		 url:'/BusinessFiles/getScientificAchievementlist?tid=${session.loginteacher.id}',
			iconCls:'icon-save',
			rownumbers:true,
			toolbar:'#tb_ScientificAchievement',
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTSA').linkbutton('enable');
		  		$('#deleteTSA').linkbutton('enable');
		  },
		  columns:[[
		            { field:'ID', checkbox:true,},
		            {title:'id',field:'id',width:100,hidden:true},
		            {title:'作者',field:'author',width:100,align:'center',formatter:authorFormatter},
		            {title:'奖项',field:'prize',width:100,align:'center',},
		        	 {title:'奖励级别',field:'level',width:100,align:'center',sortable:true},
		        	 {title:'等次',field:'torder',width:100,align:'center',sortable:true},
		        	 {title:'时间',field:'time',width:100,align:'center',sortable:true},
		            {title:'分值',field:'score',width:100,align:'center',sortable:true},
					{title:'状态',field:'status',width:50,align:'center',sortable:true,formatter:iconFormatter},
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
	window.open("Jsps/viewImg.jsp?dir=ScientificAchievement/"+id+"&names="+names+"&files="+files);
}
	function addTSA(){
		closeTSA();
		$('#dlg_TSA').dialog('open');
	}
	function updateTSA(){
		var rows= $('#table_scientific_achievement').datagrid('getSelections');
		if(rows.length==1){
			$('#form_TSA').form('load',rows[0]);
			$('#dlg_TSA').dialog('open');
		}
	}
	function deleteTSA(){
		var rows = $('#table_scientific_achievement').datagrid('getSelections');
		if (rows) {
			var param={};
			for(var i=0;i<rows.length;i++){
				param[i] =rows[i].id;
			}
		$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
				if (res) {	
					$.ajax({
						url : "/BusinessFiles/deleteScientificAchievement",
						type : "post",
						contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
						data : {
							ids:param
							},
						success : function(data) {
							if(data.success){
								for(var i = rows.length - 1; i >= 0; i--){
									var index = $('#table_scientific_achievement').datagrid('getRowIndex',rows[i]);
									$('#table_scientific_achievement').datagrid('deleteRow',index);
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
	function saveTSA(){
		var Id=$('#idTSA').val();
		var data= new FormData($("#form_TSA")[0]);
		if(Id){
			$.ajax({
				url : "/BusinessFiles/updateScientificAchievement",
				type : "post",
				contentType : false,
				processData : false,
				data : data,
				success : function(data) {
					if(data.msg){
						$('#table_scientific_achievement').datagrid('reload');
						closeTSA();
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
			url : "/BusinessFiles/addScientificAchievement",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_scientific_achievement').datagrid('reload');
					closeTSA();
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
	function closeTSA(){
		$('#form_TSA').form('clear');
		$('#dlg_TSA').dialog('close');
	}
	function addTSAauthor(){
		$('#dlg_author').dialog('open');
		
		var ss=$('#author_TSA').val();
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
		$('#author_TSA').textbox('setValue',ss);
		$('#dlg_author').dialog('close');clearTSAauthor();
	}
	
	function clearTSAauthor(){
		var item = $('#table_author').datagrid('getRows');    
	    for (var i = item.length - 1; i >= 0; i--) {    
	        var index = $('#table_author').datagrid('getRowIndex', item[i]);    
	        $('#table_author').datagrid('deleteRow', index);    
	    }
	}
function exportRecords(){
	var rows = $('#table_scientific_achievement').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的记录");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportScientificAchievement?"+cond);

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
		<%@include file="../../inc/help/teacher/help_ScientificAchievement.inc" %>
	</div>
	<div data-options="region:'center',title:'科研成果',collapsible:false,border:false" style="overflow:hidden;">
		
		<table id="table_scientific_achievement"></table>
		<div id="tb_ScientificAchievement" style="padding:5px;height:auto">    
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTSA()" >新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTSA" disabled=true onclick="updateTSA()">修改</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTSA" disabled=true onclick="deleteTSA()">删除</a>
	 		<a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
	 	</div>
	 
 		<div id="dlg_TSA" closed="true" class="easyui-dialog" title="科研成果" style="width:450px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
	       <form  id="form_TSA" method="post" enctype="multipart/form-data" > 
				<table cellspacing="6" cellpadding="3" >
					<tr hidden="true">
						<td><input type="text" id="idTSA" name="id"/><input type="text"  name="status"/></td>
					</tr>
					<tr>
						<td>时间:</td>
						<td><input  class="easyui-datebox" style="width:130px;" type="text" id="timeTSA" name="time" /></td>
					</tr>
					<tr>
						<td>参与成员:</td>
						<td><input  style="width:240px;" class="easyui-textbox" data-options="prompt:'单击右边的编辑按钮编辑参与者'" id="author_TSA" name="author"  /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="addTSAauthor()" >编辑</a></td>
					</tr>
					<tr>
						<td>获奖奖项:</td>
						<td><input  style="width:240px;" class="easyui-textbox" id="prize" name="prize" /></td>
					</tr>
					<tr>
						<td >奖励级别:</td>
						<td><input class="easyui-combobox" style="width:130px;"  name="level" data-options="editable:false,panelHeight:'auto',valueField: 'name',textField: 'name',url:'jsondata/ScienceAchievement.json'"/></td>
					</tr>
					<tr>
						<td>奖励等次:</td>
						<td><input  class="easyui-combobox" style="width:130px;" name="torder" data-options="editable:false,panelHeight:'auto',valueField: 'name',textField: 'name',url:'jsondata/ScienceAchievement_torder.json'" /></td>
					</tr>
					<tr>
						<td>折算分值:</td>
						<td><input class="easyui-numberbox" style="width:130px;"id="score" name="score"  /><br /></td>
					</tr>
					<tr>
						<td>上传附件:</td>
						<td><input id="imgFile_TSA" name="file"  multiple style="width:300px;" class="easyui-filebox" accept="image/*"></td>
					</tr>
				</table>
				<div align="center">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTSA();">保 存</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTSA();">取 消</a>
				</div>
	         </form>
  		</div>
  
  		<%@include file="../../inc/dialog/dialog_author.inc" %>
	</div>	
</body>
</html>
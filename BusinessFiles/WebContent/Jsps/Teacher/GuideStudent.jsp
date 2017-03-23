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

<title>普通教师-指导学生</title>
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
	$('#table_guide_student').datagrid({
		 url:'/BusinessFiles/getGuideStudentlist?tid=${session.loginteacher.id}',
		 queryParams: {
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		  },
			iconCls:'icon-save',
			rownumbers:true,
			toolbar:'#tb_GuideStudent',
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTGS').linkbutton('enable');
		  		$('#deleteTGS').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'指导教师',field:'author',width:150,align:'left',formatter:authorFormatter},
	{title:'奖项',field:'prize',width:300,align:'center'},
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
	window.open("Jsps/viewImg.jsp?dir=GuideStudent/"+id+"&names="+names+"&files="+files);
}
	function addTGS(){
		closeTGS();
		$('#teacherIdTGS').val($('#tid').val());
		$('#dlg_TGS').dialog('open');
	}
	function updateTGS(){
		var rows= $('#table_guide_student').datagrid('getSelections');
		if(rows.length==1){
			$('#form_TGS').form('load',rows[0]);
			$('#dlg_TGS').dialog('open');
		}
		
	}
	function deleteTGS(){
		var rows = $('#table_guide_student').datagrid('getSelections');
		if (rows) {
			var param={};
			for(var i=0;i<rows.length;i++){
				param[i] =rows[i].id;
			}
		$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
				if (res) {	
					$.ajax({
						url : "/BusinessFiles/deleteGuideStudent",
						type : "post",
						contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
						data : {
							ids:param
							},
						success : function(data) {
							if(data.success){
								for(var i = rows.length - 1; i >= 0; i--){
									var index = $('#table_guide_student').datagrid('getRowIndex',rows[i]);
									$('#table_guide_student').datagrid('deleteRow',index);
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
	function saveTGS(){
		var Id=$('#idTGS').val();
		var data= new FormData($("#form_TGS")[0]);
		if(Id){
			$.ajax({
				url : "/BusinessFiles/updateGuideStudent",
				type : "post",
				contentType : false,
				processData : false,
				data : data,
				success : function(data) {
					if(data.msg){
						$('#table_guide_student').datagrid('reload');
						closeTGS();
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
			url : "/BusinessFiles/addGuideStudent",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_guide_student').datagrid('reload');
					closeTGS();
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
	function closeTGS(){
		$('#form_TGS').form('clear');
		$('#dlg_TGS').dialog('close');
	}
	function searchTGS(){
		$("#table_guide_student").datagrid('load',{
			DateStart:$('#date_start').datebox('getValue'),
			DateEnd:$('#date_end').datebox('getValue')
		});
	}
	function addTGSauthor(){
		$('#table_teachers').datagrid({
			onDblClickRow:function(index,row){
				var rows=$("#table_author").datagrid('getRows');
				for(var i=0;i<rows.length;i++){
					if(rows[i].id==row.id)return;
				}
				$("#table_author").datagrid('appendRow',row);
			}
		});
		$('#dlg_author').dialog('open');
		
		var ss=$('#author_TGS').val();
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
		$('#author_TGS').textbox('setValue',ss);;
		$('#dlg_author').dialog('close');
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
		<%@include file="../../inc/help/teacher/help_guideStudent.inc" %>
	</div>
	<div data-options="region:'center',title:'指导学生',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_guide_student"></table>
	<div id="tb_GuideStudent" style="padding:5px;height:auto">
   <label >时间:</label> 
   <input class="easyui-datebox" style="width:100px;" id="date_start" name="date_start" data-options="editable:false,"/>至
		<input class="easyui-datebox" style="width:100px;" id="date_end" name="date_end" data-options="editable:false,"/>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTGS()" >查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTGS()" >新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTGS" disabled=true onclick="updateTGS()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTGS" disabled=true onclick="deleteTGS()">删除</a>
  
 </div>
 
 <div id="dlg_TGS" closed="true" class="easyui-dialog" title="指导学生" style="width:450px;height:auto;padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
       <form  id="form_TGS" method="post" enctype="multipart/form-data" >
			<table cellspacing="6" cellpadding="3" >
				<tr hidden="true">
					<td><label for="idTGS"></label><input type="text" id="idTGS" name="id"/><br/></td>
				</tr>
				<tr hidden="true">
						<td><input type="text"  name="status"/></td>
				</tr>
				<tr>
					<td>获奖时间:</td>
					<td><input  class="easyui-datebox" style="width:130px;" id="timeTGS" name="time"  /></td>
				</tr>
				<tr>
					<td>指导教师:</td>
					<td><input style="width:240px;" class="easyui-textbox" data-options="prompt:'单击右边的编辑按钮编辑参与者'" id="author_TGS" name="author"  /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="addTGSauthor()" >编辑</a></td>
				</tr>
				<tr>
					<td>获奖奖项:</td>
					<td><input  style="width:240px;" class="easyui-textbox" id="prize" name="prize"  /></td>
				</tr>
				<tr>
					<td >奖励级别:</td>
					<td><input class="easyui-combobox" style="width:130px;" name="level" data-options="editable:false,panelHeight:'auto',valueField: 'name',textField: 'name',url:'jsondata/GuideStudent_level.json'"/></td>
				</tr>
				<tr>
					<td>奖励等次:</td>
					<td><input class="easyui-combobox" style="width:130px;" name="torder" data-options="editable:false,panelHeight:'auto',valueField: 'name',textField: 'name',url:'jsondata/GuideStudent_torder.json'" /></td>
				</tr>
				<tr>
					<td>折算分值:</td>
					<td><input class="easyui-numberbox" style="width:130px;" id="score" name="score"  /><br /></td>
				</tr>
				<tr>
					<td>上传附件:</td>
					<td><input id="imgFile_TGS" name="file"  multiple style="width:300px;" class="easyui-filebox" accept="image/*"/></td>
				</tr>
			</table>
			<div align="center">
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTGS();">保 存</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTGS();">取 消</a>
			</div>
         </form>
  </div>
  
  <%@include file="../../inc/dialog/dialog_author.inc" %>
	</div>
	
</body>
</html>


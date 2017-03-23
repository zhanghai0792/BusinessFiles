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


<title>普通教师-专著|教材</title>
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
	$('#table_monograph_textbook').datagrid({
		 url:'/BusinessFiles/getMonographTextbooklist?tid=${session.loginteacher.id}',
			iconCls:'icon-save',
			rownumbers:true,
			singleSelect : true,
			pagination:true,
			toolbar:'#tb_TMT',
			pageSize:10,
			pageList:[10,20,30],
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			onSelect:function(){
		  		$('#updateTMT').linkbutton('enable');
		  		$('#deleteTMT').linkbutton('enable');
		  },
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'编者',field:'author',width:250,formatter:authorMTFormatter},
	{title:'专著/编写教材名称',field:'bookName',width:200,align:'left'},
	{title:'出版时间',field:'publishDate',width:100,align:'center',sortable:true},
	{title:'出版单位',field:'publication',width:100,align:'center',sortable:true},
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
	window.open("Jsps/viewImg.jsp?dir=MonographTextbook/"+id+"&names="+names+"&files="+files);
}
function addTMT(){
	//closeTMT();
	$('#dlg_TMT').dialog('open');	
}
function updateTMT(){
	var rows= $('#table_monograph_textbook').datagrid('getSelections');
	//$('#imgShow_MonographTextbook').attr("src",'/BusinessFiles/attachments_Img/MonographTextbook/'+row.attachment+'?&t='+Math.random());
	if(rows.length==1){
		$('#form_TMT').form('load',rows[0]);
		$('#dlg_TMT').dialog('open');
	}
	
}
function deleteTMT(){
	var rows = $('#table_monograph_textbook').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
				$.ajax({
					url : "/BusinessFiles/deleteMonographTextbook",
					type : "post",
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					data : {
						ids:param
						},
					success : function(data) {
						if(data.success){
							for(var i = rows.length - 1; i >= 0; i--){
								var index = $('#table_monograph_textbook').datagrid('getRowIndex',rows[i]);
								$('#table_monograph_textbook').datagrid('deleteRow',index);
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
function saveTMT(){
	var Id=$('#idTMT').val();
	var data= new FormData($("#form_TMT")[0]);
	if(Id){
		$.ajax({
			url : "/BusinessFiles/updateMonographTextbook",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				if(data.msg){
					$('#table_monograph_textbook').datagrid('reload');
					closeTMT();clearTMTauthor();
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
		url : "/BusinessFiles/addMonographTextbook",
		type : "post",
		contentType : false,
		processData : false,
		data : data,
		success : function(data) {
			if(data.msg){
				$('#table_monograph_textbook').datagrid('reload');
				closeTMT();clearTMTauthor();
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
function closeTMT(){
	$('#form_TMT').form('clear');
	$('#dlg_TMT').dialog('close');
	clearTMTauthor();
}
function addTMTauthor(){
	$('#dlg_author').dialog('open');
	
	var ss=$('#author_TMT').val();
	if(ss!=""){
		var s=new Array();
		s=ss.split(",");
		for(var i=0;i<s.length;i++){
			var str=s[i].split("|");
			var name=str[0];
			var id=str[1]; 
			var type_author=str[2];
			$("#table_author").datagrid('appendRow',{id:id,name:name,type_author:type_author});
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
			ss+=rows[i].name+"|"+rows[i].id+"|"+rows[i].type_author+",";
		}
		ss=ss.substring(0,ss.length-1);
	$('#author_TMT').textbox('setValue',ss);
	$('#dlg_author').dialog('close');
	clearTMTauthor();
}

function closeTMTauthor(){
	//$("#table_author").datagrid('loadData', { total: 0, rows: [] });
	$('#dlg_author').dialog('close');
	clearTMTauthor();
}
function clearTMTauthor(){
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
		<%@include file="../../inc/help/teacher/help_book.inc" %>
	</div>
	<div data-options="region:'center',title:'专著|教材',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_monograph_textbook"></table>

		 <div id="tb_TMT" style="padding:5px;height:auto">       
		       <a href="javascript:void(0)" style="margin-left:10px;" class="easyui-linkbutton" iconCls="icon-add" onclick="addTMT()" >新增</a>
		       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="updateTMT" disabled=true onclick="updateTMT()">修改</a>
		       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="deleteTMT" disabled=true onclick="deleteTMT()">删除</a>
		 </div>
 
 		<div id="dlg_TMT" closed="true" class="easyui-dialog" title="专著/教材" style="width:450px;height:auto;padding:5px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体"
            data-options="iconCls: 'icon-save',closable:true">
       		<form  id="form_TMT" method="post" enctype="multipart/form-data" > 
				<table cellspacing="6" cellpadding="3" >
					<tr hidden="true">
						<td><input type="text" id="idTMT" name="id"/></td>
					</tr>
					<tr hidden="true">
						<td><input type="text"  name="status"/></td>
					</tr>
					<tr>
						<td>出版时间:<input  class="easyui-datebox" style="width:140px;" data-options="editable:false" type="text" id="publishDate" name="publishDate"  /><br /></td>
					</tr>
					<tr>
						<td>参编作者:<input class="easyui-textbox" style="width:240px;" data-options="prompt:'单击右边的编辑按钮编辑参与者'" id="author_TMT" name="author"  /><a href="javascript:void(0)" style="margin-left:2px;" class="easyui-linkbutton" iconCls="icon-edit" onclick="addTMTauthor()" >编辑</a></td>
					</tr>
					<tr>
						<td>专著/教材:<input class="easyui-textbox" style="width:250px;" name="bookName" /></td>
					</tr>
					<tr>
						<td >出版单位:<input class="easyui-textbox" style="width:130px;" id="publication" name="publication" /></td>
					</tr>
					<tr>
						<td>折算分值:<input class="easyui-numberbox" style="width:130px;" type="text" id="score" name="score" /></td>
					</tr>
					<tr>
						<td>上传附件:<inputt class="easyui-filebox" id="imgFile_TMT" name="file"  multiple style="width:200px;" accept="image/*"></td>
					</tr>
				</table> 	
				<div align="center">
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTMT();">保 存</a>
					<a  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeTMT();">取 消</a>
				</div>
	         </form>
  		</div>
  
  		<%@include file="../../inc/dialog/dialog_author_withType.inc" %>
	</div>
	
</body>
</html>

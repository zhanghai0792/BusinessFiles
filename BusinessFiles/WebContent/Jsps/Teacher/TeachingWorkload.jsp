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


<title>普通教师-教学工作量</title>
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
var editRow_TeachingWorkload = undefined;
 
$(function(){
	var year = new Date().getYear()+1900;
	$('#termYear').numberspinner('setValue', year);  
	search();
});
	

function search(){
	var term = $('#termYear').val()+"-"+$('#term').combobox('getValue');
	$.post("/BusinessFiles/getTeachingWorkloadlist?tid=${session.loginteacher.id}",
		{
			termDate:term
	    },
		function(data){
			if(!data.success){
       			alert(data.msg);
       			return;
       		}
       		$("#table_teaching").datagrid({data:data.teachings});
       		$("#table_practice").datagrid({data:data.practices});
       		$("#table_paper").datagrid({data:data.papers});
       		
       		var total = 0;
       		data:data.teachings.forEach(function(val,index,arr){
       			total += Number(val.periodNum);       			
			});
			data:data.practices.forEach(function(val,index,arr){
       			total += Number(val.discountClassNum);       			
			});
			data:data.papers.forEach(function(val,index,arr){
       			total += Number(val.discountClassNum);       			
			});
			$("#div_main").panel("setTitle","工作量汇总:"+total);
	  	}
	);
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
    <div  data-options="region:'east',title:'帮助',collapsible:true,split:true,border:false,width:250">
		<%@include file="../../inc/help/teacher/help_theory_teaching.inc" %>
	</div>
	<div id="div_main" data-options="region:'center',title:'教学工作量核对',collapsible:false,border:false" style="overflow:hidden;">
		
		<div id="tb_TeachingWorkload">
		    <input class="easyui-numberspinner" id="termYear" data-options="min:2010,max:2100,required:true,onSpinUp:search,onSpinDown:search" style="width:80px;"></input>年
			<select class="easyui-combobox" id="term" style="width:90px;" data-options="onSelect:search">
				<option value="1">上学期</option>
				<option value="2">下学期</option>
			</select>
		 </div>
		 <br/>
		<table class="easyui-datagrid" id="table_teaching" data-options="title:'理论教学'">
			<thead>
				<tr>
					<th data-options="field:'teacherId',width:70">工号</th>
					<th data-options="field:'teacherName',width:70">姓名</th>
					<th data-options="field:'professTitle',width:70">职称</th>
					<th data-options="field:'positiontype',width:70">专/兼职</th>
					<th data-options="field:'courseId',width:70">课程代码</th>
					<th data-options="field:'courseName',width:150">课程名称</th>
					<th data-options="field:'courseTo',width:80">课程归属</th>
					<th data-options="field:'courseType',width:70">课程类型</th>
					<th data-options="field:'tclass',width:70">班级代码</th>
					<th data-options="field:'weekClassNum',width:70">周课时</th>
					<th data-options="field:'periodNum',width:70">总课时</th>
				</tr>
			</thead>
		</table>
		 <br/>
		<table class="easyui-datagrid" id="table_practice" data-options="title:'实践教学'">
			<thead>
				<tr>
					<th data-options="field:'teacherId',width:70">工号</th>
					<th data-options="field:'teacherName',width:70">姓名</th>
					<th data-options="field:'professTitle',width:70">职称</th>
					<th data-options="field:'positiontype',width:70">专/兼职</th>
					<th data-options="field:'courseName',width:150">课程名称</th>
					<th data-options="field:'courseType',width:70">课程类型</th>
					<th data-options="field:'tclass',width:70">班级代码</th>
					<th data-options="field:'tclassNum',width:70">学生数</th>
					<th data-options="field:'weekNum',width:70">实践周数</th>
					<th data-options="field:'discountClassNum',width:70">折算课时</th>
				</tr>
			</thead>
		</table>
		 <br/>
		<table class="easyui-datagrid" id="table_paper" data-options="title:'论文指导'">
			<thead>
				<tr>
					<th data-options="field:'teacherId',width:70">工号</th>
					<th data-options="field:'teacherName',width:70">指导教师</th>
					<th data-options="field:'professTitle',width:70">职称</th>
					<th data-options="field:'tclassNum',width:70">学生数</th>
					<th data-options="field:'discountClassNum',width:70">折算课时</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
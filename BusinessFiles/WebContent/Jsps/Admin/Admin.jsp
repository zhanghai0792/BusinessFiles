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
<title>职工管理</title>

<link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css" />
<script type="text/javascript" src="easyUI/jquery.min.js"></script>
<script type="text/javascript" src="easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyUI/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="datagrid_view/datagrid-detailview.js"></script>

<script type="text/javascript">
	var ctrlType = 0;//控件类型
	var dataType = 0;//数据类型
	var logicOp;//逻辑运算符
	function changeKey(record){
		ctrlType = record.ctrlType;
		dataType = record.dataType;
		
		//重新渲染存放“值”的组件
		switch(ctrlType){
			case 0:
				$("#span_value").html("<input id='value' class='easyui-textbox'>");
				break;
			case 1:
				$("#span_value").html("<input id='value' class='easyui-datebox'>");
				break;
			case 2:
				$("#span_value").html("<input id='value' class='easyui-combobox'>");
				break;
		}				
		$.parser.parse($("#span_value"));
		
		//如果为combobox，则动态加载选项
		if(ctrlType == 2){
			$("#value").combobox({
				url:record.url,
				valueField:record.valueField,
				textField:record.textField
			});	
		}
	}
	
	//添加查询条件
	function addCondition(){
		var key_hidden = $("#cmb_key").combobox('getValue');
		var key_show = $("#cmb_key").combobox('getText');
		var relation_hidden = $("#cmb_relation").combobox('getValue');
		var relation_show = $("#cmb_relation").combobox('getText');
		var value_hidden,value_show;
		switch(ctrlType){
		case 0:
			value_hidden = $("#value").textbox('getValue');//传递字符串型应该用单引号，但单引号不能传递，用特殊字符代替，后台需转换
			value_show = $("#value").textbox('getText');
			break;
		case 1:
			value_hidden = $("#value").datebox('getValue');
			value_show = $("#value").datebox('getText');
			break;
		case 2:
			value_hidden = $("#value").combobox('getValue');
			value_show = $("#value").combobox('getText');
			break;
		}
		
		//如果是模糊查询，则需要加通配符%
		if(relation_hidden == "like"){
			value_hidden = "%"+value_hidden+"%";
		}
		//传递字符串型应该用单引号，但单引号不能传递，用特殊字符代替，后台需转换
		if(dataType == 0){
			value_hidden = "@"+value_hidden+"@";
		}
		
		
		
		var cond_show = key_show+relation_show+value_show;
		var cond_hidden = key_hidden+" "+relation_hidden+" "+value_hidden;			
		if($("#cond_show").textbox('getValue').length > 0){
			cond_show = $("#cond_show").textbox('getValue')+(logicOp == 0?" 或 ":" 且 ")+cond_show;
			cond_hidden = $("#cond_hidden").val()+(logicOp == 0?" or ":" and ")+cond_hidden;
			$("#cond_show").textbox('setValue',cond_show);
			$("#cond_hidden").val(cond_hidden);
		}else{
			$("#cond_show").textbox('setValue',cond_show);
			$("#cond_hidden").val(cond_hidden);
		}
	}
	
	//重置查询条件
	function resetCondition(){
		$("#cond_show").textbox('setValue',"");
		$("#cond_hidden").val("");
	}
	
	//选择逻辑运算“且”-“或”
	function setLogicRelation(value){
		logicOp = value;
	}
	

	
	function format_role(value,row,index){
		switch(Number(value)){
			case 1: return "普通教师";
			case 2: return "教务管理员";
			case 3: return "科研管理员";
			case 0: return "系统管理员";
		}
	
	}
	
	function format_sex(val,row,index){
		if(val=="1")return"男";else return "女";
	}
//显示对话框添加记录
	function showNewDlg(){
		$('#dlg').dialog('open');
		$('#fm').form('clear');
		url = "/BusinessFiles/addTeacher";	
	}
	
	//显示对话框修改记录
	function showEditDlg(){
		var row = $('#table_Teacher').datagrid('getSelected');
		if (!row){
			return;
		}
		
		$('#dlg').dialog('open');
		$('#fm').form('load',row);
		url = "/BusinessFiles/updateTeacher?isupdate=-1"//不更新session标志
	}
	
	function showImportDlg(){
		$('#dlg_import').dialog('open');
	}
	
	function ImportRecords(){
	 	var file=$('#file_xls').filebox("getValue");
		if(file.indexOf(".xls")<0 && file.indexOf(".xlsx")<0){
			$.messager.alert('提示', "请选择excel文件！", 'info');
			return;
		}
	
		var data= new FormData($("#form_import")[0]);
		$.ajax({
			url : "/BusinessFiles/importTeacher",
			type : "post",
			contentType : false,
			processData : false,
			data : data,
			success : function(data) {
				$('#dlg_import').dialog('close');
				if(data.success){
					$('#table_Teacher').datagrid('reload');
					$.messager.show({
						title:'提示',
						timeout:3000,
						msg:data.msg,
						showType:'slide',
					});				
				}else
					$.messager.show({
						title:'提示',
						timeout:3000,
						msg:data.msg,
						showType:'slide',
					});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 $.messager.alert("提 示", "操作失败");
			}
		});
		
	 }
 
	//保存记录（包括添加和修改）
	function saveRecord(){
		var data= new FormData($("#fm")[0]);
		$('#fm').form('submit',{
			url: url,
			processData : false,
			data : data,
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				$('#dlg').dialog('close');		// close the dialog
				$('#table_Teacher').datagrid('reload');	// reload the user data
			}
		});	
	}	
	
 function DeleteRecords(){
	var rows = $('#table_Teacher').datagrid('getSelections');
	if (rows) {
		var param={};
		for(var i=0;i<rows.length;i++){
			param[i] =rows[i].id;
		}
	$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
			if (res) {	
	$.ajax({
		url : '/BusinessFiles/deleteTeacher',
		type : "post",
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		data : {
			ids:param
			},
		success : function(data) {
			if(data.success){
				for(var i = rows.length - 1; i >= 0; i--){
					var index = $('#table_Teacher').datagrid('getRowIndex',rows[i]);
					$('#table_Teacher').datagrid('deleteRow',index);
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
//查找记录
	function searchRecord(){
		$('#table_Teacher').datagrid({  
		    url:'/BusinessFiles/getTeacherlist?realInfo=selectAll',
		    queryParams:{  
		       condition:$("#cond_hidden").val(),
		       timestamp:(new Date()).valueOf() //加时间戳，解决缓存问题    
		    },
			method:'post'
		}); 
	}

function exportRecords(){
	var rows = $('#table_Teacher').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的记录");
		return;		
	}
	var cond="";
	for(var i=0;i<rows.length;i++){
		cond+=("ids="+rows[i].id+"&");
	}
	cond=cond.substring(0,cond.length-1);
	window.open("/BusinessFiles/exportTeacher?"+cond);
}
</script>
		
</head>

<body class="easyui-layout">
	<div data-options="region:'north',title:'',split:true," style="height:15%;overflow: hidden;">
 		<%@include file="../../inc/header.inc" %> 	
	</div> 
   
    <div data-options="region:'west',title:'功能导航',iconCls:'icon-reload',split:true,border:false" 
    	style="width:12%;background:#B3DFDA;padding-left:5px;">
 		<%@include file="../../inc/menu/admin.inc" %> 	
    </div> 
    <div  data-options="region:'east',title:'帮助',collapsible:true,width:250">
		<%@include file="../../inc/help/teacher/help_book.inc" %>
	</div>
	<div data-options="region:'center',title:'职工管理'" >
		<table id="table_Teacher" class="easyui-datagrid" data-options="toolbar:'#toolbar',fit:true,pagination:true,pageSize:20,pageList:[10,20,50]">
			<thead>
		        <tr>
		          <!-- field应于数据库字段名、bean类对象属性保持一致 -->
		          <th data-options="field:'selector',checkbox:true"></th>
				  <th data-options="field:'id',width:60,sortable:true,halign:'center'">工号</th>
		          <th data-options="field:'cardId',width:120,halign:'center'">身份证号</th>
		          <th data-options="field:'name',width:60,halign:'center'">姓名</th>
		          <th data-options="field:'sex',width:30,formatter:format_sex,halign:'center'">性别</th>
		          <th data-options="field:'phone',width:80,halign:'center'">电话</th>
		          <th data-options="field:'political',width:60,sortable:true,halign:'center'">政治面貌</th>
		          <th data-options="field:'dept',width:100,halign:'center'">所属科室</th>
		          <th data-options="field:'finalDegree',width:60,halign:'center'">学位</th>
		          <th data-options="field:'finalDegreeDate',width:80,halign:'center'">获得学位时间</th>
		          <th data-options="field:'finalEducat',width:60,halign:'center'">学历</th>
		          <th data-options="field:'finalEducatDate',width:80,halign:'center'">获得学历时间</th>
		          <th data-options="field:'school',width:100,halign:'center'">毕业院校</th>
		          <th data-options="field:'major',width:100,halign:'center'">最后毕业专业</th>
		          <th data-options="field:'professTitle',width:70,halign:'center'">评定职称</th>
		          <th data-options="field:'professDate',width:80,halign:'center'">评定职称时间</th>
		          <th data-options="field:'employTitle',width:70,halign:'center'">聘任职称</th>
		          <th data-options="field:'employDate',width:80,halign:'center'">聘任职称时间</th>
		           <th data-options="field:'type',width:100,formatter:format_role,halign:'center'">角色</th>
		        </tr>
			</thead>
		</table>

 
		<div id="toolbar" style="padding:5px;height:auto">
			<input class="easyui-combobox" style="width:100px;" id="cmb_key" data-options="onSelect:changeKey,url:'jsondata/key_employee.json',valueField:'id',textField:'text',editable:false"/>
	      	<input class="easyui-combobox" style="width:50px;" id="cmb_relation" data-options="url:'jsondata/relation.json',valueField:'id',textField:'text',editable:false"/>
	      	<span id="span_value"><input class="easyui-textbox" style="width:80px;" id="value" /></span>
	      	<a class="easyui-splitbutton" data-options="plain:false,menu:'#mm2',iconCls:'icon-ok'" onclick="addCondition()">增加条件</a>
	      	<a class="easyui-linkbutton" iconCls="icon-search" onclick="searchRecord()" >查找</a>
	      	&emsp;&emsp;查询条件<input id="cond_show" class="easyui-textbox" disabled=true; style="width:200px;" />
	      	<input id="cond_hidden" type="text" style="width:300px;display:none" />
	      	<a class="easyui-linkbutton" iconCls="icon-reset" onclick="resetCondition()" >重置查询</a>	
	      	<br/>
	      	<a class="easyui-linkbutton" iconCls="icon-add" onclick="showNewDlg()" >新增</a>
	      	<a class="easyui-linkbutton" iconCls="icon-edit" onclick="showEditDlg()">修改</a>		
      		<a class="easyui-linkbutton" iconCls="icon-remove" onclick="DeleteRecords()">删除</a>
	      	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-xls" onclick="showImportDlg()">导入</a>
  	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
		</div>
		<div id="mm2" style="width:50px;">
	      <div onclick="setLogicRelation(1)">与</div>
	      <div onclick="setLogicRelation(0)">或</div>
	    </div>
   		<%@include file="../../inc/dialog/dialog_employee_edit.inc" %>
   		<%@include file="../../inc/dialog/dialog_employee_import.inc" %>
	</div>	
</body>
</html>

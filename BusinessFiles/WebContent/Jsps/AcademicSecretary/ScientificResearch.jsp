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

$(function(){
	$('#table_scientific_research').datagrid({
			rownumbers:true,
			pagination:true,
			pageSize:10,
			pageList:[10,20,30,50,100],
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
function DeleteRecords(){
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

//查找记录
	function searchRecord(){
		$('#table_scientific_research').datagrid({  
		    url:'/BusinessFiles/getScientificResearchlist',
		    queryParams:{  
		       condition:$("#cond_hidden").val(),
		       timestamp:(new Date()).valueOf() //加时间戳，解决缓存问题    
		    },
			method:'post'
		}); 
	}
	
function CheckRecords(){
	var rows= $('#table_scientific_research').datagrid('getSelections');
	if(rows.length<=0){
		return;
	}
	var param={};
	for(var i=0;i<rows.length;i++){
		param[i] =rows[i].id;
	}
	$.ajax({
		url : "/BusinessFiles/reviewScientificResearch",
		type : "post",
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		dataType : 'json',
		data :{ids:param},
		success : function(data) {
			if(data.success){
				$('#table_scientific_research').datagrid('reload');
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
	var rows = $('#table_scientific_research').datagrid('getSelections');
	if (rows.length == 0) {
		alert("请选中需导出的记录");
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
 		<%@include file="../../inc/menu/AcademicSecretary.inc" %> 	
    </div> 
    <div  data-options="region:'east',title:'帮助',collapsible:true,width:250">
		<%@include file="../../inc/help/AcademicSecretary/help_ScientificResearch.inc" %>
	</div>
	<div data-options="region:'center',title:'科研课题',collapsible:false,border:false" style="overflow:hidden;">
		<table id="table_scientific_research"></table>
<div id="tb_ScientificResearch" style="padding:5px;height:auto">
     <input class="easyui-combobox" style="width:100px;" id="cmb_key" data-options="onSelect:changeKey,url:'jsondata/key_ScientificResearch.json',valueField:'id',textField:'text',editable:false"/>
	      <input class="easyui-combobox" style="width:50px;" id="cmb_relation" data-options="url:'jsondata/relation.json',valueField:'id',textField:'text',editable:false"/>
	      <span id="span_value"><input class="easyui-textbox" style="width:80px;" id="value" /></span>
	      <a class="easyui-splitbutton" data-options="plain:false,menu:'#mm2',iconCls:'icon-ok'" onclick="addCondition()">增加条件</a>
	      <a class="easyui-linkbutton" iconCls="icon-search" onclick="searchRecord()" >查找</a>
	      &emsp;&emsp;查询条件<input id="cond_show" class="easyui-textbox" disabled=true; style="width:200px;" />
	      <input id="cond_hidden" type="text" style="width:300px;display:none" />
	      <a class="easyui-linkbutton" iconCls="icon-reset" onclick="resetCondition()" >重置查询</a>
		       <a class="easyui-linkbutton" iconCls="icon-remove" onclick="DeleteRecords()">删除</a>
		       <a class="easyui-linkbutton" iconCls="icon-ok" onclick="CheckRecords()">审核通过</a>
  <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-download'," onclick="exportRecords()">导出</a>
   	
 </div>
	    <div id="mm2" style="width:50px;">
	      <div onclick="setLogicRelation(1)">与</div>
	      <div onclick="setLogicRelation(0)">或</div>
	    </div>
 
 
	</div>
	
</body>
</html>

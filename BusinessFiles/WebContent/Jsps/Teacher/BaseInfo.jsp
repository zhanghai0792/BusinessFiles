<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />  
<title>普通教师-教师业务管理系统</title>
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
	new uploadPreview({
		UpBtn : "imgFile_TPerson",
		DivShow : "imgdiv_TPerson",
		ImgShow : "imgShow_TPerson"
	});
});
function SaveInformation() {
	var data= new FormData($("#form_updateTeacher")[0]);
	$.ajax({
	url : "/BusinessFiles/updateTeacher",
	type : "post",
	contentType : false,
	processData : false,
	data : data,
	success : function(data) {
		$.messager.show({
					title:'提示',
					timeout:3000,
					msg:'修改成功',
					showType:'slide',
				});
			//console.info(data);
		$("#form_updateTeacher").form('load',data);
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		alert("失败:"
				+ XMLHttpRequest.responseText
						.substring(XMLHttpRequest.responseText
								.indexOf("<h1>") + 4,
								XMLHttpRequest.responseText
										.indexOf("</h1>")));
		}
	});
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
		<%@include file="../../inc/help/teacher/help_baseInfo.inc" %>
	</div>
	<div data-options="region:'center',title:'教师基本信息',collapsible:false,border:false" style="overflow:hidden;">
		<div class="easyui-panel" title="" align="left"
	style="height:100%;width:100%;" data-options="tools:'#tool_information'">
	 <form  id="form_updateTeacher" method="post" enctype="multipart/form-data" > 
		<fieldset>
			<img id="u39_img" style="padding-top:8px;padding-left:10px;"
				src="images/u6.png"><label
				style="padding-left:5px;font-size:20px;line-height:20px;">基本信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<div style="overflow: hidden;"> 
			<table border='0' style="float:left;">
			<tr hidden="true">
				<td><input type="text" id="t_password" name="password" value="${session.loginteacher.password}" ></td>
				<td><input type="text" id="t_type" name="type" value="${session.loginteacher.type}" ></td>
				<td><input type="text"  name="isDelete" value="${session.loginteacher.isDelete}" ></td>
			</tr>
				<tr style="height: 45px;">
					<td style="text-align: right;width:70px;">工号:</td>
					<td style="text-align: left;width:170px;"><input type="text" value="${session.loginteacher.id}"
						class="leftText" id="id" name="id" readonly="readonly" ></td><%-- --%>
					<td style="text-align: right;width:70px;">姓名:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						class="leftText" id="t_name" name="name" readonly="readonly" value="${session.loginteacher.name}"
						></td>
					<td style=";text-align: right;width:70px;">性别:</td>
					<td style="text-align: left;"><input type="text"
						 class="easyui-combobox"  name="sex" value="${session.loginteacher.sex}" data-options="valueField: 'id',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_sex.json' "  ><span style="color:red;font-size:16px;">*</span></td>
				</tr>
				<tr style="height: 45px;">
					<td style="text-align: right;width:70px;">民族:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						 class="easyui-combobox" name="nation" data-options="editable:false,valueField:'name',textField:'name',url:'jsondata/Teacher_nation.json'" id="t_nation" value="${session.loginteacher.nation}" ><span style="color:red;font-size:16px;">*</span></td>
					<td style="text-align: right;width:65px;">政治面貌:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						 class="easyui-combobox" data-options="valueField:'name',panelHeight:'auto',textField:'name',editable:false,url:'jsondata/Teacher_political.json'" id="t_political" name="political" value="${session.loginteacher.political}" 
						><span style="color:red;font-size:16px;">*</span></td>
					
				</tr>
				<tr style="height: 45px;">
					<td style=";text-align: right;width:70px;">出生年月:</td>
					<td style="text-align: left;"><input type="text"
						 class="easyui-datebox"  name="birthDate" value="${session.loginteacher.birthDate}" id="t_birthDate" ><span style="color:red;font-size:16px;">*</span></td>
					<td style=";text-align: right;width:65px;">身份证号:</td>
					<td style="text-align: left;"><input type="text" id="t_cardId" 
						class="leftText" name="cardId" value="${session.loginteacher.cardId}" ></td>
					<td style="text-align: right;width:70px;">手机号:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						class="leftText"  name="phone"  value="${session.loginteacher.phone}"
						></td>	
				</tr>
			</table>
			
			<div id="imgdiv_TPerson" style="margin-left:15px;margin-top:5px;width:130px;height:150px;float:left">
						 <s:if test="#session.loginteacher.photoName!=''&&#session.loginteacher.photoName!=null">
						<img id="imgShow_TPerson" style="width:130px;height:130px;"
							src="attachments_Img/Teacher/photo/<s:property value='#session.loginteacher.photoName' />"
							alt="">
					</s:if><s:else>
						<img id="imgShow_TPerson" style="width:130px;height:130px;" src="attachments_Img/Teacher/photo/avatar.gif" alt="">
					</s:else>  
					<input id="imgFile_TPerson" name="photo"  multiple  type="file" accept="image/*" value="${sessionScope.loginteacher.photoName} }">
					
				</div>
			</div>
			
		</fieldset>
		<fieldset >
			<img id="u39_img" style="padding-top:8px;padding-left:10px;"
				src="images/u6.png"><label
				style="padding-left:5px;font-size:20px;line-height:20px;">教育信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<table border='0'>
				<tr style="height: 45px;">
					<td style="text-align: right;width:70px;">最后学位:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						 class="easyui-combobox" name="finalDegree" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_finalDegree.json' " id="t_finalDegree" value="${session.loginteacher.finalDegree}" ><span style="color:red;font-size:16px;">*</span></td>
					<td style="text-align: right;width:120px;">获得最后学位时间:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						 class="easyui-datebox"  name="finalDegreeDate" value="${session.loginteacher.finalDegreeDate}" id="t_finalDegreeDate"
						></td>
					<td style="text-align: right;width:70px;">毕业院校:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						class="leftText"  name="school"  value="${session.loginteacher.school}"
						></td>	
				</tr>
				<tr style="height: 45px;">
					<td style=";text-align: right;">最后学历:</td>
					<td style="text-align: left;"><input type="text"
						 class="easyui-combobox" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_finalEducat.json'" id="t_finalEducat" name="finalEducat" value="${session.loginteacher.finalEducat}" ><span style="color:red;font-size:16px;">*</span></td>
					
					<td style="text-align: right;width:120px;">获得最后学历时间:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						 class="easyui-datebox"  name="finalEducatDate" value="${session.loginteacher.finalEducatDate}" id="t_finalDegreeDate"
						></td>
					<td hidden="true" style="text-align: right;width:70px;">职位类型:</td>
					<td hidden="true" style="text-align: left;width:170px;"><input type="text"
						 class="easyui-combobox" data-options="valueField: 'name',textField: 'name', panelHeight:'auto',editable:false,url:'jsondata/Teacher_positiontype.json'" id="t_positiontype" name="positiontype" value="${session.loginteacher.positiontype}" ><span style="color:red;font-size:16px;">*</span></td>
					<td style="text-align: right;width:70px;">专业:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						class="leftText"  name="major"  value="${session.loginteacher.major}"
						></td>
				</tr>
			</table>
		</fieldset>
		<fieldset>
			<img id="u39_img" style="padding-top:8px;padding-left:10px;"
				src="images/u6.png"><label
				style="padding-left:5px;font-size:20px;line-height:20px;">工作信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<table border='0'>
				<tr style="height: 45px;">
					<td style="text-align: right;width:70px;">评定职称:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						 class="easyui-combobox" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_title.json'" id="t_professTitle" name="professTitle" value="${session.loginteacher.professTitle}" ><span style="color:red;font-size:16px;">*</span></td>
					<td style="text-align: right;width:78px;">评定职称时间:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						  class="easyui-datebox"  name="professDate" value="${session.loginteacher.professDate}" id="t_professDate"
						></td>
					<td style=";text-align: right;">院系:</td>
					<td style="text-align: left;"><input type="text"
						 class="easyui-combobox" data-options="editable:false,valueField:'name',textField:'name',url:'jsondata/Teacher_grade.json'" name="grade" value="${session.loginteacher.grade}" id="t_grade" ><span style="color:red;font-size:16px;">*</span></td>

				</tr>
				<tr style="height: 45px;">
					<td style="text-align: right;width:70px;">聘任职称:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						  class="easyui-combobox" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_title.json'" id="t_employTitle" name="employTitle" value="${session.loginteacher.employTitle}" ><span style="color:red;font-size:16px;">*</span></td>
					<td style="text-align: right;width:78px;">聘任职称时间:</td>
					<td style="text-align: left;width:170px;"><input type="text"
						 class="easyui-datebox"   name="employDate" value="${session.loginteacher.employDate}" id="t_employDate"
						></td>
					<td style=";text-align: right;">部门:</td>
					<td style="text-align: left;"><input type="text"
						 class="easyui-combobox" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_dept.json'" id="t_dept" name="dept" value="${session.loginteacher.dept}" ><span style="color:red;font-size:16px;">*</span></td>
				</tr>
				</table>
		</fieldset>
		<div border='0' align="center" style="width:100%; ">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'," style=" width:80px;margin-top:15px; height:30px; cursor: pointer;"
      							onclick="SaveInformation()">保存</a>  
		</div>
</form>
		</div>
	</div>
	
</body>
</html>


    
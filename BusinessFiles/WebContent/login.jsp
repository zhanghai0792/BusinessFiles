<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<title>教师业务管理系统</title>
<link rel="Shortcut Icon" href="images/favicon.ico">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script type="text/javascript" src="js/jquery.md5.js"></script>
<SCRIPT language=javascript type=text/javascript>
	
	//换一张
	function loadimage(){
		document.getElementById("randImage").src = "image.jsp?" + Math.random();//禁止缓存
	}
	function login(){
		//alert($.md5($('#password').val()));
		//$('#password').val($.md5($('#password').val()));
		adminlogin.submit();
		return false;
	}
	function reset(){
		adminlogin.reset();return false;
	}
	$(function() {
		var info = $("#info").val();
		if (info != "") {
			//showErrorInfo("hideBatchDeleteBox",info);
			alert(info);
		}
		<%--if('${message}' != ''){
			alert('${message}');
		}
		--%>
	});
	</SCRIPT>

<STYLE type=text/css>
BODY {
	TEXT-ALIGN: center;
	PADDING-BOTTOM: 0px;
	BACKGROUND-COLOR: #ddeef2;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	PADDING-TOP: 0px
}

A:link {
	COLOR: #000000;
	TEXT-DECORATION: none
}

A:visited {
	COLOR: #000000;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: #ff0000;
	TEXT-DECORATION: underline
}

A:active {
	TEXT-DECORATION: none
}

.input {
	BORDER-BOTTOM: #ccc 1px solid;
	BORDER-LEFT: #ccc 1px solid;
	LINE-HEIGHT: 20px;
	WIDTH: 182px;
	HEIGHT: 20px;
	BORDER-TOP: #ccc 1px solid;
	BORDER-RIGHT: #ccc 1px solid
}

.input1 {
	BORDER-BOTTOM: #ccc 1px solid;
	BORDER-LEFT: #ccc 1px solid;
	LINE-HEIGHT: 20px;
	WIDTH: 120px;
	HEIGHT: 20px;
	BORDER-TOP: #ccc 1px solid;
	BORDER-RIGHT: #ccc 1px solid;
}
</STYLE>
</head>
<body>

<input id="info" value="${message}" type="hidden"/>

<!--  <s:fielderror fieldName="userinfo"/>
<s:fielderror>
<s:param>userinfo</s:param>
</s:fielderror>-->

<FORM id=adminlogin  method=post name=adminlogin  action="login" ><%-- action="user!login" --%>
<DIV></DIV>
<TABLE style="MARGIN: auto; WIDTH: 100%; HEIGHT: 100%" border=0
	cellSpacing=0 cellPadding=0>
	<TBODY>
		<TR>
			<TD height=150>&nbsp;</TD>
		</TR>
		<TR style="HEIGHT: 254px">
			<TD>
			<DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG style="DISPLAY: block" src="images/body_03.jpg"></DIV>
			<DIV style="BACKGROUND-COLOR: #278296">
			<DIV style="MARGIN: 0px auto; WIDTH: 936px">
			<DIV style="BACKGROUND: url(images/body_05.jpg) no-repeat; HEIGHT: 155px">
			<DIV style="TEXT-ALIGN: left; WIDTH: 265px; FLOAT: right; HEIGHT: 125px; _height: 95px">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
				<TBODY>
					<TR>
						<TD style="HEIGHT: 45px"><INPUT type="text" class=input value="" name="teacher.id" id="id"></TD>
					</TR>
					<TR>
						<TD><INPUT type="password" class=input value="" name="teacher.password" id="password" 
						oncopy="return false" onpaste="return false" oncut="return false" oncontextmenu="return false"/></TD>
						<%--
						${teacher.password }
					--%></TR>
					<TR>
						<TD style="HEIGHT: 50px">
							<label style="font-size: 8pt"> (用户名为工号，默认密码为身份证号后6位)</label>
						</TD>
					</TR>

				</TBODY>
			</TABLE>
			</DIV>
			<DIV style="HEIGHT: 1px; CLEAR: both"></DIV>
			<DIV style="WIDTH: 380px; FLOAT: right; CLEAR: both">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width=300>
				<TBODY>
					<TR>
						<TD width=100 align=right>
							<!-- 登入 -->
							<INPUT style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px" id=btnLogin src="images/btn1.jpg" type=image name=btnLogin onclick="login()">
						</TD>
						<TD width=100 align=middle>
							<!-- 重置 -->
							<INPUT style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px" id=btnReset src="images/btn2.jpg" type=image name=btnReset onclick="reset()">
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</DIV>
			</DIV>
			</DIV>
			</DIV>
			<DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG src="images/body_06.jpg"></DIV>
			</TD>
		</TR>
		<TR style="HEIGHT: 30%">
			<TD>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
<%--<s:debug/>--%>
</body>
</html>
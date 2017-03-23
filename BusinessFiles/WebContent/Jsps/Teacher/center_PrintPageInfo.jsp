 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

 <script type="text/javascript" charset="utf-8">
 
$(function(){

 $('#PrintPageInfoTab').tabs({ 
				fit:true,
				border:false,
	});

 
});


 </script>
 
 
<div  id="PrintPageInfoTab" class="easyui-tabs">
	<div title="教师业务情况登记表" href='Jsps/Teacher/PrintTeacher.jsp' align="center" closable="true" data-options=""></div>
	<div title="个人情况简表" href='Jsps/Teacher/PrintPersonInfo.jsp' align="center" closable="true" data-options=""></div>
</div>
 

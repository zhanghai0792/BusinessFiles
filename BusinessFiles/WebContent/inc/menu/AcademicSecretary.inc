<%
	String panelIndex = request.getParameter("panelIndex");
 %>
<div class="easyui-accordion">
	<div title="学术研究" <%if("2".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/PaperResearch.jsp?panelIndex=2">学术论文</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/ScientificResearch.jsp?panelIndex=2" >科研课题</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/EducationalResearch.jsp?panelIndex=2">教改课题</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/Books.jsp?panelIndex=2" >专著|教材</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/Patent.jsp?panelIndex=2" >专利</a>
	</div>
	<div title="获奖情况" <%if("3".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/TeachingAchievement.jsp?panelIndex=3">教学成果及奖励</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/ScientificAchievement.jsp?panelIndex=3" >科研成果奖</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/TeachingMatch.jsp?panelIndex=3">教学比赛获奖</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/GuideStudent.jsp?panelIndex=3" >指导学生获奖</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/AcademicSecretary/TeachingOtherPrize.jsp?panelIndex=3" >其他获奖</a>
	</div>
</div>
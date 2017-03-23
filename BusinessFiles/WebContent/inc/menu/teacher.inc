<%
	String panelIndex = request.getParameter("panelIndex");
 %>
<div class="easyui-accordion">
	<div title="个人信息" <%if("0".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/BaseInfo.jsp?panelIndex=0">基本信息</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/LearningExperience.jsp?panelIndex=0" >学习经历</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/WorkingExperience.jsp?panelIndex=0">工作经历</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/Certificates.jsp?panelIndex=0" >证书信息</a>
	</div>
	<div title="教学工作" <%if("1".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/TeachingWorkload.jsp?panelIndex=1">教学工作量核对</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/TeachingMaterial.jsp?panelIndex=1" >教材使用</a>
	</div>	
	<div title="学术研究" <%if("2".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/PaperResearch.jsp?panelIndex=2">学术论文</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/ScientificResearch.jsp?panelIndex=2" >科研课题</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/EducationalResearch.jsp?panelIndex=2">教改课题</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/Books.jsp?panelIndex=2" >专著|教材</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/Patent.jsp?panelIndex=2" >专利</a>
	</div>
	<div title="获奖情况" <%if("3".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/TeachingAchievement.jsp?panelIndex=3">教学成果及奖励</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/ScientificAchievement.jsp?panelIndex=3" >科研成果奖</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/TeachingMatch.jsp?panelIndex=3">教学比赛获奖</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/GuideStudent.jsp?panelIndex=3" >指导学生获奖</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/TeachingOtherPrize.jsp?panelIndex=3" >其他获奖</a>
	</div>
	<div title="其它工作" <%if("4".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/Group.jsp?panelIndex=4">学术团体</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/Meeting.jsp?panelIndex=4" >学术会议</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/Training.jsp?panelIndex=4">进修培训</a>
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Teacher/Others.jsp?panelIndex=4" >其它</a>
	</div>
</div>
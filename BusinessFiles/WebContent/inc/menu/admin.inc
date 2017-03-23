<%
	String panelIndex = request.getParameter("panelIndex");
 %>
<div class="easyui-accordion">
	<div title="职工管理" <%if("0".equals(panelIndex)){ %>data-options="selected:true"<%} %> style="overflow:auto;padding:10px;">	
		<a style="width:100%;text-align:left;font-family: 微软雅黑" data-options="plain:true" class="easyui-linkbutton" href="Jsps/Admin/Admin.jsp?panelIndex=0">职工管理</a>
	</div>
</div>
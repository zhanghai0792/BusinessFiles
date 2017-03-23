<div id="dialog_scientific" class="easyui-dialog" style="width:900px;height:600px;" closed="true"
	data-options="iconCls: 'icon-save',buttons: '#dlg-buttons'"	align="center" title="科研详情">
	<div style="width:100%;height:50%;">
	<table id="table_check_paper" class="easyui-datagrid" title="学术论文详情"
		data-options="fit:true,showFooter: true,fitColumns:true,nowrap: false,striped: true"
		style="font-size: 14px;">
		<thead>
			<tr>
				<th data-options="field:'number'" width="80" align="center">序号</th>
				<th data-options="field:'paper'"	width=" 260" align="center">论文篇名</th>
				<th
					data-options="field:'publishTime'" width="120" align="center">发表时间</th>
				<th
					data-options="field:'publication'"	width="240" align="center">发表刊物</th>
				<th
					data-options="field:'rank'"	width="100" align="center">本人排名</th>
				<th
					data-options="field:'total'" width="100" align="center">总人数</th>
				<th
					data-options="field:'score'" width="100" align="center">分值</th>
			</tr>
		</thead>
	</table>
	</div>
	<div style="width:100%;height:50%;">
	<table id="table_check_research" class="easyui-datagrid" title="科研课题详情"
		data-options="fit:true,showFooter: true,fitColumns:true,nowrap: false,striped: true"
		style="font-size: 14px;">
		<thead>
		<tr>
			<th
				data-options="field:'number'" width="80" align="center" width="80" align="center">序号</th>
			<th
				data-options="field:'topic'" width="260" align="center" width="260" align="center">课题名称</th>
			<th
				data-options="field:'projectTime'" width="120" align="center">立项时间</th>
			<th
				data-options="field:'approver'"	width="220" align="center" width="260" align="center">批准（合作）单位</th>
			<th
				data-options="field:'rank'" width="100" align="center">本人排名</th>
			<th
				data-options="field:'total'" width="100" align="center">总人数</th>
			<th
				data-options="field:'score'" width="100" align="center">分值</th>
		</tr>
	</thead>
	</table>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		ExecuteQueryDw();
	});

	//执行查询
	function ExecuteQueryDw() {
		var url_cp = "/BusinessFiles/servlet/GetPaperServlet?teacherId=14181";
		var url_cr = "/BusinessFiles/servlet/GetScientificServlet?teacherId=14181";
		$('#table_check_paper').datagrid({
			url : url_cp
		});
		$('#table_check_research').datagrid({
			url : url_cr
		});
	}
</script>
<div id="dialog_workload" class="easyui-dialog"
	style="width:900px;height:400px;" closed="true"
	data-options="iconCls: 'icon-save',
				buttons: '#dlg-buttons'"
	title="查看教师教学详情" align="center">
	<table id="table_check_workload" class="easyui-datagrid"
		data-options="fit:true,showFooter: true,fitColumns:true,nowrap: false,striped: true"
		style="font-size: 14px">
		<thead>
			<tr>
				<th data-options="field:'number'" width="80" align="center">序号</th>
				<th data-options="field:'course'" width="240" align="center">任教课程名称</th>
				<th data-options="field:'class'" width="200" align="center">班级</th>
				<th data-options="field:'material'" width="240" align="center">教材版别</th>
				<th data-options="field:'period'" width="200" align="center">总课时</th>
			</tr>
		</thead>
	</table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		ExecuteQueryDw();
	});

	//执行查询
	function ExecuteQueryDw() {
		var url_dw = "/BusinessFiles/servlet/GetWorkloadServlet";
		$('#table_check_workload').datagrid({
			url : url_dw,
			queryParams : {
				category : 1,
				teacherId : 14181
			}
		});
	}
</script>
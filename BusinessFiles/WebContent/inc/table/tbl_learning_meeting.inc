<table id="table_learning_meeting" class="easyui-datagrid"  
	data-options="fit:true,toolbar:'#toolbar_learning_meeting',fitColumns:true,nowrap: false,striped: true" style="font-size: 14px">
	<thead>
		<tr>
			<th data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}" width="80" align="center">序号</th>
			<th data-options="field:'name',editor :{ type : 'textbox',options : { required : true }}" width="280" align="center">学术会议名称</th>
			<th data-options="field:'major',editor :{ type : 'textbox',options : { required : true }}" width="200" align="center">专业方向</th>
			<th data-options="field:'organization',editor :{ type : 'textbox',options : { required : true }}" width="240" align="center">组委会</th>
			<th data-options="field:'time',editor :{ type : 'datebox',options : { required : true }}" width="200" align="center">会议时间</th>
			<th data-options="field:'address',editor :{ type : 'textbox',options : { required : true }}" width="200" align="center">会议地点</th>
		</tr>
	</thead>
</table>
<div id="toolbar_learning_meeting" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendLm()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteLm()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateLm()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveLm()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectLm()">撤销</a>
</div>
<script>
	var editRow = undefined;

	function AppendLm() {
		if (editRow == undefined) {
			editRow = $('#table_learning_meeting').datagrid('getRows').length;
			$("#table_learning_meeting").datagrid('unselectAll');
			$("#table_learning_meeting").datagrid('insertRow', {
				index : editRow,
				row : {}
			});
			$("#table_learning_meeting").datagrid('beginEdit', editRow);
		}
	}

	function DeleteLm() {
		if (editRow == undefined) {
			var row = $('#table_learning_meeting').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_learning_meeting').datagrid(
								'getRowIndex', row);
						$('#table_learning_meeting').datagrid('deleteRow', rowIndex);
						$.messager.show({
							title : '我的消息',
							msg : '删除成功!',
							showType : 'show',
							timeout : 3000,
							style : {
								right : '',
								top : document.body.scrollTop
										+ document.documentElement.scrollTop,
								bottom : ''
							}
						});
						editRow = undefined;
					}
				});
			}
		}
	}
	function SaveLm() {
		if (editRow != undefined) {
			$("#table_learning_meeting").datagrid('endEdit', editRow);
			editRow = undefined;
		}
	}

	function UpdateLm() {
		if (editRow == undefined) {
			var row = $('#table_learning_meeting').datagrid('getSelected');
			var index = $('#table_learning_meeting').datagrid('getRowIndex', row);
			$('#table_learning_meeting').datagrid('beginEdit', index);
			editRow = index;
		}
	}
	function rejectLm() {
		$('#table_learning_meeting').datagrid('rejectChanges');
		editRow = undefined;
	}
</script>
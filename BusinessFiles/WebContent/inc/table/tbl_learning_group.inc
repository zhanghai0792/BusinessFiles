<table id="table_learning_group" class="easyui-datagrid"  
	data-options="fit:true,toolbar:'#toolbar_learning_group',fitColumns:true,nowrap: false,striped: true" style="font-size: 14px">
	<thead>
		<tr>
			<th data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}" width="80" align="center">序号</th>
			<th data-options="field:'name',editor :{ type : 'textbox',options : { required : true }}" width="280" align="center">学术团体名称</th>
			<th data-options="field:'level',editor :{ type : 'textbox',options : { required : true }}" width="200" align="center">团体级别</th>
			<th data-options="field:'major',editor :{ type : 'textbox',options : { required : true }}" width="240" align="center">专业方向</th>
			<th data-options="field:'job',editor :{ type : 'textbox',options : { required : true }}" width="200" align="center">担任职务</th>
		</tr>
	</thead>
</table>
<div id="toolbar_learning_group" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendLg()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteLg()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateLg()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveLg()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectLg()">撤销</a>
</div>
<script>
	var editRow = undefined;

	function AppendLg() {
		if (editRow == undefined) {
			editRow = $('#table_learning_group').datagrid('getRows').length;
			$("#table_learning_group").datagrid('unselectAll');
			$("#table_learning_group").datagrid('insertRow', {
				index : editRow,
				row : {}
			});
			$("#table_learning_group").datagrid('beginEdit', editRow);
		}
	}

	function DeleteLg() {
		if (editRow == undefined) {
			var row = $('#table_learning_group').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_learning_group').datagrid(
								'getRowIndex', row);
						$('#table_learning_group').datagrid('deleteRow', rowIndex);
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
	function SaveLg() {
		if (editRow != undefined) {
			$("#table_learning_group").datagrid('endEdit', editRow);
			editRow = undefined;
		}
	}

	function UpdateLg() {
		if (editRow == undefined) {
			var row = $('#table_learning_group').datagrid('getSelected');
			var index = $('#table_learning_group').datagrid('getRowIndex', row);
			$('#table_learning_group').datagrid('beginEdit', index);
			editRow = index;
		}
	}
	function rejectLg() {
		$('#table_learning_group').datagrid('rejectChanges');
		editRow = undefined;
	}
</script>
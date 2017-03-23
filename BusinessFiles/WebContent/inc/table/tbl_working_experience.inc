<table id="table_working" class="easyui-datagrid" title="教师工作经历"
	data-options="fit:true,toolbar:'#toolbar_working',fitColumns:true,nowrap: false,striped: true,singleSelect: true,
				">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'numberbox',options : { required : true }}"
				width="80" align="center">序号</th>
			<th
				data-options="field:'company',editor :{ type : 'textbox',options : { required : true }}"
				width="240" align="center">工作单位</th>
			<th
				data-options="field:'role',editor :{ type : 'textbox',options : { required : true }}"
				width="240" align="center">职务</th>
			<th
				data-options="field:'startDate',editor :{ type : 'datebox',options : { required : true,editable:false }}"
				width="200" align="center">入职时间</th>
			<th
				data-options="field:'endDate',editor :{ type : 'datebox',options : { required : true,editable:false }}"
				width="200" align="center">离职时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar_working" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendWe()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteWe()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateWe()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveWe()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectWe()">撤销</a>
</div>
<script>
	var editRow_we = undefined;     
	function AppendWe() {
		if (editRow_we == undefined) {
			editRow_we = $('#table_working').datagrid('getRows').length;
			$("#table_working").datagrid('unselectAll');
			$("#table_working").datagrid('insertRow', {
				index : editRow_we,
				row : {}
			});
			$("#table_working").datagrid('beginEdit', editRow_we);
		}
	}

	function DeleteWe() {
		if (editRow_we == undefined) {
			var row = $('#table_working').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_working').datagrid(
								'getRowIndex', row);
						$('#table_working').datagrid('deleteRow', rowIndex);
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
						editRow_we = undefined;
					}
				});
			}
		}
	}
	function SaveWe() {
		if (editRow_we != undefined) {
			$("#table_working").datagrid('endEdit', editRow_we);
			editRow_we = undefined;
		}
	}

	function UpdateWe() {
		if (editRow_we == undefined) {
			var row = $('#table_working').datagrid('getSelected');
			var index = $('#table_working').datagrid('getRowIndex', row);
			$('#table_working').datagrid('beginEdit', index);
			editRow_we = index;
		}
	}
	function rejectWe() {
		$('#table_working').datagrid('rejectChanges');
		editRow_we = undefined;
	}
</script>
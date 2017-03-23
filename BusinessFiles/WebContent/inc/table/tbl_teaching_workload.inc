<table id="table_workload" class="easyui-datagrid"  
	data-options="fit:true,toolbar:'#toolbar_workload',showFooter: true,fitColumns:true,nowrap: false,striped: true" style="font-size: 14px">
	<thead>
		<tr>
			<th data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}" width="80" align="center">序号</th>
			<th data-options="field:'courseId',editor :{ type : 'textbox',options : { required : true }}" width="120" align="center">课程代码</th>
			<th data-options="field:'courseName',editor :{ type : 'textbox',options : { required : true }}" width="240" align="center">任教课程名称</th>
			<th data-options="field:'tclass',editor :{ type : 'textbox',options : { required : true }}" width="150" align="center">班级</th>
			<th data-options="field:'tclassNum',editor :{ type : 'textbox',options : { required : true }}" width="150" align="center">班级人数</th>
			<th data-options="field:'material',editor :{ type : 'textbox',options : { required : true }}" width="240" align="center">教材版别</th>
			<th data-options="field:'periodNum',editor :{ type : 'textbox',options : { required : true }}" width="200" align="center">总课时</th>
		</tr>
	</thead>
</table>
<div id="toolbar_workload" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendTw()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteTw()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateTw()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveTw()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectTw()">撤销</a>
</div>
<script>
	var editRow = undefined;
	$(function (){
	//alert("r45");
	});
	function AppendTw() {
		if (editRow == undefined) {
			editRow = $('#table_workload').datagrid('getRows').length;
			$("#table_workload").datagrid('unselectAll');
			$("#table_workload").datagrid('insertRow', {
				index : editRow,
				row : {}
			});
			$("#table_workload").datagrid('beginEdit', editRow);
		}
	}

	function DeleteTw() {
		if (editRow == undefined) {
			var row = $('#table_workload').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_workload').datagrid(
								'getRowIndex', row);
						$('#table_workload').datagrid('deleteRow', rowIndex);
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
	function SaveTw() {
		if (editRow != undefined) {
			$("#table_workload").datagrid('endEdit', editRow);
			editRow = undefined;
		}
	}

	function UpdateTw() {
		if (editRow == undefined) {
			var row = $('#table_workload').datagrid('getSelected');
			var index = $('#table_workload').datagrid('getRowIndex', row);
			$('#table_workload').datagrid('beginEdit', index);
			editRow = index;
		}
	}
	function rejectTw() {
		$('#table_workload').datagrid('rejectChanges');
		editRow = undefined;
	}
</script>
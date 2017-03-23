<table id="table_training_study" class="easyui-datagrid"  
	data-options="fit:true,toolbar:'#toolbar_training_study',fitColumns:true,nowrap: false,striped: true" style="font-size: 14px">
	<thead>
		<tr>
			<th data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}" width="80" align="center">序号</th>
			<th data-options="field:'type',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: names_ts}}" width="180" align="center" formatter: "unitformatter_ts">类型</th>
			<th data-options="field:'direction',editor :{ type : 'textbox',options : { required : true }}" width="260" align="center">学习内容</th>
			<th data-options="field:'startDate',editor :{ type : 'datebox',options : { required : true }}" width="160" align="center">开始时间</th>
			<th data-options="field:'totalDays',editor :{ type : 'textbox',options : { required : true }}" width="160" align="center">总天数</th>
			<th data-options="field:'address',editor :{ type : 'textbox',options : { required : true }}" width="240" align="center">地点</th>
		</tr>
	</thead>
</table>
<div id="toolbar_training_study" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendTs()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteTs()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateTs()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveTs()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectTs()">撤销</a>
</div>
<script>
	var names_ts = [{ "value": "1", "text": "进修学习" }, { "value": "2", "text": "短期培训" },{ "value": "3", "text": "企业挂职" }];
	
	function unitformatter_ts(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < names_ts.length; i++) {  
	        if (names_ts[i].value == value) {  
	            return names_ts[i].text;  
	        }  
	    }  
    } 

	var editRow_ts = undefined;

	function AppendTs() {
		if (editRow_ts == undefined) {
			editRow_ts = $('#table_training_study').datagrid('getRows').length;
			$("#table_training_study").datagrid('unselectAll');
			$("#table_training_study").datagrid('insertRow', {
				index : editRow_ts,
				row : {}
			});
			$("#table_training_study").datagrid('beginEdit', editRow_ts);
		}
	}

	function DeleteTs() {
		if (editRow_ts == undefined) {
			var row = $('#table_training_study').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_training_study').datagrid(
								'getRowIndex', row);
						$('#table_training_study').datagrid('deleteRow', rowIndex);
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
						editRow_ts = undefined;
					}
				});
			}
		}
	}
	function SaveTs() {
		if (editRow_ts != undefined) {
			$("#table_training_study").datagrid('endEdit', editRow_ts);
			editRow_ts = undefined;
		}
	}

	function UpdateTs() {
		if (editRow_ts == undefined) {
			var row = $('#table_training_study').datagrid('getSelected');
			var index = $('#table_training_study').datagrid('getRowIndex', row);
			$('#table_training_study').datagrid('beginEdit', index);
			editRow_ts = index;
		}
	}
	function rejectTs() {
		$('#table_training_study').datagrid('rejectChanges');
		editRow_ts = undefined;
	}
</script>
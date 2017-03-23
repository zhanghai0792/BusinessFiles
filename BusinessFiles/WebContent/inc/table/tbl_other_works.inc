<table id="table_other_works" class="easyui-datagrid"  
	data-options="fit:true,toolbar:'#toolbar_other_works',fitColumns:true,nowrap: false,striped: true" style="font-size: 14px">
	<thead>
		<tr>
			<th data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}" width="80" align="center">序号</th>
			<th data-options="field:'work',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: works}}" width="260" align="center" formatter: "unitformatter_ow">工作性质 </th>
			<th data-options="field:'department',editor :{ type : 'textbox',options : { required : true }}" width="260" align="center">部门</th>
			<th data-options="field:'time',editor :{ type : 'textbox',options : { required : true }}" width="160" align="center">持续时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar_other_works" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendOw()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteOw()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateOw()" >编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveOw()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectOw()">撤销</a>
</div>
<script>
	var works = [{ "value": "1", "text": "班主任" }, { "value": "2", "text": "教研室主任" },{ "value": "3", "text": "教研室副主任" }];
	
	function unitformatter_ow(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < works.length; i++) {  
	        if (works[i].value == value) {  
	            return works[i].text;  
	        }  
	    }  
    } 

	var editRow_ow = undefined;

	function AppendOw() {
		if (editRow_ow == undefined) {
			editRow_ow = $('#table_other_works').datagrid('getRows').length;
			$("#table_other_works").datagrid('unselectAll');
			$("#table_other_works").datagrid('insertRow', {
				index : editRow_ow,
				row : {}
			});
			$("#table_other_works").datagrid('beginEdit', editRow_ow);
		}
	}

	function DeleteOw() {
		if (editRow_ow == undefined) {
			var row = $('#table_other_works').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_other_works').datagrid(
								'getRowIndex', row);
						$('#table_other_works').datagrid('deleteRow', rowIndex);
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
						editRow_ow = undefined;
					}
				});
			}
		}
	}
	function SaveOw() {
		if (editRow_ow != undefined) {
			$("#table_other_works").datagrid('endEdit', editRow_ow);
			editRow_ow = undefined;
		}
	}

	function UpdateOw() {
		if (editRow_ow == undefined) {
			var row = $('#table_other_works').datagrid('getSelected');
			var index = $('#table_other_works').datagrid('getRowIndex', row);
			$('#table_other_works').datagrid('beginEdit', index);
			editRow_ow = index;
		}
	}
	function rejectOw() {
		$('#table_other_works').datagrid('rejectChanges');
		editRow_ow = undefined;
	}
</script>
<table id="table_learning" class="easyui-datagrid" title="教师学习经历（从大学填起）"
	data-options="fit:true,toolbar:'#toolbar_learning',fitColumns:true,nowrap: false,striped: true,singleSelect: true,url:'getLElist',">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'numberbox',options : { required : true }}"
				width="50" >序号</th>
			<th
				data-options="field:'level',editor :{ type : 'combobox',options : { required : true,editable:false,valueField:'text',
		textField:'text',data: levels}}"
				width="50"  >层次</th><!-- formatter:unitformatter -->
			<th
				data-options="field:'school',editor :{ type : 'textbox',options : { required : true }}"
				width="50" >学校</th>
			<th
				data-options="field:'department',editor :{ type : 'textbox',options : { required : true }}"
				width="50" >专业</th>
			<th
				data-options="field:'time',editor :{ type : 'datebox',options : { required : true,editable:false }}"
				width="50" >毕业时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar_learning" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendApps()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteApps()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateApps()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveApps()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectApps()">撤销</a>
</div>
<script>
	var editRow = undefined;
	var levels = [{ "value": "bkxs", "text": "本科学士" }, { "value": "bkss", "text": "本科硕士" }, { "value": "ssyjs", "text": "硕士研究生" },{ "bsyjs": "3", "text": "博士研究生" }];
	
	function unitformatter(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < levels.length; i++) {  
	        if (levels[i].value == value) {  
	            return levles[i].text;  
	        }  
	    }  
    } 
     
	function AppendApps() {
		if (editRow == undefined) {
			editRow = $('#table_learning').datagrid('getRows').length;
			$("#table_learning").datagrid('unselectAll');
			$("#table_learning").datagrid('insertRow', {
				index : editRow,
				row : {}
			});
			$("#table_learning").datagrid('beginEdit', editRow);
		}
	}

	function DeleteApps() {
		if (editRow == undefined) {
			var row = $('#table_learning').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_learning').datagrid(
								'getRowIndex', row);
						$('#table_learning').datagrid('deleteRow', rowIndex);
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
	function SaveApps() {
		if (editRow != undefined) {
			$("#table_learning").datagrid('endEdit', editRow);
			editRow = undefined;
		}
	}

	function UpdateApps() {
		if (editRow == undefined) {
			var row = $('#table_learning').datagrid('getSelected');
			var index = $('#table_learning').datagrid('getRowIndex', row);
			$('#table_learning').datagrid('beginEdit', index);
			editRow = index;
		}
	}
	function rejectApps() {
		$('#table_learning').datagrid('rejectChanges');
		editRow = undefined;
	}
</script>
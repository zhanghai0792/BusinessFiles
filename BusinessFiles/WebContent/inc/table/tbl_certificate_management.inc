<table id="table_certificate" class="easyui-datagrid" title="教师证书管理"
	data-options="fit:true,toolbar:'#toolbar_certficate',striped: true,view:detailview,fitColumns:true,singleSelect: true,onLoadSuccess:datagridLoadSuccess,detailFormatter:showDetail">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'numberbox',options : { required : true }}"
				width="80" align="center">序号</th>
			<th
				data-options="field:'name',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: names}}"
				width="200" align="center"formatter:"unitformatter">证书名称</th>
				
			<th data-options="field:'attachment'" width="260" align="center"
				formatter="fileboxformatter">电子附件</th>
			<!-- 加上附件的预览图效果 -->
		</tr>
	</thead>
</table>
<div id="toolbar_certficate" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendCm()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteCm()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateCm()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveCm()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectCm()">撤销</a>
</div>

<script>

	var names = [{ "value": "bkby", "text": "本科毕业证书" }, { "value": "bkxw", "text": "本科学位证书" },
	{ "value": "ssby", "text": "硕士毕业证书" }, { "value": "ssxw", "text": "硕士学位证书" },
	{ "value": "bsby", "text": "博士毕业证书" }, { "value": "bsxw", "text": "博士学位证书" },
	{ "bsyjs": "js", "text": "讲师职称资格证书" },{ "bsyjs": "fjs", "text": "副教授职称资格证书" },{ "bsyjs": "zjs", "text": "教授职称资格证书" }];
	
	function unitformatter(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < levels.length; i++) {  
	        if (names[i].value == value) {  
	            return names[i].text;  
	        }  
	    }  
    } 
    
	function datagridLoadSuccess(data) {
		$('.fileid').filebox({
			multiple : true,
			prompt : '选择文件',
			buttonText : '上传'

		});
	}
	function fileboxformatter(value, row, index) {
		return "<input class='fileid'     style='width:200px'>";
	}
	
	var editRow_cm = undefined;
	function AppendCm() {
		if (editRow_cm == undefined) {
			editRow_cm = $('#table_certificate').datagrid('getRows').length;
			$("#table_certificate").datagrid('unselectAll');
			$("#table_certificate").datagrid('insertRow', {
				index : editRow_cm,
				row : {}
			});
			$("#table_certificate").datagrid('beginEdit', editRow_cm);
		}
	}

	function DeleteCm() {
		if (editRow_cm == undefined) {
			var row = $('#table_certificate').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_certificate').datagrid(
								'getRowIndex', row);
						$('#table_certificate').datagrid('deleteRow', rowIndex);
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
						editRow_cm = undefined;
					}
				});
			}
		}
	}
	function SaveCm() {
		if (editRow_cm != undefined) {
			$("#table_certificate").datagrid('endEdit', editRow_cm);
			editRow_cm = undefined;
		}
	}

	function UpdateCm() {
		if (editRow_cm == undefined) {
			var row = $('#table_certificate').datagrid('getSelected');
			var index = $('#table_certificate').datagrid('getRowIndex', row);
			$('#table_certificate').datagrid('beginEdit', index);
			editRow_cm = index;
		}
	}
	function rejectCm() {
		$('#table_certificate').datagrid('rejectChanges');
		editRow_cm = undefined;
	}
	function showDetail(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
</script>
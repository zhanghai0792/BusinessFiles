<table id="table_otherPrize" class="easyui-datagrid"
	data-options="fit:true,toolbar:'#toolbar_otherPrize',onLoadSuccess:datagridLoadSuccess_to,fitColumns:true,nowrap: false,striped: true,view:detailview,detailFormatter:showDetail_to"
	style="font-size: 14px">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'numberbox',options : { required : true }}"
				width="80" align="center">序号</th>
			<th
				data-options="field:'prize',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: prizes_to}}"
				width="240" align="center">奖项</th>
			<th
				data-options="field:'level',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: levels_to}}"
				width="200" align="center"formatter: "unitformatter_to">奖励级别</th>
			<th
				data-options="field:'time',editor :{ type : 'datebox',options : { required : true,editable:false }}"
				width="140" align="center">时间</th>
			<th data-options="field:'attachment'" width="240" align="center"
				formatter="fileboxformatter_to">附件</th>
		</tr>
	</thead>
</table>
<div id="toolbar_otherPrize" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendTo()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteTo()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateTo()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveTo()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectTo()">撤销</a>
</div>
<script>
	var prizes_to = [{ "value": "1", "text": "优秀教师" }, { "value": "2", "text": "优秀班主任" }, { "value": "3", "text": "优秀指导教师" }, { "value": "4", "text": "优秀辅导员" },{ "value": "5", "text": "先进工作者" }];
	var levels_to = [{ "value": "1", "text": "国家级" }, { "value": "2", "text": "省部级" }, { "value": "3", "text": "市厅级" },{ "value": "4", "text": "校级" }];
	
	function unitformatter_to(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < prizes_to.length; i++) {  
	        if (prizes_to[i].value == value) {  
	            return prizes_to[i].text;  
	        }  
	    }  
	    for (var i = 0; i < levels_to.length; i++) {  
	        if (levels_to[i].value == value) {  
	            return levels_to[i].text;  
	        }  
	    } 
    } 

	function datagridLoadSuccess_to(data) {
		$('.fileid').filebox({
			multiple : true,
			prompt : '选择文件',
			buttonText : '上传'
		});
		
	}
	function fileboxformatter_to(value, row, index) {
		return "<input class='fileid'     style='width:180px'>";
	}

	var editRow_to = undefined;
	function AppendTo() {
		if (editRow_to == undefined) {
			editRow_to = $('#table_otherPrize').datagrid('getRows').length;
			$('#table_otherPrize').datagrid('unselectAll');
			$('#table_otherPrize').datagrid('insertRow', {
				index : editRow_to,
				row : {}
			});
			$('#table_otherPrize').datagrid('beginEdit', editRow_to);
		}
	}

	function DeleteTo() {
		if (editRow_to == undefined) {
			var row = $('#table_otherPrize').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_otherPrize').datagrid(
								'getRowIndex', row);
						$('#table_otherPrize').datagrid('deleteRow', rowIndex);
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
						editRow_to = undefined;
					}
				});
			}
		}
	}
	function SaveTo() {
		if (editRow_to != undefined) {
			$("#table_otherPrize").datagrid('endEdit', editRow_to);
			editRow_to = undefined;
		}
	}

	function UpdateTo() {
		if (editRow_to == undefined) {
			var row = $('#table_otherPrize').datagrid('getSelected');
			var index = $('#table_otherPrize').datagrid('getRowIndex', row);
			$('#table_otherPrize').datagrid('beginEdit', index);
			editRow_to = index;
		}
	}
	function rejectTo() {
		$('#table_otherPrize').datagrid('rejectChanges');
		editRow_to = undefined;
	}
	function showDetail_to(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
</script>
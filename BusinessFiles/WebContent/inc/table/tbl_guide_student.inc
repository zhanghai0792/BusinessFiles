<table id="table_guide" class="easyui-datagrid"  
	data-options="fit:true,toolbar:'#toolbar_guide',onLoadSuccess:datagridLoadSuccess_gs,fitColumns:true,nowrap: false,striped: true,view:detailview,detailFormatter:showDetail_gs" style="font-size: 14px">
	<thead>
		<tr>
			<th data-options="field:'number',editor :{ type : 'numberbox',options : { required : true }}" width="80" align="center">序号</th>
			<th data-options="field:'prize',editor :{ type : 'textbox',options : { required : true }}" width="310" align="center">奖项</th>
			<th data-options="field:'level',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: levels}}" width="100" align="center" formatter: "unitformatter_gs">奖励级别</th>
			<th data-options="field:'order',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: orders}}" width="110" align="center" formatter: "unitformatter_gs">等次</th>
			<th data-options="field:'time',editor :{ type : 'datebox',options : { required : true,editable:false }}" width="120" align="center">时间</th>
			<th
				data-options="field:'score',editor :{ type : 'textbox',options : { required : true }}"
				width="100" align="center">分值</th>
			<th data-options="field:'attachment'" width="240" align="center" formatter="fileboxformatter_gs">附件</th>
		</tr>
	</thead>
</table>
<div id="toolbar_guide" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendGs()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteGs()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateGs()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveGs()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectGs()">撤销</a>
</div>
<script>
	var levels = [{ "value": "1", "text": "国家级" }, { "value": "2", "text": "省部级" }, { "value": "3", "text": "市厅级" }];
	var orders = [{ "value": "1", "text": "特等奖" }, { "value": "2", "text": "一等奖" }, { "value": "3", "text": "二等奖" }, { "value": "4", "text": "三等奖" }];
	
	function unitformatter_gs(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < levels.length; i++) {  
	        if (levels[i].value == value) {  
	            return levels[i].text;  
	        }  
	    }  
	    for (var i = 0; i < orders.length; i++) {  
	        if (orders[i].value == value) {  
	            return orders[i].text;  
	        }  
	    }  
    } 

	function datagridLoadSuccess_gs(data) {
		$('.fileid').filebox({
			multiple : true,
			prompt : '选择文件',
			buttonText : '上传'
		});
		
	}
	function fileboxformatter_gs(value, row, index) {
		return "<input class='fileid'     style='width:180px'>";
	}

	var editRow_gs = undefined;
	function AppendGs() {
		if (editRow_gs == undefined) {
			editRow_gs = $('#table_guide').datagrid('getRows').length;
			$("#table_guide").datagrid('unselectAll');
			$("#table_guide").datagrid('insertRow', {
				index : editRow_gs,
				row : {}
			});
			$("#table_guide").datagrid('beginEdit', editRow_gs);
		}
	}

	function DeleteGs() {
		if (editRow_gs == undefined) {
			var row = $('#table_guide').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_guide').datagrid(
								'getRowIndex', row);
						$('#table_guide').datagrid('deleteRow', rowIndex);
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
						editRow_gs = undefined;
					}
				});
			}
		}
	}
	function SaveGs() {
		if (editRow_gs != undefined) {
			$("#table_guide").datagrid('endEdit', editRow_gs);
			editRow_gs = undefined;
		}
	}

	function UpdateGs() {
		if (editRow_gs == undefined) {
			var row = $('#table_guide').datagrid('getSelected');
			var index = $('#table_guide').datagrid('getRowIndex', row);
			$('#table_guide').datagrid('beginEdit', index);
			editRow_gs = index;
		}
	}
	function rejectGs() {
		$('#table_guide').datagrid('rejectChanges');
		editRow_gs = undefined;
	}
	function showDetail_gs(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
	</script>
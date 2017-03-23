<table id="table_achievement" class="easyui-datagrid"
	data-options="fit:true,toolbar:'#toolbar_achievement',onLoadSuccess:datagridLoadSuccess_ta,fitColumns:true,nowrap: false,striped: true,view:detailview,detailFormatter:showDetail_ta"
	style="font-size: 14px">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'numberbox',options : { required : true }}"
				width="80" align="center">序号</th>
			<th
				data-options="field:'prize',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: prizes}}"
				width="200" align="center">奖项</th>
			<th
				data-options="field:'level',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: levels_ta}}"
				width="110" align="center"formatter: "unitformatter_ta">奖励级别</th>
			<th
				data-options="field:'order',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: orders_ta}}"
				width="100" align="center"formatter: "unitformatter_ta">等次</th>
			<th
				data-options="field:'time',editor :{ type : 'datebox',options : { required : true,editable:false }}"
				width="120" align="center">时间</th>
			<th
				data-options="field:'rank',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks}}"
				width="100" align="center"formatter: "unitformatter_ta">本人排名</th>
			<th
				data-options="field:'total',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks}}"
				width="100" align="center"formatter: "unitformatter_ta">总人数</th>
			<th
				data-options="field:'score',editor :{ type : 'textbox',options : { required : true }}"
				width="100" align="center">分值</th>
			<th data-options="field:'attachment'" width="240" align="center"
				formatter="fileboxformatter_ta">附件</th>
		</tr>
	</thead>
</table>
<div id="toolbar_achievement" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendTa()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteTa()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateTa()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveTa()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectTa()">撤销</a>
</div>
<script>
	var prizes = [{ "value": "1", "text": "教学成果奖" }, { "value": "2", "text": "优秀教材奖" }, { "value": "3", "text": "教学竞赛奖" }];
	var levels_ta = [{ "value": "1", "text": "国家级" }, { "value": "2", "text": "省部级" }, { "value": "3", "text": "市厅级" }];
	var orders_ta = [{ "value": "1", "text": "特等奖" }, { "value": "2", "text": "一等奖" }, { "value": "3", "text": "二等奖" }, { "value": "4", "text": "三等奖" }];
	var ranks = [{ "value": "1", "text": "1" }, { "value": "2", "text": "2" }, { "value": "3", "text": "3" },{ "value": "4", "text": "4" },{ "value": "5", "text": "5" },{ "value": "6", "text": "6" }];
	
	function unitformatter_ta(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < ranks.length; i++) {  
	        if (ranks[i].value == value) {  
	            return ranks[i].text;  
	        }  
	    }  
	    for (var i = 0; i < prizes.length; i++) {  
	        if (prizes[i].value == value) {  
	            return prizes[i].text;  
	        }  
	    }  
	    for (var i = 0; i < levels_ta.length; i++) {  
	        if (levels_ta[i].value == value) {  
	            return levels_ta[i].text;  
	        }  
	    }  
	    for (var i = 0; i < orders_ta.length; i++) {  
	        if (orders_ta[i].value == value) {  
	            return orders_ta[i].text;  
	        }  
	    }  
    } 

	function datagridLoadSuccess_ta(data) {
		$('.fileid').filebox({
			multiple : true,
			prompt : '选择文件',
			buttonText : '上传'
		});
		
	}
	function fileboxformatter_ta(value, row, index) {
		return "<input class='fileid'     style='width:180px'>";
	}

	var editRow_ta = undefined;
	function AppendTa() {
		if (editRow_ta == undefined) {
			editRow_ta = $('#table_achievement').datagrid('getRows').length;
			$('#table_achievement').datagrid('unselectAll');
			$('#table_achievement').datagrid('insertRow', {
				index : editRow_ta,
				row : {}
			});
			$('#table_achievement').datagrid('beginEdit', editRow_ta);
		}
	}

	function DeleteTa() {
		if (editRow_ta == undefined) {
			var row = $('#table_achievement').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_achievement').datagrid(
								'getRowIndex', row);
						$('#table_achievement').datagrid('deleteRow', rowIndex);
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
						editRow_ta = undefined;
					}
				});
			}
		}
	}
	function SaveTa() {
		if (editRow_ta != undefined) {
			$("#table_achievement").datagrid('endEdit', editRow_ta);
			editRow_ta = undefined;
		}
	}

	function UpdateTa() {
		if (editRow_ta == undefined) {
			var row = $('#table_achievement').datagrid('getSelected');
			var index = $('#table_achievement').datagrid('getRowIndex', row);
			$('#table_achievement').datagrid('beginEdit', index);
			editRow_ta = index;
		}
	}
	function rejectTa() {
		$('#table_achievement').datagrid('rejectChanges');
		editRow_ta = undefined;
	}
	function showDetail_ta(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
</script>
<table id="table_paper" class="easyui-datagrid"
	data-options="fit:true,toolbar:'#toolbar_paper',fitColumns:true,nowrap: false,view:detailview,striped: true,onLoadSuccess:datagridLoadSuccess,detailFormatter:showDetail"
	style="font-size: 14px">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}"
				width="80" align="center">序号</th>
			<th
				data-options="field:'paperTitle',editor :{ type : 'textbox',options : { required : true }}"
				width="260" align="center">论文篇名</th>
			<th
				data-options="field:'publishDate',editor :{ type : 'datebox',options : { required : true }}"
				width="120" align="center">发表时间</th>
			<th
				data-options="field:'publication',editor :{ type : 'textbox',options : { required : true }}"
				width="240" align="center">发表刊物</th>
			<th
				data-options="field:'publishNum',editor :{ type : 'textbox',options : { required : true }}"
				width="120" align="center">刊号</th>
			<th
				data-options="field:'level',editor :{ type : 'combobox',options : { required : true,editable:false,valueField:'text',
		textField:'text',data: level_pr}}"
				width="120" align="center" formatter: unitformatter_tp>刊物等级</th>
			<th
				data-options="field:'rank',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks}}"
				width="80" align="center" formatter: "unitformatter_tp">本人排名</th>
			<th
				data-options="field:'totalNum',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks}}"
				width="80" align="center" formatter: "unitformatter_tp">总人数</th>
			<th
				data-options="field:'score',editor :{ type : 'numberbox',options : { required : true }}"
				width="100" align="center">分值</th>
			<th data-options="field:'attachment'" width="240" align="center"
				formatter="fileboxformatter_tp">附件</th>
		</tr>
	</thead>
</table>
<div id="toolbar_paper" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendTp()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteTp()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateTp()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveTp()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectTp()">撤销</a>
</div>
<script>
	var ranks = [{ "value": "1", "text": "1" }, { "value": "2", "text": "2" }, { "value": "3", "text": "3" },{ "value": "4", "text": "4" },{ "value": "5", "text": "5" },{ "value": "6", "text": "6" }];
	var level_pr = [{ "value": "1", "text": "北大图书馆核心期刊" }, { "value": "2", "text": "CSSCI来源期刊" }, { "value": "3", "text": "中国人文社会科学核心期刊" },{ "value": "4", "text": "EI期刊收录" }];
	
	function unitformatter_tp(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < ranks.length; i++) {  
	        if (ranks[i].value == value) {  
	            return ranks[i].text;  
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
	function fileboxformatter_tp(value, row, index) {
		return "<input class='fileid'     style='width:180px'>";
	}

	var editRow_tp = undefined;
	function AppendTp() {
		if (editRow_tp == undefined) {
			editRow_tp = $('#table_paper').datagrid('getRows').length;
			$('#table_paper').datagrid('unselectAll');
			$('#table_paper').datagrid('insertRow', {
				index : editRow_tp,
				row : {}
			});
			$('#table_paper').datagrid('beginEdit', editRow_tp);
		}
	}

	function DeleteTp() {
		if (editRow_tp == undefined) {
			var row = $('#table_paper').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_paper').datagrid(
								'getRowIndex', row);
						$('#table_paper').datagrid('deleteRow', rowIndex);
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
						editRow_tp = undefined;
					}
				});
			}
		}
	}
	function SaveTp() {
		if (editRow_tp != undefined) {
			$("#table_paper").datagrid('endEdit', editRow_tp);
			editRow_tp = undefined;
		}
	}

	function UpdateTp() {
		if (editRow_tp == undefined) {
			var row = $('#table_paper').datagrid('getSelected');
			var index = $('#table_paper').datagrid('getRowIndex', row);
			$('#table_paper').datagrid('beginEdit', index);
			editRow_tp = index;
		}
	}
	function rejectTp() {
		$('#table_paper').datagrid('rejectChanges');
		editRow_tp = undefined;
	}
	function showDetail(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
</script>
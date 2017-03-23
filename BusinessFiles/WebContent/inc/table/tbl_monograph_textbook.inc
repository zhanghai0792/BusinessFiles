<table id="table_textbook" class="easyui-datagrid"  
	data-options="fit:true,toolbar:'#toolbar_textbook',onLoadSuccess:datagridLoadSuccess_mt,fitColumns:true,nowrap: false,striped: true,view:detailview,detailFormatter:showDetail_mt" style="font-size: 14px">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}"
				width="80" align="center" width="80" align="center">序号</th>
			<th
				data-options="field:'bookName',editor :{ type : 'textbox',options : { required : true }}"
				width="260" align="center" width="260" align="center">专著/编写教材名称</th>
			<th
				data-options="field:'publishDate',editor :{ type : 'datebox',options : { required : true }}"
				width="120" align="center">出版时间</th>
			<th
				data-options="field:'publication',editor :{ type : 'textbox',options : { required : true }}"
				width="240" align="center" width="260" align="center">出版单位</th>
			<th
				data-options="field:'rank',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks_mt}}"
				width="100" align="center"formatter: "unitformatter_er">本人排名</th>
			<th
				data-options="field:'totalNum',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks_mt}}"
				width="100" align="center"formatter: "unitformatter_er">总人数</th>
			<th
				data-options="field:'score',editor :{ type : 'textbox',options : { required : true }}"
				width="100" align="center">分值</th>
			<th data-options="field:'attachment'" width="240" align="center"
				formatter="fileboxformatter_mt">附件</th>
		</tr>
	</thead>
</table>
<div id="toolbar_textbook" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendMt()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteMt()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateMt()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveMt()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectMt()">撤销</a>
</div>
<script>
	var ranks_mt = [{ "value": "1", "text": "1" }, { "value": "2", "text": "2" }, { "value": "3", "text": "3" },{ "value": "4", "text": "4" },{ "value": "5", "text": "5" },{ "value": "6", "text": "6" }
					,{ "value": "7", "text": "7" },{ "value": "8", "text": "8" },{ "value": "9", "text": "9" },{ "value": "10", "text": "10" }];
	
	function unitformatter_mt(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < ranks_mt.length; i++) {  
	        if (ranks_mt[i].value == value) {  
	            return ranks_mt[i].text;  
	        }  
	    }  
    } 

	function datagridLoadSuccess_mt(data) {
		$('.fileid').filebox({
			multiple : true,
			prompt : '选择文件',
			buttonText : '上传'
		});
		
	}
	function fileboxformatter_mt(value, row, index) {
		return "<input class='fileid'     style='width:180px'>";
	}

	var editRow_mt = undefined;
	function AppendMt() {
		if (editRow_mt == undefined) {
			editRow_mt = $('#table_textbook').datagrid('getRows').length;
			$("#table_textbook").datagrid('unselectAll');
			$("#table_textbook").datagrid('insertRow', {
				index : editRow_mt,
				row : {}
			});
			$("#table_textbook").datagrid('beginEdit', editRow_mt);
		}
	}

	function DeleteMt() {
		if (editRow_mt == undefined) {
			var row = $('#table_textbook').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_textbook').datagrid(
								'getRowIndex', row);
						$('#table_textbook').datagrid('deleteRow', rowIndex);
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
						editRow_mt = undefined;
					}
				});
			}
		}
	}
	function SaveMt() {
		if (editRow_mt != undefined) {
			$("#table_textbook").datagrid('endEdit', editRow_mt);
			editRow_mt = undefined;
		}
	}

	function UpdateMt() {
		if (editRow_mt == undefined) {
			var row = $('#table_textbook').datagrid('getSelected');
			var index = $('#table_textbook').datagrid('getRowIndex', row);
			$('#table_textbook').datagrid('beginEdit', index);
			editRow_mt = index;
		}
	}
	function rejectMt() {
		$('#table_textbook').datagrid('rejectChanges');
		editRow_mt = undefined;
	}
	function showDetail_mt(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
</script>
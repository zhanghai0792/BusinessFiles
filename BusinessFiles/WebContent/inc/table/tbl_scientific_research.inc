<table id="table_scientific" class="easyui-datagrid"
	data-options="fit:true,toolbar:'#toolbar_scientific',onLoadSuccess:datagridLoadSuccess_sr,fitColumns:true,nowrap: false,striped: true,view:detailview,detailFormatter:showDetail_sr"
	style="font-size: 14px">
	<thead>
		<tr>
			<th
				data-options="field:'number',editor :{ type : 'textbox',options : { required : true }}"
				width="80" align="center" width="80" align="center">序号</th>
			<th
				data-options="field:'topic',editor :{ type : 'textbox',options : { required : true }}"
				width="240" align="center" width="260" align="center">课题名称</th>
			<th
				data-options="field:'topicNum',editor :{ type : 'textbox',options : { required : true }}"
				width="120" align="center" width="80" align="center">立项编号</th>
			<th
				data-options="field:'startDate',editor :{ type : 'datebox',options : { required : true }}"
				width="120" align="center">立项时间</th>
			<th
				data-options="field:'endDate',editor :{ type : 'datebox',options : { required : true }}"
				width="120" align="center">结题时间</th>
			<th
				data-options="field:'approver',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: approvers}}"
				width="180" align="center" width="260" align="center" formatter: "unitformatter_sr">批准（合作）单位</th>
			<th
				data-options="field:'level',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: levels_sr}}"
				width="80" align="center" formatter: "unitformatter_sr">级别</th>
			<th
				data-options="field:'funds',editor :{ type : 'textbox',options : { required : true }}"
				width="100" align="center" width="260" align="center">经费</th>
			<th
				data-options="field:'rank',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks_sr}}"
				width="80" align="center"formatter: "unitformatter_sr">本人排名</th>
			<th
				data-options="field:'totalNum',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks_sr}}"
				width="80" align="center"formatter: "unitformatter_sr">总人数</th>
			<th
				data-options="field:'score',editor :{ type : 'textbox',options : { required : true }}"
				width="80" align="center">分值</th>
			<th data-options="field:'attachment'" width="240" align="center"
				formatter="fileboxformatter_sr">附件</th>
		</tr>
	</thead>
</table>
<div id="toolbar_scientific" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendSr()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteSr()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateSr()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveSr()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectSr()">撤销</a>
</div>
<script>
	var ranks_sr = [{ "value": "1", "text": "1" }, { "value": "2", "text": "2" }, { "value": "3", "text": "3" },{ "value": "4", "text": "4" },{ "value": "5", "text": "5" },{ "value": "6", "text": "6" }
					,{ "value": "7", "text": "7" },{ "value": "8", "text": "8" },{ "value": "9", "text": "9" },{ "value": "10", "text": "10" }];
	var levels_sr = [{ "value": "1", "text": "重点" }, { "value": "2", "text": "一般" }];
	var approvers = [{ "value": "1", "text": "江西省青年科学基金" }, { "value": "2", "text": "国家社科基金" }];
	
	function unitformatter_sr(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < ranks_sr.length; i++) {  
	        if (ranks_sr[i].value == value) {  
	            return ranks_sr[i].text;  
	        }  
	    }  
    } 

	function datagridLoadSuccess_sr(data) {
		$('.fileid').filebox({
			multiple : true,
			prompt : '选择文件',
			buttonText : '上传'
		});
		
	}
	function fileboxformatter_sr(value, row, index) {
		return "<input class='fileid'     style='width:180px'>";
	}

	var editRow_sr = undefined;
	function AppendSr() {
		if (editRow_sr == undefined) {
			editRow_sr = $('#table_scientific').datagrid('getRows').length;
			$("#table_scientific").datagrid('unselectAll');
			$("#table_scientific").datagrid('insertRow', {
				index : editRow_sr,
				row : {}
			});
			$("#table_scientific").datagrid('beginEdit', editRow_sr);
		}
	}

	function DeleteSr() {
		if (editRow_sr == undefined) {
			var row = $('#table_scientific').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_scientific').datagrid(
								'getRowIndex', row);
						$('#table_scientific').datagrid('deleteRow', rowIndex);
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
						editRow_sr = undefined;
					}
				});
			}
		}
	}
	function SaveSr() {
		if (editRow_sr != undefined) {
			$("#table_scientific").datagrid('endEdit', editRow_sr);
			editRow_sr = undefined;
		}
	}

	function UpdateSr() {
		if (editRow_sr == undefined) {
			var row = $('#table_scientific').datagrid('getSelected');
			var index = $('#table_scientific').datagrid('getRowIndex', row);
			$('#table_scientific').datagrid('beginEdit', index);
			editRow_sr = index;
		}
	}
	function rejectSr() {
		$('#table_scientific').datagrid('rejectChanges');
		editRow_sr = undefined;
	}
	function showDetail_sr(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
</script>
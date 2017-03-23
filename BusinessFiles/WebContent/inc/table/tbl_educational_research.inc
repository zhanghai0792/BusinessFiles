<table id="table_educational" class="easyui-datagrid"
	data-options="fit:true,toolbar:'#toolbar_educational',onLoadSuccess:datagridLoadSuccess_er,fitColumns:true,nowrap: false,striped: true,view:detailview,detailFormatter:showDetail_er"
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
				data-options="field:'startTime',editor :{ type : 'datebox',options : { required : true }}"
				width="120" align="center">立项时间</th>
			<th
				data-options="field:'endTime',editor :{ type : 'datebox',options : { required : true }}"
				width="120" align="center">结题时间</th>
			<th
				data-options="field:'approver',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: approvers_er}}"
				width="180" align="center" width="260" align="center" formatter: "unitformatter_sr">批准（合作）单位</th>
			<th
				data-options="field:'level',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: levels_er}}"
				width="80" align="center" formatter: "unitformatter_sr">级别</th>
			<th
				data-options="field:'funds',editor :{ type : 'textbox',options : { required : true }}"
				width="100" align="center" width="260" align="center">经费</th>
			<th
				data-options="field:'rank',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks_er}}"
				width="80" align="center"formatter: "unitformatter_sr">本人排名</th>
			<th
				data-options="field:'total',editor :{ type : 'combobox',options : { required : true,valueField:'text',
		textField:'text',data: ranks_er}}"
				width="80" align="center"formatter: "unitformatter_sr">总人数</th>
			<th
				data-options="field:'score',editor :{ type : 'textbox',options : { required : true }}"
				width="80" align="center">分值</th>
			<th data-options="field:'attachment'" width="240" align="center"
				formatter="fileboxformatter_sr">附件</th>
		</tr>
	</thead>
</table>
<div id="toolbar_educational" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"
		onClick="AppendEr()">添加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" onClick="DeleteEr()">删除</a> <a href="#"
		class="easyui-linkbutton" iconCls="icon-edit" onClick="UpdateEr()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save"
		onClick="SaveEr()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-undo" onClick="rejectEr()">撤销</a>
</div>
<script>
	var ranks_er = [{ "value": "1", "text": "1" }, { "value": "2", "text": "2" }, { "value": "3", "text": "3" },{ "value": "4", "text": "4" },{ "value": "5", "text": "5" },{ "value": "6", "text": "6" }
					,{ "value": "7", "text": "7" },{ "value": "8", "text": "8" },{ "value": "9", "text": "9" },{ "value": "10", "text": "10" }];
	var levels_er = [{ "value": "1", "text": "重点" }, { "value": "2", "text": "一般" }];
	var approvers_er = [{ "value": "1", "text": "江西省高等学校教学改革研究课题组" }, { "value": "2", "text": "国家教学改革研究课题组" }];
	
	function unitformatter_er(value, rowData, rowIndex) {  
	    if (value == 0) {  
	        return;  
	    }  	   
	    for (var i = 0; i < ranks_er.length; i++) {  
	        if (ranks_er[i].value == value) {  
	            return ranks_er[i].text;  
	        }  
	    }  
    } 

	function datagridLoadSuccess_er(data) {
		$('.fileid').filebox({
			multiple : true,
			prompt : '选择文件',
			buttonText : '上传'
		});
		
	}
	function fileboxformatter_er(value, row, index) {
		return "<input class='fileid'     style='width:180px'>";
	}

	var editRow_er = undefined;
	function AppendEr() {
		if (editRow_er == undefined) {
			editRow_er = $('#table_educational').datagrid('getRows').length;
			$("#table_educational").datagrid('unselectAll');
			$("#table_educational").datagrid('insertRow', {
				index : editRow_er,
				row : {}
			});
			$("#table_educational").datagrid('beginEdit', editRow_er);
		}
	}

	function DeleteEr() {
		if (editRow_er == undefined) {
			var row = $('#table_educational').datagrid('getSelected');
			if (row) {
				$.messager.confirm("提示", "您确定要删除这些数据吗？", function(res) {
					if (res) {
						var rowIndex = $('#table_educational').datagrid(
								'getRowIndex', row);
						$('#table_educational').datagrid('deleteRow', rowIndex);
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
						editRow_er = undefined;
					}
				});
			}
		}
	}
	function SaveEr() {
		if (editRow_er != undefined) {
			$("#table_educational").datagrid('endEdit', editRow_er);
			editRow_er = undefined;
		}
	}

	function UpdateEr() {
		if (editRow_er == undefined) {
			var row = $('#table_educational').datagrid('getSelected');
			var index = $('#table_educational').datagrid('getRowIndex', row);
			$('#table_educational').datagrid('beginEdit', index);
			editRow_er = index;
		}
	}
	function rejectEr() {
		$('#table_educational').datagrid('rejectChanges');
		editRow_er = undefined;
	}
	function showDetail_er(rowIndex, rowData)
	{ return '<table><tr>' + 
			 '<td rowspan=4 style="border:0"> <img src="images/' + rowData.number + '.png" style="height:200px;width=200px"> <img src="images/' + rowData.number + '1.png" style="height:200px;width=200px;padding-left:10px"></td>' + 
			 '</tr></table>';	}
	</script>
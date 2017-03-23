var editRow_LG = undefined;
function loadlearninggroup(){
	$('#table_learning_group').datagrid({
		 url:'/BusinessFiles/getLGlist',
			iconCls:'icon-save',
			//collapsible : true,
			rownumbers:true,
			singleSelect : true,
			//pagination:true,
			//pageSize:MypSize,
			//pageList:MypList,
			//fit:true,
			fitColumns:true,
			nowarp:true,
			border:false,
			//checkOnSelect: true,
 			//selectOnCheck: true,
			idField:'id',//翻页操作有效
			//frozenColumns:[[]],
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'学术团体名称',field:'name',width:100,align:'left',editor :{ type : 'textbox',options : { required : true }}},
	{title:'团体级别',field:'level',width:128,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}},
	{title:'专业方向',field:'major',width:128,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}},
	{title:'担任职务',field:'job',width:100,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true}}},
	]],
	onAfterEdit: function (rowIndex,rowData,changes) 
	{
		rowData.teacherId=$('#id').val();
		var inserted =$(this).datagrid('getChanges', "inserted");
		var updated = $(this).datagrid('getChanges', "updated");
		if(inserted.length<1 && updated.length<1){
			editRow_LG = undefined;
			$(this).datagrid('unselectAll');
				return;
			}
			if(inserted.length>0){
				toolAction.baseAjax("addLG",$(this),rowData,"增加成功");
			}else if(updated.length>0){ 
				toolAction.baseAjax("updateLG",$(this),rowData,"修改成功");
			}
			alert(editRow_LG);
			//editRow_LG = undefined;
			//updated.length=0;
	},
		 toolbar: [{
						text: '增加',
						iconCls: 'icon-add',
						handler: function () {
							if (editRow_LG == undefined) {
								$('#table_learning_group').datagrid('unselectAll');
									$('#table_learning_group').datagrid('insertRow', {index :0,row : {}});
									$('#table_learning_group').datagrid('beginEdit', 0);
									editRow_LG = 0;			
								}
						}
					}, "-",{
						text: '编辑',
						iconCls: 'icon-edit',
						handler: function () {
							if(editRow_LG == undefined){
								var row = $('#table_learning_group').datagrid('getSelected');
									if(row){
									var index = $('#table_learning_group').datagrid('getRowIndex',row);
									editRow_LG = index;
										$('#table_learning_group').datagrid('beginEdit', index);
								}
									}
						}
					},"-",{
						text: '撤销',
						iconCls: 'icon-redo',
						handler:function(){
							$('#table_learning_group').datagrid('rejectChanges');
							editRow_LG = undefined;
							}
						},"-",{
						text: '删除',
						iconCls: 'icon-cut',
						handler: function () {
							if(editRow_LG == undefined){
								var row = $('#table_learning_group').datagrid('getSelected');
								if (row) {
								$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
										if (res) {	
											var rowIndex = $('#table_learning_group').datagrid('getRowIndex', row);
											$('#table_learning_group').datagrid('deleteRow', rowIndex);
											toolAction.baseAjax("deleteLG",$('#table_learning_group'),row,"删除成功");
												}	
										});
								}
							}
						}
					},"-",{
						text: '保存',
						iconCls: 'icon-save',
						handler: function () {
							if(editRow_LG != undefined){
								$('#table_learning_group').datagrid('endEdit', editRow_LG);
								editRow_LG = undefined;
								//$('#table_learning_group').datagrid('reload');
							}
						}
					}],
			});
}
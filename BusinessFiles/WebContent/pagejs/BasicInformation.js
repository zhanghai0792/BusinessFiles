
var editRow_WE = undefined;
function loadWorkingExperience(){
	$('#table_working_experience').datagrid({
		url:'/BusinessFiles/getWElist',
		iconCls:'icon-save',
		//collapsible : true,
		rownumbers:true,
		singleSelect : true,
		//pagination:true,
		//pageSize:1,
		//pageList:[1,2,3],
		//height:'80%',
		//fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'id',//翻页操作有效
		//
		columns:[[
		{ field:'ID', checkbox:true,},
{title:'id',field:'id',width:100,align:'left',hidden:true},
{title:'工作单位',field:'company',width:100,align:'left',editor :{ type : 'textbox',options : { required : true }}},
{title:'入职时间',field:'startDate',width:128,align:'center',sortable:true,editor :{ type : 'datebox',options : { required : true,editable:false }}},
{title:'离职时间',field:'endDate',width:128,align:'center',sortable:true,editor :{ type : 'datebox',options : { required : true,editable:false }}},
{title:'职务',field:'role',width:100,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}}
]],
onAfterEdit: function (rowIndex,rowData,changes) 
{
	rowData.teacherId=$('#id').val();
	var inserted =$(this).datagrid('getChanges', "inserted");
	var updated = $(this).datagrid('getChanges', "updated");
	if(inserted.length<1 && updated.length<1){
		editRow_WE = undefined;
		$(this).datagrid('unselectAll');
			return;
		}
		if(inserted.length>0){
			toolAction.baseAjax("addWE",$(this),rowData,"增加成功");
		}else if(updated.length>0){ 
			toolAction.baseAjax("updateWE",$(this),rowData,"修改成功");
		}
		//editRow_LE = undefined;
		//updated.length=0;
},	
	 toolbar: [{
					text: '增加',
					iconCls: 'icon-add',
					handler: function () {
						if (editRow_WE == undefined) {
							$('#table_working_experience').datagrid('unselectAll');
								$('#table_working_experience').datagrid('insertRow', {index :0,row : {}});
								$('#table_working_experience').datagrid('beginEdit', 0);
								editRow_WE = 0;			
							}
					}
				}, "-",{
					text: '编辑',
					iconCls: 'icon-edit',
					handler: function () {
						if(editRow_WE == undefined){
							var row = $('#table_working_experience').datagrid('getSelected');
								if(row){
								var index = $('#table_working_experience').datagrid('getRowIndex',row);
									editRow_WE = index;
									$('#table_working_experience').datagrid('beginEdit', index);	
							}
								}
					}
				},"-",{
					text: '撤销',
					iconCls: 'icon-redo',
					handler:function(){
						$('#table_working_experience').datagrid('rejectChanges');
						editRow_WE = undefined;
						}
					},"-",{
					text: '删除',
					iconCls: 'icon-cut',
					handler: function () {
						if(editRow_WE == undefined){
							var row = $('#table_working_experience').datagrid('getSelected');
							if (row) {
							$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
									if (res) {	
										var rowIndex = $('#table_working_experience').datagrid('getRowIndex', row);
										$('#table_working_experience').datagrid('deleteRow', rowIndex);
										toolAction.baseAjax("deleteWE",$('#table_working_experience'),row,"删除成功");
											}	
									});
							}
						}
					}
				},"-",{
					text: '保存',
					iconCls: 'icon-save',
					handler: function () {
						if(editRow_WE != undefined){
							$('#table_working_experience').datagrid('endEdit', editRow_WE);
							editRow_WE = undefined;
						}
					}
				}],
		});
}
var editRow_LE = undefined;
var levels = [{ "value": "本科学士", "text": "本科学士" }, { "value": "本科硕士", "text": "本科硕士" }, { "value": "硕士研究生", "text": "硕士研究生" },{ "bsyjs": "博士研究生", "text": "博士研究生" }];
function loadLearningExperience(){
			$('#table_learning_experience').datagrid({
				url:'/BusinessFiles/getLElist',
				iconCls:'icon-save',
				//collapsible : true,
				rownumbers:true,
				singleSelect : true,
				//pagination:true,
				//pageSize:MypSize,
				//pageList:MypList,
				//fit:true,
				fitColumns:false,
				nowarp:true,
				border:false,
				idField:'id',//翻页操作有效
				columns:[[
				{ field:'ID', checkbox:true,},
		{title:'id',field:'id',width:100,hidden:true},
		{title:'层次',field:'level',width:100,align:'left',editor :{ type : 'combobox',options : { required : true,editable:false,valueField:'text',
			textField:'text',data: levels}}},
		{title:'专业',field:'major',width:128,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}},
		{title:'学校',field:'school',width:128,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}},
		{title:'毕业时间',field:'graduatDate',width:100,align:'center',sortable:true,editor :{ type : 'datebox',options : { required : true,editable:false }}}
		]],
		
		onAfterEdit: function (rowIndex,rowData,changes) 
		{
			rowData.teacherId=$('#id').val();
			var inserted =$(this).datagrid('getChanges', "inserted");
			var updated = $(this).datagrid('getChanges', "updated");
			if(inserted.length<1 && updated.length<1){
				editRow_LE = undefined;
				$(this).datagrid('unselectAll');
					return;
				}
				if(inserted.length>0){
					toolAction.baseAjax("addLE",$(this),rowData,"增加成功");
				}else if(updated.length>0){ 
					toolAction.baseAjax("updateLE",$(this),rowData,"修改成功");
				}
				//editRow_LE = undefined;
				//updated.length=0;
		},
			 toolbar: [{
						text: '增加',
						iconCls: 'icon-add',
						handler: function () {
							if (editRow_LE == undefined) {
								$('#table_learning_experience').datagrid('unselectAll');
									$('#table_learning_experience').datagrid('insertRow', {index :0,row : {}});
									$('#table_learning_experience').datagrid('beginEdit', 0);
									editRow_LE = 0;			
								}
							}
						}, "-",{
							text: '编辑',
							iconCls: 'icon-edit',
							handler: function () {
								if(editRow_LE == undefined){
									var row = $('#table_learning_experience').datagrid('getSelected');
										if(row){
										var index = $('#table_learning_experience').datagrid('getRowIndex',row);
											editRow_LE = index;
											$('#table_learning_experience').datagrid('beginEdit', index);	
									}
										}
							}
						},"-",{
							text: '撤销',
							iconCls: 'icon-redo',
							handler:function(){
								$('#table_learning_experience').datagrid('rejectChanges');
								editRow_LE = undefined;
								}
							},"-",{
							text: '删除',
							iconCls: 'icon-cut',
							handler: function () {
								if(editRow_LE == undefined){
									var row = $('#table_learning_experience').datagrid('getSelected');
									if (row) {
									$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
											if (res) {	
												var rowIndex = $('#table_learning_experience').datagrid('getRowIndex', row);
												$('#table_learning_experience').datagrid('deleteRow', rowIndex);
												toolAction.baseAjax("deleteLE",$('#table_learning_experience'),row,"删除成功");
													}	
											});
									}
								}
							}
						},"-",{
							text: '保存',
							iconCls: 'icon-save',
							handler: function () {
							if(editRow_LE != undefined){
									$('#table_learning_experience').datagrid('endEdit', editRow_LE);
									editRow_LE = undefined;
									//$('#table_learning_experience').datagrid('reload');
								}
							}
						}],
				});
		}
var editRow_CM = undefined;
function loadCertificate(){
	$('#table_certificate_manage').datagrid({
		url:'/BusinessFiles/getCMlist',
		iconCls:'icon-save',
		//collapsible : true,
		rownumbers:true,
		//singleSelect : true,
		//pagination:true,
		//pageSize:MypSize,
		//pageList:MypList,
		//fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'ID',//翻页操作有效
		columns:[[
		{ field:'ID', checkbox:true,},
{title:'id',field:'id',width:100,align:'left',hidden:true},
{title:'证书名称',field:'name',width:100,align:'left',editor :{ type : 'combobox',options : { required : true ,editable:false}}},
{title:'证书附件',field:'attachment',width:128,align:'center',sortable:true,editor :{ type : 'textbox',options : { required : true }}},
]],
onAfterEdit: function (rowIndex,rowData,changes) 
{
	rowData.teacherId=$('#id').val();
	var inserted =$(this).datagrid('getChanges', "inserted");
	var updated = $(this).datagrid('getChanges', "updated");
	if(inserted.length<1 && updated.length<1){
		editRow_CM = undefined;
		$(this).datagrid('unselectAll');
			return;
		}
		if(inserted.length>0){
			toolAction.baseAjax("addCM",$(this),rowData,"增加成功");
		}else if(updated.length>0){ 
			toolAction.baseAjax("updateCM",$(this),rowData,"修改成功");
		}
		//editRow_LE = undefined;
		//updated.length=0;
},
	 toolbar: [{
					text: '增加',
					iconCls: 'icon-add',
					handler: function () {
						if (editRow_CM == undefined) {
							$('#table_certificate_manage').datagrid('unselectAll');
								$('#table_certificate_manage').datagrid('insertRow', {index :0,row : {}});
								$('#table_certificate_manage').datagrid('beginEdit', 0);
								editRow_CM = 0;			
							}
					}
				}, "-",{
					text: '编辑',
					iconCls: 'icon-edit',
					handler: function () {
						if(editRow_CM == undefined){
							var row = $('#table_certificate_manage').datagrid('getSelected');
								if(row){
								var index = $('#table_certificate_manage').datagrid('getRowIndex',row);
								editRow_CM = index;
									$('#table_certificate_manage').datagrid('beginEdit', index);	
							}
								}
					}
				},"-",{
					text: '撤销',
					iconCls: 'icon-redo',
					handler:function(){
						$('#table_certificate_manage').datagrid('rejectChanges');
						editRow_CM = undefined;
						}
					},"-",{
					text: '删除',
					iconCls: 'icon-cut',
					handler: function () {
						if(editRow_CM == undefined){
							var row = $('#table_certificate_manage').datagrid('getSelected');
							if (row) {
							$.messager.confirm("提示", "您确定要删除这些数据吗？", function (res) {
									if (res) {	
										var rowIndex = $('#table_certificate_manage').datagrid('getRowIndex', row);
										$('#table_certificate_manage').datagrid('deleteRow', rowIndex);
										toolAction.baseAjax("deleteCM",$('#table_certificate_manage'),row,"删除成功");
											}	
									});
							}
						}
					}
				},"-",{
					text: '保存',
					iconCls: 'icon-save',
					handler: function () {
						if(editRow_CM != undefined){
							$('#table_certificate_manage').datagrid('endEdit', editRow_CM);
							editRow_CM = undefined;
						}
					}
				}],
		});
}
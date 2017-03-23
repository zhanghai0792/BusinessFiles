function loadotherworks(){
	$('#table_other_works').datagrid({
		 url:'/BusinessFiles/getOWlist',
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
			frozenColumns:[[]],
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'工作性质',field:'work',width:100,align:'left'},
	{title:'部门',field:'department',width:128,align:'center',sortable:true},
	{title:'持续时间',field:'time',width:100,align:'center',sortable:true},
	]],
		 toolbar: [{
						text: '增加',
						iconCls: 'icon-add',
						handler: function () {
						}
					}, "-",{
						text: '编辑',
						iconCls: 'icon-edit',
						handler: function () {
						}
					},"-",{
						text: '删除',
						iconCls: 'icon-cut',
						handler: function () {
						}
					},"-",{
						text: '保存',
						iconCls: 'icon-save',
						handler: function () {
						}
					}],
			});
}
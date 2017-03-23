function loadtrainingstudy(){
	$('#table_training_study').datagrid({
		 url:'/BusinessFiles/getTSlist',
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
	{title:'类型',field:'type',width:100,align:'left'},
	{title:'学习内容',field:'direction',width:128,align:'center',sortable:true},
	{title:'开始时间',field:'startDate',width:100,align:'center',sortable:true},
	{title:'总天数',field:'totalDays',width:100,align:'center',sortable:true},
	{title:'地点',field:'address',width:100,align:'center',sortable:true},
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
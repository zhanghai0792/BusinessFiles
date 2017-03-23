function loadlearningmeeting(){
	$('#table_learning_meeting').datagrid({
		 url:'/BusinessFiles/getLMlist',
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
	{title:'学术会议名称',field:'name',width:100,align:'left'},
	{title:'专业方向',field:'major',width:128,align:'center',sortable:true},
	{title:'组委会',field:'organization',width:128,align:'center',sortable:true},
	{title:'会议时间',field:'time',width:100,align:'center',sortable:true},
	{title:'会议地点',field:'address',width:100,align:'center',sortable:true},
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
 function loadTeachingWorkload(){
	 $('#table_workload').datagrid({
		url:'/BusinessFiles/getTWlist',
		iconCls:'icon-save',
		//collapsible : true,
		rownumbers:true,
		//singleSelect : true,
		//pagination:true,
		//pageSize:MypSize,
		//pageList:MypList,
		fit:true,
		fitColumns:false,
		nowarp:true,
		border:false,
		idField:'id',//翻页操作有效
		//frozenColumns:[[]],
		columns:[[
		{ field:'ID', checkbox:true,},
{title:'id',field:'id',width:100,align:'left',hidden:true},
{title:'课程代码',field:'courseId',width:100,align:'left'},
{title:'任教课程名称',field:'courseName',width:128,align:'center',sortable:true},
{title:'班级',field:'tclass',width:128,align:'center',sortable:true},
{title:'班级人数',field:'tclassNum',width:128,align:'center',sortable:true},
{title:'教材版别',field:'material',width:128,align:'center',sortable:true},
{title:'总课时',field:'periodNum',width:128,align:'center',sortable:true}
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
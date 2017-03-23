//指导学生获奖
function loadGuideStudent(){
	$('#table_guide_student').datagrid({
		 url:'/BusinessFiles/getGSlist',
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
	{title:'奖项',field:'prize',width:100,align:'left'},
	{title:'奖励级别',field:'level',width:128,align:'center',sortable:true},
	{title:'等次',field:'order',width:128,align:'center',sortable:true},
	{title:'时间',field:'time',width:100,align:'center',sortable:true},
	{title:'分值',field:'score',width:100,align:'center',sortable:true},
	{title:'附件',field:'attachment',width:100,align:'center',sortable:true}
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
//教学成果获奖
function loadTeachingAchievement(){
	$('#table_teaching_achievement').datagrid({
		url:'/BusinessFiles/getTAlist',
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
      {title:'id',field:'id',width:100,align:'left'},
      {title:'奖项',field:'prize',width:100,align:'left'},
  	 {title:'奖励级别',field:'level',width:128,align:'center',sortable:true},
  	 {title:'等次',field:'order',width:128,align:'center',sortable:true},
  	 {title:'时间',field:'time',width:100,align:'center',sortable:true},
      {title:'本人排名',field:'rank',width:100,align:'center',sortable:true},
      {title:'总人数',field:'totalNum',width:100,align:'center',sortable:true},
      {title:'分值',field:'score',width:100,align:'center',sortable:true},
      {title:'附件',field:'attachment',width:100,align:'center',sortable:true}
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
//其他获奖
function loadTeachingOtherPrize(){
	$('#table_teaching_otherPrize').datagrid({
		url:'/BusinessFiles/getTOlist',
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
    {title:'id',field:'id',width:100,align:'left'},
    {title:'奖项',field:'prize',width:100,align:'left'},
  	{title:'奖励级别',field:'level',width:128,align:'center',sortable:true},
  	{title:'等次',field:'order',width:128,align:'center',sortable:true},
  	{title:'时间',field:'time',width:100,align:'center',sortable:true},
  	{title:'分值',field:'score',width:100,align:'center',sortable:true},
  	{title:'附件',field:'attachment',width:100,align:'center',sortable:true}
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
//学术论文
function loadPaperResearch(){
		 $('#table_paper_research').datagrid({
			 url:'/BusinessFiles/getPRlist',
				iconCls:'icon-save',
				//collapsible : true,
				rownumbers:true,
				//singleSelect : true,
				//pagination:true,
				//pageSize:MypSize,
				//pageList:MypList,
				//pagination:true,
				//pageSize:10,
				//pageList:[10,20,30],
				//fit:true,
				fitColumns:false,
				nowarp:true,
				border:false,
				idField:'id',//翻页操作有效
				frozenColumns:[[]],
				columns:[[
				{ field:'ID', checkbox:true,},
		{title:'id',field:'id',width:100,align:'left',hidden:true},
		{title:'论文篇名',field:'paperTitle',width:200,align:'left'},
		{title:'发表时间',field:'publishDate',width:100,align:'center',sortable:true},
		{title:'发表刊物',field:'publication',width:100,align:'center',sortable:true},
		{title:'刊号',field:'publishNum',width:100,align:'center',sortable:true},
		{title:'刊物等级',field:'level',width:100,align:'center',sortable:true},
		{title:'本人排名',field:'rank',width:50,align:'center',sortable:true},
		{title:'总人数',field:'totalNum',width:50,align:'center',sortable:true},
		{title:'分值',field:'score',width:50,align:'center',sortable:true},
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
//科研课题
function loadScientificResearch(){
	 $('#table_scientific_research').datagrid({
		 url:'/BusinessFiles/getSRlist',
			iconCls:'icon-save',
			//collapsible : true,
			rownumbers:true,
			//singleSelect : true,
			//pagination:true,
			//pageSize:MypSize,
			//pageList:MypList,
			//pagination:true,
			//pageSize:10,
			//pageList:[10,20,30],
			//fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			frozenColumns:[[]],
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:10,align:'left',hidden:true},
	{title:'课题名称',field:'topic',width:480,align:'left'},
	{title:'立项编号',field:'topicNum',width:100,align:'left',sortable:true},
	{title:'立项时间',field:'startDate',width:100,align:'left',sortable:true},
	{title:'结题时间',field:'endDate',width:100,align:'left',sortable:true},
	{title:'批准（合作）单位',field:'approver',width:100,align:'left',sortable:true},
	{title:'级别',field:'level',width:100,align:'left',sortable:true},
	{title:'经费',field:'funds',width:100,align:'left',sortable:true},
	{title:'本人排名',field:'rank',width:50,align:'left',sortable:true},
	{title:'总人数',field:'totalNum',width:50,align:'left',sortable:true},
	{title:'分值',field:'score',width:50,align:'left',sortable:true},
	{title:'附件',field:'attachment',width:100,align:'left',sortable:true}
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
//教改课题
function loadEducationalResearch(){
	$('#table_educational_research').datagrid({
		 url:'/BusinessFiles/getERlist',
			iconCls:'icon-save',
			//collapsible : true,
			rownumbers:true,
			singleSelect : true,
			//pageSize:10,
			//pageList:[10,20,30],
			//pagination:true,
			//pageSize:MypSize,
			//pageList:MypList,
			//fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			frozenColumns:[[]],
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'课题名称',field:'topic',width:100,align:'left'},
	{title:'立项编号',field:'topicNum',width:128,align:'center',sortable:true},
	{title:'立项时间',field:'startDate',width:128,align:'center',sortable:true},
	{title:'结题时间',field:'endDate',width:100,align:'center',sortable:true},
	{title:'批准（合作）单位',field:'approver',width:100,align:'center',sortable:true},
	{title:'级别',field:'level',width:100,align:'center',sortable:true},
	{title:'经费',field:'funds',width:100,align:'center',sortable:true},
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
//专著教材
function loadMonographTextbook(){
	$('#table_monograph_textbook').datagrid({
		 url:'/BusinessFiles/getMTlist',
			iconCls:'icon-save',
			//collapsible : true,
			rownumbers:true,
			//singleSelect : true,
			//pagination:true,
			//pageSize:MypSize,
			//pageList:MypList,
			//pageSize:10,
			//pageList:[10,20,30],
			fit:true,
			fitColumns:false,
			nowarp:true,
			border:false,
			idField:'id',//翻页操作有效
			frozenColumns:[[]],
			columns:[[
			{ field:'ID', checkbox:true,},
	{title:'id',field:'id',width:100,align:'left',hidden:true},
	{title:'专著/编写教材名称',field:'bookName',width:100,align:'left'},
	{title:'出版时间',field:'publishDate',width:128,align:'center',sortable:true},
	{title:'出版单位',field:'publication',width:128,align:'center',sortable:true},
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
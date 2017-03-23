//封装的公共方法
//普通datagrid发送请求,增删查改
var MypSize=20;
var MypList=[20,50,100,150,200];

var toolAction = {
	baseAjaxData:new Array(),// 存放常用的ajax请求的返回结果
	//单个修改和更新
	baseAjax:function(actionName,dg,data,successMsg) {
			$.ajax({
				url : "/BusinessFiles/"+actionName,
				type : "post",
				dataType : "json",
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				data : data,
				success : function(data) {
					if(data.msg){
						$.messager.show({
							title:'提示',
							timeout:3000,
							msg:successMsg,
							showType:'slide',
							style:{
								top:document.body.scrollTop+document.documentElement.scrollTop,
							}
						});
						dg.datagrid('reload');
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					 $.messager.alert("提 示", "操作失败");
				}
			});
	},
	
}
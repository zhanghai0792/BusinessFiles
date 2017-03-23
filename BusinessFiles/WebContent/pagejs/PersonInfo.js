function SaveInformation() {
		//var formData = new FormData($("#form_updateTeacher").serialize());
		//formData=decodeURIComponent(formData,true);
		//console.info($("#form_updateTeacher").serialize());
		//console.info($('#birthDate').datebox('getValue'));
		console.info(decodeURIComponent($("#form_updateTeacher").serialize(),true));
		var actionName =$("#form_updateTeacher").prop("action");
		console.info(actionName);
		$.ajax({
		url : actionName,
		type : "post",
		dataType : "json",
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		data : decodeURIComponent($("#form_updateTeacher").serialize(),true),
		success : function(data) {
			$.messager.show({
						title:'提示',
						timeout:3000,
						msg:'修改成功',
						showType:'slide',
						style:{
							top:document.body.scrollTop+document.documentElement.scrollTop,
						}
					});
			console.info(data);
			$("#form_updateTeacher").form('load',data);
		},
		error : function() {
			alert("失败");
		}
	});
}
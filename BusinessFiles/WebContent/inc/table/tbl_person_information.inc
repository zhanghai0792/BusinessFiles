<div class="easyui-panel" title="教师个人信息" align="left"
	style="height:100%;width:85%;" data-options="tools:'#tool_information'">
	<form id="form_updateTeacher" method="post" style="padding-left:110px;" action="updateTeacher">

		<table>
			<tr style="line-height: 50px;">
				<td style="font-size: 14px;">工号:</td>
				<td><input class="easyui-textbox" type="text" name="id" id="id" readonly="readonly"
					style="height: 23px" value="${session.loginteacher.id}" >
					</td>
				<td style="padding-left: 10px;font-size: 14px;">姓名:</td>
				<td><input class="easyui-textbox" type="text" id="name" readonly="readonly"
					name="name" style="height: 23px" value="${session.loginteacher.name}"
					></input></td>
				<td style="padding-left: 10px;font-size: 14px;">出生年月:</td><!-- ${session.loginteacher.birthDate} -->
				<td><input class="easyui-datebox" type="text" id="birthDate" name="birthDate"
					style="height: 23px" data-options="prompt:'输入日期',editable:false," value="${session.loginteacher.birthDate}"
					 ></input><span style="color:red;font-size:16px;">*</span></td>
				
			</tr>
			<tr style="line-height: 50px;">
				<td style="font-size: 14px;">民族:</td>
				<td><input class="easyui-combobox" type="text" id="nation" name="nation" data-options="editable:false,valueField:'id',textField:'name',url:'jsondata/nation_zh_CN.json'"
					style="height: 23px" value="${session.loginteacher.nation}"  /><span style="color:red;font-size:16px;">*</span></td>
				<td style="padding-left: 10px;font-size: 14px;">政治面貌:</td>
				<td><input class="easyui-combobox" id="political" name="political" style="width:130px;height: 21px;"
				data-options="valueField:'id',textField:'name',editable:false,
				data: [{
						id: '党员',
						name: '党员'
						},{
							id: '群众',
							name: '群众'
						}]" value='${session.loginteacher.political}'  /><span style="color:red;font-size:16px;">*</span></td>
				<td style="padding-left: 10px;font-size: 14px;">身份证号:</td>
				<td><input class="easyui-textbox" type="text" id="cardId" name="cardId" readonly="readonly"
					style="height: 23px;width: auto;" value="${session.loginteacher.cardId}"
					></input></td>
				</tr>
				<tr style="line-height: 50px;">
				<td style="font-size: 14px;">评定职称:</td>
				<td><input class="easyui-combobox" id="professTitle" name="professTitle" style="width:130px;height: 21px;"  value="${session.loginteacher.professTitle}"
					data-options="valueField: 'id',textField: 'name',editable:false,
						data: [{
						id: '助教',
						name: '助教'
						},{
							id: '讲师',
							name: '讲师'
						},{
							id: '副教授',
							name: '副教授'
						},{
							id: '教授',
							name: '教授'
						}]"/><span style="color:red;font-size:16px;">*</span></td>
				<td style="padding-left: 10px;font-size: 14px;">评定职称时间:</td>
				<td><input class="easyui-datebox" type="text" id="professDate" name="professDate" style="height: 21px" readonly="readonly"sss
					data-options="prompt:'输入日期'" value="${session.loginteacher.professDate}" ></input>
				</td>
				<td style="padding-left: 10px;font-size: 14px;">聘任职称:</td>
				<td><input class="easyui-combobox" id="employTitle" name="employTitle" style="width:130px;height: 21px;"  value="${session.loginteacher.employTitle}"
					data-options="valueField: 'id',textField: 'name',editable:false,
						data: [{
						id: '助教',
						name: '助教'
						},{
							id: '讲师',
							name: '讲师'
						},{
							id: '副教授',
							name: '副教授'
						},{
							id: '教授',
							name: '教授'
						}]"/><span style="color:red;font-size:16px;">*</span></td>			

			</tr>
			<tr style="line-height: 50px;">
			<td style="font-size: 14px;">聘任职称时间:</td>
				<td><input class="easyui-datebox" type="text" id="employDate" name="employDate" style="height: 21px" readonly="readonly"
					data-options="prompt:'输入日期'" value="${session.loginteacher.employDate}" ></input>
				</td>
				<td style="padding-left: 10px;font-size: 14px;">最后学位:</td>
				<td><input class="easyui-combobox" id="finalDegree" name="finalDegree" style="width:130px;height: 21px;"  value="${session.loginteacher.finalDegree}"
				data-options="valueField: 'id',textField: 'name',editable:false,
						data: [{
						id:'学士',
						name:'学士'
						},{
							id:'硕士',
							name:'硕士'
						},{
							id:'博士',
							name:'博士'
						}]"/><span style="color:red;font-size:16px;">*</span></td>
				<td style="padding-left: 10px;font-size: 14px;">最后学历:</td>
				<td><input class="easyui-combobox" id="finalEducat" name="finalEducat" style="width:130px;height: 21px;"  value="${session.loginteacher.finalEducat}"
					data-options="valueField: 'id',textField: 'name',editable:false,
						data: [{
						id: '本科',
						name: '本科'
						},{
							id: '研究生',
							name: '研究生'
						}]"/><span style="color:red;font-size:16px;">*</span></td>
				
			</tr>
			<tr style="line-height: 50px;">
			<td style="font-size: 14px;">获得最后学位时间:</td>
				<td><input class="easyui-datebox" type="text" id="finalDegreeDate" name="finalDegreeDate" style="height: 21px" readonly="readonly"
					data-options="prompt:'输入日期'" value="${session.loginteacher.finalDegreeDate}" ></input>
				</td>
				<td style="padding-left: 10px;font-size: 14px;">院系:</td>
				<td><input class="easyui-combobox" id="grade" name="grade" value="${session.loginteacher.grade}"
					style="width:160px;height: 21px;"  data-options="editable:false,valueField:'id',textField:'name',url:'jsondata/grade-1.json'">
						<span style="color:red;font-size:16px;">*</span></td>
				<td style="padding-left: 10px;font-size: 14px;">部门/教研室:</td>
				<td><input class="easyui-combobox" id="dept" name="dept" style="width:130px;height: 21px;"  value="${session.loginteacher.dept}"
					data-options="valueField: 'id',textField: 'name',editable:false,
						data: [{
						id:'教务科',
						name:'教务科'
						},{
							id:'教研科',
							name:'教研科'
						},{
							id:'教研室',
							name:'教研室'
						}]"/><span style="color:red;font-size:16px;">*</span>
				</td>
			</tr>
		</table>

		<div id="tool_information" style="padding:5px;">
			 <a href="javascript:void(0);"
				class="icon-save"  onclick="SaveInformation()"></a>
		</div>
	</form>
	<s:debug/>
</div>
<script type="text/javaScript">
$(function(){

});
	

	function SaveInformation() {
		
		//var formData = new FormData($("#form_updateTeacher").serialize());
		//formData=decodeURIComponent(formData,true);
		//console.info($("#form_updateTeacher").serialize());
		//console.info($('#birthDate').datebox('getValue'));
		console.info(decodeURIComponent($("#form_updateTeacher").serialize(),true));
		var actionName =$("#form_updateTeacher").prop("action");
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
</script>
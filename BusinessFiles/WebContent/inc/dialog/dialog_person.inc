<div id="dialog_person" class="easyui-dialog" style="width:900px;height:400px;" closed="true" data-options="iconCls: 'icon-save',
				buttons: '#dlg-buttons'" title="填写教师个人信息" align="center">
<form id="form_person" method="post" style="padding:10px;">
	<table >
		<tr style="line-height: 50px;">
			<td style="font-size: 14px;">工号:</td>
			<td style="font-size: 14px;">14181</td>
			<td style="padding-left: 30px;font-size: 14px;">姓名:</td>
			<td style="font-size: 14px;">周小雄</td>
			<td style="padding-left: 30px;font-size: 14px;">出生年月:</td>
			<td><input class="easyui-datebox" type="text" name="borth"
				style="height: 21px" data-options="prompt:'输入日期',required:true"></input>
			</td>

		</tr>
		<tr style="line-height: 50px;">

			<td style="font-size: 14px;">民族:</td>
			<td><input class="easyui-textbox" type="text" name="nation"
				style="height: 21px" data-options="prompt:'输入民族'" value="汉"></input>
			</td>
			<td style="padding-left: 30px;font-size: 14px;">政治面貌:</td>
			<td><select class="easyui-combobox" name="political_status" style="width:150px;height: 21px;">
				<option value="DY">党员</option>
				<option value="QZ">群众</option>
			</select>
			</td>
			<td style="padding-left: 30px;font-size: 14px;">职称:</td>
			<td><select class="easyui-combobox" name="title" style="width:150px;height: 21px;">
				<option value="ZJ">助教</option>
				<option value="JS">讲师</option>
				<option value="FJS">副教授</option>
				<option value="JS">教授</option>
			</select>
			</td>

		</tr>
		<tr style="line-height: 50px;">
			<td style="font-size: 14px;">最后学位:</td>
			<td><select class="easyui-combobox" name="final_degree" style="width:150px;height: 21px;">
				<option value="XS">学士</option>
				<option value="SS">硕士</option>
				<option value="BS">博士</option>
			</select>
			</td>
			<td style="padding-left: 30px;font-size: 14px;">最后学历:</td>
			<td><select class="easyui-combobox" name="final_education" style="width:150px;height: 21px;">
				<option value="BK">本科</option>
				<option value="YJS">研究生</option>
			</select>
			</td>
			<td style="padding-left: 30px;font-size: 14px;">获得学位时间:</td>
			<td><input class="easyui-datebox" type="text" name="degree_time"
				style="height: 21px" data-options="prompt:'输入日期'"></input>
			</td>
		</tr>
		<tr style="line-height: 50px;">
			<td style="font-size: 14px;">院系:</td>
			<td><select class="easyui-combobox" name="school" style="width:150px;height: 21px;">
				<option value="XX">信息科学与技术学院</option>
				<option value="WY">外国语学院</option>
				<option value="LY">旅游与国土资源学院</option>
			</select>
			</td>
			<td style="padding-left: 30px;font-size: 14px;">部门/教研室:</td>
			<td><select class="easyui-combobox" name="partment" style="width:150px;height: 21px;">
				<option value="JW">教务科</option>
				<option value="JY">教研科</option>
				<option value="JYS">教研室</option>
			</select>
			</td>
			
		</tr>
	</table>
</form>
</div>
<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:alert('保存成功')">提交修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dialog_person').dialog('close')">关闭</a>
	</div>
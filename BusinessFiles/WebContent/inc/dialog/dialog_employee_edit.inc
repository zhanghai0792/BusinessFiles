<div id="dlg" class="easyui-dialog" data-options="title:'编辑职工信息',buttons:'#dlg-buttons'" style="width:540px;height:320px;padding:10px 20px" closed="true" >
			<form id="fm" method="post">
				<table>
				<tr>
				<td>
					<label>工&emsp;&emsp;号:</label>
					<input name="id" class="easyui-textbox" required="true">
				</td>
				<td>
					<label>&emsp;&emsp;&emsp;&emsp;身份证号:</label>
					<input name="cardId" class="easyui-textbox">
				</td>
				</tr>
				<tr>
				<td>
					<label>姓&emsp;&emsp;名:</label>
					<input name="name" class="easyui-textbox" required="true">
				</td>
				<td>
					<label>&emsp;&emsp;&emsp;&emsp;性&emsp;&emsp;别:</label>
					<input name="sex" class="easyui-combobox" data-options="url:'jsondata/Teacher_sex.json',valueField: 'id',textField: 'name',panelHeight:'auto',editable:false">
				</td>
				</tr>
				<tr>
				<td>
					<label>电&emsp;&emsp;话:</label>
					<input class="easyui-textbox" id="phone" name="phone" />
				</td>
				<td>
					<label>&emsp;&emsp;&emsp;&emsp;政治面貌:</label>
					<input name="political" class="easyui-combobox" data-options="valueField: 'name',textField: 'name', panelHeight:'auto',editable:false,url:'jsondata/Teacher_political.json' ">
				</td>
				</tr>
				<tr>
				<td>
					<label>毕业院校:</label>
					<input class="easyui-textbox" id="schoolAT" name="school" />
				</td>
				<td>
					<label>&emsp;&emsp;&emsp;&emsp;毕业专业:</label>
					<input class="easyui-textbox" id="majorAT" name="major"  />
				</td>
				</tr>
				<tr>
				<td>
					<label>学&emsp;&emsp;位:</label>
					<input class="easyui-combobox"  id="finalDegree" name="finalDegree" data-options="valueField: 'name',textField: 'name', panelHeight:'auto',editable:false,url:'jsondata/Teacher_finalDegree.json' " />
				</td>				
				<td>
					<label>&emsp;&emsp;获得学位时间:</label>
					<input class="easyui-datebox" data-options="editable:false," type="text" id="finalDegreeDate" name="finalDegreeDate"  />
				</td>
				</tr>
				<tr>
				<td>
					<label>学&emsp;&emsp;历:</label>
					<input class="easyui-combobox" type="text" id="finalEducat" name="finalEducat" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_finalEducat.json'" />
				</td>				
				<td>
					<label>&emsp;&emsp;获得学历时间:</label>
					<input class="easyui-datebox"  data-options="editable:false," type="text" id="finalEducatDate" name="finalEducatDate"  />
				</td>
				</tr>
				<tr>
				<td>
					<label>评定职称:</label>
					<input class="easyui-combobox" type="text" id="professTitle" name="professTitle" data-options="valueField: 'name',textField: 'name',editable:false,panelHeight:'auto',url:'jsondata/Teacher_title.json'" />
				</td>
				<td>
					<label>&emsp;&emsp;评定职称时间:</label>
					<input  class="easyui-datebox" data-options="editable:false," type="text" id="professDate" name="professDate"  />
				</td>
				</tr>
				<tr>
				<td>
					<label>聘任职称:</label>
					<input class="easyui-combobox" type="text" id="employTitle" name="employTitle" data-options="valueField: 'name',textField: 'name',panelHeight:'auto',editable:false,url:'jsondata/Teacher_title.json'" />
				</td>
				<td>
					<label>&emsp;&emsp;聘任职称时间:</label>
					<input  class="easyui-datebox" data-options="editable:false" type="text" id="employDate" name="employDate"/>
				</td>
				</tr>
				<tr>
				<td>
					<label>所属科室:</label>
					<input name="sectorId" class="easyui-textbox">
				</td>
				<td>
					<label>&emsp;&emsp;&emsp;&emsp;角&emsp;&emsp;色:</label>
					<input class="easyui-combobox" id="typeAT" name="type" data-options="valueField: 'id',textField: 'name', panelHeight:'auto',editable:false,url:'jsondata/Teacher_type.json' " />
				</td>
				</tr>
				</table>
			</form>
		</div>
		<div id="dlg-buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRecord()">保存</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
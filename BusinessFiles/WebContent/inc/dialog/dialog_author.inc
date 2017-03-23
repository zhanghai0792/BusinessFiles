﻿<script>
function addToAuthor(index,row){
	$("#table_author").datagrid('appendRow',row);
}

function removeFromAuthor(index,row){			
	$("#table_author").datagrid('deleteRow',index);
}
</script>
<div id="dlg_author" closed="true" class="easyui-dialog" title="选择参与者" style="padding:10px;font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体" data-options="closable:false,width:450">
	<div  style="width:180px;float:left;">
		<div class="easyui-panel" title="参与者列表" >
			<table  class="easyui-datagrid" id="table_author" data-options="onDblClickRow:removeFromAuthor">
				<thead>
					<tr>
						<th data-options="field:'id',width:80">工号</th>
						<th data-options="field:'name',width:100">姓名</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>	
	<div style="width:180px;float:left;margin-left:10px;"  >
		<div class="easyui-panel" title="所有教师" data-options="height:300,">
			<table class="easyui-datagrid" id="table_teachers" data-options="url:'/BusinessFiles/getTeacherlist',fit:true,onDblClickRow:addToAuthor">
				<thead>
					<tr>
						<th data-options="field:'id',width:80">工号</th>
						<th data-options="field:'name'">姓名</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div style="clear:both;"></div>
	<p style="color:red">操作提示：双击右边教师列表中的教师可将其添加到参与者列表中；双击左边参与者列表的参与者，将其从列表中移除。</p>
	<div style="margin-left:120px;margin-top:10px;">
		<a onclick="saveAuthor();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:70px;height:30px;cursor: pointer;">保存</a>
		<a onClick="$('#dlg_author').dialog('close');"  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:70px;height:30px;cursor: pointer;">取消</a>
	</div>
</div> 
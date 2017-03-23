<div id="dlg_import" closed="true" class="easyui-dialog" title="导入论文指导" style="width:300px;padding:10px">
	<form id="form_import" method="post" enctype="multipart/form-data">
		Excel文件:<input class="easyui-filebox" id="file_xls" name="file" style="width:200px" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /></td>
		<br/><br/>
		<div align="center">
			<a  class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="ImportRecords();">导入</a>
			&emsp;&emsp;<a  href="template/teachers.xls">下载模板</a>				
		</div>	     
	</form>
</div>
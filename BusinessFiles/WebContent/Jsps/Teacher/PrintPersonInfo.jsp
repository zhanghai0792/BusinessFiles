<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="" align="center" style="height: 100%;width:100%" data-options="tools:'#tool_information_1'">
<form  method="post" style="padding:10px;" >

	<table border="1" style="border-collapse:collapse;width:100%;" >
	<caption><b>信息科学与技术学院教师业务情况简表</b></caption>
		<p><lable>院系:信息学院</lable><lable>教研室:应用</lable></p>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">姓名:</td>
			<td style="font-size: 10px;">${session.loginteacher.name}</td>
			<td  style="font-size: 10px;">出生年月:</td>
			<td style="font-size: 10px;">${session.loginteacher.birthDate}</td>
			<td  rowspan='4' style="font-size: 10px;">照片</td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">民族:</td>
			<td style="font-size: 10px;">${session.loginteacher.nation}</td>
			<td style="font-size: 10px;">政治面貌:</td>
			<td style="font-size: 10px;">${session.loginteacher.political}</td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">最高学位:</td>
			<td style="font-size: 10px;">${session.loginteacher.finalDegree}</td>
			<td style="font-size: 10px;">最高学历:</td>
			<td style="font-size: 10px;">${session.loginteacher.finalEducat}</td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">获得学历时间:</td>
			<td style="font-size: 10px;">${session.loginteacher.finalDegreeDate}</td>
			<td style="font-size: 10px;">职称:</td>
			<td style="font-size: 10px;">${session.loginteacher.professTitle}</td>
		</tr>
	</table>

	<table border="1" style="border-collapse:collapse;width:100%">
		<tr style="line-height:20px;" align="center"><td colspan='5'>主要学习经历</td></tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">序号</td>
			<td style="font-size: 10px;">层次</td>
			<td style="font-size: 10px;">学校</td>
			<td style="font-size: 10px;">专业</td>
			<td style="font-size: 10px;">毕业时间</td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">1</td>
			<td style="font-size: 10px;">本科</td>
			<td style="font-size: 10px;">华东师范大学</td>
			<td style="font-size: 10px;">教育技术学</td>
			<td style="font-size: 10px;">2004-06</td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">2</td>
			<td style="font-size: 10px;">研究生</td>
			<td style="font-size: 10px;">华中科技大学</td>
			<td style="font-size: 10px;">计算机科学技术</td>
			<td style="font-size: 10px;">2008-10</td>
		</tr>
</table>
<table border="1" style="border-collapse:collapse;width:100%">
		<tr style="line-height:20px;" align="center"><td colspan='6'>年度考核情况</td></tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">序号</td>
			<td style="font-size: 10px;">年度</td>
			<td style="font-size: 10px;">考核情况</td>
			<td style="font-size: 10px;">序号</td>
			<td style="font-size: 10px;">年度</td>
			<td style="font-size: 10px;">考核情况</td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">1</td>
			<td style="font-size: 10px;">2010</td>
			<td style="font-size: 10px;">合格</td>
			<td style="font-size: 10px;">5</td>
			<td style="font-size: 10px;">2014</td>
			<td style="font-size: 10px;"></td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">2</td>
			<td style="font-size: 10px;">2011</td>
			<td style="font-size: 10px;">合格</td>
			<td style="font-size: 10px;">6</td>
			<td style="font-size: 10px;">2015</td>
			<td style="font-size: 10px;"></td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">3</td>
			<td style="font-size: 10px;">2012</td>
			<td style="font-size: 10px;"> </td>
			<td style="font-size: 10px;">7</td>
			<td style="font-size: 10px;">2016</td>
			<td style="font-size: 10px;"></td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">4</td>
			<td style="font-size: 10px;">2013</td>
			<td style="font-size: 10px;"> </td>
			<td style="font-size: 10px;">8</td>
			<td style="font-size: 10px;">2017</td>
			<td style="font-size: 10px;"></td>
		</tr>
</table>
<table border="1" style="border-collapse:collapse;width:100%">
		<tr style="line-height:20px;" align="center"><td colspan='6'>其他培训情况或职业资格证书</td></tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">序号</td>
			<td style="font-size: 10px;">名称</td>
			<td style="font-size: 10px;">时间</td>
			<td style="font-size: 10px;">序号</td>
			<td style="font-size: 10px;">名称</td>
			<td style="font-size: 10px;">时间</td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">1</td>
			<td style="font-size: 10px;">软件设计师</td>
			<td style="font-size: 10px;">2011</td>
			<td style="font-size: 10px;">5</td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;"></td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">2</td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;">6</td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;"></td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">3</td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;"> </td>
			<td style="font-size: 10px;">7</td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;"></td>
		</tr>
		<tr style="line-height:20px;" align="center">
			<td style="font-size: 10px;">4</td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;">8</td>
			<td style="font-size: 10px;"></td>
			<td style="font-size: 10px;"></td>
		</tr>
</table>
</form>		
		<!-- <tr style="line-height: 50px;">
			<td style="font-size: 10px;">工号:</td>
			<td style="font-size: 10px;">14181</td>
			<td style="align:center;font-size: 10px;">姓名:</td>
			<td style="font-size: 10px;">周小雄</td>
			<td style="align:center;font-size: 10px;">出生年月:</td>
			<td><input class="easyui-datebox" type="text" name="borth"
				style="height: 21px" data-options="prompt:'输入日期',required:true"></input>
			</td>

		</tr> -->
<!-- 		<tr style="line-height: 50px;">

			<td style="font-size: 10px;">民族:</td>
			<td><input class="easyui-textbox" type="text" name="nation"
				style="height: 21px" data-options="prompt:'输入民族'"></input>
			</td>
			<td style="align:center;font-size: 10px;">政治面貌:</td>
			<td><select class="easyui-combobox" name="political_status" style="width:150px;height: 21px;">
				<option value="DY">党员</option>
				<option value="QZ">群众</option>
			</select>
			</td>
			<td style="align:center;font-size: 10px;">职称:</td>
			<td><select class="easyui-combobox" name="title" style="width:150px;height: 21px;">
				<option value="ZJ">助教</option>
				<option value="JS">讲师</option>
				<option value="FJS">副教授</option>
				<option value="JS">教授</option>
			</select>
			</td>

		</tr>
		<tr style="line-height: 50px;">
			<td style="font-size: 10px;">最后学位:</td>
			<td><select class="easyui-combobox" name="final_degree" style="width:150px;height: 21px;">
				<option value="XS">学士</option>
				<option value="SS">硕士</option>
				<option value="BS">博士</option>
			</select>
			</td>
			<td style="align:center;font-size: 10px;">最后学历:</td>
			<td><select class="easyui-combobox" name="final_education" style="width:150px;height: 21px;">
				<option value="BK">本科</option>
				<option value="YJS">研究生</option>
			</select>
			</td>
			<td style="align:center;font-size: 10px;">获得学位时间:</td>
			<td><input class="easyui-datebox" type="text" name="degree_time"
				style="height: 21px" data-options="prompt:'输入日期'"></input>
			</td>
		</tr>
		<tr style="line-height: 50px;">
			<td style="font-size: 10px;">院系:</td>
			<td><select class="easyui-combobox" name="school" style="width:150px;height: 21px;">
				<option value="XX">信息科学与技术学院</option>
				<option value="WY">外国语学院</option>
				<option value="LY">旅游与国土资源学院</option>
			</select>
			</td>
			<td style="align:center;font-size: 10px;">部门/教研室:</td>
			<td><select class="easyui-combobox" name="partment" style="width:150px;height: 21px;">
				<option value="JW">教务科</option>
				<option value="JY">教研科</option>
				<option value="JYS">教研室</option>
			</select>
			</td>
			
		</tr> -->
</div>
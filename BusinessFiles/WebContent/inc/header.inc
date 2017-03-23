<div  style="background:url(images/logo.png) no-repeat 0px 0px;background-size:cover;-moz-background-size: cover;width:100%;height:100%;" >
	<span style="margin-top:45px;float:right; padding-right:30px;font-size:16px;" class="head">欢迎您  ${session.loginteacher.name}! <a href="javascript:void(0)" id="editpass" style="font-size:16px;">修改密码</a><a	href="javascript:void(0);" style="margin-left:5px;" onClick="destroySession()" >安全退出</a> </span>
</div>

	<!--修改密码窗口-->
    <div id="w" class="easyui-window" closed="true" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 280px; height: 200px; padding: 5px;
        background: #fafafa;">
       <table cellpadding=3>
          	<tr>
                  <td>当前密码：</td>
                  <td><input id="nowPwd" type="Password"  /></td>
              </tr>
              <tr>
                  <td>新密码：</td>
                  <td><input id="newPwd" type="Password" /></td>
              </tr>
              <tr>
                  <td>确认密码：</td>
                  <td><input id="cfmPwd" type="Password" /></td>
              </tr>
                </table>
            <div style="text-align: right; height: 30px; line-height: 30px;">
                <a class="easyui-linkbutton" icon="icon-ok" onclick="serverLogin();" > 确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#w').window('close');">取消</a>
            </div>
    </div>
    <script>
    $('#editpass').click(function() {
     $('#w').window('open');
     $('#newPwd').val('');
     $('#cfmPwd').val();
    $('#nowPwd').val('');
 });
 
 function serverLogin() {
    var newpass = $('#newPwd').val();
    var rePass = $('#cfmPwd').val();
    var curPass = $('#nowPwd').val();
		if (curPass == '') {
        $.messager.alert('系统提示', '请输入当前密码！', 'warning');
        return false;
    }
    if (newpass == '') {
        $.messager.alert('系统提示', '请输入新密码！', 'warning');
        return false;
    }
    if (rePass == '') {
        $.messager.alert('系统提示', '请再一次输入密码！', 'warning');
        return false;
    }

    if (newpass!= rePass) {
        $.messager.alert('系统提示', '新密码与确认密码不一至！请重新输入', 'warning');
        return false;
    }

	$.ajax({
		url:'/BusinessFiles/editPwd',
		data:{
			newPwd:newpass,//$.md5(newpass),
			oldPwd:curPass//$.md5(curPass)
			},
		dataType : 'json',
		type:'post',
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		error : function(x) {
			$.messager.alert('提示', "密码修改失败！", 'error');
		},
		success:function(data){
			console.info(data);
			if(data.success){
				$.messager.alert('提示', "密码修改成功！", 'info');
				$('#newPwd').val('');
			     $('#cfmPwd').val();
			    $('#nowPwd').val('');
        		closePwd();
			}else{
				$.messager.alert('提示', "密码修改失败！"+data.msg, 'error');
			}
			
		}
	});
}
    </script>